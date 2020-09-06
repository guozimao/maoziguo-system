package com.ruoyi.system.service;

import java.util.List;

import com.ruoyi.businessteam.domain.MerchantShopRelation;
import com.ruoyi.system.domain.DtMerchant;
import com.ruoyi.system.domain.SysUser;

/**
 * 商家信息Service接口
 * 
 * @author zimao
 * @date 2020-09-01
 */
public interface IDtMerchantService 
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
     * 批量删除商家信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDtMerchantByIds(String ids);

    /**
     * 删除商家信息信息
     * 
     * @param id 商家信息ID
     * @return 结果
     */
    public int deleteDtMerchantById(Long id);

    /**
     * 根据用户id查找商家id
     * */
    Long selectIdByUserId(Long userId);

    List<MerchantShopRelation> getMerchantShopRelationByUserId(Long id);

    SysUser getUserIdByName(String userName);

    MerchantShopRelation getMerchantUserIdByShopName(String shopName);
}
