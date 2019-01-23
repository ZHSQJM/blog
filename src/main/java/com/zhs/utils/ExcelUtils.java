package com.zhs.utils;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.zhs.ResultData;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @project: zhs-blog
 * @author: zhs
 * @date: 2018/12/21 16:26
 * @package: com.zhs.utils
 * @description: 阿里巴巴的excel导出
 */
public class ExcelUtils {

    /**
     * 导出
     * @param list
     * @param response
     * @param clazz
     * @return
     */

    public static void export(List<? extends BaseRowModel> list, HttpServletResponse response, Class<? extends BaseRowModel> clazz) {
      //  ResultData rd=null;
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX, true);
        try {
          //  rd =new ResultData();
            String fileName = new String(
                    (new SimpleDateFormat("yyyy-MM-dd").format(new Date())).getBytes(), "UTF-8");
            Sheet sheet2 = new Sheet(2, 3,clazz, "sheet", null);
            writer.write(list, sheet2);
//response.setContentType("multipart/form-data");
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName + ".xlsx", "utf-8"));
//response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            out.flush();
         //   rd.setMsg("导出成功");
        } catch (Exception e) {
            e.printStackTrace();
          //  rd.setMsg("导出失败");
           // return rd;
        } finally {
            writer.finish();
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
          //  return rd;
        }
    }
}
