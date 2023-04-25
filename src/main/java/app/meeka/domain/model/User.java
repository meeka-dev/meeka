package app.meeka.domain.model;


import app.meeka.domain.exception.InvalidUserInfoException;

import cn.hutool.core.util.RandomUtil;
import jakarta.persistence.*;
import org.springframework.data.annotation.PersistenceCreator;

import java.time.OffsetDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;
import static java.time.OffsetDateTime.now;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String nikeName;
    private String icon;
    private int fans;
    private int followee;
    @Enumerated(EnumType.ORDINAL)
    private Gender gender;
    private OffsetDateTime birthday;
    private String city;
    private String introduce;
    private OffsetDateTime creatTime;
    private OffsetDateTime updateTime;

    @PersistenceCreator
    protected User() {

    }


    public enum Gender{
        MALE,FEMALE,UNKNOWN
    }
    public User(UserInfo userInfo) throws InvalidUserInfoException {
        if (!userInfo.isValid()) {
            throw new InvalidUserInfoException();
        }
        this.email = userInfo.email();
        this.password = "";
        this.nikeName = "用户"+ RandomUtil.randomString(10);
        this.icon = null;
        this.fans = 0;
        this.followee = 0;
        this.gender = Gender.UNKNOWN;
        this.birthday = null;
        this.city = null;
        this.introduce = null;
        this.creatTime = now();
        this.updateTime = creatTime;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNikeName() {
        return nikeName;
    }

    public String getIcon() {
        return icon;
    }

    public int getFans() {
        return fans;
    }

    public int getFollowee() {
        return followee;
    }

    public Gender getGender() {
        return gender;
    }

    public OffsetDateTime getBirthday() {
        return birthday;
    }

    public String getCity() {
        return city;
    }

    public String getIntroduce() {
        return introduce;
    }

    public OffsetDateTime getCreatTime() {
        return creatTime;
    }

    public OffsetDateTime getUpdateTime() {
        return updateTime;
    }
}
