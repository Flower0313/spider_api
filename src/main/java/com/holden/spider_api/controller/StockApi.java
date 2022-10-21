package com.holden.spider_api.controller;

import com.alibaba.fastjson.JSONObject;
import com.holden.spider_api.util.ClickHouseUtil;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName spider_api-StockApi
 * @Author Holden_—__——___———____————_____Xiao
 * @Create 2022年8月09日15:41 - 周二
 * @Describe
 */
@RestController
public class StockApi {

    @RequestMapping("value =/getAstock")
    public String stockDetail(HttpServletRequest reqest) throws SQLException {

        String code = reqest.getParameter("code");
        String date = reqest.getParameter("date");
        if ("".equals(code) || "".equals(date) || code == null || date == null) {
            return "{\"message\":\"Incomplete parameters\",\"status\":\"-3\"}";
        }
        if (!timeIsLegel(date)) {
            return "{\"message\":\"Illegal time format\",\"status\":\"-2\"}";
        }
        Connection connection = ClickHouseUtil.getConnection();
        JSONObject result = new JSONObject();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from spider_base.stock_detail where code ='" + code + "' and ds = '" + date + "'");
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        result.put("code", code);
        result.put("date", date);
        while (resultSet.next()) {
            JSONObject data = new JSONObject();
            for (int i = 1; i <= columnCount; i++) {
                data.put(metaData.getColumnName(i), resultSet.getString(i));
            }
            result.put("data", data);
        }
        //若没查询到数据
        if ("".equals(result.getString("data")) || result.getString("data") == null) {
            return "{\"message\":\"No query data\",\"status\":\"-4\"}";
        }
        result.put("message", "ok");
        result.put("status", "-1");
        System.out.println(reqest);
        return result.toString();
    }

    /**
     * @param str 时间字符串
     * @return 返回是否满足时间格式
     */
    public boolean timeIsLegel(String str) {
        Pattern p = Pattern.compile("\\d{4}\\-\\d{1,2}\\-\\d{1,2}");//构造一个模式
        Matcher m = p.matcher(str);//构造一个匹配器
        return m.matches();
    }

    @RequestMapping("value =/index")
    public ModelAndView test3(HttpServletRequest reqest, Model model) throws SQLException {
        String code = reqest.getParameter("code");
        if (code == null || "".equals(code)) {
            ModelAndView mv = new ModelAndView("404");
            return mv;
        }
        Connection connection = ClickHouseUtil.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select ds,closing_price from spider_base.stock_detail where code = '" + code + "' order by rk desc limit 14;");
        List<String> data = new ArrayList<String>();
        List<String> line = new ArrayList<String>();
        ResultSetMetaData metaData = resultSet.getMetaData();
        while (resultSet.next()) {
            line.add(resultSet.getString(1).substring(5));
            data.add(resultSet.getString(2));
        }
        Object[] datas = data.toArray();
        Object[] lines = line.toArray();
        System.out.println();
        model.addAttribute("datas", StringUtils.strip(Arrays.toString(datas), "[]"));
        model.addAttribute("lines", StringUtils.strip(Arrays.toString(lines), "[]"));
        model.addAttribute("detail", getDetail(code));
        model.addAttribute("code", code);

        ModelAndView mv = new ModelAndView("HelloWorld");
        return mv;

    }

    public HashMap getDetail(String code) throws SQLException {
        Connection connection = ClickHouseUtil.getConnection();
        Statement statement = connection.createStatement();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = new Date(System.currentTimeMillis() - 86400000L);
        ResultSet resultSet = statement.executeQuery("select * from spider_base.stock_detail where code ='" + code + "' and ds = '" + formatter.format(date) + "'");
        ResultSetMetaData metaData = resultSet.getMetaData();
        HashMap<String, String> stringMap = new HashMap<>();
        int columnCount = metaData.getColumnCount();
        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                stringMap.put(metaData.getColumnName(i), resultSet.getString(i));
            }
        }
        //若没查询到数据
        return stringMap;
    }

    @RequestMapping("value =/lrx")
    public String lrx(){
        return "刘瑞歆老狗给肖华陛下转100元";
    }

}
