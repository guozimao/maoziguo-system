package com.ruoyi.merchant.domain;

import com.ruoyi.common.annotation.Excel;

import java.math.BigDecimal;
import java.util.Date;

public class MerchantOrder {
    @Excel(name = "下单日期", type = Excel.Type.EXPORT, dateFormat = "yyyy-MM-dd")
    private Date orderDate;
    @Excel(name = "店铺名称", type = Excel.Type.EXPORT)
    private String shopName;
    @Excel(name = "会员名", type = Excel.Type.EXPORT)
    private String platformNickname;
    @Excel(name = "实付款", type = Excel.Type.EXPORT)
    private BigDecimal promotersModifyUnitPrice;
    @Excel(name = "佣金", type = Excel.Type.EXPORT)
    private BigDecimal commission;
    @Excel(name = "流量费", type = Excel.Type.EXPORT)
    private BigDecimal flowFee;

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

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public BigDecimal getFlowFee() {
        return flowFee;
    }

    public void setFlowFee(BigDecimal flowFee) {
        this.flowFee = flowFee;
    }
}
