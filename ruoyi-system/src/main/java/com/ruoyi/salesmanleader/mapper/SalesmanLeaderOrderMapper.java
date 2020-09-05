package com.ruoyi.salesmanleader.mapper;


import com.ruoyi.salesmanleader.domain.reponse.SalesmanLeaderOrderRespDto;
import com.ruoyi.salesmanleader.domain.request.SalesmanLeaderOrderReqDto;

import java.util.List;

/**
 * 商业任务信息Mapper接口
 * 
 * @author zimao
 * @date 2020-08-27
 */
public interface SalesmanLeaderOrderMapper
{
    List<SalesmanLeaderOrderRespDto> selectSalesmanLeaderOrderList(SalesmanLeaderOrderReqDto groupOrderReqDto);
}
