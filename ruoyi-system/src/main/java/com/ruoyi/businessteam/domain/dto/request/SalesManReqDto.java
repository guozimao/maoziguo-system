package com.ruoyi.businessteam.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;

import java.math.BigDecimal;
import java.util.Date;

public class SalesManReqDto {

    private Long id;

    private Long userId;

    /** 登录名称 */
    private String loginName;

    /** 用户名称 */
    private String userName;

    private String phonenumber;

    private String email;

    private Integer status;

    private BigDecimal commission;

    private Integer receivingLimit;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 关联状态（0已关联 1未关联） */
    private String associationStatus;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAssociationStatus() {
        return associationStatus;
    }

    public void setAssociationStatus(String associationStatus) {
        this.associationStatus = associationStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public Integer getReceivingLimit() {
        return receivingLimit;
    }

    public void setReceivingLimit(Integer receivingLimit) {
        this.receivingLimit = receivingLimit;
    }
}
