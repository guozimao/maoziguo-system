package com.ruoyi.groupcompany.domain.request;


public class SupplementOrderReqDto {

    private static final long serialVersionUID = 1L;

    private Long taskId;

    private Long orderId;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
