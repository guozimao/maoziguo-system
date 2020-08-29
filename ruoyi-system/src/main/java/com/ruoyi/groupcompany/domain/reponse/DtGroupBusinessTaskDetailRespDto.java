package com.ruoyi.groupcompany.domain.reponse;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

public class DtGroupBusinessTaskDetailRespDto {

    private static final long serialVersionUID = 1L;

    /** 任务编码 */
    private String taskNo;

    private String shopName;

    /** 订单日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date orderDate;

    private BigDecimal unitPrice;

    private BigDecimal promotersModifyUnitPrice;

    private String keyword1;

    private String keyword2;

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

    public String getKeyword1() {
        return keyword1;
    }

    public void setKeyword1(String keyword1) {
        this.keyword1 = keyword1;
    }

    public String getKeyword2() {
        return keyword2;
    }

    public void setKeyword2(String keyword2) {
        this.keyword2 = keyword2;
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
}
