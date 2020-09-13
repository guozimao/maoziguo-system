package com.ruoyi.groupcompany.mapper;


import com.ruoyi.groupcompany.domain.reponse.GroupOrderRespDto;
import com.ruoyi.groupcompany.domain.request.GroupOrderReqDto;
import com.ruoyi.merchant.domain.MerchantOrder;
import com.ruoyi.salesmanleader.domain.SalesmanLeaderOrder;

import java.util.List;

/**
 * 商业任务信息Mapper接口
 * 
 * @author zimao
 * @date 2020-08-27
 */
public interface GroupOrderMapper
{
    List<GroupOrderRespDto> selectGroupOrderList(GroupOrderReqDto groupOrderReqDto);

    List<MerchantOrder> getMerchantOrderList(GroupOrderReqDto groupOrderReqDto);

    List<SalesmanLeaderOrder> selectSalesmanLeaderOrder(GroupOrderReqDto groupOrderReqDto);
}
