package com.zhs.utils;

import lombok.Cleanup;
import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;

import java.io.*;

/**
 * @project: zhs-blog
 * @author: zhs
 * @date: 2018/12/20 17:06
 * @package: com.zhs.utils
 * @description:
 */
public class Img2Base64Util {


    public static void main(String[] args) {

        // 待处理的图片
        String imgFile = "C:\\Users\\dellpc\\Desktop\\0094.png";
        String imgbese = getImgStr(imgFile);
        System.out.println(imgbese.length());
        System.out.println(imgbese);
        // 新生成的图片
        String imgFilePath = "C:\\Users\\dellpc\\Desktop\\0094.jpg";
        generateImage(imgbese, imgFilePath);
    }

    /**
     * 将图片转换成Base64编码
     * @param imgFile 待处理图片
     * @return
     */

    public static String getImgStr(String imgFile) {
// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;
// 读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(Base64.encodeBase64(data));
    }

    /**
     * 对字节数组字符串进行Base64解码并生成图片
     * @param imgStr 图片数据
     * @param imgFilePath 保存图片全路径地址
     * @return
     */

    public static boolean generateImage(String imgStr, String imgFilePath) {
          //图像数据为空
        if (imgStr == null) {
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
        // Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                // 调整异常数据
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            // 生成jpg图片
            @Cleanup
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            return true;
        } catch (Exception e) {
            return false;
        }

    }
}
