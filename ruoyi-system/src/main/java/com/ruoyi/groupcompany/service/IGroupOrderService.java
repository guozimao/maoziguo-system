package com.ruoyi.groupcompany.service;

import com.ruoyi.groupcompany.domain.DtBusinessTaskDetail;

import java.util.List;

public interface IGroupOrderService {
    /*** 订单列表 **/
    List<DtBusinessTaskDetail> selectGroupDtBusinessTaskDtoList(DtBusinessTaskDetail dtBusinessTaskDetail);
}
