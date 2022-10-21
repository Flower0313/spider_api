package com.holden.spider_api.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName spider_api-PicBanner
 * @Author Holden_—__——___———____————_____Xiao
 * @Create 2022年10月21日18:53 - 周五
 * @Describe
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PicBanner {
    private Long id;
    private String picName;
    private String picUrl;
}
