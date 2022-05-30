package cn.raccoon.team.boot.mapper;

import cn.raccoon.team.boot.entity.File;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface FileMapper extends BaseMapper<File> {

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    Boolean insertFile(File file);
}
