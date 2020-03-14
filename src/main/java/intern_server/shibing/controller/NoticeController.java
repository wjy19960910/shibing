package intern_server.shibing.controller;

import com.github.pagehelper.PageInfo;
import intern_server.shibing.data.po.Notice;
import intern_server.shibing.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: wangjingyuan
 * @Date: 2020/3/6 16:13
 */
@RestController
@RequestMapping("/auth/notice")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @GetMapping("/fetchNoticeInfoData")
    public PageInfo fetchNoticeInfoData(@RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "10") Integer pageSize, @RequestParam(required = false) String fuzzy,
                                        @RequestParam(required = false) String roleName,@RequestParam(required = false) String teacherNumber,@RequestParam(required = false) String companyNumber){
        return noticeService.selectNoticeInfoData(page,pageSize,fuzzy,roleName,teacherNumber,companyNumber);
    }

    @GetMapping("/fetchNoticeInfoDataAll")
    public PageInfo fetchNoticeInfoDataAll(@RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "10") Integer pageSize, @RequestParam(required = false) String fuzzy){
        return noticeService.selectNoticeInfoDataAll(page,pageSize,fuzzy);
    }

    @PostMapping("/insertNoticeInfo")
    public Map<String,Object> insertNoticeInfo(@RequestBody Notice notice){
        return noticeService.insertNoticeInfo(notice);
    }

    @PutMapping("/editNoticeInfo")
    public Map<String,Object> editNoticeInfo(@RequestBody Notice notice){
       return noticeService.editNoticeInfo(notice);
    }

    @DeleteMapping("/removeNoticeInfo/{ids}")
    public Map<String,Object> removeNoticeInfo(@PathVariable String[] ids){
        return noticeService.removeNoticeInfo(ids);
    }

    @GetMapping("/getNoticeInfoById/{id}")
    public Map<String,Object> getNoticeInfoById(@PathVariable String id){
        return noticeService.getNoticeInfoById(id);
    }

}
