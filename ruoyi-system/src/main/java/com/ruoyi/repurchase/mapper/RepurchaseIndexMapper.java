package com.ruoyi.repurchase.mapper;

import com.ruoyi.repurchase.domain.MemberConsumptionTrack;
import com.ruoyi.system.domain.SysConfig;

import java.util.List;
import java.util.Map;

/**
 * 复购 数据层
 * 
 * @author ruoyi
 */
public interface RepurchaseIndexMapper
{
    List<MemberConsumptionTrack> queryList(Map filterMap);

    MemberConsumptionTrack selectConsumption(MemberConsumptionTrack consumption);

    int insertConsumption(MemberConsumptionTrack consumption);

    int updateConsumption(MemberConsumptionTrack consumption);
}