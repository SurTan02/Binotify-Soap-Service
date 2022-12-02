DROP DATABASE IF EXISTS `database`;

CREATE DATABASE `database`;

CREATE TABLE `database`.`subscription`
(
    `creator_id`        INT NOT NULL,
    `subscriber_id`     INT NOT NULL,
    `status`            ENUM('PENDING', 'ACCEPTED', 'REJECTED') NOT NULL DEFAULT 'PENDING',
    PRIMARY KEY (`creator_id`, `subscriber_id`)
);

CREATE TABLE `database`.`logging`
(
    `id`                INT AUTO_INCREMENT NOT NULL,
    `description`       VARCHAR(256) NOT NULL,
    `IP`                VARCHAR(16) NOT NULL,
    `endpoint`          VARCHAR(256) NOT NULL,
    `requested_at`      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);

CREATE TABLE `database`.`api_keys`
(
    `client`      ENUM('BINOTIFY REST SERVICE', 'BINOTIFY APP') NOT NULL,
    `client_key`  CHAR(128),
    PRIMARY KEY (`client_key`)
);

INSERT INTO `database`.`subscription` values (1, 1, 'ACCEPTED');

INSERT INTO `database`.`subscription` values (2, 1, 'PENDING');

INSERT INTO `database`.`subscription` values (3, 1, 'REJECTED');

INSERT INTO `database`.`subscription` values (4, 1, 'ACCEPTED');

INSERT INTO `database`.`subscription` values (5, 1, 'PENDING');

INSERT INTO `database`.`subscription` values (6, 1, 'REJECTED');