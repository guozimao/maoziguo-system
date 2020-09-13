package com.ruoyi.salesman.service.impl;


import com.ruoyi.common.constant.QueryParaConstants;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.oss.OssClientUtils;
import com.ruoyi.salesman.domain.SalesmanTask;
import com.ruoyi.salesman.domain.SalesmanTaskDetail;
import com.ruoyi.salesman.domain.reponse.SalesmanOrderRespDto;
import com.ruoyi.salesman.domain.request.SalesmanOrderReqDto;
import com.ruoyi.salesman.mapper.SalesmanTaskMapper;
import com.ruoyi.salesman.mapper.SalesmanOrderMapper;
import com.ruoyi.salesman.service.ISalesmanOrderService;
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
public class SalesmanOrderServiceImpl implements ISalesmanOrderService
{
    private static final Logger log = LoggerFactory.getLogger(SalesmanOrderServiceImpl.class);
    @Autowired
    private SalesmanOrderMapper salesmanOrderMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public List<SalesmanOrderRespDto> selectSalesmanTaskDtoList(SalesmanOrderReqDto salesmanOrderReqDto) {
        List<SalesmanOrderRespDto> orders = salesmanOrderMapper.selectSalesmanOrderList(salesmanOrderReqDto);
        List<Long> userIds = orders.stream().map(SalesmanOrderRespDto::getSalesmanLeaderUserId).collect(Collectors.toList());
        userIds.addAll(orders.stream().map(SalesmanOrderRespDto::getMerchantUserId).collect(Collectors.toList()));
        List<SysUser> sysUsers = sysUserMapper.selectUserListByIds(userIds);
        Map<Long,String> userIdAndName = sysUsers.stream().collect(Collectors.toMap(SysUser::getUserId, SysUser::getUserName, (key1, key2) -> key2));
        for(SalesmanOrderRespDto item:orders){
            item.setSalesmanLeaderName(userIdAndName.get(item.getSalesmanLeaderUserId()));
            item.setMerchantUserName(userIdAndName.get(item.getMerchantUserId()));
            item.setPictureUrl(OssClientUtils.getPictureUrlByOssParam(item.getPictureUrl()));
            item.setSalesmanCommitUrl(OssClientUtils.getPictureUrlByOssParam(item.getSalesmanCommitUrl()));
        }
        return orders;
    }

}
