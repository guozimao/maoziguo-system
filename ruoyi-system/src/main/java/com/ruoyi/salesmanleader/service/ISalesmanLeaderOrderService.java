package com.ruoyi.salesmanleader.service;

import com.ruoyi.salesmanleader.domain.SalesmanLeaderTaskDetail;
import com.ruoyi.salesmanleader.domain.reponse.SalesmanLeaderOrderRespDto;
import com.ruoyi.salesmanleader.domain.request.SalesmanLeaderOrderReqDto;
import com.ruoyi.system.domain.SysUser;

import java.util.List;

public interface ISalesmanLeaderOrderService {
    /*** 订单列表 **/
    List<SalesmanLeaderOrderRespDto> selectSalesmanLeaderTaskDtoList(SalesmanLeaderOrderReqDto groupOrderReqDto, SysUser user);

    int updateDtsalesmanLeaderTaskDetail(SalesmanLeaderTaskDetail salesmanLeaderTaskDetail);

    int stopOrder(String ids);

    int recoverOrder(String ids);

    SysUser getUserBySalesManUserName(String salesmanUserName);

    int retreatOrder(Long[] toLongArray);
}
