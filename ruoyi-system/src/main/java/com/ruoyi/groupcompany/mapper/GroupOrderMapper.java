package com.ruoyi.groupcompany.mapper;


import com.ruoyi.groupcompany.domain.reponse.GroupOrderRespDto;
import com.ruoyi.groupcompany.domain.request.GroupOrderReqDto;

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
}
