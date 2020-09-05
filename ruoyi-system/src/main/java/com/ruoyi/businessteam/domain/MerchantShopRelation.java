package com.ruoyi.businessteam.domain;


public class MerchantShopRelation {
    private static final long serialVersionUID = 1L;
    private Long merchantUserId;
    private String shopName;

    public Long getMerchantUserId() {
        return merchantUserId;
    }

    public void setMerchantUserId(Long merchantUserId) {
        this.merchantUserId = merchantUserId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
