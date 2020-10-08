package com.ruoyi.salesman.domain.reponse;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class SalesmanTaskRespDto {

    private static final long serialVersionUID = 1L;

    private Long id;

    /** 部门id */
    private Long deptId;

    private String deptName;

    /** 订单数量 */
    private Integer orderNumber;
    /** 总本金 */
    private BigDecimal totalPrincipal;
    /** 实际总本金 */
    private BigDecimal actualTotalPrincipal;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date requiredCompletionDate;

    /** 订单状态（0 完成 1 待完成） */
    private String orderStatus;

    /** 集团分配时间 */
    private Date groupAllocateTime;

    /** 完成时间 */
    private Date completionTime;

    /** 生成时间 */
    private Date createTime;

    private List<SalesmanTaskDetailRespDto> salesmanTaskDetailRespDtos;


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

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Date getGroupAllocateTime() {
        return groupAllocateTime;
    }

    public void setGroupAllocateTime(Date groupAllocateTime) {
        this.groupAllocateTime = groupAllocateTime;
    }

    public Date getRequiredCompletionDate() {
        return requiredCompletionDate;
    }

    public void setRequiredCompletionDate(Date requiredCompletionDate) {
        this.requiredCompletionDate = requiredCompletionDate;
    }

    public List<SalesmanTaskDetailRespDto> getSalesmanTaskDetailRespDtos() {
        return salesmanTaskDetailRespDtos;
    }

    public void setSalesmanTaskDetailRespDtos(List<SalesmanTaskDetailRespDto> salesmanTaskDetailRespDtos) {
        this.salesmanTaskDetailRespDtos = salesmanTaskDetailRespDtos;
    }
}
