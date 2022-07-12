package cn.raccoon.team.boot.service.impl;

import cn.raccoon.team.boot.entity.File;
import cn.raccoon.team.boot.exception.CommonException;
import cn.raccoon.team.boot.exception.EmError;
import cn.raccoon.team.boot.mapper.FileMapper;
import cn.raccoon.team.boot.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @Date 2021/12/20 16:33
 */
@Service
public class FileServiceImpl implements IFileService {

    @Autowired
    private FileMapper mapper;

    @Value("${file-path}")
    private String path;

    @Override
    public File uploadFile(MultipartFile file, String directory) {

        File upload = transferFile(file, directory);

        mapper.insertFile(upload);

        return upload;
    }

    /**
     * @description 上传文件并返回文件信息
     *
     * @author wangjie
     * @date 15:07 2022年06月24日
     * @param file
     * @param directory
     * @return cn.raccoon.team.boot.entity.File
     */
    private File transferFile(MultipartFile file, String directory) {
        BufferedInputStream bis = null;
        File myFile = new File();
        try {
            // 获取文件名称
            String originalFilename = file.getOriginalFilename();
            // 校验文件的内容
            bis = new BufferedInputStream(file.getInputStream());
            if (bis == null) {
                throw new CommonException(EmError.FILE_EXIST);
            }
            // 获取文件后缀名
            String suffixName = originalFilename.substring(originalFilename.lastIndexOf("."));
            // 重新随机生成名字
            String filename = UUID.randomUUID() + suffixName;
            java.io.File realFile = null;
            // 文件存放路径
            String realPath = null;

            if (StringUtils.hasLength(directory)) {
                realPath = directory.trim() + "/" + filename;
            } else {
                realPath = filename;
            }
            // 保存到服务器
            realFile = new java.io.File(path + "/" + realPath);
            myFile.setPath(realPath);

            if (!realFile.getParentFile().exists()) {
                realFile.setWritable(true, false);
                realFile.getParentFile().mkdirs();
                file.transferTo(realFile);
            } else {
                file.transferTo(realFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return myFile;
    }
}
