package com.ruoyi.groupcompany.service.impl;


import com.ruoyi.common.constant.QueryParaConstants;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.oss.OssClientUtils;
import com.ruoyi.groupcompany.domain.DtBusinessTask;
import com.ruoyi.groupcompany.domain.DtBusinessTaskDetail;
import com.ruoyi.groupcompany.domain.reponse.GroupOrderRespDto;

import com.ruoyi.groupcompany.domain.request.GroupOrderReqDto;

import com.ruoyi.groupcompany.mapper.DtBusinessTaskMapper;
import com.ruoyi.groupcompany.mapper.GroupOrderMapper;

import com.ruoyi.groupcompany.service.IGroupOrderService;

import com.ruoyi.merchant.domain.MerchantOrder;
import com.ruoyi.salesmanleader.domain.SalesmanLeaderOrder;
import com.ruoyi.system.domain.SysUser;

import com.ruoyi.system.mapper.SysUserMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 商业订单信息Service业务层处理
 * 
 * @author zimao
 * @date 2020-08-27
 */
@Service
public class GroupOrderServiceImpl implements IGroupOrderService
{
    private static final Logger log = LoggerFactory.getLogger(GroupOrderServiceImpl.class);
    @Autowired
    private GroupOrderMapper groupOrderMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private DtBusinessTaskMapper dtBusinessTaskMapper;

    @Override
    public List<GroupOrderRespDto> selectGroupDtBusinessTaskDtoList(GroupOrderReqDto groupOrderReqDto) {
        List<GroupOrderRespDto> orders = groupOrderMapper.selectGroupOrderList(groupOrderReqDto);
        if(orders.isEmpty()){
            return Collections.emptyList();
        }
        List<Long> userIds = orders.stream().map(GroupOrderRespDto::getSalesmanLeaderUserId).collect(Collectors.toList());
        userIds.addAll(orders.stream().map(GroupOrderRespDto::getMerchantUserId).collect(Collectors.toList()));
        List<SysUser> sysUsers = sysUserMapper.selectUserListByIds(userIds);
        Map<Long,String> userIdAndName = sysUsers.stream().collect(Collectors.toMap(SysUser::getUserId, SysUser::getUserName, (key1, key2) -> key2));
        for(GroupOrderRespDto item:orders){
            item.setSalesmanLeaderName(userIdAndName.get(item.getSalesmanLeaderUserId()));
            item.setMerchantUserName(userIdAndName.get(item.getMerchantUserId()));
            item.setPictureUrl(OssClientUtils.getPictureUrlByOssParam(item.getPictureUrl()));
            item.setSalesmanCommitUrl(OssClientUtils.getPictureUrlByOssParam(item.getSalesmanCommitUrl()));
        }
        return orders;
    }

    @Override
    public int updateDtBusinessTaskDetail(DtBusinessTaskDetail dtBusinessTaskDetail) {
        if(!DateUtils.isSameDay(new Date(),dtBusinessTaskDetail.getOrderDate())){
            throw new BusinessException("能操作的数据日期是" + DateUtils.getDate());
        }
        return dtBusinessTaskMapper.updateDtBusinessTaskDetail(dtBusinessTaskDetail);
    }

    @Override
    public int stopOrder(String ids) {
        Long[] idlist = Convert.toLongArray(ids);
        List<DtBusinessTaskDetail> dtBusinessTaskDetails = dtBusinessTaskMapper.selectDtBusinessTaskDetailListByIds(idlist);
        List<String> statusList = dtBusinessTaskDetails.stream().map(DtBusinessTaskDetail::getStatus).collect(Collectors.toList());
        if(statusList.contains("0")){
            throw new BusinessException("有订单存在已完成，坏不了单");
        }
        Integer num = 0;
        for(Long id : idlist){
            DtBusinessTaskDetail detail = new DtBusinessTaskDetail();
            detail.setId(id);
            detail.setStatus("2");
            num = num + dtBusinessTaskMapper.updateDtBusinessTaskDetail(detail);
        }
        return num;
    }

    @Override
    public int recoverOrder(String ids) {
        Long[] idlist = Convert.toLongArray(ids);
        List<DtBusinessTaskDetail> dtBusinessTaskDetails = dtBusinessTaskMapper.selectDtBusinessTaskDetailListByIds(idlist);
        List<Long> taskIds = dtBusinessTaskDetails.stream().map(DtBusinessTaskDetail::getTaskId).collect(Collectors.toList());
        List<DtBusinessTask> dtBusinessTasks = dtBusinessTaskMapper.selectDtBusinessTaskByIds(taskIds);
        List<String> statusList = dtBusinessTaskDetails.stream().map(DtBusinessTaskDetail::getStatus).collect(Collectors.toList());
        List<String> orderStatusList = dtBusinessTasks.stream().map(DtBusinessTask::getOrderStatus).collect(Collectors.toList());
        if(statusList.contains("0") || statusList.contains("1")){
            throw new BusinessException("存在订单是非停单状态的，修复不了订单,请勿选非停单状态下的订单");
        }
        if(orderStatusList.contains("0")){
            throw new BusinessException("有任务存在已完成，修复不了订单");
        }
        Integer num = 0;
        for(Long id : idlist){
            DtBusinessTaskDetail detail = new DtBusinessTaskDetail();
            detail.setId(id);
            detail.setStatus("1");
            num = num + dtBusinessTaskMapper.updateDtBusinessTaskDetail(detail);
        }
        return num;
    }

