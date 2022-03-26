package com.atguigu.ucenter.controller;


import com.atguigu.base.entity.Result;
import com.atguigu.common.utils.JwtUtils;
import com.atguigu.common.vo.MemberForOrder;
import com.atguigu.ucenter.entity.Member;
import com.atguigu.ucenter.entity.vo.LoginVo;
import com.atguigu.ucenter.entity.vo.RegisterVo;
import com.atguigu.ucenter.service.MemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author HaiYu
 * @since 2022-03-01
 */
@Api(description = "前台用户管理")
@RestController
@RequestMapping("/ucenter/member")
public class MemberController {
    @Autowired
    MemberService memberService;

    @ApiOperation("用户注册")
    @PostMapping("/register")
    public Result register(@RequestBody RegisterVo registerVo) {
        memberService.register(registerVo);
        return Result.ok();
    }

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public Result login(@RequestBody LoginVo loginVo) {
        String token = memberService.login(loginVo);
        String memberId = JwtUtils.getMemberIdByJwtToken(token);
        Member member = memberService.getById(memberId);
        member.setPassword(null);
        return Result.ok().data("token", token).data("member", member);
    }

    @ApiOperation("根据token字符串获取用户信息")
    @GetMapping("/getMemberByToken")
    public Result getMemberByToken(HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        Member member = memberService.getById(memberId);
        if (member != null) {
            member.setPassword(null);
        }
        return member != null ? Result.ok().data("member", member) : Result.error();
    }

    @ApiOperation("查询手机号是否已注册")
    @GetMapping("/queryMemberByPhone/{phone}")
    public Result queryMemberByPhone(@PathVariable String phone) {
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", phone);
        int count = memberService.count(wrapper);
        return count > 0 ? Result.error() : Result.ok();
    }

    @ApiOperation("根据memberId获取用户信息")
    @GetMapping(value = "/getMemberByMemberIdForOrder/{memberId}")
    public MemberForOrder getMemberByMemberIdForOrder(@PathVariable String memberId) {
        Member member = memberService.getById(memberId);

        MemberForOrder memberForOrder = new MemberForOrder();
        BeanUtils.copyProperties(member, memberForOrder);
        return memberForOrder;
    }

    @ApiOperation("统计注册人数，给statistics模块调用")
    @GetMapping("/countRegister/{day}")
    public Integer countRegister(@PathVariable String day) {
        return memberService.countRegister(day);
    }
}

