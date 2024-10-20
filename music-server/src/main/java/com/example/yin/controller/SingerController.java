package com.example.yin.controller;

import com.example.yin.common.R;
import com.example.yin.model.domain.Singer;
import com.example.yin.service.SingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/singer")
public class SingerController {

    @Autowired
    SingerService singerService;

    @GetMapping("")
    public R getAllUser(){
        List<Singer> list = singerService.list();
        return R.success("所有歌手返回成功",list);
    }

    /**
     * 新添歌手
     * @param singer
     * @return
     */
    @PostMapping("/add")
    public R setSinger(@RequestBody Singer singer){
        boolean save = singerService.save(singer);
        if(save){
            return R.success("新增歌手成功");
        }else {
            return R.error("新增歌手失败");
        }
    }

    /**
     * 更新歌手信息
     * @param singer
     * @return
     */
    @PostMapping("/update")
    public R updateSingerMsg(@RequestBody Singer singer){
        boolean update = singerService.updateById(singer);
        if (update){
            return R.success("歌手信息更新成功");
        }else {
            return R.error("歌手信息更新失败");
        }
    }

    /**
     * 删除歌手
     * @param id
     * @return
     */
    @DeleteMapping("/delete")
    public R deleteSinger(@RequestParam("id") Integer id){
        boolean delete = singerService.removeById(id);
        if (delete){
            return R.success("歌手删除成功");
        }else {
            return R.error("歌手删除失败");
        }
    }

}
