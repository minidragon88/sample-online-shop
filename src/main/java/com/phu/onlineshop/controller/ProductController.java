package com.phu.onlineshop.controller;

import com.phu.onlineshop.APIResponse;
import com.phu.onlineshop.Utils;
import com.phu.onlineshop.model.log.UserActionLog;
import com.phu.onlineshop.model.log.UserActionLog.Severity;
import com.phu.onlineshop.model.product.Product;
import com.phu.onlineshop.model.product.SearchMessage;
import com.phu.onlineshop.model.product.SearchMessage.Column;
import com.phu.onlineshop.model.product.SearchMessage.Operator;
import com.phu.onlineshop.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController
{
    @Autowired
    private EntityManager entityManager;
    @Autowired
    LogService logService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @PostMapping("/search")
    public ResponseEntity<APIResponse<List<Product>>> findAll(@RequestHeader final Map<String, String> headers, @RequestBody final SearchMessage message)
    {
        final String endpoint = "/product/search";
        final String uuid = UUID.randomUUID().toString();
        final UserActionLog log = UserActionLog.newLog(uuid, Severity.INFO, headers, endpoint, message.toString());
        logService.addLog(log);
        LOGGER.info("Request {} search {} with body {}", uuid, endpoint, message);
        final Column column = message.getColumn();
        final Operator operator = message.getOperator();
        final List<Operator> allowOperators = column.getAllowOperators();
        if (!allowOperators.contains(operator)) {
            final String errorMessage = String.format(
                    "Filtering by method \"%s\" is not allowed in column \"%s\". Supported operators %s",
                    operator.getName(),
                    column.getColumn(),
                    allowOperators.stream().map(allowOperator -> allowOperator.getName()).collect(Collectors.toList()));
            return new APIResponse<List<Product>>(HttpStatus.BAD_REQUEST.value(), errorMessage, null).toResponseEntity();
        }
        if (message.getPage() < 0) {
            final String errorMessage = "Page should start from 0";
            return new APIResponse<List<Product>>(HttpStatus.BAD_REQUEST.value(), errorMessage, null).toResponseEntity();
        }
        final List<Product> results = Utils.buildProductSearchQuery(entityManager, message).getResultList();
        if (results.isEmpty()) {
            LOGGER.info("Request {} ends up with no results", uuid);
            return ResponseEntity.noContent().build();
        }
        return new APIResponse<List<Product>>(HttpStatus.OK.value(), null, results).toResponseEntity();
    }
}
