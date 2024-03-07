package com.lion.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lion.domain.Product;
import org.apache.ibatis.annotations.Param;

public interface ProductMapper extends BaseMapper<Product> {
    Page<Product> findProductPage(Page<Product> page);
    Product findOne(int pid);



    void addFavorite(@Param("pid") Integer pid, @Param("mid") Integer mid);
    void delFavorite(@Param("pid") Integer pid,@Param("mid") Integer mid);
    Page<Product> findMemberFavorite(Page<Product> page,@Param("mid")Integer mid);
    int findFavoriteByPidAndMid(@Param("pid") Integer pid,@Param("mid")Integer mid);


}
