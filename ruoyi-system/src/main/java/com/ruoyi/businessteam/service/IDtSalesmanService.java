package com.ruoyi.businessteam.service;

import java.util.List;
import com.ruoyi.businessteam.domain.DtSalesman;
import com.ruoyi.businessteam.domain.dto.request.SalesManReqDto;
import com.ruoyi.businessteam.domain.dto.response.SalesManRespDto;

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
     * @param salesManReqDto 业务人员信息
     * @param leaderId 部门负责人id
     * @param depId 部门id
     * @return 业务人员信息集合
     */
    public List<SalesManRespDto> selectDtSalesmanList(SalesManReqDto salesManReqDto, Long leaderId, Long depId);

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
