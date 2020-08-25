package com.ruoyi.promoters.application.domain.dto.request;


public class ApplicationReqDto {

    private static final long serialVersionUID = 1L;

    /** 申请人Id */
    private String applicantName;

    /** 关联状态（0已关联 1未关联 2申请中 3不通过） */
    private String associationStatus;

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getAssociationStatus() {
        return associationStatus;
    }

    public void setAssociationStatus(String associationStatus) {
        this.associationStatus = associationStatus;
    }
}
