package com.ruoyi.groupcompany.domain.reponse;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

public class DtGroupBusinessTaskDetailRespDto {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long taskId;

    /** 任务编码 */
    private String taskNo;

    private String shopName;

    private String platformUrl;

    private String callCenter;

    /** 订单日期 */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date orderDate;

    private BigDecimal unitPrice;

    private BigDecimal promotersModifyUnitPrice;

    private String keyword;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getPromotersModifyUnitPrice() {
        return promotersModifyUnitPrice;
    }

    public void setPromotersModifyUnitPrice(BigDecimal promotersModifyUnitPrice) {
        this.promotersModifyUnitPrice = promotersModifyUnitPrice;
    }

    public String getPlatformUrl() {
        return platformUrl;
    }

    public void setPlatformUrl(String platformUrl) {
        this.platformUrl = platformUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCallCenter() {
        return callCenter;
    }

    public void setCallCenter(String callCenter) {
        this.callCenter = callCenter;
    }
}
