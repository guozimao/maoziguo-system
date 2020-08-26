package com.ruoyi.promoters.application.domain.dto.response;

public class ApplicationRespDto {
    private static final long serialVersionUID = 1L;

    /** 申请ID */
    private Long id;

    /** 申请人name */
    private String applicantName;

    /** 申请人Id */
    private Long applicantId;

    /** 审核人name */
    private String approverName;

    /** 审核人id */
    private Long approverId;

    private Long deptId;

    /** 部门名称 */
    private String deptName;

    /** 关联状态（0已关联 1未关联 2申请中 3不通过） */
    private String associationStatus;

    private String hiddenTxt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getAssociationStatus() {
        return associationStatus;
    }

    public void setAssociationStatus(String associationStatus) {
        this.associationStatus = associationStatus;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public Long getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(Long applicantId) {
        this.applicantId = applicantId;
    }

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    public Long getApproverId() {
        return approverId;
    }

    public void setApproverId(Long approverId) {
        this.approverId = approverId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getHiddenTxt() {
        return hiddenTxt;
    }

    public void setHiddenTxt(String hiddenTxt) {
        this.hiddenTxt = hiddenTxt;
    }
}
