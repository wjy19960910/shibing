package intern_server.shibing.controller;

import intern_server.shibing.constant.Constants;
import intern_server.shibing.data.po.Report;
import intern_server.shibing.data.po.Student;
import intern_server.shibing.data.po.SysDict;
import intern_server.shibing.data.vo.DealVo;
import intern_server.shibing.service.CommonService;
import intern_server.shibing.utils.Global;
import intern_server.shibing.utils.RedisUtil;
import intern_server.shibing.utils.file.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Author: wangjingyuan
 * @Date: 2020/3/8 21:46
 */
@RestController
public class CommonController {

    @Resource
    RedisUtil redisUtil;

    @Autowired
    private CommonService commonService;

    @RequestMapping("/auth/redis/data/{key}")
    public Object initRedisData(@PathVariable String key) {
        if (StringUtils.isBlank(key)) {
            return "";
        }
        return redisUtil.get(key);
    }

    /**
     * 本地资源通用下载
     */
    @GetMapping("/common/download")
    public void resourceDownload(@RequestParam("resource") String resource, HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
        // 本地资源路径
        String localPath = Global.getProfile();
        // 数据库资源地址
        String downloadPath = localPath +"/"+"download/"+resource;
        // 下载名称
        String downloadName = StringUtils.substringAfterLast(downloadPath, "/");
        response.setCharacterEncoding("utf-8");
        response.setContentType("UTF-8");
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, downloadName));
        FileUtils.writeBytes(downloadPath, response.getOutputStream());
    }

    /**
     * 处理中心通用逻辑
     * @return
     */
    @GetMapping("/common/getDealInfo")
    public List<DealVo> fetchDealInfo(){
        return commonService.fetchDealInfo();
    }

    /**
     * 查询属于登陆老师的学生的信息
     * @return
     */
    @GetMapping("/common/fetchStudentByTeacherNumber")
    public List<Student> fetchStudentByTeacherNumber() throws Exception {
        return commonService.fetchStudentByTeacherNumber();
    }

    @GetMapping("/common/getSysDict")
    public List<SysDict> getSysDict() {
        return commonService.getSysDict();
    }

    public Map<String,Object> createSysDict(@RequestBody SysDict sysDict){
        return commonService.createSysDict(sysDict);

    }


}
