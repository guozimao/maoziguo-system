package com.ruoyi.businessteam.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.ruoyi.businessteam.domain.dto.request.SalesManReqDto;
import com.ruoyi.businessteam.domain.dto.response.SalesManRespDto;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.mapper.SysUserMapper;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.businessteam.mapper.DtSalesmanMapper;
import com.ruoyi.businessteam.domain.DtSalesman;
import com.ruoyi.businessteam.service.IDtSalesmanService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private SysUserMapper sysUserMapper;

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
     * @param salesManReqDto 业务人员信息
     * @param leaderId 部门负责人id
     * @param depId 部门id
     * @return 业务人员信息
     */
    @Override
    public List<SalesManRespDto> selectDtSalesmanList(SalesManReqDto salesManReqDto, Long leaderId, Long depId)
    {

        return dtSalesmanMapper.selectDtSalesmanList(salesManReqDto,leaderId,depId);
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

    /**
     * 查询业务人员详情
     *
     * @param id 业务人员信息ID
     * @return 结果
     */
    @Override
    public SalesManRespDto selectSalesManReqDtoById(Long id) {
        DtSalesman dtSalesman = dtSalesmanMapper.selectDtSalesmanById(id);
        SysUser sysUser = sysUserMapper.selectUserById(dtSalesman.getUserId());
        return formatSalesMapReqDto(dtSalesman,sysUser);
    }

    @Override
    @Transactional
    public int updateDtSalesmanReq(SalesManReqDto salesManReqDto) {
        SysUser user = new SysUser();
        DtSalesman salesman = new DtSalesman();
        if(isExistSameUserName(salesManReqDto.getUserName())
                && isNonUpdateSameOne(salesManReqDto.getUserName(),salesManReqDto.getUserId())){
            throw new BusinessException(salesManReqDto.getUserName() + " 已被注册");
        }
        try {
            BeanUtils.copyProperties(user,salesManReqDto);
            BeanUtils.copyProperties(salesman,salesManReqDto);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        if(sysUserMapper.updateUser(user) < 1){
            throw new BusinessException("沒有任何更新");
        }
        return dtSalesmanMapper.updateDtSalesman(salesman);
    }

    @Override
    public int deleteDtSalesmanDeptByIds(String ids) {
        Long[] StrArray = Convert.toLongArray(ids);
        List<Long> userIds = dtSalesmanMapper.selectUserIdsByIds(StrArray);
        return sysUserMapper.updateDeptI2NulldByIds(userIds);
    }

    @Override
    public boolean hasNoSalesMan(Long id) {
        DtSalesman dtSalesman = dtSalesmanMapper.selectDtSalesmanWithNoDeleteById(id);
        return !StringUtils.isNotNull(dtSalesman);
    }

    @Override
    public boolean hasNoNormalStatus(Long id) {
        DtSalesman dtSalesman = dtSalesmanMapper.selectDtSalesmanWithNoDeleteById(id);
        SysUser sysUser = sysUserMapper.selectUserById(dtSalesman.getUserId());
        return sysUser.getStatus().equals(UserConstants.USER_DISABLE);
    }

    @Override
    public boolean hasAssociationWithTeam(Long id, Long deptId) {
        DtSalesman dtSalesman = dtSalesmanMapper.selectDtSalesmanWithNoDeleteById(id);
        SysUser sysUser = sysUserMapper.selectUserById(dtSalesman.getUserId());
        return StringUtils.isNotNull(sysUser.getDeptId());
    }

    private boolean isNonUpdateSameOne(String userName, Long userId) {
        SysUser info = sysUserMapper.selectUserById(userId);
        return !StringUtils.isNotNull(info)
                || !info.getUserName().equals(userName);
    }

    private SalesManRespDto formatSalesMapReqDto(DtSalesman dtSalesman, SysUser sysUser) {
        SalesManRespDto result = new SalesManRespDto();
        try {
            BeanUtils.copyProperties(result,dtSalesman);
            BeanUtils.copyProperties(result,sysUser);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean isExistSameUserName(String userName) {
        SysUser info = sysUserMapper.checkUserNameUnique(userName);
        return StringUtils.isNotNull(info);
    }
}
