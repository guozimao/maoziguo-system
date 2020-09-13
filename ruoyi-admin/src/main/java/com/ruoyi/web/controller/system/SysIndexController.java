package com.ruoyi.web.controller.system;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import com.ruoyi.businessteam.service.IDtSalesmanService;
import com.ruoyi.common.constant.UserConstants;

import com.ruoyi.system.domain.SysNotice;
import com.ruoyi.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import com.ruoyi.common.config.Global;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysMenu;
import com.ruoyi.system.domain.SysUser;

/**
 * 首页 业务处理
 * 
 * @author ruoyi
 */
@Controller
public class SysIndexController extends BaseController
{
    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private IDtSalesmanLeaderService salesmanLeaderService;

    @Autowired
    private IDtSalesmanService dtSalesmanService;

    @Autowired
    private IDtMerchantService dtMerchantService;

    @Autowired
    private ISysNoticeService sysNoticeService;

    // 系统首页
    @GetMapping("/index")
    public String index(ModelMap mmap)
    {
        // 取身份信息
        SysUser user = ShiroUtils.getSysUser();
        // 根据用户id取出菜单
        List<SysMenu> menus = menuService.selectMenusByUser(user);
        mmap.put("menus", menus);
        mmap.put("user", user);
        mmap.put("sideTheme", configService.selectConfigByKey("sys.index.sideTheme"));
        mmap.put("skinName", configService.selectConfigByKey("sys.index.skinName"));
        mmap.put("copyrightYear", Global.getCopyrightYear());
        mmap.put("demoEnabled", Global.isDemoEnabled());
        return "index";
    }

    // 切换主题
    @GetMapping("/system/switchSkin")
    public String switchSkin(ModelMap mmap)
    {
        return "skin";
    }

    // 系统介绍
    @GetMapping("/system/main")
    public String main(ModelMap mmap)
    {
        SysUser user = ShiroUtils.getSysUser();
        if(UserConstants.SALEMAN_LEADER_TYPE.equals(user.getUserType())){
            Long salesManLeaderId = salesmanLeaderService.selectIdByUserId(user.getUserId());
            mmap.put("salesManLeaderId",salesManLeaderId);
        }else if(UserConstants.SALEMAN_USER_TYPE.equals(user.getUserType())){
            Long salesManId = dtSalesmanService.selectIdByUserId(user.getUserId());
            mmap.put("salesManId",salesManId);
        }else if(UserConstants.MERCHANT_USER_TYPE.equals(user.getUserType())){
            Long merchantId =dtMerchantService.selectIdByUserId(user.getUserId());
            mmap.put("merchantId",merchantId);
        }
        List<Long> noticeIds = sysNoticeService.selectNoticeIdsList4Last();
        List<SysNotice> sysNotices  = sysNoticeService.selectNoticeListByIds(noticeIds);
        Map<String,SysNotice> typeAndNoticeMap = sysNotices.stream().collect(Collectors.toMap(SysNotice::getNoticeType,a -> a,(k1,k2) -> k2));
        //mmap.put("version", Global.getVersion());
        //最新通知
        if(typeAndNoticeMap.containsKey("1")){
            mmap.put("advise",typeAndNoticeMap.get("1"));
        }else {
            mmap.put("advise",new SysNotice());
        }
        //最新公告
        if(typeAndNoticeMap.containsKey("2")){
            mmap.put("announcement", typeAndNoticeMap.get("2"));
        }else {
            mmap.put("announcement",new SysNotice());
        }
        mmap.put("user",user);
        return "main";
    }
}
