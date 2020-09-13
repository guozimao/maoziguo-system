package com.ruoyi.merchant.service.impl;


import com.ruoyi.common.utils.oss.OssClientUtils;
import com.ruoyi.merchant.domain.reponse.MerchantOrderRespDto;
import com.ruoyi.merchant.domain.request.MerchantOrderReqDto;
import com.ruoyi.merchant.mapper.MerchantOrderMapper;
import com.ruoyi.merchant.service.IMerchantOrderService;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.mapper.SysUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private SysUserMapper sysUserMapper;
    @Override
    public List<MerchantOrderRespDto> selectMerchantTaskDtoList(MerchantOrderReqDto merchantOrderReqDto) {
        List<MerchantOrderRespDto> orders = merchantOrderMapper.selectMerchantOrderList(merchantOrderReqDto);
        for(MerchantOrderRespDto item:orders){
            item.setPictureUrl(OssClientUtils.getPictureUrlByOssParam(item.getPictureUrl()));
            item.setSalesmanCommitUrl(OssClientUtils.getPictureUrlByOssParam(item.getSalesmanCommitUrl()));
        }
        return orders;
    }

}
