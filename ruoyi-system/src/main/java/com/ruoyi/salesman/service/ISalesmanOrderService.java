package com.ruoyi.salesman.service;

import com.ruoyi.salesman.domain.SalesmanTaskDetail;
import com.ruoyi.salesman.domain.reponse.SalesmanOrderRespDto;
import com.ruoyi.salesman.domain.request.SalesmanOrderReqDto;

import java.util.List;

public interface ISalesmanOrderService {
    /*** 订单列表 **/
    List<SalesmanOrderRespDto> selectSalesmanTaskDtoList(SalesmanOrderReqDto salesmanOrderReqDto);

    int updateSalesmanTaskDetail(SalesmanTaskDetail salesmanTaskDetail);

    int stopOrder(String ids);

    int recoverOrder(String ids);

    void doProcessReqParam(SalesmanOrderReqDto salesmanOrderReqDto);
}
