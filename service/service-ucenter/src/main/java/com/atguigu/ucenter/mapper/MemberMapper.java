package com.atguigu.ucenter.mapper;

import com.atguigu.ucenter.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author HaiYu
 * @since 2022-03-01
 */
public interface MemberMapper extends BaseMapper<Member> {

    // 统计注册人数，给statistics模块调用
    Integer countRegister(String day);
}
