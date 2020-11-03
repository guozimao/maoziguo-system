package com.ruoyi.salesman.domain.reponse;

import java.math.BigDecimal;

public class CommitOrder {
    private static final long serialVersionUID = 1L;

    private String img;

    private String promotersUnitPriceRemark;

    private BigDecimal promotersModifyUnitPrice;

    private Long id;

    private String commitOrderPicBase64;

    public String getCommitOrderPicBase64() {
        return commitOrderPicBase64;
    }

    public void setCommitOrderPicBase64(String commitOrderPicBase64) {
        this.commitOrderPicBase64 = commitOrderPicBase64;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPromotersUnitPriceRemark() {
        return promotersUnitPriceRemark;
    }

    public void setPromotersUnitPriceRemark(String promotersUnitPriceRemark) {
        this.promotersUnitPriceRemark = promotersUnitPriceRemark;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPromotersModifyUnitPrice() {
        return promotersModifyUnitPrice;
    }

    public void setPromotersModifyUnitPrice(BigDecimal promotersModifyUnitPrice) {
        this.promotersModifyUnitPrice = promotersModifyUnitPrice;
    }
}
