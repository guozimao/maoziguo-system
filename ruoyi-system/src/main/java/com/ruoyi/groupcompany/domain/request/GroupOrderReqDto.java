package com.ruoyi.groupcompany.domain.request;

import java.util.Date;

public class GroupOrderReqDto {

    private static final long serialVersionUID = 1L;

    private Date startDate;
    private Date endDate;
    private String shopName;
    private String platformNickname;
    /** 地推员 */
    private String salesmanLeaderName;
    /** 地推员id */
    private Long salesmanLeaderUserId;
    /** 商家 */
    private String merchantName;
    /** 商家id */
    private Long merchantUserId;

    private String status;
    /** 00系统用户 01业务组长 02业务员 03商家 */
    private String exportType;

    private Long taskId;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getPlatformNickname() {
        return platformNickname;
    }

    public void setPlatformNickname(String platformNickname) {
        this.platformNickname = platformNickname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSalesmanLeaderName() {
        return salesmanLeaderName;
    }

    public void setSalesmanLeaderName(String salesmanLeaderName) {
        this.salesmanLeaderName = salesmanLeaderName;
    }

    public Long getSalesmanLeaderUserId() {
        return salesmanLeaderUserId;
    }

    public void setSalesmanLeaderUserId(Long salesmanLeaderUserId) {
        this.salesmanLeaderUserId = salesmanLeaderUserId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public Long getMerchantUserId() {
        return merchantUserId;
    }

    public void setMerchantUserId(Long merchantUserId) {
        this.merchantUserId = merchantUserId;
    }

    public String getExportType() {
        return exportType;
    }

    public void setExportType(String exportType) {
        this.exportType = exportType;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
}
