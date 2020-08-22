package com.ruoyi.businessteam.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.businessteam.mapper.DtSalesmanMapper;
import com.ruoyi.businessteam.domain.DtSalesman;
import com.ruoyi.businessteam.service.IDtSalesmanService;
import com.ruoyi.common.core.text.Convert;

/**
 * 业务人员信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-08-22
 */
@Service
public class DtSalesmanServiceImpl implements IDtSalesmanService 
{
    @Autowired
    private DtSalesmanMapper dtSalesmanMapper;

    /**
     * 查询业务人员信息
     * 
     * @param id 业务人员信息ID
     * @return 业务人员信息
     */
    @Override
    public DtSalesman selectDtSalesmanById(Long id)
    {
        return dtSalesmanMapper.selectDtSalesmanById(id);
    }

    /**
     * 查询业务人员信息列表
     * 
     * @param dtSalesman 业务人员信息
     * @return 业务人员信息
     */
    @Override
    public List<DtSalesman> selectDtSalesmanList(DtSalesman dtSalesman)
    {
        return dtSalesmanMapper.selectDtSalesmanList(dtSalesman);
    }

    /**
     * 新增业务人员信息
     * 
     * @param dtSalesman 业务人员信息
     * @return 结果
     */
    @Override
    public int insertDtSalesman(DtSalesman dtSalesman)
    {
        return dtSalesmanMapper.insertDtSalesman(dtSalesman);
    }

    /**
     * 修改业务人员信息
     * 
     * @param dtSalesman 业务人员信息
     * @return 结果
     */
    @Override
    public int updateDtSalesman(DtSalesman dtSalesman)
    {
        return dtSalesmanMapper.updateDtSalesman(dtSalesman);
    }

    /**
     * 删除业务人员信息对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDtSalesmanByIds(String ids)
    {
        return dtSalesmanMapper.deleteDtSalesmanByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除业务人员信息信息
     * 
     * @param id 业务人员信息ID
     * @return 结果
     */
    @Override
    public int deleteDtSalesmanById(Long id)
    {
        return dtSalesmanMapper.deleteDtSalesmanById(id);
    }
}
