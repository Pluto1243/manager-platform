package cn.raccoon.team.boot.service.impl;

import cn.raccoon.team.boot.entity.File;
import cn.raccoon.team.boot.mapper.FileMapper;
import cn.raccoon.team.boot.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Date 2021/12/20 16:33
 */
@Service
public class FileServiceImpl implements IFileService {

    @Autowired
    private FileMapper mapper;

    @Override
    public Boolean uploadFile(File file) {
        return mapper.insertFile(file);
    }
}
