package com.lion.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lion.domain.Product;
import com.lion.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavoriteService {

    @Autowired
    private ProductMapper productMapper;
    /**
     * 查询用户是否收藏线路
     */
    public boolean findFavorite(Integer pid,Integer mid){
        int result = productMapper.findFavoriteByPidAndMid(pid, mid);
        if (result == 0){ // 没有收藏
            return false;
        }else{
            return true; // 收藏了
        }

    }
    // 我的收藏
    public Page<Product> findMemberFavorite(int page,int size,int mid){
        System.out.println("==================="+mid);
        Page favoriteProduct = productMapper.findMemberFavorite(new Page(page, size), mid);
        return favoriteProduct;
    }

    // 收藏
    public void addFavorite(Integer pid,Integer mid){
        productMapper.addFavorite(pid,mid);
    }

    // 取消收藏
    public void delFavorite(Integer pid,Integer mid){
        productMapper.delFavorite(pid,mid);
    }

}
