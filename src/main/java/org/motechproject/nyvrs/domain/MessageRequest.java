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

    @Field(required = true)
    private Integer retryCount;

    @Field(required = true)
    private MessageRequestStatus status;

    public MessageRequest(String callerId, Integer week) {
        this.retryCount = 0;
        this.callerId = callerId;
        this.week = week;
        this.status = MessageRequestStatus.NEW;
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

    public MessageRequestStatus getStatus() {
        return status;
    }

    public void setStatus(MessageRequestStatus status) {
        this.status = status;
    }
}
