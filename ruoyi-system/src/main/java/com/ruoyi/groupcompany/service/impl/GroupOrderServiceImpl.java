package com.ruoyi.groupcompany.service.impl;

import com.ruoyi.businessteam.mapper.DtSalesmanMapper;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.oss.OssClientUtils;
import com.ruoyi.groupcompany.domain.DtBusinessTask;
import com.ruoyi.groupcompany.domain.DtBusinessTaskDetail;
import com.ruoyi.groupcompany.domain.reponse.DtGroupBusinessTaskDetailRespDto;
import com.ruoyi.groupcompany.domain.reponse.DtGroupBusinessTaskRespDto;
import com.ruoyi.groupcompany.domain.request.AssginReqDto;
import com.ruoyi.groupcompany.domain.request.DtGroupBusinessTaskReqDto;
import com.ruoyi.groupcompany.mapper.DtBusinessTaskMapper;
import com.ruoyi.groupcompany.service.IDtBusinessTaskService;
import com.ruoyi.groupcompany.service.IGroupOrderService;
import com.ruoyi.system.domain.SysDept;
import com.ruoyi.system.mapper.SysDeptMapper;
import com.ruoyi.system.mapper.SysUserMapper;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 商业任务信息Service业务层处理
 * 
 * @author zimao
 * @date 2020-08-27
 */
@Service
public class GroupOrderServiceImpl implements IGroupOrderService
{
    private static final Logger log = LoggerFactory.getLogger(GroupOrderServiceImpl.class);
    @Autowired
    private DtBusinessTaskMapper dtBusinessTaskMapper;


    @Override
    public List<DtBusinessTaskDetail> selectGroupDtBusinessTaskDtoList(DtBusinessTaskDetail dtBusinessTaskDetail) {

        return null;
    }
}
