package com.example.yin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.yin.common.R;
import com.example.yin.model.domain.Admin;
import com.example.yin.model.request.AdminRequest;
import com.example.yin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zzy
 * @since 2024-10-18
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @PostMapping("/login/status")
    public R getLoginStatus(@RequestBody AdminRequest admin, HttpSession session) {
        String name = admin.getUsername();
        String password = admin.getPassword();
        Admin admin1 = adminService.getOne(new QueryWrapper<Admin>().eq("name", name).eq("password", password));
        if ( admin1 != null ) {
            session.setAttribute("admin", name);
            return R.success("登录成功");
        }else {
            return R.error("用户不存在或密码输入错误");
        }
    }

}