    @Override
    public void doProcessReqParam(GroupOrderReqDto groupOrderReqDto) {
        if(StringUtils.isNotEmpty(groupOrderReqDto.getSalesmanLeaderName())){
            SysUser user = sysUserMapper.selectUserByUserName(groupOrderReqDto.getSalesmanLeaderName());
            if(user != null){
                groupOrderReqDto.setSalesmanLeaderUserId(user.getUserId());
            }else {
                groupOrderReqDto.setSalesmanLeaderUserId(QueryParaConstants.PARAM_NULL);
            }
        }
        if(StringUtils.isNotEmpty(groupOrderReqDto.getMerchantName())){
            SysUser user = sysUserMapper.selectUserByUserName(groupOrderReqDto.getMerchantName());
            if(user != null){
                groupOrderReqDto.setMerchantUserId(user.getUserId());
            }else{
                groupOrderReqDto.setMerchantUserId(QueryParaConstants.PARAM_NULL);
            }
        }
    }

    @Override
    public List<MerchantOrder> selectMerchantOrder(GroupOrderReqDto groupOrderReqDto) {
        List<MerchantOrder> orders = groupOrderMapper.getMerchantOrderList(groupOrderReqDto);
        BigDecimal priceTotal = new BigDecimal(0);
        for (MerchantOrder order : orders){
            priceTotal = priceTotal.add(order.getPromotersModifyUnitPrice());
        }
        MerchantOrder merchantOrder = new MerchantOrder();
        merchantOrder.setPlatformNickname("小计");
        merchantOrder.setPromotersModifyUnitPrice(priceTotal);
        orders.add(merchantOrder);
        MerchantOrder merchantOrder2 = new MerchantOrder();
        merchantOrder2.setPlatformNickname("合计");
        return orders;
    }

    @Override
    public List<SalesmanLeaderOrder> selectSalesmanLedaderOrder(GroupOrderReqDto groupOrderReqDto) {
        List<SalesmanLeaderOrder> orders = groupOrderMapper.selectSalesmanLeaderOrder(groupOrderReqDto);
        List<Long> salesmanLeaderuserIds = orders.stream().map(SalesmanLeaderOrder::getSalesmanLeaderUserId).collect(Collectors.toSet()).stream().collect(Collectors.toList());
        List<SysUser> users = sysUserMapper.selectUserListByIds(salesmanLeaderuserIds);
        Map<Long,String> userIdAndNameMap = users.stream().collect(Collectors.toMap(SysUser::getUserId,SysUser::getUserName,(k1,k2) -> k2));
        BigDecimal promotersModifyUnitPriceTotal = new BigDecimal(0);
        BigDecimal unitPriceTotal = new BigDecimal(0);
        BigDecimal priceDifferences4total = new BigDecimal(0);
        Integer amount = 0;
        for(SalesmanLeaderOrder order:orders){
            order.setPriceDifferences(order.getPromotersModifyUnitPrice().subtract(order.getUnitPrice()));
            order.setSalesmanLeaderUserName(userIdAndNameMap.get(order.getSalesmanLeaderUserId()));
            order.setAmount(1);
            promotersModifyUnitPriceTotal = promotersModifyUnitPriceTotal.add(order.getPromotersModifyUnitPrice());
            unitPriceTotal = unitPriceTotal.add(order.getUnitPrice());
            priceDifferences4total = priceDifferences4total.add(order.getPriceDifferences());
            amount += order.getAmount();
        }
        SalesmanLeaderOrder order = new SalesmanLeaderOrder();
        order.setPlatformNickname("合计");
        order.setPromotersModifyUnitPrice(promotersModifyUnitPriceTotal);
        order.setUnitPrice(unitPriceTotal);
        order.setAmount(amount);
        order.setPriceDifferences(priceDifferences4total);
        orders.add(order);
        return orders;
    }

    @Override
    public int editPicture(Long id, String ossParam) {
        GroupOrderRespDto dto = groupOrderMapper.selectGroupOrderDtoById(id);
        if(StringUtils.isNull(dto)){
            throw new BusinessException("此订单不存在");
        }
        if(!DateUtils.isSameDay(new Date(),dto.getOrderDate())){
            throw new BusinessException("能操作的数据日期是" + DateUtils.getDate());
        }
        List<Long> orderIds = groupOrderMapper.selectOrderWithTodayAndNoNFinishByPlatformUrl(dto.getPlatformUrl());
        return groupOrderMapper.updateOrderPictureUrlById(orderIds,ossParam);
    }

    @Override
    public boolean hasNoSamePlatformNickname4DB(DtBusinessTaskDetail dtBusinessTaskDetail) {
        if(StringUtils.isEmpty(dtBusinessTaskDetail.getPlatformNickname())){
            return false;
        }
        SalesmanLeaderOrder order = groupOrderMapper.selectSalesmanLeaderOrderById(dtBusinessTaskDetail.getId());
        if(StringUtils.isNull(order)){
            throw new BusinessException("找不到订单");
        }
        if(StringUtils.isEmpty(order.getPlatformNickname())){
            return true;
        }
        return !order.getPlatformNickname().equals(dtBusinessTaskDetail.getPlatformNickname());
    }

}
