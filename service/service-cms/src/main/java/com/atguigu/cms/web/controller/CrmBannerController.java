package com.atguigu.cms.web.controller;

import com.atguigu.base.entity.Result;
import com.atguigu.cms.entity.CrmBanner;
import com.atguigu.cms.service.CrmBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 
 * @author HaiYu
 */
@Api(description = "前台banner展示")
@RestController
@RequestMapping("/cms/banner")
public class CrmBannerController {

    @Autowired
    CrmBannerService crmBannerService;

    @ApiOperation("查询所有banner信息")
    @GetMapping("/getAll")
    public Result getAll() {
        List<CrmBanner> list = crmBannerService.getAll();
        return Result.ok().data("list", list);
    }
}
