package com.ruoyi.salesman.domain.reponse;

public class CommitOrder {
    private static final long serialVersionUID = 1L;

    private String img;

    private String promotersUnitPriceRemark;

    private String platformNickname;

    private Long id;

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

    public String getPlatformNickname() {
        return platformNickname;
    }

    public void setPlatformNickname(String platformNickname) {
        this.platformNickname = platformNickname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
