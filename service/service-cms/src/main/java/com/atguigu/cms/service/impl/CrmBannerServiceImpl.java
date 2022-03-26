package com.atguigu.cms.service.impl;

import com.atguigu.cms.entity.CrmBanner;
import com.atguigu.cms.mapper.CrmBannerMapper;
import com.atguigu.cms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author HaiYu
 * @since 2022-02-27
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Cacheable(value = "banner", key = "'selectIndexList'")
    @Override
    public List<CrmBanner> getAll() {
        return this.list(null);
    }
}
