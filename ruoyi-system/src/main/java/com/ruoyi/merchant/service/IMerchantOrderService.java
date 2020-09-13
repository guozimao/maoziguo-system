package com.ruoyi.merchant.service;

import com.ruoyi.merchant.domain.MerchantOrder;
import com.ruoyi.merchant.domain.reponse.MerchantOrderRespDto;
import com.ruoyi.merchant.domain.request.MerchantOrderReqDto;

import java.util.List;

public interface IMerchantOrderService {
    /*** 订单列表 **/
    List<MerchantOrderRespDto> selectMerchantTaskDtoList(MerchantOrderReqDto groupOrderReqDto);

    List<MerchantOrder> selectMerchantOrder(MerchantOrderReqDto merchantOrderReqDto);
}
