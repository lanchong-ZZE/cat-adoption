package com.example.demo.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.common.Result;
import com.example.demo.entity.*;
import com.example.demo.mapper.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    AdminMapper adminMapper;
    @Resource
    CatMapper catMapper;
    @Resource
    GoodsMapper goodsMapper;
    @Resource
    PostMapper postMapper;
    @Resource
    UserMapper userMapper;
    @Resource
    AdoptMapper adoptMapper;
    @Resource
    OrdersMapper ordersMapper;

    @PostMapping("/loginadmin")
    public Result<?> login(@RequestBody Admin admin){
        Admin res = adminMapper.selectOne(Wrappers.<Admin>lambdaQuery().eq(Admin::getAdmin,admin.getAdmin()).eq(Admin::getPwd,admin.getPwd()));
        if(res == null)
        {
            return Result.error("-1","管理员密码错误");
        }
        return Result.success(res);
    }

    @PostMapping("/add_cat")
    public Result<?> add_cat(@RequestBody Cat cat){
        catMapper.insert(cat);
        return Result.success();
    }

    @DeleteMapping("/delete_cat")
    public Result<?> delete_cat(@RequestParam("catid") Integer id){
        System.out.println(id);
        catMapper.deleteById(id);
        return Result.success();
    }

    @PutMapping("/update_cat")
    public Result<?> update_cat(@RequestBody Cat cat){
        catMapper.updateById(cat);
        return Result.success(cat);
    }

    @PostMapping("/add_goods")
    public Result<?> add_goods(@RequestBody Goods goods){
        goodsMapper.insert(goods);
        return Result.success();
    }

    @DeleteMapping("/delete_goods")
    public Result<?> delete_goods(@RequestParam("goodsid") Integer id){
        System.out.println(id);
        goodsMapper.deleteById(id);
        return Result.success();
    }

    @PutMapping("/update_goods")
    public Result<?> update_goods(@RequestBody Goods goods){
        goodsMapper.updateById(goods);
        return Result.success(goods);
    }

    @DeleteMapping("/delete_post")
    public Result<?> delete_post(@RequestParam("postid") Integer id){
        System.out.println(id);
        postMapper.deleteById(id);
        return Result.success();
    }

    @GetMapping("/show_users")
    public Result<?> show_users(){
        List<User> user = userMapper.selectList(null);
        return Result.success(user);
    }

    @DeleteMapping("/delete_users")
    public Result<?> delete_users(@RequestParam("id") Integer id){
        System.out.println(id);
        userMapper.deleteById(id);
        return Result.success();
    }

    @GetMapping("/show_adopt_history")
    public Result<?> show_adopt_history(){
        List<Adopt> adopt = adoptMapper.selectList(null);
        return Result.success(adopt);
    }

    @GetMapping("/show_orders")
    public Result<?> show_orders(){
        List<Orders> orders = ordersMapper.selectList(Wrappers.<Orders>lambdaQuery().orderByAsc(Orders::getUserid));
        return Result.success(orders);
    }

    @PostMapping("/show_apply")
    public Result<?> show_apply(@RequestBody Adopt adopt){
        List<Adopt> res = adoptMapper.selectList(Wrappers.<Adopt>lambdaQuery()
                .eq(Adopt::getAdoptstate,adopt.getAdoptstate())
                .orderByDesc(Adopt::getAdoptid));
        return Result.success(res);
    }

    @PutMapping("/update_apply")
    public Result<?> update_apply(@RequestBody Adopt adopt){
        adoptMapper.updateById(adopt);
        return Result.success(adopt);
    }
}
