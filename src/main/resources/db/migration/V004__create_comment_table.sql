CREATE TABLE `comments`
(
    `id`          bigint(20) UNSIGNED                                           NOT NULL AUTO_INCREMENT,
    `user_id`     bigint(20) UNSIGNED                                           NOT NULL,
    `post_id`     bigint(20) UNSIGNED                                           NOT NULL,
    `parent_id`   bigint(20) UNSIGNED                                           NOT NULL,
    `answer_id`   bigint(20) UNSIGNED                                           NOT NULL,
    `content`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `liked`       int(8) UNSIGNED                                               NULL     DEFAULT NULL,
    `status`      tinyint(1) UNSIGNED                                           NULL     DEFAULT NULL,
    `create_time` timestamp                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` timestamp                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`) USING BTREE
)