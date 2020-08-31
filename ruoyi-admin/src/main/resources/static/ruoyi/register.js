
$(function() {
    validateRule();
    $('.imgcode').click(function() {
		var url = ctx + "captcha/captchaImage?type=" + captchaType + "&s=" + Math.random();
		$(".imgcode").attr("src", url);
	});
});
$.validator.addMethod("isEmail",function(value,element,params){
    var checkEmail = /^[a-z0-9]+@([a-z0-9]+\.)+[a-z]{2,4}$/i;
    return this.optional(element)||(checkEmail.test(value));
},"*请输入正确的邮箱！");

//手机号码验证
$.validator.addMethod("isMobile", function(value, element) {
    var length = value.length;
    var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
    return this.optional(element) || (length == 11 && mobile.test(value));
}, "请正确填写手机号码");

$.validator.setDefaults({
    submitHandler: function() {
    	register();
    }
});

function register() {
	$.modal.loading($("#btnSubmit").data("loading"));
	var loginName = $.common.trim($("input[name='loginName']").val());
    var password = $.common.trim($("input[name='password']").val());
    var username = $.common.trim($("input[name='username']").val());
    var phonenumber = $.common.trim($("input[name='phonenumber']").val());
    var email = $.common.trim($("input[name='email']").val());
    var validateCode = $("input[name='validateCode']").val();
    var userType = $("input[name='userType']:checked").val();
    $.ajax({
        type: "post",
        url: ctx + "register",
        data: {
            "loginName": loginName,
            "password": password,
            "userName":username,
            "phonenumber":phonenumber,
            "email": email,
            "validateCode": validateCode,
            "userType":userType
        },
        success: function(r) {
            if (r.code == 0) {
            	layer.alert("<font color='red'>恭喜你，您的账号 " + loginName + " 注册成功！</font>", {
        	        icon: 1,
        	        title: "系统提示"
        	    },
        	    function(index) {
        	        //关闭弹窗
        	        layer.close(index);
        	        location.href = ctx + 'login';
        	    });
            } else {
            	$.modal.closeLoading();
            	$('.imgcode').click();
            	$(".code").val("");
            	$.modal.msg(r.msg);
            }
        }
    });
}

function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
    $("#registerForm").validate({
        rules: {
            username: {
                required: true,
                minlength: 2
            },
            loginName: {
                required: true,
                minlength: 2
            },
            phonenumber: {
                required: true,
                minlength : 11,
                isMobile : true
            },
            email: {
                required: true,
                isEmail : true
            },
            password: {
                required: true,
                minlength: 5
            },
            confirmPassword: {
                required: true,
                equalTo: "[name='password']"
            }
        },
        messages: {
            username: {
                required: icon + "请输入您的昵称",
                minlength: icon + "用户名不能小于2个字符"
            },
            loginName: {
                required: icon + "请输入您的账号",
                minlength: icon + "用户名不能小于2个字符"
            },
            phonenumber:{
                required : icon + "请输入手机号",
                minlength : icon + "不能小于11个字符",
                isMobile : icon + "请正确填写手机号码"
            },
            email:{
                required: icon + "请输入邮箱",
                isEmail: icon + "请填写正确的邮箱"
            },
            password: {
            	required: icon + "请输入您的密码",
                minlength: icon + "密码不能小于5个字符",
            },
            confirmPassword: {
                required: icon + "请再次输入您的密码",
                equalTo: icon + "两次密码输入不一致"
            }
        }
    })
}
