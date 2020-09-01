package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.DtMerchantMapper;
import com.ruoyi.system.domain.DtMerchant;
import com.ruoyi.system.service.IDtMerchantService;
import com.ruoyi.common.core.text.Convert;

/**
 * 商家信息Service业务层处理
 * 
 * @author zimao
 * @date 2020-09-01
 */
@Service
public class DtMerchantServiceImpl implements IDtMerchantService 
{
    @Autowired
    private DtMerchantMapper dtMerchantMapper;

    /**
     * 查询商家信息
     * 
     * @param id 商家信息ID
     * @return 商家信息
     */
    @Override
    public DtMerchant selectDtMerchantById(Long id)
    {
        return dtMerchantMapper.selectDtMerchantById(id);
    }

    /**
     * 查询商家信息列表
     * 
     * @param dtMerchant 商家信息
     * @return 商家信息
     */
    @Override
    public List<DtMerchant> selectDtMerchantList(DtMerchant dtMerchant)
    {
        return dtMerchantMapper.selectDtMerchantList(dtMerchant);
    }

    /**
     * 新增商家信息
     * 
     * @param dtMerchant 商家信息
     * @return 结果
     */
    @Override
    public int insertDtMerchant(DtMerchant dtMerchant)
    {
        return dtMerchantMapper.insertDtMerchant(dtMerchant);
    }

    /**
     * 修改商家信息
     * 
     * @param dtMerchant 商家信息
     * @return 结果
     */
    @Override
    public int updateDtMerchant(DtMerchant dtMerchant)
    {
        return dtMerchantMapper.updateDtMerchant(dtMerchant);
    }

    /**
     * 删除商家信息对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDtMerchantByIds(String ids)
    {
        return dtMerchantMapper.deleteDtMerchantByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除商家信息信息
     * 
     * @param id 商家信息ID
     * @return 结果
     */
    @Override
    public int deleteDtMerchantById(Long id)
    {
        return dtMerchantMapper.deleteDtMerchantById(id);
    }

    @Override
    public Long selectIdByUserId(Long userId) {
        return dtMerchantMapper.selectIdByUserId(userId);
    }
}
