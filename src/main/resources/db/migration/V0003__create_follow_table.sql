CREATE TABLE `follow`
(
    `id`             bigint(20) PRIMARY KEY AUTO_INCREMENT,
    `user_id`        bigint(20),
    `follow_user_id` bigint(20),
    `create_time`    datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
)