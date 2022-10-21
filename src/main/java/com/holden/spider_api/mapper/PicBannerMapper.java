package com.holden.spider_api.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @ClassName spider_api-PicBannerMapper
 * @Author Holden_—__——___———____————_____Xiao
 * @Create 2022年10月21日18:56 - 周五
 * @Describe
 */
public interface PicBannerMapper {
    List<Map<String, String>> allPicBanner();
}
