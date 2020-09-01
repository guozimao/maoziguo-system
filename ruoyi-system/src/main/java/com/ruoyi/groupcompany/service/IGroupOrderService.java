package com.ruoyi.groupcompany.service;

import com.ruoyi.groupcompany.domain.DtBusinessTaskDetail;
import com.ruoyi.groupcompany.domain.reponse.GroupOrderRespDto;
import com.ruoyi.groupcompany.domain.request.GroupOrderReqDto;

import java.util.List;

public interface IGroupOrderService {
    /*** 订单列表 **/
    List<GroupOrderRespDto> selectGroupDtBusinessTaskDtoList(GroupOrderReqDto groupOrderReqDto);

    int updateDtBusinessTaskDetail(DtBusinessTaskDetail dtBusinessTaskDetail);

    int stopOrder(String ids);

    int recoverOrder(String ids);
}
