package com.ruoyi.salesmanleader.service;

import com.ruoyi.salesmanleader.domain.SalesmanLeaderTaskDetail;
import com.ruoyi.salesmanleader.domain.reponse.SalesmanLeaderOrderRespDto;
import com.ruoyi.salesmanleader.domain.request.SalesmanLeaderOrderReqDto;

import java.util.List;

public interface ISalesmanOrderService {
    /*** 订单列表 **/
    List<SalesmanLeaderOrderRespDto> selectGroupDtBusinessTaskDtoList(SalesmanLeaderOrderReqDto groupOrderReqDto);

    int updateDtBusinessTaskDetail(SalesmanLeaderTaskDetail dtBusinessTaskDetail);

    int stopOrder(String ids);

    int recoverOrder(String ids);
}
