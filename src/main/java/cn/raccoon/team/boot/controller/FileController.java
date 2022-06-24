package cn.raccoon.team.boot.controller;

import cn.raccoon.team.boot.exception.response.R;
import cn.raccoon.team.boot.service.IFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Api(tags = "文件")
@RequestMapping("/file")
public class FileController {
    @Autowired
    private IFileService fileService;

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
        return R.ok(fileService.uploadFile(file, directory));
    }
}
