package com.zhs.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.zhs.entity.SysPermission;
import com.zhs.service.IPermissionService;
import com.zhs.shiro.MyRealm;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Created with IDEA
 * @author:周华生
 * @Date:2018/8/17 14:39
 *@描述:shiro的配置类
 **/

@Configuration
@Slf4j
public class ShiroConfig {
    //创建shiroFilterFactorybean

    @Autowired
    private IPermissionService permissionService;

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private int timeout;

   // @Value("${spring.redis.password}")
   // private String password;

  @Bean
  public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
      ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
      shiroFilterFactoryBean.setSecurityManager(securityManager);

      //修改认证失败跳转的登录页面  默认的是login.jsp
      shiroFilterFactoryBean.setLoginUrl("/login");

      shiroFilterFactoryBean.setUnauthorizedUrl("/v1/api/unauthorizedUrl");
      //添加shiro的内置过滤器
      /*
      shiro内置过滤器。可以实现相关的拦截器
      常用的过滤器:
      anon:无需认证（登录）可以访问
      authc:必须认证才可以访问
      user:如果使用了remeberMe的功能可以直接访问
      perms:该资源必须得到资源权限才可以访问
      sysRole:该资源必须得到角色权限才可以访问
       */
      Map<String,String> filterMap=new LinkedHashMap<>(16);

      //swagger测试接口
      filterMap.put("/swagger-ui.html", "anon");
      filterMap.put("/swagger-resources/**", "anon");
      filterMap.put("/v2/api-docs/**", "anon");
      filterMap.put("/webjars/springfox-swagger-ui/**", "anon");

      //登录接口
      filterMap.put("/v1/api/login","anon");

      /**
       * websocket放行路径
       */
      filterMap.put("/websocket","anon");
      filterMap.put("/ws-push","anon");
      filterMap.put("/welcome","anon");
      filterMap.put("/endpointWisely","anon");
      filterMap.put("/topic","anon");
      filterMap.put("/topic/getResponse","anon");
      filterMap.put("/user","anon");
      filterMap.put("/msg","anon");
      //注册接口
      filterMap.put("/v1/api/register","anon");

      //获取加密
      filterMap.put("/v1/api/getPairKey","anon");

      //退出访问
      filterMap.put("/logout", "logout");


      //静态资源
      filterMap.put("/js/**","anon");
      filterMap.put("/css/**","anon");
      filterMap.put("/favicon.ico","anon");
      filterMap.put("/fonts/**","anon");
      filterMap.put("/img/**","anon");


      List<SysPermission> list=permissionService.findAllPermission();

      for(SysPermission tp: list) {
          log.info("被拦截的接口有"+tp);
              String perm = "perms[" + tp.getPerms() + "]";
              filterMap.put(tp.getResurl(), perm);
      }
      filterMap.put("/**","authc");

      shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
      return  shiroFilterFactoryBean;
  }
    /**
     * decription: /添加shiro与thymeleaf的标签
     * @param
     * @return: at.pollux.thymeleaf.shiro.dialect.ShiroDialect
     * @auther: zhs
     * @date: 2018/10/18 10:42
     * @remarks:
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }
    /**
     * decription: 创建DefaultWebSecurityManager
     * @param telnetRealm ]
     * @return: org.apache.shiro.web.mgt.DefaultWebSecurityManager
     * @auther: zhs
     * @date: 2018/10/18 15:52
     * @remarks:
     */
    @Bean(name ="securityManager" )
   public DefaultWebSecurityManager getDefaultWebSecurityManager( MyRealm telnetRealm){
       DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        //使用redis缓存
        securityManager.setCacheManager(cacheManager());
       //关联realm
       securityManager.setRealm(telnetRealm);
        securityManager.setRememberMeManager(rememberMeManager());
       return  securityManager;
   }

    /**
     * decription: 自定义realm
     * @param matcher
     * @return: com.qq.shiro.MyRealm
     * @auther: zhs
     * @date: 2018/10/18 10:42
     * @remarks:    
     */
    @Bean(name = "realm")
    public MyRealm getRealm(HashedCredentialsMatcher matcher){
        MyRealm realm = new MyRealm();
        realm.setCredentialsMatcher(matcher);
        return realm;
    }


    /**
     * decription:
     * @param
     * @return: org.crazycake.shiro.RedisManager
     * @auther: zhs
     * @date: 2018/10/18 15:52
     * @remarks:    
     */
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        // 配置缓存过期时间
        redisManager.setExpire(1800);
        redisManager.setTimeout(timeout);
       // redisManager.setPassword(password);
        return redisManager;
    }

    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     * @return
     */
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }
    /**
     * 密码匹配凭证管理器
     *
     * @return
     */
    @Bean(name = "hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        // 采用MD5方式加密
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        // 设置加密次数
        hashedCredentialsMatcher.setHashIterations(1024);
        return hashedCredentialsMatcher;
    }

    /**
     * cookie对象;
     * @return
     */
    public SimpleCookie rememberMeCookie(){
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //<!-- 记住我cookie生效时间30天 ,单位秒;-->
        simpleCookie.setMaxAge(2592000);
        return simpleCookie;
    }
    /**
     * cookie管理对象;记住我功能
     * @return
     */
    public CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(Base64.decode("3AvVhmFLUs0KTA3Kprsdag=="));
        return cookieRememberMeManager;
    }

    @Bean
    public SessionDAO sessionDAO() {

        return new MemorySessionDAO();
    }
}