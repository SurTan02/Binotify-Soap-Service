DROP DATABASE IF EXISTS `database`;

CREATE DATABASE `database`;

CREATE TABLE `database`.`subscription`
(
    `creator_id`   INT NOT NULL,
    `subscriber_id`     INT NOT NULL,
    `status` ENUM('PENDING', 'ACCEPTED', 'REJECTED') NOT NULL DEFAULT 'PENDING',
    PRIMARY KEY (`creator_id`, `subscriber_id`)
);

CREATE TABLE `database`.`logging`
(
    `id`   INT AUTO_INCREMENT NOT NULL,
    `description`   VARCHAR(256) NOT NULL,
    `IP`   VARCHAR(16) NOT NULL,
    `endpoint`   VARCHAR(256) NOT NULL,
    `requested_at`   TIMESTAMP NOT NULL,
    PRIMARY KEY (`id`)
);