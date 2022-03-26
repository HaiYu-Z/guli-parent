package com.atguigu.cms.service;

import com.atguigu.cms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author HaiYu
 * @since 2022-02-27
 */
public interface CrmBannerService extends IService<CrmBanner> {

    List<CrmBanner> getAll();
}
