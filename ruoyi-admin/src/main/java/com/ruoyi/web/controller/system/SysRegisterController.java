package com.ruoyi.web.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.framework.shiro.service.SysRegisterService;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.service.ISysConfigService;

import javax.validation.Valid;

/**
 * 注册验证
 * 
 * @author ruoyi
 */
@Controller
public class SysRegisterController extends BaseController
{
    @Autowired
    private SysRegisterService registerService;

    @Autowired
    private ISysConfigService configService;

    @GetMapping("/register")
    public String register()
    {
        return "register";
    }

    @PostMapping("/register")
    @ResponseBody
    public AjaxResult ajaxRegister(@Valid SysUser user, BindingResult bindingResult)
    {
        if(bindingResult.getErrorCount() > 0){
            return doPocessVailResult(bindingResult);
        }
        if (!("true".equals(configService.selectConfigByKey("sys.account.registerUser"))))
        {
            return error("当前系统没有开启注册功能！");
        }
        String msg = registerService.register(user);
        return StringUtils.isEmpty(msg) ? success() : error(msg);
    }

    private AjaxResult doPocessVailResult(BindingResult bindingResult) {
        StringBuffer sb = new StringBuffer();
        for(ObjectError error : bindingResult.getAllErrors()){
            sb.append(error.getDefaultMessage() + ".");
        }
        return error(sb.toString());
    }
}
