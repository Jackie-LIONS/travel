package com.lion.controller.frontdesk;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lion.domain.Member;
import com.lion.domain.Product;
import com.lion.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

// 收藏
@Controller
@RequestMapping("/frontdesk/favorite")
public class FavoriteController {
    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private HttpSession session;
    // 收藏
    @RequestMapping("/add")
    public String add(Integer pid,  @RequestHeader("Referer")String referer){
        Member member = (Member)session.getAttribute("member");
        favoriteService.addFavorite(pid,member.getMid());
        return "redirect:"+referer;
    }
    // 取消收藏
    @RequestMapping("/del")
    public String del(Integer pid, @RequestHeader("Referer")String referer){
        Member member = (Member)session.getAttribute("member");
        System.out.println("**************************"+member+"******************");
        favoriteService.delFavorite(pid,member.getMid());
        return "redirect:"+referer;
    }

    // 我的收藏
    @RequestMapping("/myFavorite")
    public ModelAndView myFavorite(@RequestParam(defaultValue = "1") int page,
                                   @RequestParam(defaultValue = "10") int size){
        ModelAndView modelAndView = new ModelAndView();
        Member member = (Member)session.getAttribute("member");
        System.out.println("***************======********"+member+"******************");
        Page<Product> productPage = favoriteService.findMemberFavorite(page, size, member.getMid());
        modelAndView.addObject("productPage",productPage);
        modelAndView.setViewName("/frontdesk/my_favorite");
        return modelAndView;
    }
}
