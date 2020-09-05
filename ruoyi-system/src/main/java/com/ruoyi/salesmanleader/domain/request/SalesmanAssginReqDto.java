package com.ruoyi.salesmanleader.domain.request;


public class SalesmanAssginReqDto {

    private static final long serialVersionUID = 1L;

    private String ids;

    private Long deptId;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }
}
