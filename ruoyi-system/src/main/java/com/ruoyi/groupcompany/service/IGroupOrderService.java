package com.ruoyi.groupcompany.service;

import com.ruoyi.groupcompany.domain.DtBusinessTaskDetail;
import com.ruoyi.groupcompany.domain.GroupOrder;
import com.ruoyi.groupcompany.domain.reponse.GroupOrderRespDto;
import com.ruoyi.groupcompany.domain.request.GroupOrderReqDto;
import com.ruoyi.groupcompany.domain.request.SupplementOrderReqDto;
import com.ruoyi.merchant.domain.MerchantOrder;
import com.ruoyi.salesmanleader.domain.SalesmanLeaderOrder;

import java.net.URL;
import java.util.List;

public interface IGroupOrderService {
    /*** 订单列表 **/
    List<GroupOrderRespDto> selectGroupDtBusinessTaskDtoList(GroupOrderReqDto groupOrderReqDto);

    int updateDtBusinessTaskDetail(DtBusinessTaskDetail dtBusinessTaskDetail);

    int stopOrder(String ids);

    int recoverOrder(String ids);

    void doProcessReqParam(GroupOrderReqDto groupOrderReqDto);

    List<MerchantOrder> selectMerchantOrder(GroupOrderReqDto groupOrderReqDto);

    List<SalesmanLeaderOrder> selectSalesmanLedaderOrder(GroupOrderReqDto groupOrderReqDto);

    int editPicture(Long id, String ossParam);

    boolean hasNoSamePlatformNickname4DB(DtBusinessTaskDetail dtBusinessTaskDetail);

    String importOrder(List<GroupOrder> orderList);

    List<GroupOrderRespDto> selectGroupOrderRespDtoListWithSupplementOrder();

    int supplementOrder(SupplementOrderReqDto supplementOrderReqDto);

    int withdraw(Long id);
}
