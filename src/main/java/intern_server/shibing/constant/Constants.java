package intern_server.shibing.constant;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;

/**
 *
 */
public class Constants {
    /**
     * 系统全局标识
     */

    // header认证字段
    public static final String AUTHENTICATION = "Authorization";
    //账号已经存在
    public static final String ALREADY_EXISTS="00001";
    // token有效期
    public static final long EXPIRE_TIME = 7 * 24 * 3600 * 1000;
    //DES秘钥
    public static final byte[] KEY= { 0x00, 0x00, 0x00, 0x00,
            (byte) 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0x00,
            (byte) 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0x00, 0x00, 0x00,
            0x00, (byte) 0x00 }; // 24
    public static final String SUCCESS_CODE="0000";

    public static final String NOTICE_TYPE = "noticeType";

    /**
     * 资源映射路径 前缀
     */
    public static final String RESOURCE_PREFIX = "/profile";

}
