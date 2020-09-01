package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.DtMerchant;

/**
 * 商家信息Mapper接口
 * 
 * @author zimao
 * @date 2020-09-01
 */
public interface DtMerchantMapper 
{
    /**
     * 查询商家信息
     * 
     * @param id 商家信息ID
     * @return 商家信息
     */
    public DtMerchant selectDtMerchantById(Long id);

    /**
     * 查询商家信息列表
     * 
     * @param dtMerchant 商家信息
     * @return 商家信息集合
     */
    public List<DtMerchant> selectDtMerchantList(DtMerchant dtMerchant);

    /**
     * 新增商家信息
     * 
     * @param dtMerchant 商家信息
     * @return 结果
     */
    public int insertDtMerchant(DtMerchant dtMerchant);

    /**
     * 修改商家信息
     * 
     * @param dtMerchant 商家信息
     * @return 结果
     */
    public int updateDtMerchant(DtMerchant dtMerchant);

    /**
     * 删除商家信息
     * 
     * @param id 商家信息ID
     * @return 结果
     */
    public int deleteDtMerchantById(Long id);

    /**
     * 批量删除商家信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDtMerchantByIds(String[] ids);

    Long selectIdByUserId(Long userId);

    void deleteDtMerchantByUserIds(Long[] userIds);
}
