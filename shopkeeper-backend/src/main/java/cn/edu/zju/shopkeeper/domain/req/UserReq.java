package cn.edu.zju.shopkeeper.domain.req;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/16 上午9:31
 * Description 用户请求类
 */
public class UserReq {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 手机号
     */
    private Long phoneNumber;
    /**
     * 电子邮箱
     */
    private String email;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 密码
     */
    private String password;
    /**
     * 旧密码
     */
    private String oldPassword;
    /**
     * 用户类别（0卖家，1买家）
     */
    private Integer type;

    /**
     * 登录方式（0为用户名登录，1为手机号登录）
     */
    private Integer loginMethod;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getLoginMethod() {
        return loginMethod;
    }

    public void setLoginMethod(Integer loginMethod) {
        this.loginMethod = loginMethod;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    @Override
    public String toString() {
        return "UserReq{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", oldPassword='" + oldPassword + '\'' +
                ", type=" + type +
                ", loginMethod=" + loginMethod +
                '}';
    }
}
