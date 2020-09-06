package com.ruoyi.salesmanleader.domain.request;


public class AssginSalesmanReqDto {

    private static final long serialVersionUID = 1L;

    private String ids;

    private Long salesmanUserId;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public Long getSalesmanUserId() {
        return salesmanUserId;
    }

    public void setSalesmanUserId(Long salesmanUserId) {
        this.salesmanUserId = salesmanUserId;
    }
}
