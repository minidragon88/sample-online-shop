package com.phu.onlineshop.service;

import com.phu.onlineshop.Utils;
import com.phu.onlineshop.model.log.UserActionLog;
import com.phu.onlineshop.repository.LogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
@Lazy(false)
@Transactional
public class LogService
{
    private static final ConcurrentLinkedQueue<UserActionLog> LOG_QUEUE = new ConcurrentLinkedQueue<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(LogService.class);
    private static final List<String> IDDLE_MESSAGES = Arrays.asList(
            "Hello darkness my old friends ...",
            "Log is my love ...",
            "Log, where are you now?",
            "I'm hungry, please give me logs",
            "To talk about my life, only one word - Log ...",
            "Once upon a time, there was a service prince to wait for the log princess ...",
            "Baby shark doo doo doo doo ...");
    private static final Random RANDOM = new Random();

    @Autowired
    LogRepository repository;
    @Autowired
    EntityManager entityManager;
    @Autowired
    PlatformTransactionManager transactionManager;

    public void addLog(final UserActionLog log)
    {
        LOGGER.info("Added log to queue");
        LOG_QUEUE.add(log);
    }

    @Scheduled(fixedDelay = Utils.LOG_FLUSH_DURATION)
    public void flushLogs()
    {
        if (LOG_QUEUE.isEmpty()) {
            LOGGER.info(IDDLE_MESSAGES.get(RANDOM.nextInt(IDDLE_MESSAGES.size())));
            return;
        }
        final List<UserActionLog> logs = new ArrayList<>();
        synchronized (this) {
            logs.addAll(LOG_QUEUE);
            LOG_QUEUE.clear();
        }
        int attemp = 0;
        boolean proceed = true;
        LOGGER.info("Begin flushLogs. Size {}", logs.size());
        do {
            final DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.ISOLATION_READ_COMMITTED);
            final TransactionStatus status = transactionManager.getTransaction(def);
            try {
                attemp++;
                repository.saveAll(logs);
                transactionManager.commit(status);
                proceed = false;
            }
            catch (final Exception ex) {
                transactionManager.rollback(status);
                if (attemp < Utils.RUNTIME_CONFIGURATION.getMaxLogFlushAttemp()) {
                    proceed = true;
                    LOGGER.warn("Retrying flushing log. Errors {}", ex.getMessage());
                }
                else {
                    proceed = false;
                    LOGGER.error("Give up flushLogs. Errors {}. Lost data size {}", ex.getMessage(), logs.size());
                }
            }
        } while (proceed);
        LOGGER.info("flushLogs successfully. Size {}", logs.size());
    }

    public List<UserActionLog> findAll()
    {
        return repository.findAll();
    }
}
