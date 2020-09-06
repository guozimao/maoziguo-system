package com.ruoyi.system.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.ruoyi.businessteam.domain.MerchantShopRelation;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.DtMerchantMapper;
import com.ruoyi.system.domain.DtMerchant;
import com.ruoyi.system.service.IDtMerchantService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 查询商家信息
     * 
     * @param id 商家信息ID
     * @return 商家信息
     */
    @Override
    public DtMerchant selectDtMerchantById(Long id)
    {
        DtMerchant merchant = dtMerchantMapper.selectDtMerchantById(id);
        SysUser user = sysUserMapper.selectUserById(merchant.getUserId());
        merchant.setUserName(user.getUserName());
        return merchant;
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
        List<DtMerchant> resultList = dtMerchantMapper.selectDtMerchantList(dtMerchant);
        if(resultList.size() < 1){
            return resultList;
        }
        List<Long> userIds = resultList.stream().map(DtMerchant::getUserId).collect(Collectors.toList());
        List<SysUser> users = sysUserMapper.selectUserListByIds(userIds);
        Map<Long,String> userIdAndNameMap = users.stream().collect(Collectors.toMap(SysUser::getUserId,SysUser::getUserName,(k1,k2) -> k2));
        List<MerchantShopRelation> merchantShopRelations = dtMerchantMapper.selectMerchantShopRelationByMerchantUserIds(userIds);
        Map<Long, Set<String>> userIdShopnameSetMap = merchantShopRelations.stream().collect(Collectors.groupingBy(MerchantShopRelation::getMerchantUserId, Collectors.mapping(MerchantShopRelation::getShopName,Collectors.toSet())));
        for(DtMerchant merchant : resultList){
            merchant.setUserName(userIdAndNameMap.get(merchant.getUserId()));
            if(userIdShopnameSetMap.get(merchant.getUserId()) != null){
                Iterator<String> iterator = userIdShopnameSetMap.get(merchant.getUserId()).iterator();
                merchant.setAssociatedShopName(StringUtils.join(iterator,","));
            }
        }
        return resultList;
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
    @Transactional
    public int updateDtMerchant(DtMerchant dtMerchant)
    {
        SysUser oldUser = sysUserMapper.selectUserById(dtMerchant.getUserId());

        if(!oldUser.getUserName().equals(dtMerchant.getUserName()) &&
                StringUtils.isNotNull(sysUserMapper.checkUserName(dtMerchant.getUserName()))){
            throw new BusinessException("用户昵称,已被注册");
        }


        Set<String> shopNames = dtMerchant.getShopNames().stream().filter(item -> StringUtils.isNotEmpty(item.trim())).collect(Collectors.toSet());
        if(shopNames.size() > 0){
            List<MerchantShopRelation> relations = dtMerchantMapper.selectMerchantShopRelationByShopNamesAndUserId(shopNames,dtMerchant.getUserId());
            if(relations.size() > 0){
                throw new BusinessException("存在店铺名称被其它商家注册了");
            }
            dtMerchantMapper.deleteMerchantShopRelationByUserId(dtMerchant.getUserId());
            dtMerchantMapper.batchInsertMerchantShopRelationByUserId(dtMerchant.getUserId(),shopNames);
        }

        SysUser user = new SysUser();
        user.setUserId(dtMerchant.getUserId());
        user.setUserName(dtMerchant.getUserName());
        sysUserMapper.updateUser(user);
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

    @Override
    public List<MerchantShopRelation> getMerchantShopRelationByUserId(Long id) {
        List<Long> userIds = new ArrayList<>();
        userIds.add(id);
        return dtMerchantMapper.selectMerchantShopRelationByMerchantUserIds(userIds);
    }

    @Override
    public SysUser getUserIdByName(String userName) {
        return sysUserMapper.selectUserByUserName(userName);
    }

    @Override
    public MerchantShopRelation getMerchantUserIdByShopName(String shopName) {
        Set<String> stringSet = new HashSet<>();
        stringSet.add(shopName);
        List<MerchantShopRelation> list = dtMerchantMapper.selectMerchantShopRelationByShopNames(stringSet);
        return list.size() > 0 ? list.get(0) : null;
    }
}
