package app.meeka.domain.model;


import app.meeka.domain.exception.InvalidUserInfoException;
import cn.hutool.core.util.RandomUtil;
import jakarta.persistence.*;
import org.springframework.data.annotation.PersistenceCreator;

import java.time.OffsetDateTime;
import java.util.regex.Pattern;

import static jakarta.persistence.GenerationType.IDENTITY;
import static java.time.OffsetDateTime.now;
import static java.util.Objects.nonNull;

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
    @Enumerated(EnumType.ORDINAL)
    private Gender gender;
    private OffsetDateTime birthday;
    private String city;
    private String introduce;
    private OffsetDateTime createTime;
    private OffsetDateTime updateTime;


    @PersistenceCreator
    protected User() {

    }


    public static final String PASSWORD_REGEX = "^(?![a-zA-Z]+$)(?!\\d+$)(?![^\\da-zA-Z\s]+$).{6,12}$";
    public static final String NICKNAME_REGEX = "^[\\u4e00-\\u9fa5a-zA-Z0-9]{6,12}$";

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


    public User(UserInfo userInfo) throws InvalidUserInfoException {
        if (!userInfo.isValid()) {
            throw new InvalidUserInfoException();
        }
        this.email = userInfo.email();
        this.password = "";
        this.nickName = "用户" + RandomUtil.randomString(10);
        this.icon = "jpg";
        this.fans = 0;
        this.followee = 0;
        this.gender = Gender.UNKNOWN;
        this.birthday = null;
        this.city = null;
        this.introduce = null;
        this.createTime = now();
        this.updateTime = this.createTime;
    }

    public void updateFollow() {
        this.followee++;
    }

    public void updateFans() {
        this.fans++;
    }

    public boolean updatePassword(String password) {

        if (nonNull(this.password) && Pattern.matches(PASSWORD_REGEX, password)) {
            this.password = password;
        } else {
            return false;
        }
        return true;
    }

    public boolean updateNickName(String nickName) {
        if (nonNull(this.nickName) && Pattern.matches(NICKNAME_REGEX, nickName)) {
            this.nickName = nickName;
        } else {
            return false;
        }
        return true;
    }

    public void updateIcon(String icon) {
        this.icon = icon;
    }

    public void updateIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public void updateCity(String city) {
        this.city = city;
    }

    public void updateGender(String gender) throws InvalidUserInfoException {
        switch (gender) {
            case "男" -> this.gender = Gender.MALE;
            case "女" -> this.gender = Gender.FEMALE;
            case "未知" -> this.gender = Gender.UNKNOWN;
            default -> throw new InvalidUserInfoException();
        }
    }

    public void updateBirthday(OffsetDateTime birthday) {
        this.birthday = birthday;
    }

    public enum Gender {
        MALE, FEMALE, UNKNOWN
    }

}
