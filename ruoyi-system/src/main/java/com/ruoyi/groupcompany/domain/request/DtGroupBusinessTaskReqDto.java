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

    /** 任务编码 */
    @Excel(name = "任务编码")
    private String taskNo;

    /** 订单日期 */
    @Excel(name = "订单日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date orderDate;

    /** 店铺名 */
    @Excel(name = "店铺名")
    private String shopName;

    private String platformNickname;

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
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

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPlatformNickname() {
        return platformNickname;
    }

    public void setPlatformNickname(String platformNickname) {
        this.platformNickname = platformNickname;
    }

}
