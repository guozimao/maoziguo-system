package com.ruoyi.salesman.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商业任务信息明细对象 dt_business_task_detail
 * 
 * @author zimao
 * @date 2020-08-27
 */
public class SalesmanTaskDetail
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 任务主表id */
    private Long taskId;

    /** 任务编码 */
    @Excel(name = "任务代码")
    private String taskNo;

    /** 订单日期 */
    @Excel(name = "日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date orderDate;

    /** 图片url */
    @Excel(name = "主图oss参数")
    private String pictureUrl;

    /** 店铺名 */
    @Excel(name = "店铺名称（非掌柜名）")
    private String shopName;

    /** 客服 */
    private String callCenter;

    @Excel(name = "链接")
    private String platformUrl;

    /** 单价/元 */
    @Excel(name = "单价/元")
    private BigDecimal unitPrice;

    /** 备注 */
    @Excel(name = "单价备注")
    private String unitPriceRemark;

    /** 特殊备注 */
    @Excel(name = "特殊备注")
    private String specialRemarks;

    /** 关键字 */
    @Excel(name = "关键词")
    private String keyword;

    /** 地推员修改的单价/元 */
    private BigDecimal promotersModifyUnitPrice;

    /** 地推员备注 */
    private String promotersUnitPriceRemark;

    private BigDecimal commission;

    private String platformNickname;

    private String hasNicknameVerification;

    private Long salesmanLeaderUserId;

    private Long merchantUserId;

    private String merchatUserName;

    private String salesmanCommitUrl;

    private String status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTaskId(Long taskId) 
    {
        this.taskId = taskId;
    }

    public Long getTaskId() 
    {
        return taskId;
    }
    public void setTaskNo(String taskNo) 
    {
        this.taskNo = taskNo;
    }

    public String getTaskNo() 
    {
        return taskNo;
    }
    public void setOrderDate(Date orderDate) 
    {
        this.orderDate = orderDate;
    }

    public Date getOrderDate() 
    {
        return orderDate;
    }
    public void setPictureUrl(String pictureUrl) 
    {
        this.pictureUrl = pictureUrl;
    }

    public String getPictureUrl() 
    {
        return pictureUrl;
    }
    public void setShopName(String shopName) 
    {
        this.shopName = shopName;
    }

    public String getShopName() 
    {
        return shopName;
    }
    public void setUnitPrice(BigDecimal unitPrice) 
    {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getUnitPrice() 
    {
        return unitPrice;
    }
    public void setUnitPriceRemark(String unitPriceRemark) 
    {
        this.unitPriceRemark = unitPriceRemark;
    }

    public String getUnitPriceRemark() 
    {
        return unitPriceRemark;
    }
    public void setSpecialRemarks(String specialRemarks) 
    {
        this.specialRemarks = specialRemarks;
    }

    public String getSpecialRemarks() 
    {
        return specialRemarks;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setPromotersModifyUnitPrice(BigDecimal promotersModifyUnitPrice)
    {
        this.promotersModifyUnitPrice = promotersModifyUnitPrice;
    }

    public BigDecimal getPromotersModifyUnitPrice() 
    {
        return promotersModifyUnitPrice;
    }
    public void setPromotersUnitPriceRemark(String promotersUnitPriceRemark) 
    {
        this.promotersUnitPriceRemark = promotersUnitPriceRemark;
    }

    public String getPromotersUnitPriceRemark() 
    {
        return promotersUnitPriceRemark;
    }

    public String getPlatformUrl() {
        return platformUrl;
    }

    public void setPlatformUrl(String platformUrl) {
        this.platformUrl = platformUrl;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public String getPlatformNickname() {
        return platformNickname;
    }

    public void setPlatformNickname(String platformNickname) {
        this.platformNickname = platformNickname;
    }

    public Long getSalesmanLeaderUserId() {
        return salesmanLeaderUserId;
    }

    public void setSalesmanLeaderUserId(Long salesmanLeaderUserId) {
        this.salesmanLeaderUserId = salesmanLeaderUserId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSalesmanCommitUrl() {
        return salesmanCommitUrl;
    }

    public void setSalesmanCommitUrl(String salesmanCommitUrl) {
        this.salesmanCommitUrl = salesmanCommitUrl;
    }

    public Long getMerchantUserId() {
        return merchantUserId;
    }

    public void setMerchantUserId(Long merchantUserId) {
        this.merchantUserId = merchantUserId;
    }

    public String getMerchatUserName() {
        return merchatUserName;
    }

    public void setMerchatUserName(String merchatUserName) {
        this.merchatUserName = merchatUserName;
    }

    public String getCallCenter() {
        return callCenter;
    }

    public void setCallCenter(String callCenter) {
        this.callCenter = callCenter;
    }

    public String getHasNicknameVerification() {
        return hasNicknameVerification;
    }

    public void setHasNicknameVerification(String hasNicknameVerification) {
        this.hasNicknameVerification = hasNicknameVerification;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("taskId", getTaskId())
            .append("taskNo", getTaskNo())
            .append("orderDate", getOrderDate())
            .append("pictureUrl", getPictureUrl())
            .append("shopName", getShopName())
            .append("unitPrice", getUnitPrice())
            .append("unitPriceRemark", getUnitPriceRemark())
            .append("specialRemarks", getSpecialRemarks())
            .append("promotersModifyUnitPrice", getPromotersModifyUnitPrice())
            .append("promotersUnitPriceRemark", getPromotersUnitPriceRemark())
            .toString();
    }
}
