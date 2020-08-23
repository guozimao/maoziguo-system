package com.ruoyi.businessteam.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;

import java.math.BigDecimal;
import java.util.Date;

public class SalesManRespDto{

    /** 业务员id  **/
    private Long id;

    private Long userId;

    /** 佣金 */
    private BigDecimal commission;

    /** 接单上限 */
    private Long receivingLimit;

    /** 关联状态（0已关联 1未关联） */
    private String associationStatus;

    /** 登录名称 */
    private String loginName;

    /** 用户名称 */
    private String userName;

    private String phonenumber;

    private String email;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 帐号状态（0正常 1停用） */
    @Excel(name = "帐号状态", readConverterExp = "0=正常,1=停用")
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public Long getReceivingLimit() {
        return receivingLimit;
    }

    public void setReceivingLimit(Long receivingLimit) {
        this.receivingLimit = receivingLimit;
    }

    public String getAssociationStatus() {
        return associationStatus;
    }

    public void setAssociationStatus(String associationStatus) {
        this.associationStatus = associationStatus;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

}
