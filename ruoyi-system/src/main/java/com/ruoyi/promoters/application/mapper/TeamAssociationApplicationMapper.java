package com.ruoyi.promoters.application.mapper;

import java.util.List;
import com.ruoyi.promoters.application.domain.TeamAssociationApplication;
import com.ruoyi.promoters.application.domain.dto.request.ApplicationReqDto;
import com.ruoyi.promoters.application.domain.dto.response.ApplicationRespDto;
import org.apache.ibatis.annotations.Param;

/**
 * 团队关联申请Mapper接口
 * 
 * @author ruoyi
 * @date 2020-08-23
 */
public interface TeamAssociationApplicationMapper 
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
     * @param teamAssociationApplication 团队关联申请
     * @return 团队关联申请集合
     */
    public List<TeamAssociationApplication> selectTeamAssociationApplicationList(TeamAssociationApplication teamAssociationApplication);

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
     * 删除团队关联申请
     * 
     * @param id 团队关联申请ID
     * @return 结果
     */
    public int deleteTeamAssociationApplicationById(Long id);

    /**
     * 批量删除团队关联申请
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTeamAssociationApplicationByIds(String[] ids);

    /**
     * 通过id,userId,deptId去查询团队关联申请记录
     *
     * **/
    TeamAssociationApplication selectTeamAssociationApplicationByThreeParam(@Param("id") Long id,
                                                                            @Param("userId") Long userId,
                                                                            @Param("deptId") Long deptId);


    /**
     * 查询团队关联申请列表
     *
     * ***/
    List<ApplicationRespDto> selectApplicationList(@Param("approverId") Long approverId,
                                                   @Param("associationStatus") String associationStatus);

    int updateTeamAssociationStatusApplicationByThreeParam(@Param("approverId")Long  approverId,
                                                           @Param("userId") Long userId,
                                                           @Param("depId") Long deptId,
                                                           @Param("associationStatus") String associationStatus);
}
