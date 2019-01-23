package com.zhs;



import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
/**
 *扫描的是dao层
 */
/**
 * decription: 项目启动类 直接运行
 * @param
 * @return:
 * @author: zhs
 * @date: 2018/12/3 14:22
 * @remarks:
 */
@MapperScan(basePackages = "com.zhs.dao")
public class SimpleBlogApplication {

    public static void main(String[] args) {
        System.out.println("dadsa");
        SpringApplication.run(SimpleBlogApplication.class, args);
    }

}

