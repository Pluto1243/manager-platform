package cn.raccoon.team.boot.service;

import cn.raccoon.team.boot.entity.File;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Date 2021/12/20 16:32
 */
public interface IFileService {
    /**
     * 上传文件
     *
     * @param file 文件
     * @param  directory 文件夹名称
     * @return cn.raccoon.team.boot.entity.File
     */
    File uploadFile(MultipartFile file, String directory);
}
