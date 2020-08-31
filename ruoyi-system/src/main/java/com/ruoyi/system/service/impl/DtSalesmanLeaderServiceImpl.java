package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.DtSalesmanLeaderMapper;
import com.ruoyi.system.domain.DtSalesmanLeader;
import com.ruoyi.system.service.IDtSalesmanLeaderService;
import com.ruoyi.common.core.text.Convert;

/**
 * 业务组长信息Service业务层处理
 * 
 * @author zimao
 * @date 2020-08-31
 */
@Service
public class DtSalesmanLeaderServiceImpl implements IDtSalesmanLeaderService 
{
    @Autowired
    private DtSalesmanLeaderMapper dtSalesmanLeaderMapper;

    /**
     * 查询业务组长信息
     * 
     * @param id 业务组长信息ID
     * @return 业务组长信息
     */
    @Override
    public DtSalesmanLeader selectDtSalesmanLeaderById(Long id)
    {
        return dtSalesmanLeaderMapper.selectDtSalesmanLeaderById(id);
    }

    /**
     * 查询业务组长信息列表
     * 
     * @param dtSalesmanLeader 业务组长信息
     * @return 业务组长信息
     */
    @Override
    public List<DtSalesmanLeader> selectDtSalesmanLeaderList(DtSalesmanLeader dtSalesmanLeader)
    {
        return dtSalesmanLeaderMapper.selectDtSalesmanLeaderList(dtSalesmanLeader);
    }

    /**
     * 新增业务组长信息
     * 
     * @param dtSalesmanLeader 业务组长信息
     * @return 结果
     */
    @Override
    public int insertDtSalesmanLeader(DtSalesmanLeader dtSalesmanLeader)
    {
        return dtSalesmanLeaderMapper.insertDtSalesmanLeader(dtSalesmanLeader);
    }

    /**
     * 修改业务组长信息
     * 
     * @param dtSalesmanLeader 业务组长信息
     * @return 结果
     */
    @Override
    public int updateDtSalesmanLeader(DtSalesmanLeader dtSalesmanLeader)
    {
        return dtSalesmanLeaderMapper.updateDtSalesmanLeader(dtSalesmanLeader);
    }

    /**
     * 删除业务组长信息对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDtSalesmanLeaderByIds(String ids)
    {
        return dtSalesmanLeaderMapper.deleteDtSalesmanLeaderByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除业务组长信息信息
     * 
     * @param id 业务组长信息ID
     * @return 结果
     */
    @Override
    public int deleteDtSalesmanLeaderById(Long id)
    {
        return dtSalesmanLeaderMapper.deleteDtSalesmanLeaderById(id);
    }
}
