CREATE TABLE `follow`
(
    `id`    bigint(20) PRIMARY KEY AUTO_INCREMENT,
    `userId`    bigint(20),
    `followUserId`    bigint(20),
    `create_time` datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP
)