package com.example.yin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.yin.common.R;
import com.example.yin.utils.MinioUploadUtil;
import com.example.yin.model.domain.Collect;
import com.example.yin.model.domain.Singer;
import com.example.yin.model.domain.Song;
import com.example.yin.dao.SongMapper;
import com.example.yin.service.CollectService;
import com.example.yin.service.SingerService;
import com.example.yin.service.SongService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.minio.MinioClient;
import io.minio.RemoveObjectArgs;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zzy
 * @since 2024-10-18
 */
@Service
public class SongServiceImpl extends ServiceImpl<SongMapper, Song> implements SongService {

    @Autowired
    CollectService collectService;

    @Autowired
    SingerService singerService;

    @Autowired
    MinioClient minioClient;

    @Value("${minio.bucket-name}")
    private String bucketName;




    @Override
    public R getCollectSongsByUserId(Integer id) {
        // 根据用户id找到收藏列表的所有歌曲id
        List<Collect> collects = collectService.list(new QueryWrapper<Collect>().eq("user_id", id));
        List<Integer> songIds = collects.stream().map(c -> c.getSongId()).collect(Collectors.toList());
        if (songIds.size() > 0 && songIds != null) {
            List<Song> songs = this.listByIds(songIds);
            return R.success("返回的指定用户收藏列表成功",songs);
        }else {
            return R.warning("该用户收藏列表为空");
        }
    }

    @Override
    public R getSongOfSingerName(String name) {
        List<Singer> singers = singerService.list(new QueryWrapper<Singer>().like("name", name));
        List<Integer> singerIds = singers.stream().map(s -> s.getId()).collect(Collectors.toList());
        if (singerIds.size() > 0 && singerIds != null) {
            List<Song> songs = this.list(new QueryWrapper<Song>().in("singer_id", singerIds));
            if (songs.size() > 0 && songs != null) {
                return R.success("根据歌手名查询歌曲成功",songs);
            }else {
                return R.warning("根据歌手名未查询到歌曲");
            }
        }else {
            return R.warning("根据歌手名未查询到歌曲");
        }
    }

    @Transactional
    @Override
    public R updateSongPic(MultipartFile urlFile, int id) {
        String fileName =  urlFile.getOriginalFilename();
        String storeUrlPath = "/user01/singer/song/" + fileName;
        MinioUploadUtil.uploadSongImgFile(urlFile);
        Song song = new Song();
        song.setId(id);
        song.setPic(storeUrlPath);
        if (this.updateById(song)) {
            return R.success("上传成功", storeUrlPath);
        } else {
            return R.error("上传失败");
        }
    }

    @Transactional
    @Override
    public R updateSongUrl(MultipartFile urlFile, int id) {
        Song song = this.getById(id);
        String path = song.getUrl();
        String[] parts = path.split("/");
        String fileName = parts[parts.length - 1];

        //获取原来文件信息
        RemoveObjectArgs removeObjectArgs=RemoveObjectArgs.builder()
                .bucket(bucketName)
                .object(fileName)
                .build();
        //删除原文件
        try {
            minioClient.removeObject(removeObjectArgs);
        } catch (ErrorResponseException e) {
            throw new RuntimeException(e);
        } catch (InsufficientDataException e) {
            throw new RuntimeException(e);
        } catch (InternalException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (InvalidResponseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (ServerException e) {
            throw new RuntimeException(e);
        } catch (XmlParserException e) {
            throw new RuntimeException(e);
        }

        //上传新文件
        fileName = urlFile.getOriginalFilename();
        String s = MinioUploadUtil.uploadFile(urlFile);
        String storeUrlPath = "/"+bucketName+"/" + fileName;
        song.setId(id);
        song.setUrl(storeUrlPath);
        song.setName(fileName);
        if (s.equals("File uploaded successfully!")&&this.updateById(song)) {
            return R.success("更新成功", storeUrlPath);
        } else {
            return R.error("更新失败");
        }
    }

    @Override
    public R updateSongLrc(MultipartFile lrcFile, int id) {
        Song song = this.getById(id);
        if (lrcFile!=null&&!(song.getLyric().equals("[00:00:00]暂无歌词"))){
            byte[] fileContent = new byte[0];
            try {
                fileContent = lrcFile.getBytes();
                String content = new String(fileContent, "GB2312");
                song.setLyric(content);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (this.updateById(song)) {
            return R.success("更新成功");
        } else {
            return R.error("更新失败");
        }
    }

    @Transactional
    @Override
    public R deleteSong(Integer id) {
        //删除minio云端歌曲
        Song song = getById(id);
        String path = song.getUrl();
        String[] parts = path.split("/");
        String fileName = parts[parts.length - 1];
        String s = null;
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket("user01")
                    .object(fileName)
                    .build());
            s = "File deleted successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            s = "File deletion failed!" + e.getMessage();
        }
        //删除数据库歌曲信息
        boolean remove = this.removeById(id);
        if(remove && s.equals("File deleted successfully!")) {
            return R.success("删除成功");
        }else {
            return R.error("删除失败");
        }
    }
}
