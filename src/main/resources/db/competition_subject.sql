/*
 Navicat Premium Data Transfer

 Source Server         : myql57
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : competition_subject

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 28/06/2020 20:34:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for login_log
-- ----------------------------
DROP TABLE IF EXISTS `login_log`;
CREATE TABLE `login_log`  (
  `id` bigint(18) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ip` varchar(38) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `os` varchar(38) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `browser` varchar(38) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `login_time` datetime(0) NULL DEFAULT NULL,
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '0 登录成功 1登录失败',
  `msg` varchar(38) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '提示信息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for project_apply
-- ----------------------------
DROP TABLE IF EXISTS `project_apply`;
CREATE TABLE `project_apply`  (
  `id` int(16) NOT NULL AUTO_INCREMENT COMMENT '项目立项id',
  `project_id` int(16) NOT NULL COMMENT '项目基本信息id',
  `budget_id` int(16) NULL DEFAULT NULL COMMENT '经费预算id',
  `delete_flag` int(2) NOT NULL DEFAULT 0 COMMENT '删除标识(默认0 删除 1)',
  `opinion` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核意见',
  `status` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核状态',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK8x54h07ohu0jiedgvjmh2kg7o`(`budget_id`) USING BTREE,
  CONSTRAINT `project_apply_ibfk_1` FOREIGN KEY (`budget_id`) REFERENCES `sys_budget` (`budget_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of project_apply
-- ----------------------------
INSERT INTO `project_apply` VALUES (5, 12, 28, 0, '', '1');
INSERT INTO `project_apply` VALUES (9, 15, 29, 0, '通过', '3');
INSERT INTO `project_apply` VALUES (10, 16, 30, 0, '通过', '3');
INSERT INTO `project_apply` VALUES (14, 20, 34, 0, '', '3');
INSERT INTO `project_apply` VALUES (15, 21, 36, 1, '', '2');
INSERT INTO `project_apply` VALUES (18, 24, 39, 0, '通过', '3');
INSERT INTO `project_apply` VALUES (20, 27, 43, 0, '', '0');
INSERT INTO `project_apply` VALUES (21, 28, 44, 0, NULL, '0');
INSERT INTO `project_apply` VALUES (22, 29, 45, 0, NULL, '0');
INSERT INTO `project_apply` VALUES (23, 30, 46, 0, NULL, '0');
INSERT INTO `project_apply` VALUES (24, 31, 47, 1, NULL, '0');
INSERT INTO `project_apply` VALUES (25, 32, 48, 0, '通过', '3');

-- ----------------------------
-- Table structure for sys_budget
-- ----------------------------
DROP TABLE IF EXISTS `sys_budget`;
CREATE TABLE `sys_budget`  (
  `budget_id` int(16) NOT NULL AUTO_INCREMENT COMMENT '经费预算id',
  `enroll` int(16) NULL DEFAULT NULL COMMENT '参赛注册费',
  `travel` int(16) NULL DEFAULT NULL COMMENT '差旅费',
  `guide` int(16) NULL DEFAULT NULL COMMENT '指导费',
  `bonus` int(36) NULL DEFAULT NULL COMMENT '教师奖金',
  `consume` int(16) NULL DEFAULT NULL COMMENT '耗材费',
  `other` int(16) NULL DEFAULT NULL COMMENT '其它',
  `sum` int(36) NULL DEFAULT NULL COMMENT '合计',
  `train` int(16) NULL DEFAULT NULL COMMENT '培训费',
  PRIMARY KEY (`budget_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 49 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_budget
-- ----------------------------
INSERT INTO `sys_budget` VALUES (28, 100, 900, 100, 600, 0, 500, 2300, 100);
INSERT INTO `sys_budget` VALUES (29, 100, 900, 100, 3000, 0, 500, 4700, 100);
INSERT INTO `sys_budget` VALUES (30, 100, 900, 100, 3000, 0, 500, 4700, 100);
INSERT INTO `sys_budget` VALUES (34, 100, 900, 100, 600, 0, 500, 2300, 100);
INSERT INTO `sys_budget` VALUES (36, 0, 1000, 100, 3000, 230, 200, 4530, 0);
INSERT INTO `sys_budget` VALUES (39, 100, 1200, 100, 3000, 100, 100, 4700, 100);
INSERT INTO `sys_budget` VALUES (43, 6, 1116, 26, 222226, 26, 26, 223452, 26);
INSERT INTO `sys_budget` VALUES (44, 1, 1111, 1, 111111, 1, 1, 112227, 1);
INSERT INTO `sys_budget` VALUES (45, 1, 1111, 1, 11111, 1, 1, 12227, 1);
INSERT INTO `sys_budget` VALUES (46, 1, 1111, 1, 11111, 1, 1, 12227, 1);
INSERT INTO `sys_budget` VALUES (47, 22, 2222, 2, 22288, 2, 22, 24560, 2);
INSERT INTO `sys_budget` VALUES (48, 122, 2221, 111, 22322, 22, 222, 25242, 222);

-- ----------------------------
-- Table structure for sys_capital
-- ----------------------------
DROP TABLE IF EXISTS `sys_capital`;
CREATE TABLE `sys_capital`  (
  `capital_id` int(16) NOT NULL AUTO_INCREMENT,
  `enroll` int(16) NULL DEFAULT NULL COMMENT '注册费',
  `review` int(16) NULL DEFAULT NULL COMMENT '评审费',
  `train` int(16) NULL DEFAULT NULL COMMENT '培训费',
  `travel` int(16) NULL DEFAULT NULL COMMENT '差旅费',
  `guide` int(16) NULL DEFAULT NULL COMMENT '指导费',
  `bonus` int(16) NULL DEFAULT NULL COMMENT '教师奖金',
  `consume` int(16) NULL DEFAULT NULL COMMENT '耗材费',
  `lead` int(16) NULL DEFAULT NULL,
  `organize` int(16) NULL DEFAULT NULL COMMENT '组织费',
  `sum` int(16) NULL DEFAULT NULL COMMENT '合计',
  PRIMARY KEY (`capital_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_capital
-- ----------------------------
INSERT INTO `sys_capital` VALUES (10, 6, 100, 100, 1200, 100, 6000, 100, 100, 200, 8006);
INSERT INTO `sys_capital` VALUES (11, 10, 100, 10, 300, 100, 3000, 100, 110, 10, 3840);
INSERT INTO `sys_capital` VALUES (12, 100, 100, 0, 1200, 100, 30000, 100, 100, 100, 31900);
INSERT INTO `sys_capital` VALUES (13, 0, 200, 100, 1000, 500, 20000, 100, 200, 100, 22400);
INSERT INTO `sys_capital` VALUES (14, 0, 200, 100, 1000, 500, 20000, 100, 200, 100, 22400);
INSERT INTO `sys_capital` VALUES (15, 0, 200, 100, 1000, 500, 20000, 100, 200, 100, 22400);
INSERT INTO `sys_capital` VALUES (16, 0, 100, 100, 1000, 500, 20000, 100, 100, 100, 22100);
INSERT INTO `sys_capital` VALUES (17, 0, 100, 0, 0, 100, 3000, 10, 200, 100, 3610);
INSERT INTO `sys_capital` VALUES (18, 200, 100, 100, 1000, 200, 25000, 100, 200, 200, 27200);
INSERT INTO `sys_capital` VALUES (19, 200, 1, 1, 1000, 1, 1111, 1, 1, 1, 2318);

-- ----------------------------
-- Table structure for sys_conclusion
-- ----------------------------
DROP TABLE IF EXISTS `sys_conclusion`;
CREATE TABLE `sys_conclusion`  (
  `conclusion_id` int(16) NOT NULL AUTO_INCREMENT COMMENT '结题id',
  `apply_id` int(16) NOT NULL,
  `prize_id` int(16) NOT NULL COMMENT '获奖id',
  `capital_id` int(16) NOT NULL COMMENT '实际资金id',
  `delete_flag` int(2) NOT NULL DEFAULT 0 COMMENT '删除标识(默认0 删除 1)',
  `teacher_id` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `team_no` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '团队编号',
  `status` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结题状态',
  `file` varchar(118) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结题报告书',
  `opinion` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结题审核意见',
  PRIMARY KEY (`conclusion_id`) USING BTREE,
  INDEX `capital_id`(`capital_id`) USING BTREE,
  INDEX `prize_id`(`prize_id`) USING BTREE,
  INDEX `conclusion_id`(`conclusion_id`, `prize_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_conclusion
-- ----------------------------
INSERT INTO `sys_conclusion` VALUES (1, 15, 11, 10, 0, '201212', 'KFCenter001', '1', '7d93977b-610b-4036-aec8-409da206f4a4_Java基础面试题.pdf', NULL);
INSERT INTO `sys_conclusion` VALUES (2, 24, 12, 11, 0, '201212', '1585746564778', '3', '7d93977b-610b-4036-aec8-409da206f4a4_Java基础面试题.pdf', '通过');
INSERT INTO `sys_conclusion` VALUES (3, 32, 17, 16, 0, '201212', '1585746564779', '3', '7d93977b-610b-4036-aec8-409da206f4a4_Java基础面试题.pdf', '非常好，特批通过');
INSERT INTO `sys_conclusion` VALUES (4, 24, 18, 17, 0, '201011', '1585746564779', '1', '7d93977b-610b-4036-aec8-409da206f4a4_Java基础面试题.pdf', NULL);
INSERT INTO `sys_conclusion` VALUES (5, 15, 19, 18, 0, '201011', '1585746564779', '1', '7d93977b-610b-4036-aec8-409da206f4a4_Java基础面试题.pdf', NULL);
INSERT INTO `sys_conclusion` VALUES (6, 15, 20, 19, 1, '201212', 'KFCenter001', '0', '7d93977b-610b-4036-aec8-409da206f4a4_Java基础面试题.pdf', NULL);

-- ----------------------------
-- Table structure for sys_prize
-- ----------------------------
DROP TABLE IF EXISTS `sys_prize`;
CREATE TABLE `sys_prize`  (
  `prize_id` int(16) NOT NULL AUTO_INCREMENT,
  `ranking` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '获奖名次',
  `level` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '获奖级别',
  PRIMARY KEY (`prize_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_prize
-- ----------------------------
INSERT INTO `sys_prize` VALUES (11, '1', '14');
INSERT INTO `sys_prize` VALUES (12, '1', '1');
INSERT INTO `sys_prize` VALUES (13, '14', '14');
INSERT INTO `sys_prize` VALUES (14, '1', '14');
INSERT INTO `sys_prize` VALUES (15, '1', '14');
INSERT INTO `sys_prize` VALUES (16, '1', '14');
INSERT INTO `sys_prize` VALUES (17, '1', '14');
INSERT INTO `sys_prize` VALUES (18, '1', '1');
INSERT INTO `sys_prize` VALUES (19, '1', '14');
INSERT INTO `sys_prize` VALUES (20, '1', '1');

-- ----------------------------
-- Table structure for sys_project
-- ----------------------------
DROP TABLE IF EXISTS `sys_project`;
CREATE TABLE `sys_project`  (
  `project_id` int(16) NOT NULL AUTO_INCREMENT COMMENT '项目基本信息id',
  `name` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '赛事名称',
  `organization` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组赛单位',
  `format` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '赛制',
  `teacher_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目负责人',
  `phone` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `mail` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电子邮件',
  `start_date` datetime(1) NULL DEFAULT NULL COMMENT '竞赛起始日期',
  `end_date` datetime(1) NULL DEFAULT NULL COMMENT '竞赛结束日期',
  `major` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '专业',
  `sponsor` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '竞赛主办单位',
  `contractor` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '竞赛承办单位',
  `project_date` datetime(1) NULL DEFAULT NULL COMMENT '申请立项日期',
  `objective` varchar(116) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '论证组赛的目的和意义',
  `invitation` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '竞赛邀请函或通知附件',
  PRIMARY KEY (`project_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_project
-- ----------------------------
INSERT INTO `sys_project` VALUES (12, '挑战杯', '某大学与', '2', '201011', '198789845', '666@qq.com', '2019-12-01 00:00:00.0', '2019-12-01 00:00:00.0', '软件工程', '某大学', '阿里', '2019-12-01 21:23:26.0', '拿奖', '7d93977b-610b-4036-aec8-409da206f4a4_Java基础面试题.pdf');
INSERT INTO `sys_project` VALUES (15, '蓝桥杯', '软件开发中心', '1', '201212', '15461664', '666@qq.com', '2019-12-02 15:26:32.0', '2019-12-02 15:26:34.0', '软件工程', '北京', '北京大学', '2019-12-02 15:26:47.0', '拿奖', '7d93977b-610b-4036-aec8-409da206f4a4_Java基础面试题.pdf');
INSERT INTO `sys_project` VALUES (16, '大创', '软件开发中心', '2', '201010', '15461664', '666@qq.com', '2019-12-02 15:29:32.0', '2019-12-02 15:29:34.0', '软件工程', '某企业', '阿里', '2019-12-02 15:29:49.0', '拿奖', '7d93977b-610b-4036-aec8-409da206f4a4_Java基础面试题.pdf');
INSERT INTO `sys_project` VALUES (20, '人工智能大赛', '大数据学院', '2', '201010', '15461664', '666@qq.com', '2019-12-03 13:32:14.0', '2019-12-19 00:00:00.0', '软件工程', '北京', '北京大学', '2019-12-03 13:32:35.0', '拿奖', '7d93977b-610b-4036-aec8-409da206f4a4_Java基础面试题.pdf');
INSERT INTO `sys_project` VALUES (21, '大设', '软件开发中心', '2', '201212', '15461664', '666@qq.com', '2019-12-03 13:45:41.0', '2019-12-18 00:00:00.0', '软件工程', '某企业', '杭州阿里', '2019-12-03 13:46:02.0', '拿奖', '7d93977b-610b-4036-aec8-409da206f4a4_Java基础面试题.pdf');
INSERT INTO `sys_project` VALUES (24, '互联网+', '大数据学院', '1', '201212', '16594646', '66@qq.com', '2019-12-19 22:21:38.0', '2019-12-19 22:21:42.0', '软件工程', '阿里', '阿里', '2019-12-19 22:21:45.0', '拿奖', '7d93977b-610b-4036-aec8-409da206f4a4_Java基础面试题.pdf');
INSERT INTO `sys_project` VALUES (31, '169', '26111', '1', '201212', '2611', '226', '2020-04-02 19:06:00.0', '2020-04-02 19:06:02.0', '26111', '26118', '26118', '2020-04-02 19:06:06.0', '1866688881', '7d93977b-610b-4036-aec8-409da206f4a4_Java基础面试题.pdf');
INSERT INTO `sys_project` VALUES (32, '大创', '大数据学院', '1', '201212', '1654646546456', '785685@qq.com', '2020-04-05 16:08:12.0', '2021-04-30 00:00:00.0', '软件工程', '某公司', '某大学', '2020-04-05 16:08:31.0', '拿奖', '7d93977b-610b-4036-aec8-409da206f4a4_Java基础面试题.pdf');

-- ----------------------------
-- Table structure for sys_teacher
-- ----------------------------
DROP TABLE IF EXISTS `sys_teacher`;
CREATE TABLE `sys_teacher`  (
  `teacher_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '教师工号',
  `account_num` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账号',
  `delete_flag` int(2) NOT NULL DEFAULT 0,
  `teacher_name` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '教师名称',
  `college_no` int(36) NULL DEFAULT NULL COMMENT '二级学院编号',
  PRIMARY KEY (`teacher_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_teacher
-- ----------------------------
INSERT INTO `sys_teacher` VALUES ('201010', '100', 0, '莫智懿', 116);
INSERT INTO `sys_teacher` VALUES ('201011', '111', 0, '许传本', 116);
INSERT INTO `sys_teacher` VALUES ('201212', 'teacher', 0, '朱肖颖', 116);

-- ----------------------------
-- Table structure for sys_team
-- ----------------------------
DROP TABLE IF EXISTS `sys_team`;
CREATE TABLE `sys_team`  (
  `team_no` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '团队编号',
  `title` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '赛题',
  `team_name` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '团队名称',
  `project_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '项目编号',
  `delete_flag` int(2) NOT NULL DEFAULT 0 COMMENT '删除标识',
  `enroll_time` datetime(0) NULL DEFAULT NULL COMMENT '注册时间',
  `teacher_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`team_no`) USING BTREE,
  INDEX `team_no`(`team_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_team
-- ----------------------------
INSERT INTO `sys_team` VALUES ('1585746564778', '互联网+', '神经网络分析队', '15', 0, '2019-12-19 22:54:03', '201010');
INSERT INTO `sys_team` VALUES ('1585746564779', '大设', '必胜队', '21', 0, '2020-04-01 21:09:24', '201212');
INSERT INTO `sys_team` VALUES ('1585802501076', '互联网+', '网络神经对抗队', '24', 0, '2020-04-02 12:41:41', '201212');
INSERT INTO `sys_team` VALUES ('65578298', '大创', 'day day up队', '24', 1, '2019-12-19 22:24:38', '201212');
INSERT INTO `sys_team` VALUES ('KFCenter001', '蓝桥杯', 'opencv人脸识别队', '15', 0, '2020-04-02 12:37:33', '201212');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(6) NOT NULL AUTO_INCREMENT,
  `utype` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_name` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `mail` varchar(38) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, '1', 'admin', 'f748e3c0dded73c1dce922fc3a62510e', NULL);
INSERT INTO `sys_user` VALUES (2, '2', 'worker', 'f748e3c0dded73c1dce922fc3a62510e', NULL);
INSERT INTO `sys_user` VALUES (3, '3', 'teacher', 'f748e3c0dded73c1dce922fc3a62510e', NULL);
INSERT INTO `sys_user` VALUES (4, '14', 'tale', 'super', NULL);
INSERT INTO `sys_user` VALUES (5, '14', 'tale', 'f748e3c0dded73c1dce922fc3a62510e', NULL);
INSERT INTO `sys_user` VALUES (10, '3', '111', 'f748e3c0dded73c1dce922fc3a62510e', NULL);

-- ----------------------------
-- Table structure for team_member
-- ----------------------------
DROP TABLE IF EXISTS `team_member`;
CREATE TABLE `team_member`  (
  `member_no` int(16) NOT NULL AUTO_INCREMENT COMMENT '团队成员编号',
  `team_no` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '团队编号',
  `student_no` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学号',
  `student_name` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学生姓名',
  `college_name` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学院名称',
  `class_name` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '班级名称',
  `grade` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '年级',
  `major` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '专业',
  `mail` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `mobile_number` int(11) NULL DEFAULT NULL COMMENT '手机号码',
  `delete_flag` int(2) NOT NULL DEFAULT 0 COMMENT '删除标识',
  PRIMARY KEY (`member_no`) USING BTREE,
  INDEX `team_no`(`team_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of team_member
-- ----------------------------
INSERT INTO `team_member` VALUES (1, 'KFCenter001', '1', 'tale', '大数据学院', '软件5班', '17', '软件工程', '66666@tale.com', 1888686866, 0);
INSERT INTO `team_member` VALUES (12, '1585745808657', '1', '1', '1', '1', '1', '1', '1', 1, 0);
INSERT INTO `team_member` VALUES (13, '1585746564778', '1', 'tale', '大数据学院', '5班', '17', '软件工程', '666@qq.com', 183666666, 0);
INSERT INTO `team_member` VALUES (14, '1585746564779', '6', '小明', '大数据学院', '5班', '17', '软件工程', '88@qq.com', 8888, 0);
INSERT INTO `team_member` VALUES (15, '1585746564779', '66', '马云', '大数据学院', '电商大佬班', '14', '电商、公益', 'taobao@qq.com', 1888888888, 0);
INSERT INTO `team_member` VALUES (16, '1585746564779', '68', '任正非', '大数据学院', '通信班', '13', '手机、通信', 'huawei@qq.com', 16668888, 0);
INSERT INTO `team_member` VALUES (17, '1585802501076', '1', 'tale', '大数据学院', '5班', '17', '软件工程', '999@qq.com', 688888, 0);

SET FOREIGN_KEY_CHECKS = 1;
