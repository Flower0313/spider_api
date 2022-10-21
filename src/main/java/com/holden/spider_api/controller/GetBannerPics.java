package com.holden.spider_api.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;

/**
 * @ClassName spider_api-GetBannerPics
 * @Author Holden_—__——___———____————_____Xiao
 * @Create 2022年10月21日18:07 - 周五
 * @Describe
 */
@RestController
public class GetBannerPics {
    @GetMapping(value = "/getpic")
    public void index(HttpServletResponse resp,long id) throws SQLException, IOException {
        FileSystemResource file = new FileSystemResource("D:\\WeChatProjects\\SeniorNursing\\pages\\index\\imgs\\1.jpg");
        InputStream inputStream = null;
        BufferedInputStream bufferedInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        //将文件变成excel格式
        resp.setContentType("image/png");
        try {
            inputStream = file.getInputStream();
            bufferedInputStream = new BufferedInputStream(inputStream);
            bufferedOutputStream = new BufferedOutputStream(resp.getOutputStream());
            FileCopyUtils.copy(bufferedInputStream, bufferedOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != inputStream) {
                inputStream.close();
            }
            if (null != bufferedInputStream) {
                bufferedInputStream.close();
            }
            if (null != bufferedOutputStream) {
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
            }
        }

    }
}
