package com.ruoyi.promoters.application.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.ruoyi.businessteam.domain.DtSalesman;
import com.ruoyi.businessteam.mapper.DtSalesmanMapper;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.promoters.application.domain.dto.request.ApplicationReqDto;
import com.ruoyi.promoters.application.domain.dto.response.ApplicationRespDto;
import com.ruoyi.system.domain.SysDept;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.mapper.SysDeptMapper;
import com.ruoyi.system.mapper.SysUserMapper;
import org.apache.commons.collections.MultiMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.promoters.application.mapper.TeamAssociationApplicationMapper;
import com.ruoyi.promoters.application.domain.TeamAssociationApplication;
import com.ruoyi.promoters.application.service.ITeamAssociationApplicationService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 团队关联申请Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-08-23
 */
@Service
public class TeamAssociationApplicationServiceImpl implements ITeamAssociationApplicationService 
{
    @Autowired
    private TeamAssociationApplicationMapper teamAssociationApplicationMapper;

    @Autowired
    private DtSalesmanMapper dtSalesmanMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysDeptMapper sysDeptMapper;

    /**
     * 查询团队关联申请
     * 
     * @param id 团队关联申请ID
     * @return 团队关联申请
     */
    @Override
    public TeamAssociationApplication selectTeamAssociationApplicationById(Long id)
    {
        return teamAssociationApplicationMapper.selectTeamAssociationApplicationById(id);
    }

    /**
     * 查询团队关联申请列表
     * 
     * @param applicationReqDto 团队关联申请
     * @return 团队关联申请
     */
    @Override
    public List<ApplicationRespDto> selectTeamAssociationApplicationList(ApplicationReqDto applicationReqDto,Long userId)
    {

        DtSalesman salesmen = dtSalesmanMapper.selectDtSalesmanByUserId(userId);
        if(StringUtils.isNull(salesmen)){
            throw new BusinessException("本账号不是业务员");
        }
        DtSalesman dtSalesman = dtSalesmanMapper.selectDtSalesmanByUserId(userId);
        TeamAssociationApplication teamAssociationApplication = teamAssociationApplicationMapper.selectTeamAssociationApplicationWithAssociateStatusBySalesManId(dtSalesman.getId());

        List<ApplicationRespDto> resultList = teamAssociationApplicationMapper.selectApplicationList(salesmen.getId(), applicationReqDto.getAssociationStatus());

        List<Long> userIds = resultList.stream().map(ApplicationRespDto::getApplicantId).collect(Collectors.toList());
        userIds.add(salesmen.getUserId());
        List<SysUser> userList = sysUserMapper.selectUserListByIds(userIds);
        List<Long> depts = userList.stream().map(SysUser::getDeptId).collect(Collectors.toList());
        List<SysDept> sysDepts =  sysDeptMapper.selectDeptListByIds(depts);

        Map<Long,String> userIdUserNameMap = userList.stream().collect(Collectors.toMap(SysUser::getUserId, SysUser::getUserName, (key1, key2) -> key2));
        Map<Long,String> deptIdDeptNameMap = sysDepts.stream().collect(Collectors.toMap(SysDept::getDeptId, SysDept::getDeptName, (key1, key2) -> key2));
        for(ApplicationRespDto dto : resultList){
            dto.setApplicantName(userIdUserNameMap.get(dto.getApplicantId()));
            dto.setApproverName(userIdUserNameMap.get(salesmen.getUserId()));
            dto.setDeptName(deptIdDeptNameMap.get(dto.getDeptId()));
            if(StringUtils.isNotNull(teamAssociationApplication)){
                dto.setHiddenTxt("hidden");
            }
        }
        return resultList;
    }

    /**
     * 新增团队关联申请
     * 
     * @param teamAssociationApplication 团队关联申请
     * @return 结果
     */
    @Override
    public int insertTeamAssociationApplication(TeamAssociationApplication teamAssociationApplication)
    {
        teamAssociationApplication.setCreateTime(DateUtils.getNowDate());
        return teamAssociationApplicationMapper.insertTeamAssociationApplication(teamAssociationApplication);
    }

