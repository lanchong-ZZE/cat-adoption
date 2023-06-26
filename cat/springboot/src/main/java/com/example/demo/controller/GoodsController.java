package com.example.demo.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.common.Result;
import com.example.demo.entity.Cat;
import com.example.demo.entity.Adopt;
import com.example.demo.entity.Goods;
import com.example.demo.entity.Orders;
import com.example.demo.entity.Shopcart;
import com.example.demo.entity.Post;
import com.example.demo.mapper.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Resource
    CatMapper catMapper;
    @Resource
    AdoptMapper adoptMapper;
    @Resource
    GoodsMapper goodsMapper;
    @Resource
    ShopcartMapper shopcartMapper;
    @Resource
    OrdersMapper ordersMapper;
    @Resource
    PostMapper postMapper;

    @GetMapping("/show_cat")
    public Result<?> show_cat(){
        List<Cat> cat = catMapper.selectList(Wrappers.<Cat>lambdaQuery().orderByAsc(Cat::getState));
        return Result.success(cat);
    }

    @PostMapping("/show_adopt")
    public Result<?> show_adopt(@RequestBody Adopt adopt){
        List<Adopt> res = adoptMapper.selectList(Wrappers.<Adopt>lambdaQuery()
                .eq(Adopt::getUserid,adopt.getUserid())
                .orderByDesc(Adopt::getAdoptid));
        return Result.success(res);
    }

    @RequestMapping("/find_adopt")
    public Result<?> find_adopt(@RequestParam("userid") Integer userid){
        adoptMapper.getAdpot(userid);
        return Result.success(adoptMapper.getAdpot(userid));
    }

    @PostMapping("/add_adopt")
    public Result<?> add_adopt(@RequestBody Adopt adopt){
        adoptMapper.insert(adopt);
        return Result.success();
    }

    @PutMapping("/update_cat_state")
    public Result<?> update_cat_state(@RequestBody Cat cat){
        catMapper.updateById(cat);
        return Result.success();
        //catMapper.selectById(cat.getCatid())
    }

    @GetMapping("/show_goods")
    public Result<?> show_goods(){
        List<Goods> goods = goodsMapper.selectList(null);
        return Result.success(goods);
    }

    @PostMapping("/show_shopcart")
    public Result<?> show_shopcart(@RequestBody Shopcart shopcart){
        List<Shopcart> res = shopcartMapper.selectList(Wrappers.<Shopcart>lambdaQuery()
                .eq(Shopcart::getUserid,shopcart.getUserid())
                .orderByAsc(Shopcart::getShopcartid));
        return Result.success(res);
    }

    @PostMapping("/find_shopcart")
    public Result<?> find_shopcart(@RequestBody Shopcart shopcart){
        Shopcart res = shopcartMapper.selectOne(Wrappers.<Shopcart>lambdaQuery().eq(Shopcart::getUserid,shopcart.getUserid()).eq(Shopcart::getGoodsid,shopcart.getGoodsid()));
        return Result.success(res);
    }

    @PutMapping("/add_again_shopcart")
    public Result<?> add_again_shopcart(@RequestBody Shopcart shopcart){
        shopcartMapper.updateById(shopcart);
        return Result.success();
    }

    @PostMapping("/add_shopcart")
    public Result<?> add_shopcart(@RequestBody Shopcart shopcart){
        shopcartMapper.insert(shopcart);
        return Result.success();
    }

    @DeleteMapping("/delete_shopcart")
    public Result<?> delete_shopcart(@RequestParam("shopcartid") Integer id){
        System.out.println(id);
        shopcartMapper.deleteById(id);
        return Result.success();
    }

    @PostMapping("/show_orders")
    public Result<?> show_orders(@RequestBody Orders orders){
        List<Orders> res = ordersMapper.selectList(Wrappers.<Orders>lambdaQuery()
                .eq(Orders::getUserid,orders.getUserid())
                .orderByDesc(Orders::getOrdersid));
        return Result.success(res);
    }

    @PostMapping("/add_orders")
    public Result<?> add_orders(@RequestBody Orders orders){
        ordersMapper.insert(orders);
        return Result.success();
    }

    @GetMapping("/show_post")
    public Result<?> show_post(){
        List<Post> post = postMapper.selectList(Wrappers.<Post>lambdaQuery().orderByDesc(Post::getPostid));
        return Result.success(post);
    }

    @PostMapping("/add_post")
    public Result<?> add_post(@RequestBody Post post){
        postMapper.insert(post);
        return Result.success();
    }

    @PostMapping("/show_personal_post")
    public Result<?> show_personal_post(@RequestBody Post post){
        List<Post> res = postMapper.selectList(Wrappers.<Post>lambdaQuery()
                .eq(Post::getUserid,post.getUserid())
                .orderByDesc(Post::getPostid));
        return Result.success(res);
    }

    @DeleteMapping("/delete_personal_post")
    public Result<?> delete_personal_post(@RequestParam("postid") Integer id){
        System.out.println(id);
        postMapper.deleteById(id);
        return Result.success();
    }
}
