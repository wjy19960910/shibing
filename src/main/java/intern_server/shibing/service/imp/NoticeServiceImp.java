package intern_server.shibing.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import intern_server.shibing.dao.NoticeDao;
import intern_server.shibing.data.po.Notice;
import intern_server.shibing.enums.RoleEnum;
import intern_server.shibing.service.NoticeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author: wangjingyuan
 * @Date: 2020/3/6 17:06
 */
@Service
public class NoticeServiceImp implements NoticeService {

    @Autowired
    private NoticeDao noticeDao;


    /**
     * 分页查询公告信息
     * @param page
     * @param pageSize
     * @param fuzzy
     * @param roleName
     * @param teacherNumber
     * @param companyNumber
     * @return
     */
    @Override
    public PageInfo selectNoticeInfoData(Integer page, Integer pageSize, String fuzzy, String roleName, String teacherNumber, String companyNumber) {

        PageHelper.startPage(page,pageSize);
        Notice notice = new Notice();
        if(!StringUtils.isEmpty(fuzzy)){
            notice.setFuzzy(fuzzy);
        }
        if(!StringUtils.isEmpty(teacherNumber)){
            notice.setTeacherNumber(teacherNumber);
        }
        if(!StringUtils.isEmpty(companyNumber)){
            notice.setCompanyNumber(companyNumber);
        }
        if(!StringUtils.isEmpty(roleName)){
            if(roleName.equals(RoleEnum.ADMIN.getRoleName())) {
                notice.setTeacherNumber("");
                notice.setCompanyNumber("");
            }
        }

        List<Notice> noticeList = noticeDao.selectNoticeInfoData(notice);
        if(noticeList !=null && noticeList.size()>0){
            for (Notice notice1:noticeList
                 ) {
                if(!StringUtils.isEmpty(notice1.getStatus())){
                    if("1".equals(notice1.getStatus())){
                     notice1.setStatusName("正常");
                    }else {
                     notice1.setStatusName("关闭");
                    }
                }
                if(!StringUtils.isEmpty(notice1.getNoticeType())){
                    if("0001".equals(notice1.getNoticeType())){
                      notice1.setNoticeTypeName("通知");
                    }else {
                        notice1.setNoticeTypeName("公告");
                    }
                }
            }
        }
        return new PageInfo<>(noticeList);
    }

    @Override
    public PageInfo selectNoticeInfoDataAll(Integer page, Integer pageSize, String fuzzy) {
        PageHelper.startPage(page,pageSize);
        Notice notice = new Notice();
        if(!StringUtils.isEmpty(fuzzy)){
            notice.setFuzzy(fuzzy);
        }
        List<Notice> noticeList = noticeDao.selectNoticeInfoDataAll(notice);
        return new PageInfo<>(noticeList);
    }

    @Override
    public Map<String, Object> insertNoticeInfo(Notice notice) {
        Map<String,Object> map = new HashMap<>();
        notice.setManageDate(new Date());
        notice.setManageUser(notice.getName());
        notice.setNoticeReading(0);
        notice.setId(UUID.randomUUID().toString());
        noticeDao.insert(notice);
        map.put("code", "200");
        map.put("msg", "发布公告成功！");
        map.put("level", "success");
        return map;
    }

    @Override
    public Map<String, Object> editNoticeInfo(Notice notice) {
        Map<String,Object> map = new HashMap<>();
        notice.setManageDate(new Date());
        noticeDao.updateByPrimaryKey(notice);
        map.put("code", "200");
        map.put("msg", "修改公告成功！");
        map.put("level", "success");
        return map;
    }

    @Override
    public Map<String, Object> removeNoticeInfo(String[] ids) {
        Map<String,Object> map = new HashMap<>();

        noticeDao.deleteNoticeInfoByIds(ids);
        map.put("code", "200");
        map.put("msg", "删除公告成功！");
        map.put("level", "success");
        return map;
    }

    @Override
    public Map<String, Object> getNoticeInfoById(String id) {
        Map<String,Object> map = new HashMap<>();
        Notice notice=noticeDao.selectNoticeInfoById(id);
        map.put("noticeInfo",notice);
        return map;
    }
}
