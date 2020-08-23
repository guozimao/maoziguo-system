package com.ruoyi.promoters.application.service.impl;

import java.util.List;

import com.ruoyi.businessteam.domain.DtSalesman;
import com.ruoyi.businessteam.mapper.DtSalesmanMapper;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
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
     * @param teamAssociationApplication 团队关联申请
     * @return 团队关联申请
     */
    @Override
    public List<TeamAssociationApplication> selectTeamAssociationApplicationList(TeamAssociationApplication teamAssociationApplication)
    {
        return teamAssociationApplicationMapper.selectTeamAssociationApplicationList(teamAssociationApplication);
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
        teamAssociationApplication.setCreateBy(loginName);
        TeamAssociationApplication one = teamAssociationApplicationMapper.selectTeamAssociationApplicationByThreeParam(id,userId,deptId);
        if(StringUtils.isNull(one)){
            return teamAssociationApplicationMapper.insertTeamAssociationApplication(teamAssociationApplication);
        }else {
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
}
