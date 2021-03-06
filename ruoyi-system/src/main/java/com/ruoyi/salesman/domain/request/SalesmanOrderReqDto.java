package com.ruoyi.salesman.domain.request;

import java.util.Date;

public class SalesmanOrderReqDto {

    private static final long serialVersionUID = 1L;

    private Date startDate;
    private Date endDate;
    private String shopName;
    private String platformNickname;
    /** 地推员 */
    private String salesmanLeaderName;
    /** 地推员id */
    private Long salesmanLeaderUserId;

    private Long salesmanUserId;

    private String status;

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

    public Long getSalesmanUserId() {
        return salesmanUserId;
    }

    public void setSalesmanUserId(Long salesmanUserId) {
        this.salesmanUserId = salesmanUserId;
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
}
