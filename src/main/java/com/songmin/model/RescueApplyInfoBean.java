package com.songmin.model;

public class RescueApplyInfoBean {
    private String rescueApplyId;
    private String userId;
    private String typeCode;
    private String rescueAddress;
    private String photos;
    private int healthCode;
    private String contact;

    public String getRescueApplyId() {
        return rescueApplyId;
    }

    public void setRescueApplyId(String rescueApplyId) {
        this.rescueApplyId = rescueApplyId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getRescueAddress() {
        return rescueAddress;
    }

    public void setRescueAddress(String rescueAddress) {
        this.rescueAddress = rescueAddress;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public int getHealthCode() {
        return healthCode;
    }

    public void setHealthCode(int healthCode) {
        this.healthCode = healthCode;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
