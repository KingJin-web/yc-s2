package bean;

import java.sql.Timestamp;

public class SqMember {
    private Integer id;

    private String name;

    private String nickname;

    private String pwd;


    private String phone;

    private String email;

    private String qq;

    private String head;

    private String rank;

    private Integer glod;

    private Timestamp createTime;

    private Timestamp vipTime;

    public Timestamp getVipTime() {
        return vipTime;
    }

    public void setVipTime(Timestamp vipTime) {
        this.vipTime = vipTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd == null ? null : pwd.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head == null ? null : head.trim();
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank == null ? null : rank.trim();
    }

    public Integer getGlod() {
        return glod;
    }

    public void setGlod(Integer glod) {
        this.glod = glod;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "SqMember{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", pwd='" + pwd + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", qq='" + qq + '\'' +
                ", head='" + head + '\'' +
                ", rank='" + rank + '\'' +
                ", glod=" + glod +
                ", createTime=" + createTime +
                ", vipTime=" + vipTime +
                '}';
    }
}