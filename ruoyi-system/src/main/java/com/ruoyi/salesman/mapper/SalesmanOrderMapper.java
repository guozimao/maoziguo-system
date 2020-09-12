package com.ruoyi.salesman.mapper;


import com.ruoyi.salesman.domain.SalesmanTaskDetail;
import com.ruoyi.salesman.domain.reponse.CommitOrder;
import com.ruoyi.salesman.domain.reponse.SalesmanOrderRespDto;
import com.ruoyi.salesman.domain.request.SalesmanOrderReqDto;

import java.util.List;

/**
 * 商业任务信息Mapper接口
 * 
 * @author zimao
 * @date 2020-08-27
 */
public interface SalesmanOrderMapper
{
    List<SalesmanOrderRespDto> selectSalesmanOrderList(SalesmanOrderReqDto salesmanOrderReqDto);

    List<SalesmanTaskDetail> selectSalesmanOrderListByPlatformNicknameWithRepurchase(String platformNickname);

    int updateSalesmanOrder(CommitOrder commitOrder);
}
