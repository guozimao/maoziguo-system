package com.ruoyi.repurchase.domain;


import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MemberConsumptionTrack {
    private static final long serialVersionUID = 1L;

    private Long id;

    @Excel(name = "任务编号", type = Excel.Type.IMPORT)
    private String taskNo;

    @Excel(name = "下单日期")
    private String orderDate;

    @Excel(name = "店铺名称")
    private String storeName;

    @Excel(name = "淘宝会员名")
    private String memberName;

    @Excel(name = "实付款", cellType = Excel.ColumnType.NUMERIC, type = Excel.Type.IMPORT)
    private BigDecimal actualFee;

    @Excel(name = "数量", cellType = Excel.ColumnType.NUMERIC, type = Excel.Type.IMPORT)
    private int total;

    @Excel(name = "文字备注", type = Excel.Type.IMPORT)
    private String note;

    @Excel(name = "每组应付金额", cellType = Excel.ColumnType.NUMERIC, type = Excel.Type.IMPORT)
    private BigDecimal shouldFee;

    @Excel(name = "单链接对应价格佣金(根据应付金额)", cellType = Excel.ColumnType.NUMERIC, type = Excel.Type.IMPORT)
    private BigDecimal commission;

    @Excel(name = "地推人员")
    private String promoters;

    @Excel(name = "登记人", type = Excel.Type.IMPORT)
    private String registrant;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public BigDecimal getActualFee() {
        return actualFee;
    }

    public void setActualFee(BigDecimal actualFee) {
        this.actualFee = actualFee;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public BigDecimal getShouldFee() {
        return shouldFee;
    }

    public void setShouldFee(BigDecimal shouldFee) {
        this.shouldFee = shouldFee;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public String getPromoters() {
        return promoters;
    }

    public void setPromoters(String promoters) {
        this.promoters = promoters;
    }

    public String getRegistrant() {
        return registrant;
    }

    public void setRegistrant(String registrant) {
        this.registrant = registrant;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
