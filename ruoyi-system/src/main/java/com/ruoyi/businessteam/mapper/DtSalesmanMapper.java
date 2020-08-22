package com.ruoyi.businessteam.mapper;

import java.util.List;
import com.ruoyi.businessteam.domain.DtSalesman;

/**
 * 业务人员信息Mapper接口
 * 
 * @author ruoyi
 * @date 2020-08-22
 */
public interface DtSalesmanMapper 
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
     * 删除业务人员信息
     * 
     * @param id 业务人员信息ID
     * @return 结果
     */
    public int deleteDtSalesmanById(Long id);

    /**
     * 批量删除业务人员信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDtSalesmanByIds(String[] ids);

    /**
     * 根据用户id批量删除业务人员信息
     *
     * @param userIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteDtSalesmanByUserIds(Long[] userIds);
}
