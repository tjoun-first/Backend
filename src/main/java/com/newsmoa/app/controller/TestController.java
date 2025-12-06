package com.newsmoa.app.controller;

import com.newsmoa.app.service.AdminService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final AdminService adminService;

    public TestController(AdminService adminService) {
        this.adminService = adminService;
    }

    @RequestMapping("/refresh")
    public String refresh() {
        adminService.refreshArticle();
        return "refreshed";
    }
}
