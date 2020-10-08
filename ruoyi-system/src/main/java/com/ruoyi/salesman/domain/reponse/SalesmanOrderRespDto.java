package com.ruoyi.salesman.domain.reponse;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;

import java.math.BigDecimal;
import java.util.Date;

public class SalesmanOrderRespDto {

    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 任务编码 */
    @Excel(name = "任务代码")
    private String taskNo;

    /** 订单日期 */
    @Excel(name = "日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date orderDate;

    /** 店铺名 */
    @Excel(name = "店铺名称（非掌柜名）")
    private String shopName;

    /** 客服 */
    private String callCenter;

    private String pictureUrl;

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

    private String platformNickname;

    private String promotersUnitPriceRemark;

    private String salesmanCommitUrl;

    private Long salesmanLeaderUserId;

    private Long salesmanUserId;

    private String salesmanLeaderName;

    private Long merchantUserId;

    private String merchantUserName;

    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
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

    public String getPlatformUrl() {
        return platformUrl;
    }

    public void setPlatformUrl(String platformUrl) {
        this.platformUrl = platformUrl;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getUnitPriceRemark() {
        return unitPriceRemark;
    }

    public void setUnitPriceRemark(String unitPriceRemark) {
        this.unitPriceRemark = unitPriceRemark;
    }

    public String getSpecialRemarks() {
        return specialRemarks;
    }

    public void setSpecialRemarks(String specialRemarks) {
        this.specialRemarks = specialRemarks;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public BigDecimal getPromotersModifyUnitPrice() {
        return promotersModifyUnitPrice;
    }

    public void setPromotersModifyUnitPrice(BigDecimal promotersModifyUnitPrice) {
        this.promotersModifyUnitPrice = promotersModifyUnitPrice;
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

    public String getSalesmanLeaderName() {
        return salesmanLeaderName;
    }

    public void setSalesmanLeaderName(String salesmanLeaderName) {
        this.salesmanLeaderName = salesmanLeaderName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getSalesmanCommitUrl() {
        return salesmanCommitUrl;
    }

    public void setSalesmanCommitUrl(String salesmanCommitUrl) {
        this.salesmanCommitUrl = salesmanCommitUrl;
    }

    public String getPromotersUnitPriceRemark() {
        return promotersUnitPriceRemark;
    }

    public void setPromotersUnitPriceRemark(String promotersUnitPriceRemark) {
        this.promotersUnitPriceRemark = promotersUnitPriceRemark;
    }

    public Long getMerchantUserId() {
        return merchantUserId;
    }

    public void setMerchantUserId(Long merchantUserId) {
        this.merchantUserId = merchantUserId;
    }

    public String getMerchantUserName() {
        return merchantUserName;
    }

    public void setMerchantUserName(String merchantUserName) {
        this.merchantUserName = merchantUserName;
    }

    public Long getSalesmanUserId() {
        return salesmanUserId;
    }

    public void setSalesmanUserId(Long salesmanUserId) {
        this.salesmanUserId = salesmanUserId;
    }

    public String getCallCenter() {
        return callCenter;
    }

    public void setCallCenter(String callCenter) {
        this.callCenter = callCenter;
    }
}
