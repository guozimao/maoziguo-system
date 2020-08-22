package com.ruoyi.businessteam.service;

import java.util.List;
import com.ruoyi.businessteam.domain.DtSalesman;

/**
 * 业务人员信息Service接口
 * 
 * @author ruoyi
 * @date 2020-08-22
 */
public interface IDtSalesmanService 
{
    /**
     * 查询业务人员信息
     * 
     * @param id 业务人员信息ID
     * @return 业务人员信息
     */
    public DtSalesman selectDtSalesmanById(Long id);

    /**
     * 查询业务人员信息列表
     * 
     * @param dtSalesman 业务人员信息
     * @return 业务人员信息集合
     */
    public List<DtSalesman> selectDtSalesmanList(DtSalesman dtSalesman);

    /**
     * 新增业务人员信息
     * 
     * @param dtSalesman 业务人员信息
     * @return 结果
     */
    public int insertDtSalesman(DtSalesman dtSalesman);

    /**
     * 修改业务人员信息
     * 
     * @param dtSalesman 业务人员信息
     * @return 结果
     */
    public int updateDtSalesman(DtSalesman dtSalesman);

    /**
     * 批量删除业务人员信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDtSalesmanByIds(String ids);

    /**
     * 删除业务人员信息信息
     * 
     * @param id 业务人员信息ID
     * @return 结果
     */
    public int deleteDtSalesmanById(Long id);
}
