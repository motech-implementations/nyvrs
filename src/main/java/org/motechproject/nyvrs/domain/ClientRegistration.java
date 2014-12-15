package org.motechproject.nyvrs.domain;

import org.motechproject.mds.annotations.Entity;
import org.motechproject.mds.annotations.Field;
import org.motechproject.mds.domain.MdsEntity;
import org.motechproject.nyvrs.web.domain.RegistrationRequest;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.Unique;

@Entity
public class ClientRegistration extends MdsEntity {

    @Unique
    @Column(length = 20)
    @Field(name = "client_number", required = true)
    private String number;

    @Column(length = 2)
    @Field(required = true, name = "client_gender")
    private String gender;

    @Column(length = 3)
    @Field(required = true, name = "client_age")
    private String age;

    @Column(length = 5)
    @Field(required=true, name = "client_education_level")
    private EducationLevel educationLevel;

    @Column(length = 117)
    @Field(name = "client_location")
    private String location;

    @Column(length = 20)
    @Field(required = true)
    private StatusType status;

    @Column(length = 50)
    @Field(required = true)
    private ChannelType channel;

    @Column(length = 50)
    @Field(name = "sms_confirm")
    private String smsConfirmation;

    @Column(length = 75, allowsNull = "false")
    @Field(name = "client_region")
    private String region;

    @Column(length = 50)
    @Field(defaultValue = "English", name = "client_language")
    private String language;

    @Field(type = "text")
    private String excel;

    @Field(type = "text")
    private String source;

    @Column(length = 11)
    @Field(defaultValue = "1")
    private Integer nyWeeks;

    @Field(required=true, name = "campaignid", type = "text")
    private CampaignType campaignType;

    public ClientRegistration() {
    }

    public ClientRegistration(String number, String gender, String age, EducationLevel educationLevel,
                              ChannelType channel) {
        this.number = number;
        this.gender = gender;
        this.age = age;
        this.educationLevel = educationLevel;
        this.channel = channel;

        if (Integer.parseInt(age) > 19) {
            campaignType = CampaignType.RITA;
        } else if (educationLevel == EducationLevel.NA) {
            campaignType = CampaignType.KIKI;
        } else {
            campaignType = CampaignType.RONALD;
        }
        this.status = StatusType.Incomplete;
    }

    public ClientRegistration(String number, String gender, String age, EducationLevel educationLevel, String location,
                              StatusType status, ChannelType channel, String smsConfirmation, String region,
                              String language, String excel, String source, Integer nyWeeks, CampaignType campaignType) {
        this.number = number;
        this.gender = gender;
        this.age = age;
        this.educationLevel = educationLevel;
        this.location = location;
        this.status = status;
        this.channel = channel;
        this.smsConfirmation = smsConfirmation;
        this.region = region;
        this.language = language;
        this.excel = excel;
        this.source = source;
        this.nyWeeks = nyWeeks;
        this.campaignType = campaignType;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public EducationLevel getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(EducationLevel educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public ChannelType getChannel() {
        return channel;
    }

    public void setChannel(ChannelType channel) {
        this.channel = channel;
    }

    public String getSmsConfirmation() {
        return smsConfirmation;
    }

    public void setSmsConfirmation(String smsConfirmation) {
        this.smsConfirmation = smsConfirmation;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getExcel() {
        return excel;
    }

    public void setExcel(String excel) {
        this.excel = excel;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getNyWeeks() {
        return nyWeeks;
    }

    public void setNyWeeks(Integer nyWeeks) {
        this.nyWeeks = nyWeeks;
    }

    public CampaignType getCampaignType() {
        return campaignType;
    }

    public void setCampaignType(CampaignType campaignType) {
        this.campaignType = campaignType;
    }
}
