package com.ruoyi.salesmanleader.domain.reponse;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class SalesmanLeaderTaskRespDto {

    private static final long serialVersionUID = 1L;

    private Long id;

    /** 订单数量 */
    private Integer orderNumber;
    /** 总本金 */
    private BigDecimal totalPrincipal;
    /** 实际总本金 */
    private BigDecimal actualTotalPrincipal;

    private String salesmanName;

    /** 订单状态（0 完成 1 待完成） */
    private String orderStatus;

    /** 分配时间 */
    private Date allocateTime;

    /** 完成时间 */
    private Date completionTime;

    /** 生成时间 */
    private Date createTime;

    private List<SalesmanLeaderTaskDetailRespDto> salesmanLeaderTaskDetailRespDtoList;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public BigDecimal getTotalPrincipal() {
        return totalPrincipal;
    }

    public void setTotalPrincipal(BigDecimal totalPrincipal) {
        this.totalPrincipal = totalPrincipal;
    }

    public BigDecimal getActualTotalPrincipal() {
        return actualTotalPrincipal;
    }

    public void setActualTotalPrincipal(BigDecimal actualTotalPrincipal) {
        this.actualTotalPrincipal = actualTotalPrincipal;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(Date completionTime) {
        this.completionTime = completionTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    public void setSalesmanName(String salesmanName) {
        this.salesmanName = salesmanName;
    }

    public Date getAllocateTime() {
        return allocateTime;
    }

    public void setAllocateTime(Date allocateTime) {
        this.allocateTime = allocateTime;
    }

    public List<SalesmanLeaderTaskDetailRespDto> getSalesmanLeaderTaskDetailRespDtoList() {
        return salesmanLeaderTaskDetailRespDtoList;
    }

    public void setSalesmanLeaderTaskDetailRespDtoList(List<SalesmanLeaderTaskDetailRespDto> salesmanLeaderTaskDetailRespDtoList) {
        this.salesmanLeaderTaskDetailRespDtoList = salesmanLeaderTaskDetailRespDtoList;
    }
}
