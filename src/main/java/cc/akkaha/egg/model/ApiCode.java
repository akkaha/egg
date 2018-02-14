package cc.akkaha.egg.model;

public class ApiCode {
    /**
     * 默认结果码
     */
    public static final String DEFAULT = "00000";

    /**
     * API 处理正常,结果可依赖[10000, 20000)
     */
    public static final String OK = "10000";

    /**
     * API 传参不符合要求[20000, 30000)
     */
    public static final String INVALID = "20000";

    /**
     * 后端错误或异常[90000, ~]
     */
    public static final String ERROR = "90000";
    /**
     * 未登陆
     */
    public static final String NOT_LOGIN = "90001";
    /**
     * 没有权限
     */
    public static final String PERMISSION_DENIED = "90002";
    /**
     * 服务不可达
     */
    public static final String SERVICE_UNREACHABLE = "90003";
}
