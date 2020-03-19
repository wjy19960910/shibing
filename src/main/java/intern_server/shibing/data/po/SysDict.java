package intern_server.shibing.data.po;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: wangjingyuan
 * @Date: 2020/3/17 20:48
 */
@Entity
@Table( name ="sys_dict" )
public class SysDict {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String name;
    private String code;
    private String parentId;
    private String level;
    private String state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public SysDict() {
    }
}
