package com.lion.controller.backstage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lion.domain.Admin;
import com.lion.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/backstage/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;


    @RequestMapping("/all")
    @PreAuthorize("hasAnyAuthority('/admin/all')")
    public ModelAndView all(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        Page<Admin> adminPage = adminService.findPage(page, size);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("adminPage", adminPage);
        modelAndView.setViewName("/backstage/admin_all");
        return modelAndView;
    }

    @RequestMapping("/add")
    public String add(Admin admin) {
        adminService.add(admin);
        return "redirect:/backstage/admin/all";
    }
    // 查询管理员，跳转到修改页面
    @RequestMapping("/edit")
    public ModelAndView edit(Integer aid) {
        Admin admin = adminService.findById(aid);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("admin", admin);
        modelAndView.setViewName("/backstage/admin_edit");
        return modelAndView;
    }


    // 修改管理员
    @RequestMapping("/update")
    public String update(Admin admin) {
        adminService.update(admin);
        return "redirect:/backstage/admin/all";
    }
    @RequestMapping("/desc")
    public ModelAndView desc(Integer aid){
        Admin admin = adminService.findDesc(aid);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("admin", admin);
        modelAndView.setViewName("/backstage/admin_desc");
        return modelAndView;
    }
    @RequestMapping("/updateRole")
    public String manageRole(Integer aid,Integer[] ids){
        adminService.updateRoles(aid,ids);
        return "redirect:/backstage/admin/all";
    }
    @RequestMapping("/updateStatus")
    public String updateStatus(Integer aid){
        adminService.updateStatus(aid);
        return "redirect:/backstage/admin/all";
    }



}

