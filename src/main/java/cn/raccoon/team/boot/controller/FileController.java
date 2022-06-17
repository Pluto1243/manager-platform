package cn.raccoon.team.boot.controller;

import cn.raccoon.team.boot.exception.CommonException;
import cn.raccoon.team.boot.exception.EmError;
import cn.raccoon.team.boot.exception.response.R;
import cn.raccoon.team.boot.service.IFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@Api(tags = "文件")
@RequestMapping("/file")
public class FileController {
    // 制作文件的白名单,使用Arrays.asList列出一个常量的字符串
    private static final List<String> CONTENT_TYPES = Arrays.asList("image/gif","image/jpeg","image/png");

    @Autowired
    private IFileService fileService;

    @Value("${file-path}")
    private String path;

    @Value("${file-server}")
    private String server;

    /**
     * @description 上传文件
     *
     * @date 23:28 2022年05月04日
     * @param file
     * @return cn.raccoon.team.boot.exception.response.R<cn.raccoon.team.boot.entity.File>
     */
    @PostMapping("/{directory}/uploadFile")
    @ApiOperation(value = "上传文件")
    public R<cn.raccoon.team.boot.entity.File> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable("directory") String directory) {
        // 获取文件名称
        String originalFilename = file.getOriginalFilename();

        try {
            // 校验文件类型
            String contentType = file.getContentType();
            // 列出所有文件合法类型
            if (!CONTENT_TYPES.contains(contentType)){
                throw new CommonException(EmError.FILE_TYPE_ILLEGAL);
            }

            // 校验文件的内容，ImageIO读取文件内容
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            if (bufferedImage == null) {
                throw new CommonException(EmError.FILE_EXIST);
            }
            //获取文件后缀名
            String suffixName = originalFilename.substring(originalFilename.lastIndexOf("."));
            //重新随机生成名字
            String filename = UUID.randomUUID() +suffixName;
            File realFile = null;
            // 保存到服务器
            if (directory != null) {
                realFile = new File(path + "/"+ directory.trim() + "/" + filename);
            } else {
                realFile = new File(path + "/" + filename);
            }
            if (!realFile.getParentFile().exists()) {
                realFile.setWritable(true, false);
                realFile.getParentFile().mkdirs();
                file.transferTo(realFile);
            } else {
                file.transferTo(realFile);
            }
            cn.raccoon.team.boot.entity.File myFile = new cn.raccoon.team.boot.entity.File();
            if (directory != null) {
                myFile.setPath(server + directory.trim() + "/" + filename);
            } else {
                myFile.setPath(server + filename);
            }
            if (fileService.uploadFile(myFile)) {
                return R.ok(myFile);
            } else {
                return R.error();
            }

        } catch (IOException e) {
            throw new CommonException(EmError.UNKNOWN_ERROR);
        }
    }
}
