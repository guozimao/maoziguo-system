package com.ruoyi.groupcompany.domain.request;

import com.ruoyi.common.annotation.Excel;

import java.util.Date;

public class DtGroupBusinessTaskReqDto {

    private static final long serialVersionUID = 1L;

    /** 部门id */
    @Excel(name = "部门id")
    private Long deptId;

    /** 订单状态（0 完成 1 待完成） */
    @Excel(name = "订单状态", readConverterExp = "0=,完=成,1=,待=完成")
    private String orderStatus;

    /** 订单日期 */
    @Excel(name = "订单生成日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
