package org.motechproject.nyvrs.web.domain;

import org.motechproject.nyvrs.domain.ChannelType;
import org.motechproject.nyvrs.domain.EducationLevel;

import java.util.ArrayList;
import java.util.List;

public class RegistrationRequest {

    private Long callerId;
    private Integer age;
    private Gender gender;
    private EducationLevel educationLevel;
    private ChannelType channel;

    public RegistrationRequest() {
    }

    public RegistrationRequest(String callerId, String age, String gender, String educationLevel, String channel) {
        this.callerId = Long.parseLong(callerId);
        this.age = Integer.parseInt(age);
        this.gender = Gender.valueOf(gender);
        this.educationLevel = EducationLevel.valueOf(educationLevel);
        this.channel = ChannelType.valueOf(channel);
    }

    public RegistrationRequest(Long callerId, Integer age, Gender gender, EducationLevel educationLevel,
                               ChannelType channel) {
        this.callerId = callerId;
        this.age = age;
        this.gender = gender;
        this.educationLevel = educationLevel;
        this.channel = channel;
    }

    public Long getCallerId() {
        return callerId;
    }

    public Integer getAge() {
        return age;
    }

    public Gender getGender() {
        return gender;
    }

    public EducationLevel getEducationLevel() {
        return educationLevel;
    }

    public ChannelType getChannel() {
        return channel;
    }

    public List<ValidationError> validate() {
        List<ValidationError> errors = new ArrayList<ValidationError>();

        if (callerId == null || callerId <= 0) {
            errors.add(new ValidationError("Invalid caller id"));
        }
        if (age < 15 || age > 24) {
            errors.add(new ValidationError("Age should be between 15 and 24"));
        }
        if (gender == null) {
            errors.add(new ValidationError("Invalid gender"));
        }
        if (educationLevel == null) {
            errors.add(new ValidationError("Invalid education level"));
        }
        if (channel == null) {
            errors.add(new ValidationError("Invalid channel"));
        }

        return errors;
    }

}
