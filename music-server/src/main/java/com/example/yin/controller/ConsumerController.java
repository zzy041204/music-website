package com.example.yin.controller;

import com.example.yin.common.R;
import com.example.yin.domain.Consumer;
import com.example.yin.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zzy
 * @since 2024-10-18
 */
@RestController
@RequestMapping("/user")
public class ConsumerController {

    @Autowired
    ConsumerService consumerService;

    /**
     * 返回所有用户
     * @return
     */
    @GetMapping("")
    public R getAllUser(){
        List<Consumer> list = consumerService.list();
        return R.success("所有用户返回成功",list);
    }

    /**
     * 根据给定的ID返回用户
     * @param id
     * @return
     */
    @GetMapping("/detail")
    public R getUserOfId(@RequestParam("id") Integer id){
        Consumer consumer = consumerService.getById(id);
        if(consumer != null){
            return R.success("返回指定的ID用户成功",consumer);
        }else {
            return R.error("返回指定的ID用户失败");
        }
    }

    /**
     * 删除指定用户
     * @param id
     * @return
     */
    @GetMapping("/delete")
    public R deleteUser(@RequestParam("id") Integer id){
        boolean remove = consumerService.removeById(id);
        if (remove){
            return R.success("删除指定的ID用户成功");
        }else {
            return R.error("删除指定的ID用户失败");
        }
    }


}
