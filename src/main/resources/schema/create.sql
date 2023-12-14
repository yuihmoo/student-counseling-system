CREATE DATABASE `student_counseling_system`;
CREATE USER 'yuihmoo'@'%' IDENTIFIED BY 'ZHlkZWhmMTI=';
GRANT ALL PRIVILEGES ON student_counseling_system.* TO 'yuihmoo'@'%';
FLUSH PRIVILEGES;

/*
 상담 테이블
 */
CREATE TABLE student_counseling_system.counseling
(
--     `id`        BINARY(16) DEFAULT (UUID_TO_BIN(UUID(),1)) PRIMARY KEY,
    `id`          INT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    student_id    INT      NOT NULL,
    content       VARCHAR(1000),
    employee_id  INT DEFAULT NULL,
    feedback      VARCHAR(1000),
    is_read       BOOLEAN,
    created_date  DATETIME NOT NULL
) ENGINE = INNODB
  DEFAULT CHARSET = utf8mb4;

/*
 학생 테이블
 status (0 = 재원, 1 = 퇴원)
 */
CREATE TABLE student_counseling_system.student
(
--     `id`     BINARY(16) DEFAULT (UUID_TO_BIN(UUID(),1)) PRIMARY KEY,
    `id`     INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name`   VARCHAR(1000),
    `status` CHAR(1)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8mb4;

/*
 직원 테이블
 type (C = Counselor 상담자, M = Manager 담당자)
 status (0 = 재직, 1 = 퇴직)
 */
CREATE TABLE student_counseling_system.employee
(
--     `id`     BINARY(16) DEFAULT (UUID_TO_BIN(UUID(),1)) PRIMARY KEY,
    `id`     INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name`   VARCHAR(1000),
    `type`   CHAR(1),
    `status` CHAR(1)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8mb4;
