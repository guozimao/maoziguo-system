package com.ruoyi.merchant.service.impl;


import com.ruoyi.common.utils.oss.OssClientUtils;
import com.ruoyi.groupcompany.domain.DtBusinessTask;
import com.ruoyi.groupcompany.mapper.DtBusinessTaskMapper;
import com.ruoyi.merchant.domain.reponse.MerchantOrderRespDto;
import com.ruoyi.merchant.domain.request.MerchantOrderReqDto;
import com.ruoyi.merchant.mapper.MerchantOrderMapper;
import com.ruoyi.merchant.service.IMerchantOrderService;

import com.ruoyi.system.mapper.SysUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;

/**
 * 商业订单信息Service业务层处理
 * 
 * @author zimao
 * @date 2020-08-27
 */
@Service
public class MerchantOrderServiceImpl implements IMerchantOrderService
{
    private static final Logger log = LoggerFactory.getLogger(MerchantOrderServiceImpl.class);
    @Autowired
    private MerchantOrderMapper merchantOrderMapper;

    @Autowired
    private DtBusinessTaskMapper dtBusinessTaskMapper;

    @Autowired
    private SysUserMapper sysUserMapper;
    @Override
    public List<MerchantOrderRespDto> selectMerchantTaskDtoList(MerchantOrderReqDto merchantOrderReqDto) {
        List<MerchantOrderRespDto> orders = merchantOrderMapper.selectMerchantOrderList(merchantOrderReqDto);
        if(orders.isEmpty()){
            return Collections.emptyList();
        }
        List<Long> taskIds = orders.stream().map(MerchantOrderRespDto::getTaskId).collect(Collectors.toSet()).stream().collect(Collectors.toList());
        List<DtBusinessTask> tasks = dtBusinessTaskMapper.selectDtBusinessTaskByIds(taskIds);
        Map<Long,DtBusinessTask> taskIdAndTaskMap = tasks.stream().collect(Collectors.toMap(DtBusinessTask::getId,a -> a, (k1,k2) -> k2));
        for(MerchantOrderRespDto item:orders){
            item.setCompletionTime(taskIdAndTaskMap.get(item.getTaskId()).getCompletionTime());
            item.setCreateTime(taskIdAndTaskMap.get(item.getTaskId()).getCreateTime());
            item.setPictureUrl(OssClientUtils.getPictureUrlByOssParam(item.getPictureUrl()));
            item.setSalesmanCommitUrl(OssClientUtils.getPictureUrlByOssParam(item.getSalesmanCommitUrl()));
        }
        return orders;
    }

}
