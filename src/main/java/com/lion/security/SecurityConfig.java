package com.lion.security;

import com.lion.security.handler.MyAccessDeniedHandler;
import com.lion.security.handler.MyLoginFailureHandler;
import com.lion.security.handler.MyLoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)     // 开启注解-鉴权配置
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // Spring Security配置
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/backstage/admin_login")         //自定义表单登录
                .usernameParameter("username")          //用户名
                .passwordParameter("password")          //密码
                .loginProcessingUrl("/backstage/admin/login")   //登录提交路径，提交后执行认证逻辑
                .successHandler(new MyLoginSuccessHandler())    //登录成功跳转路径
                .failureHandler(new MyLoginFailureHandler());    //登录失败跳转路径
//          权限拦截配置
        http.authorizeRequests()
                .antMatchers("/backstage/admin/login").permitAll()      //登录不需要认证
                .antMatchers("/backstage/admin_fail").permitAll()       //登录失败不需要认证
                .antMatchers("/backstage/admin_login").permitAll()      //登录页不需要认证
                .antMatchers("/**/*.css","/**/*.js").permitAll()        //静态资源放行
                .antMatchers("/backstage/**").authenticated();          //剩下的都要认证

//        退出登录配置
        http.logout()
                .logoutUrl("/backstage/admin/logout")       //退出登录的路径
                .logoutSuccessUrl("/backstage/admin_login") //退出登录成功后跳转的页面
                .clearAuthentication(true)                  //退出成功后清除认证信息
                .invalidateHttpSession(true);                //退出成功后清除session
//            异常处理
        http.exceptionHandling()
                .accessDeniedHandler(new MyAccessDeniedHandler());  //权限不足处理器

//        关闭csrf防护
        http.csrf().disable();
//        开启跨域访问
        http.cors();

    }
//    密码加密器
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
