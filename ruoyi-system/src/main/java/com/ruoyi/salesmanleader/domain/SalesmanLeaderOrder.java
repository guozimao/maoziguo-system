package com.ruoyi.salesmanleader.domain;

import com.ruoyi.common.annotation.Excel;

import java.math.BigDecimal;
import java.util.Date;

public class SalesmanLeaderOrder {
    @Excel(name = "任务编号", type = Excel.Type.EXPORT)
    private Long taskId;
    @Excel(name = "下单日期", type = Excel.Type.EXPORT, dateFormat = "yyyy-MM-dd")
    private Date orderDate;
    @Excel(name = "店铺名称", type = Excel.Type.EXPORT)
    private String shopName;
    @Excel(name = "淘宝会员名", type = Excel.Type.EXPORT)
    private String platformNickname;
    @Excel(name = "实付款", type = Excel.Type.EXPORT)
    private BigDecimal promotersModifyUnitPrice;
    @Excel(name = "数量", type = Excel.Type.EXPORT)
    private Integer  amount;
    @Excel(name = "差价", type = Excel.Type.EXPORT)
    private BigDecimal priceDifferences;
    @Excel(name = "文字备注", type = Excel.Type.EXPORT)
    private String groupCompanyRemark;
    @Excel(name = "每组应付金额", type = Excel.Type.EXPORT)
    private BigDecimal unitPrice;
    @Excel(name = "地推人员", type = Excel.Type.EXPORT)
    private String salesmanLeaderUserName;
    private Long salesmanLeaderUserId;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
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

    public BigDecimal getPromotersModifyUnitPrice() {
        return promotersModifyUnitPrice;
    }

    public void setPromotersModifyUnitPrice(BigDecimal promotersModifyUnitPrice) {
        this.promotersModifyUnitPrice = promotersModifyUnitPrice;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getPriceDifferences() {
        return priceDifferences;
    }

    public void setPriceDifferences(BigDecimal priceDifferences) {
        this.priceDifferences = priceDifferences;
    }

    public String getGroupCompanyRemark() {
        return groupCompanyRemark;
    }

    public void setGroupCompanyRemark(String groupCompanyRemark) {
        this.groupCompanyRemark = groupCompanyRemark;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getSalesmanLeaderUserName() {
        return salesmanLeaderUserName;
    }

    public void setSalesmanLeaderUserName(String salesmanLeaderUserName) {
        this.salesmanLeaderUserName = salesmanLeaderUserName;
    }

    public Long getSalesmanLeaderUserId() {
        return salesmanLeaderUserId;
    }

    public void setSalesmanLeaderUserId(Long salesmanLeaderUserId) {
        this.salesmanLeaderUserId = salesmanLeaderUserId;
    }
}
