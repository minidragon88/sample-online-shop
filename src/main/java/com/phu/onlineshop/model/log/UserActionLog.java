package com.phu.onlineshop.model.log;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.phu.onlineshop.Utils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Map;

@Entity
@Table(name = "user_action_logs")
public class UserActionLog
{
    @Id
    private String uuid;
    private Long time;
    @Enumerated(EnumType.STRING)
    private Severity severity = Severity.INFO;
    private String username;
    private String action;
    @Column(length = 100000)
    private String data;

    public UserActionLog()
    {}

    public UserActionLog(final String uuid, final Severity severity, final String username, final String action, final String data)
    {
        this.time = System.currentTimeMillis();
        this.uuid = uuid;
        this.severity = severity;
        this.username = username;
        this.action = action;
        this.data = data;
    }

    public static UserActionLog newLog(final String uuid, final Severity severity, final Map<String, String> headers, final String action, final String data)
    {
        return new UserActionLog(uuid, severity, Utils.getCurrentUsername(headers), action, data);
    }

    public String getUuid()
    {
        return uuid;
    }

    public void setUuid(final String uuid)
    {
        this.uuid = uuid;
    }

    public Long getTime()
    {
        return time;
    }

    public void setTime(final Long time)
    {
        this.time = time;
    }

    public Severity getSeverity()
    {
        return severity;
    }

    public void setSeverity(final Severity severity)
    {
        this.severity = severity;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(final String username)
    {
        this.username = username;
    }

    public String getAction()
    {
        return action;
    }

    public void setAction(final String action)
    {
        this.action = action;
    }

    public String getData()
    {
        return data;
    }

    public void setData(final String data)
    {
        this.data = data;
    }

    public enum Severity
    {
        INFO("info"), WARN("warn"), ERROR("error");
        private final String severity;
        Severity(final String severity)
        {
            this.severity = severity;
        }

        @JsonCreator
        public static Severity fromString(final String value)
        {
            for (final Severity severity : Severity.values()) {
                if (severity.getSeverity().equalsIgnoreCase(value)) {
                    return severity;
                }
            }
            return Severity.INFO;
        }

        public String getSeverity()
        {
            return severity;
        }
    }
}
