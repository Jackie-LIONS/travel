    package com.lion.security.handler;

    import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
    import com.lion.domain.Admin;
    import com.lion.domain.Permission;
    import com.lion.mapper.AdminMapper;
    import com.lion.service.AdminService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.core.GrantedAuthority;
    import org.springframework.security.core.authority.SimpleGrantedAuthority;
    import org.springframework.security.core.userdetails.User;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.core.userdetails.UserDetailsService;
    import org.springframework.security.core.userdetails.UsernameNotFoundException;
    import org.springframework.stereotype.Service;

    import java.util.ArrayList;
    import java.util.List;

    @Service
    public class MyUserDetailService implements UserDetailsService {
        @Autowired
        private AdminService adminService;


        // 自定义认证逻辑
        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            // 1.认证
            Admin admin = adminService.findByAdminName(username);
            if (admin == null) {
                throw new UsernameNotFoundException("用户不存在");
            }
    //        admin的状态是不可登录则报错（自定义）
            if (!admin.isStatus()){
                throw new UsernameNotFoundException("用户不可用");
            }


            // 2.授权
            List<Permission> permissions = adminService.findAllPermission(username);
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            for (Permission permission : permissions) {
                grantedAuthorities.add(new SimpleGrantedAuthority(permission.getPermissionDesc()));
            }


            // 3.封装为UserDetails对象
            UserDetails userDetails = User.withUsername(admin.getUsername())
                    .password(admin.getPassword())
                    .authorities(grantedAuthorities)
                    .build();


            // 4.返回封装好的UserDetails对象
            return userDetails;
        }
    }

