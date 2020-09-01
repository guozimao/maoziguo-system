package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.DtSalesmanLeader;
import org.apache.ibatis.annotations.Param;

/**
 * 业务组长信息Mapper接口
 * 
 * @author zimao
 * @date 2020-08-31
 */
public interface DtSalesmanLeaderMapper 
{
    /**
     * 查询业务组长信息
     * 
     * @param id 业务组长信息ID
     * @return 业务组长信息
     */
    public DtSalesmanLeader selectDtSalesmanLeaderById(Long id);

    /**
     * 查询业务组长信息列表
     * 
     * @param dtSalesmanLeader 业务组长信息
     * @return 业务组长信息集合
     */
    public List<DtSalesmanLeader> selectDtSalesmanLeaderList(DtSalesmanLeader dtSalesmanLeader);

    /**
     * 新增业务组长信息
     * 
     * @param dtSalesmanLeader 业务组长信息
     * @return 结果
     */
    public int insertDtSalesmanLeader(DtSalesmanLeader dtSalesmanLeader);

    /**
     * 修改业务组长信息
     * 
     * @param dtSalesmanLeader 业务组长信息
     * @return 结果
     */
    public int updateDtSalesmanLeader(DtSalesmanLeader dtSalesmanLeader);

    /**
     * 删除业务组长信息
     * 
     * @param id 业务组长信息ID
     * @return 结果
     */
    public int deleteDtSalesmanLeaderById(Long id);

    /**
     * 批量删除业务组长信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDtSalesmanLeaderByIds(String[] ids);

    void deleteDtSalesmanLeaderByUserIds(@Param("array") Long[] userIds);

    Long selectIdByUserId(Long userId);
}
