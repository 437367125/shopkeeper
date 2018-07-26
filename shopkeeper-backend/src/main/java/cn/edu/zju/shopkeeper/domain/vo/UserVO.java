package cn.edu.zju.shopkeeper.domain.vo;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/16 上午9:32
 * Description 用户VO
 */
public class UserVO {
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
     * 用户密码
     */
    private String password;
    /**
     * 用户类型：0卖家 1卖家
     */
    private Integer type;
    /**
     * 用户令牌
     */
    private String token;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "UserVO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", type=" + type +
                ", token='" + token + '\'' +
                '}';
    }
}
