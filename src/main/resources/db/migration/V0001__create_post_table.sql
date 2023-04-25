CREATE TABLE `post`
(
    `id`             bigint PRIMARY KEY AUTO_INCREMENT,
    `author_id`      bigint       NOT NULL,
    `cover`          VARCHAR(255) NULL,
    `title`          varchar(255) NOT NULL,
    `content`        text         NOT NULL,
    `status`         tinyint      NOT NULL DEFAULT 0,
    `favours`        int          NOT NULL DEFAULT 0,
    `created_at`     datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `last_edited_at` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
