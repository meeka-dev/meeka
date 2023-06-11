package app.meeka.domain.model.user;


import app.meeka.domain.exception.InvalidUserInfoException;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.springframework.data.annotation.PersistenceCreator;

import java.time.OffsetDateTime;

import static cn.hutool.core.util.RandomUtil.randomString;
import static jakarta.persistence.EnumType.ORDINAL;
import static jakarta.persistence.GenerationType.IDENTITY;
import static java.time.OffsetDateTime.now;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @SuppressWarnings("unused")
    private Long id;
    private String email;
    private String password;
    private String nickName;
    private String icon;
    private int fans;
    private int followee;
    @Enumerated(ORDINAL)
    private Gender gender;
    private OffsetDateTime birthday;
    private String city;
    private String introduce;
    private OffsetDateTime createTime;
    private OffsetDateTime updateTime;


    @PersistenceCreator
    protected User() {

    }


    public enum Gender {
        MALE, FEMALE, UNKNOWN
    }

    public User(UserInfo userInfo) throws InvalidUserInfoException {
        if (!userInfo.isValid()) throw new InvalidUserInfoException();
        this.email = userInfo.email();
        this.password = "";
        this.nickName = "用户" + randomString(10);
        this.icon = null;
        this.fans = 0;
        this.followee = 0;
        this.gender = Gender.UNKNOWN;
        this.birthday = null;
        this.city = null;
        this.introduce = null;
        this.createTime = now();
        this.updateTime = this.createTime;
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
        return nickName;
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
        return createTime;
    }

    public OffsetDateTime getUpdateTime() {
        return updateTime;
    }
}
