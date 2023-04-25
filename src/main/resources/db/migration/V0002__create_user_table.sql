CREATE TABLE `user`
(
    `id`          bigint(20) PRIMARY KEY AUTO_INCREMENT,
    `email`       varchar(32)    NULL     DEFAULT '',
    `password`    varchar(128)    NULL     DEFAULT '',
    `nick_name`   varchar(32)     NULL     DEFAULT '',
    `icon`        varchar(255)    NULL     DEFAULT '',
    `fans`        int(8) UNSIGNED NULL     DEFAULT 0,
    `followee`    int(8)          NULL     DEFAULT 0,
    `gender`      tinyint(1)      NULL     DEFAULT 0,
    `birthday`    date            NULL     DEFAULT NULL,
    `city`        varchar(64)     NULL     DEFAULT '',
    `introduce`   varchar(128)    NULL DEFAULT NULL,
    `create_time` timestamp       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` timestamp       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
