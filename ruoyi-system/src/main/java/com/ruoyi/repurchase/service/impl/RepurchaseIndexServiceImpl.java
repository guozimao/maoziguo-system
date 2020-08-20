package com.ruoyi.repurchase.service.impl;

import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.StringUtils;

import com.ruoyi.repurchase.domain.MemberConsumptionTrack;
import com.ruoyi.repurchase.domain.dto.request.MemberConsumptionTrackRequest;
import com.ruoyi.repurchase.mapper.RepurchaseIndexMapper;
import com.ruoyi.repurchase.service.IRepurchaseIndexService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 复购 服务层实现
 * 
 * @author zimao
 */
@Service
public class RepurchaseIndexServiceImpl implements IRepurchaseIndexService
{
    private static final Logger log = LoggerFactory.getLogger(RepurchaseIndexServiceImpl.class);
    @Autowired
    private RepurchaseIndexMapper repurchaseIndexMapper;


    @Override
    public List<MemberConsumptionTrack> selectList(MemberConsumptionTrackRequest memberConsumptionTrackRequest) {
        Map<String, Object> filterMap = new HashMap<>();
        filterMap.put("memberName", memberConsumptionTrackRequest.getMemberName());
        filterMap.put("storeName", memberConsumptionTrackRequest.getStoreName());
        filterMap.put("startDate", memberConsumptionTrackRequest.getScopeDate()[0]);
        filterMap.put("endDate", memberConsumptionTrackRequest.getScopeDate()[1]);
        return repurchaseIndexMapper.queryList(filterMap);
    }

    @Override
    public String importConsumption(List<MemberConsumptionTrack> consumptionList, boolean isUpdateSupport) {
        if (StringUtils.isNull(consumptionList) || consumptionList.size() == 0)
        {
            throw new BusinessException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (MemberConsumptionTrack consumption : consumptionList)
        {
            try
            {
                Date orderDate = new Date(consumption.getOrderDate());
                consumption.setOrderDate(sdf.format(orderDate));
                // 验证是否存在这个复购记录
                MemberConsumptionTrack mct = repurchaseIndexMapper.selectConsumption(consumption);
                if (StringUtils.isNull(mct))
                {
                    this.insertConsumption(consumption);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、店铺名称：" + consumption.getStoreName() + " 淘宝会员名：" + consumption.getMemberName() + " 导入成功");
                }
                else if (isUpdateSupport)
                {
                    consumption.setId(mct.getId());
                    this.updateConsumption(consumption);
                    successNum++;
                    successMsg.append("<br/>"+ successNum + "、店铺名称：" + consumption.getStoreName() + " 淘宝会员名：" + consumption.getMemberName() +  " 更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、店铺名称：" + consumption.getStoreName() + " 淘宝会员名：" + consumption.getMemberName() + " 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、店铺名称：" + consumption.getStoreName() + " 淘宝会员名：" + consumption.getMemberName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new BusinessException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    @Transactional
    public int updateConsumption(MemberConsumptionTrack consumption) {
        return repurchaseIndexMapper.updateConsumption(consumption);
    }

    @Transactional
    public int insertConsumption(MemberConsumptionTrack consumption) {
        return repurchaseIndexMapper.insertConsumption(consumption);
    }
}
