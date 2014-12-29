package org.motechproject.nyvrs.domain;

import org.motechproject.mds.annotations.Entity;
import org.motechproject.mds.annotations.Field;
import org.motechproject.mds.domain.MdsEntity;

@Entity
public class MessageRequest extends MdsEntity {

    @Field(required = true)
    private String callerId;

    @Field(required = true)
    private Integer week;

    @Field
    private Integer retryCount;

    public MessageRequest(String callerId, Integer week, Integer retryCount) {
        this.retryCount = retryCount;
        this.callerId = callerId;
        this.week = week;
    }

    public String getCallerId() {
        return callerId;
    }

    public void setCallerId(String callerId) {
        this.callerId = callerId;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public Integer getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }
}
