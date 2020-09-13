package com.ruoyi.merchant.mapper;


import com.ruoyi.merchant.domain.MerchantOrder;
import com.ruoyi.merchant.domain.reponse.MerchantOrderRespDto;
import com.ruoyi.merchant.domain.request.MerchantOrderReqDto;

import java.util.List;

/**
 * 商业任务信息Mapper接口
 * 
 * @author zimao
 * @date 2020-08-27
 */
public interface MerchantOrderMapper
{
    List<MerchantOrderRespDto> selectMerchantOrderList(MerchantOrderReqDto merchantOrderReqDto);

    List<MerchantOrder> selectMerchantOrder(MerchantOrderReqDto merchantOrderReqDto);
}