    /**
     * 修改团队关联申请
     * 
     * @param teamAssociationApplication 团队关联申请
     * @return 结果
     */
    @Override
    public int updateTeamAssociationApplication(TeamAssociationApplication teamAssociationApplication)
    {
        teamAssociationApplication.setUpdateTime(DateUtils.getNowDate());
        return teamAssociationApplicationMapper.updateTeamAssociationApplication(teamAssociationApplication);
    }

    /**
     * 删除团队关联申请对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTeamAssociationApplicationByIds(String ids)
    {
        return teamAssociationApplicationMapper.deleteTeamAssociationApplicationByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除团队关联申请信息
     * 
     * @param id 团队关联申请ID
     * @return 结果
     */
    @Override
    public int deleteTeamAssociationApplicationById(Long id)
    {
        return teamAssociationApplicationMapper.deleteTeamAssociationApplicationById(id);
    }

    @Override
    @Transactional
    public int addApplication(Long id, Long userId, Long deptId,String loginName) {
        dtSalesmanMapper.updateDtSalesmanWithStatus(id,"2");
        TeamAssociationApplication teamAssociationApplication = new TeamAssociationApplication();
        teamAssociationApplication.setApplicantId(userId);
        teamAssociationApplication.setApproverId(id);
        teamAssociationApplication.setDeptId(deptId);
        teamAssociationApplication.setAssociationStatus("2");
        teamAssociationApplication.setCreateBy(loginName);
        TeamAssociationApplication one = teamAssociationApplicationMapper.selectTeamAssociationApplicationByThreeParam(id,userId,deptId);
        if(StringUtils.isNull(one)){
            return teamAssociationApplicationMapper.insertTeamAssociationApplication(teamAssociationApplication);
        }else {
            teamAssociationApplication.setId(one.getId());
            teamAssociationApplication.setCreateBy(null);
            teamAssociationApplication.setUpdateBy(loginName);
            return teamAssociationApplicationMapper.updateTeamAssociationApplication(teamAssociationApplication);
        }
    }

    @Override
    public boolean hasApplicationVaildedRecord(Long id, Long userId,Long deptId) {
        TeamAssociationApplication teamAssociationApplication = teamAssociationApplicationMapper.selectTeamAssociationApplicationByThreeParam(id,userId,deptId);
        if(StringUtils.isNull(teamAssociationApplication)){
            return false;
        }
        DtSalesman dtSalesman = dtSalesmanMapper.selectDtSalesmanById(id);
        //关联状态（1未关联 3不通过）,可以再申请
        return !StringUtils.isNotNull(dtSalesman) ||
                (!dtSalesman.getAssociationStatus().equals("3") && !dtSalesman.getAssociationStatus().equals("1"));
    }

    @Override
    @Transactional
    public int agree(Long id,Long userId,String loginName) {
        TeamAssociationApplication teamAssociationApplication = teamAssociationApplicationMapper.selectTeamAssociationApplicationById(id);
        SysUser sysUser = new SysUser();
        sysUser.setDeptId(teamAssociationApplication.getDeptId());
        sysUser.setUserId(userId);
        sysUser.setUserName(loginName);
        sysUser.setCreateBy(loginName);
        sysUserMapper.updateUser(sysUser);
        DtSalesman dtSalesman = new DtSalesman();
        dtSalesman.setId(teamAssociationApplication.getApproverId());
        dtSalesman.setAssociationStatus("0");
        dtSalesmanMapper.updateDtSalesman(dtSalesman);
        teamAssociationApplication.setAssociationStatus("0");
        teamAssociationApplication.setUpdateBy(loginName);
        return teamAssociationApplicationMapper.updateTeamAssociationApplication(teamAssociationApplication);
    }
}
