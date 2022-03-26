package com.atguigu.ucenter.service.impl;

import com.atguigu.base.handler.GuliException;
import com.atguigu.common.utils.JwtUtils;
import com.atguigu.ucenter.entity.Member;
import com.atguigu.ucenter.entity.vo.LoginVo;
import com.atguigu.ucenter.entity.vo.RegisterVo;
import com.atguigu.ucenter.mapper.MemberMapper;
import com.atguigu.ucenter.service.MemberService;
import com.atguigu.ucenter.util.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author HaiYu
 * @since 2022-03-01
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Override
    public void register(RegisterVo registerVo) {
        // 获取参数，判空
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();
        String mobile = registerVo.getMobile();
        String code = registerVo.getCode();
        if (StringUtils.isEmpty(nickname) || StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(mobile) || StringUtils.isEmpty(code)) {
            throw new GuliException(20001, "参数缺失");
        }

        // 验证手机号是否重复
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        int count = this.count(wrapper);
        if (count > 0) {
            throw new GuliException(20001, "当前手机号已注册");
        }

        // 验证短信验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if (!Objects.equals(code, redisCode)) {
            throw new GuliException(20001, "验证码错误");
        }

        // 使用MD5加密密码
        password = MD5.encrypt(password);
        // 补充信息后插入数据库
        Member member = new Member();
        member.setNickname(nickname);
        member.setMobile(mobile);
        member.setPassword(password);
        member.setAvatar("https://guli-file-190513.oss-cn-beijing.aliyuncs.com/avatar/default.jpg");
        member.setIsDisabled(false);

        boolean save = this.save(member);
        if (!save) {
            throw new GuliException(20001, "插入数据库失败");
        }
    }

    @Override
    public String login(LoginVo loginVo) {
        // 获取参数判空
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new GuliException(20001, "手机号或密码错误");
        }

        // 根据手机号获取用户信息
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        Member member = this.getOne(wrapper);
        if (member == null) {
            throw new GuliException(20001, "手机号或密码错误");
        }

        // 密码加密后验证密码
        String md5Password = MD5.encrypt(password);
        if (!Objects.equals(md5Password, member.getPassword())) {
            throw new GuliException(20001, "手机号或密码错误");
        }

        // 生成token字符串
        return JwtUtils.getJwtToken(member.getId(), member.getNickname());
    }

    // 统计注册人数，给statistics模块调用
    @Override
    public Integer countRegister(String day) {
        return baseMapper.countRegister(day);
    }
}
