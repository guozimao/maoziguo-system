package com.ruoyi.promoters.application.service;

import java.util.List;
import com.ruoyi.promoters.application.domain.TeamAssociationApplication;
import com.ruoyi.promoters.application.domain.dto.request.ApplicationReqDto;
import com.ruoyi.promoters.application.domain.dto.response.ApplicationRespDto;

/**
 * 团队关联申请Service接口
 * 
 * @author ruoyi
 * @date 2020-08-23
 */
public interface ITeamAssociationApplicationService 
{
    /**
     * 查询团队关联申请
     * 
     * @param id 团队关联申请ID
     * @return 团队关联申请
     */
    public TeamAssociationApplication selectTeamAssociationApplicationById(Long id);

    /**
     * 查询团队关联申请列表
     * 
     * @param applicationReqDto 团队关联申请
     * @return 团队关联申请集合
     */
    public List<ApplicationRespDto> selectTeamAssociationApplicationList(ApplicationReqDto applicationReqDto,Long userId);

    /**
     * 新增团队关联申请
     * 
     * @param teamAssociationApplication 团队关联申请
     * @return 结果
     */
    public int insertTeamAssociationApplication(TeamAssociationApplication teamAssociationApplication);

    /**
     * 修改团队关联申请
     * 
     * @param teamAssociationApplication 团队关联申请
     * @return 结果
     */
    public int updateTeamAssociationApplication(TeamAssociationApplication teamAssociationApplication);

    /**
     * 批量删除团队关联申请
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTeamAssociationApplicationByIds(String ids);

    /**
     * 删除团队关联申请信息
     * 
     * @param id 团队关联申请ID
     * @return 结果
     */
    public int deleteTeamAssociationApplicationById(Long id);

    /**
     * 添加申请团队关联申请记录
     *
     * @param id 审核人salesManId
     * @param userId 申请人userId
     * @param deptId 申请归属部门
     * @param loginName 申請人账号名
     * @return 结果
     */
    int addApplication(Long id, Long userId, Long deptId,String loginName);

    /**
     * 是否有有效的申请记录
     *
     * @param id 审核人salesManId
     * @param userId 申请人userId
     * @param deptId 申请归属部门
     * @return 结果
     */
    boolean hasApplicationVaildedRecord(Long id, Long userId,Long deptId);
}
