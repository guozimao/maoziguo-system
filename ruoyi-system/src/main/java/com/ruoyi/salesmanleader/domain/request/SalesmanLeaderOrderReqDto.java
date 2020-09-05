package com.ruoyi.salesmanleader.domain.request;

import java.util.Date;

public class SalesmanLeaderOrderReqDto {

    private static final long serialVersionUID = 1L;

    private Date startDate;
    private Date endDate;
    private String shopName;
    private String platformNickname;

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
}
