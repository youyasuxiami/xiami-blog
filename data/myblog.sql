/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50719
 Source Host           : localhost:3306
 Source Schema         : myblog

 Target Server Type    : MySQL
 Target Server Version : 50719
 File Encoding         : 65001

 Date: 08/10/2020 22:48:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence`  (
  `next_val` bigint(20) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
INSERT INTO `hibernate_sequence` VALUES (1);
INSERT INTO `hibernate_sequence` VALUES (1);

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) NULL DEFAULT NULL COMMENT '类型   0：一级菜单   1：二级菜单   2：三级菜单 3：按钮',
  `icon` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) NULL DEFAULT NULL COMMENT '排序',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 108 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (88, 101, '内容管理', '/blog', '', 1, NULL, 2, '2020-07-11 11:45:56', '2020-08-02 12:14:35');
INSERT INTO `menu` VALUES (89, 102, '系统管理', '/sys', '', 1, NULL, 3, '2020-07-11 11:46:13', '2020-07-11 11:46:13');
INSERT INTO `menu` VALUES (90, 88, '文章管理', '/blog/info', '', 2, NULL, 1, '2020-07-11 11:47:31', '2020-08-02 12:37:02');
INSERT INTO `menu` VALUES (94, 89, '菜单管理', '/sys/menu/menuList', '', 2, NULL, 2, '2020-07-11 11:48:46', '2020-07-26 16:21:11');
INSERT INTO `menu` VALUES (95, 89, '角色管理', '/sys/role/roleList', '', 2, NULL, 3, '2020-07-11 11:49:06', '2020-07-26 16:21:19');
INSERT INTO `menu` VALUES (101, 0, '博客管理', '/', '', 0, NULL, 1, '2020-07-12 21:35:17', '2020-07-12 21:35:17');
INSERT INTO `menu` VALUES (102, 0, '系统管理', '/', '', 0, NULL, 2, '2020-07-12 21:35:33', '2020-07-12 21:35:33');
INSERT INTO `menu` VALUES (103, 89, '用户管理', '/sys/user/userList', NULL, 2, NULL, 1, '2020-07-20 00:00:32', '2020-07-26 16:21:27');
INSERT INTO `menu` VALUES (104, 101, '评论管理', '/comment', '', 1, NULL, 2, '2020-07-27 21:43:39', '2020-07-27 21:43:39');
INSERT INTO `menu` VALUES (106, 88, '分类管理', '/blog/type', '', 2, NULL, 2, '2020-08-02 12:20:46', '2020-08-02 12:20:46');
INSERT INTO `menu` VALUES (107, 88, '标签管理', '/blog/tag', '', 2, NULL, 3, '2020-08-02 12:38:06', '2020-08-02 12:38:06');

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限编号',
  `permission_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限名称',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (1, 'admin:login', '2020-05-14 22:57:27', '2020-05-14 22:57:30');
INSERT INTO `permission` VALUES (2, 'admin:show', '2020-05-14 22:57:57', '2020-05-14 22:57:59');
INSERT INTO `permission` VALUES (3, 'user:login', '2020-05-14 22:59:54', '2020-05-14 22:57:57');
INSERT INTO `permission` VALUES (4, 'user:show', '2020-05-14 22:59:54', '2020-05-14 22:57:57');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色编号',
  `role_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `role_desc` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `roleName`(`role_name`) USING BTREE COMMENT '角色名唯一'
) ENGINE = InnoDB AUTO_INCREMENT = 73 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (70, 'admin', '管理员', '2020-07-12 23:57:24', '2020-07-12 23:57:24');
INSERT INTO `role` VALUES (71, 'member', '会员', '2020-07-12 23:57:50', '2020-07-12 23:57:50');
INSERT INTO `role` VALUES (72, 'e', 'a', '2020-08-01 17:28:25', '2020-08-01 17:28:25');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NULL DEFAULT NULL COMMENT '角色编号',
  `permission_id` int(11) NULL DEFAULT NULL COMMENT '权限编号',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `roleId_permissionId`(`role_id`, `permission_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 825 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES (814, 70, 101, '2020-08-02 12:39:05', '2020-08-02 12:39:05');
INSERT INTO `role_permission` VALUES (815, 70, 88, '2020-08-02 12:39:05', '2020-08-02 12:39:05');
INSERT INTO `role_permission` VALUES (816, 70, 90, '2020-08-02 12:39:05', '2020-08-02 12:39:05');
INSERT INTO `role_permission` VALUES (817, 70, 106, '2020-08-02 12:39:05', '2020-08-02 12:39:05');
INSERT INTO `role_permission` VALUES (818, 70, 107, '2020-08-02 12:39:05', '2020-08-02 12:39:05');
INSERT INTO `role_permission` VALUES (819, 70, 104, '2020-08-02 12:39:05', '2020-08-02 12:39:05');
INSERT INTO `role_permission` VALUES (820, 70, 102, '2020-08-02 12:39:05', '2020-08-02 12:39:05');
INSERT INTO `role_permission` VALUES (821, 70, 89, '2020-08-02 12:39:05', '2020-08-02 12:39:05');
INSERT INTO `role_permission` VALUES (822, 70, 94, '2020-08-02 12:39:05', '2020-08-02 12:39:05');
INSERT INTO `role_permission` VALUES (823, 70, 95, '2020-08-02 12:39:05', '2020-08-02 12:39:05');
INSERT INTO `role_permission` VALUES (824, 70, 103, '2020-08-02 12:39:05', '2020-08-02 12:39:05');

-- ----------------------------
-- Table structure for role_user
-- ----------------------------
DROP TABLE IF EXISTS `role_user`;
CREATE TABLE `role_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NULL DEFAULT NULL COMMENT '角色编号',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户编号',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 94 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_user
-- ----------------------------
INSERT INTO `role_user` VALUES (80, 70, 493, '2020-07-13 12:38:53', '2020-07-13 12:38:53');
INSERT INTO `role_user` VALUES (81, 71, 493, '2020-07-13 12:38:53', '2020-07-13 12:38:53');
INSERT INTO `role_user` VALUES (85, 70, 1, '2020-07-18 23:24:30', '2020-07-18 23:24:30');
INSERT INTO `role_user` VALUES (86, 70, 508, '2020-08-02 18:21:11', '2020-08-02 18:21:11');
INSERT INTO `role_user` VALUES (90, 71, 491, '2020-09-16 01:09:57', '2020-09-16 01:09:57');
INSERT INTO `role_user` VALUES (92, 71, 512, '2020-09-20 23:53:46', '2020-09-20 23:53:46');
INSERT INTO `role_user` VALUES (93, 71, 515, '2020-09-20 23:57:08', '2020-09-20 23:57:08');

-- ----------------------------
-- Table structure for sys_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `sys_dictionary`;
CREATE TABLE `sys_dictionary`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `value` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dictionary
-- ----------------------------
INSERT INTO `sys_dictionary` VALUES (1, 'sys_role', '0', '管理员', '管理员身份', '2020-05-30 17:54:06', '2020-05-30 17:54:09');
INSERT INTO `sys_dictionary` VALUES (2, 'sys_role', '1', '游客', '游客身份', '2020-05-30 17:54:06', '2020-05-30 17:54:09');
INSERT INTO `sys_dictionary` VALUES (3, 'sys_role', '2', '会员', '会员身份', '2020-05-30 17:54:06', '2020-05-30 17:54:09');
INSERT INTO `sys_dictionary` VALUES (12, 'account_status', '0', '禁用', NULL, '2020-05-30 17:54:06', '2020-05-30 17:54:09');
INSERT INTO `sys_dictionary` VALUES (13, 'account_status', '1', '启用', NULL, '2020-05-30 17:54:06', '2020-05-30 17:54:09');
INSERT INTO `sys_dictionary` VALUES (14, 'sex', '0', '男', NULL, '2020-05-30 17:54:06', '2020-05-30 17:54:09');
INSERT INTO `sys_dictionary` VALUES (15, 'sex', '1', '女', NULL, '2020-05-30 17:54:06', '2020-05-30 17:54:09');
INSERT INTO `sys_dictionary` VALUES (16, 'menu_type', '0', '一级菜单', NULL, '2020-07-05 17:07:36', '2020-07-05 17:07:38');
INSERT INTO `sys_dictionary` VALUES (17, 'menu_type', '1', '二级菜单', NULL, '2020-07-05 17:07:36', '2020-07-05 17:07:38');
INSERT INTO `sys_dictionary` VALUES (18, 'menu_type', '2', '三级菜单', NULL, '2020-07-05 17:07:36', '2020-07-05 17:07:38');
INSERT INTO `sys_dictionary` VALUES (19, 'menu_type', '3', '按钮', NULL, '2020-07-05 17:07:36', '2020-07-05 17:07:38');
INSERT INTO `sys_dictionary` VALUES (20, 'flag', '1', '原创', NULL, '2020-07-29 23:55:42', '2020-07-29 23:55:45');
INSERT INTO `sys_dictionary` VALUES (21, 'flag', '2', '转载', NULL, '2020-07-29 23:55:42', '2020-07-29 23:55:45');
INSERT INTO `sys_dictionary` VALUES (22, 'flag', '3', '翻译', NULL, '2020-07-29 23:55:42', '2020-07-29 23:55:45');
INSERT INTO `sys_dictionary` VALUES (23, 'recommend_type', '0', '正常', NULL, '2020-09-01 12:37:58', '2020-09-01 12:38:00');
INSERT INTO `sys_dictionary` VALUES (24, 'recommend_type', '1', '一级推荐', NULL, '2020-09-01 12:37:58', '2020-09-01 12:38:00');
INSERT INTO `sys_dictionary` VALUES (25, 'recommend_type', '2', '二级推荐', NULL, '2020-09-01 12:37:58', '2020-09-01 12:38:00');
INSERT INTO `sys_dictionary` VALUES (26, 'recommend_type', '3', '三级推荐', NULL, '2020-09-01 12:37:58', '2020-09-01 12:38:00');
INSERT INTO `sys_dictionary` VALUES (27, 'recommend_type', '4', '四级推荐', NULL, '2020-09-01 12:37:58', '2020-09-01 12:38:00');

-- ----------------------------
-- Table structure for t_blog
-- ----------------------------
DROP TABLE IF EXISTS `t_blog`;
CREATE TABLE `t_blog`  (
  `id` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` int(11) NULL DEFAULT NULL COMMENT '作者id',
  `type_id` int(11) NULL DEFAULT NULL COMMENT '文章类型',
  `first_picture` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '首图',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '内容',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '博客描述',
  `recommend` tinyint(2) NOT NULL DEFAULT 0 COMMENT '推荐 0：没有选中，1：选中   0正常、1一级推荐、2二级推荐、3三级推荐、4四级推荐',
  `share_statement` tinyint(2) NOT NULL DEFAULT 0 COMMENT '转载声明 0：没有选中，1：选中',
  `appreciation` tinyint(2) NOT NULL DEFAULT 0 COMMENT '赞赏 0：没有选中，1：选中',
  `commentabled` tinyint(2) NOT NULL DEFAULT 0 COMMENT '评论',
  `flag` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文章来源 1原创 2转载 3翻译',
  `views` int(11) NULL DEFAULT 0 COMMENT '观看次数',
  `collect_count` int(11) NULL DEFAULT NULL COMMENT '点赞次数/收藏次数',
  `published` tinyint(2) NULL DEFAULT NULL COMMENT '发布/草稿   1发布、2保存为草稿',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK292449gwg5yf7ocdlmswv9w4j`(`type_id`) USING BTREE,
  INDEX `FK8ky5rrsxh01nkhctmo7d48p82`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_blog
-- ----------------------------
INSERT INTO `t_blog` VALUES ('11', 1, 1, 'http://youyasumi-oss.oss-cn-beijing.aliyuncs.com/a5b1b251-e03e-479b-9d6c-233dec65305e.png', '测试上传图片', '![xiaoxin.jpg](http://youyasumi-oss.oss-cn-beijing.aliyuncs.com/9980dff0-5f99-494e-95f0-3d7a186e749a.jpg)', '666666666666666666', 0, 0, 0, 0, '1', 0, 0, 1, '2020-09-20 23:10:14', '2020-09-20 23:10:14');
INSERT INTO `t_blog` VALUES ('67', 1, 1, 'http://youyasumi-oss.oss-cn-beijing.aliyuncs.com/8e45ef96-c34b-4190-abb5-10e2ca61d494.png', '迷人的鸭鸭', '# 女神的日常\n![微信图片_20200920234308.jpg](http://youyasumi-oss.oss-cn-beijing.aliyuncs.com/eb03d5e8-3d31-44a2-b750-6bc9114b17dd.jpg)', '你若安好，便是晴天', 0, 0, 0, 0, '1', 0, 0, 1, '2020-10-06 09:09:24', '2020-10-06 09:09:24');
INSERT INTO `t_blog` VALUES ('68', 1, 1, 'http://youyasumi-oss.oss-cn-beijing.aliyuncs.com/8e45ef96-c34b-4190-abb5-10e2ca61d494.png', '222', '系统模块\ncom.ruoyi     \n├── ruoyi-ui              // 前端框架 [80]\n├── ruoyi-gateway         // 网关模块 [8080]\n├── ruoyi-auth            // 认证中心 [9200]\n├── ruoyi-api             // 接口模块\n│       └── ruoyi-api-system                          // 系统接口\n├── ruoyi-common          // 通用模块\n│       └── ruoyi-common-core                         // 核心模块\n│       └── ruoyi-common-datascope                    // 权限范围\n│       └── ruoyi-common-log                          // 日志记录\n│       └── ruoyi-common-redis                        // 缓存服务\n│       └── ruoyi-common-security                     // 安全模块\n│       └── ruoyi-common-swagger                      // 系统接口\n├── ruoyi-modules         // 业务模块\n│       └── ruoyi-system                              // 系统模块 [9201]\n│       └── ruoyi-gen                                 // 代码生成 [9202]\n│       └── ruoyi-job                                 // 定时任务 [9203]\n├── ruoyi-visual          // 图形化管理模块\n│       └── ruoyi-visual-monitor                      // 监控中心 [9100]\n├──pom.xml                // 公共依赖\n架构图\n![4ee999d254830190f85b1c96858ae71c_1.jpg](http://youyasumi-oss.oss-cn-beijing.aliyuncs.com/7442fade-8d55-4491-9966-5cd489b3e816.jpg)\n\n\n\n内置功能\n用户管理：用户是系统操作者，该功能主要完成系统用户配置。\n部门管理：配置系统组织机构（公司、部门、小组），树结构展现支持数据权限。\n岗位管理：配置系统用户所属担任职务。\n菜单管理：配置系统菜单，操作权限，按钮权限标识等。\n角色管理：角色菜单权限分配、设置角色按机构进行数据范围权限划分。\n字典管理：对系统中经常使用的一些较为固定的数据进行维护。\n参数管理：对系统动态配置常用参数。\n通知公告：系统通知公告信息发布维护。\n操作日志：系统正常操作日志记录和查询；系统异常信息日志记录和查询。\n登录日志：系统登录日志记录查询包含登录异常。\n在线用户：当前系统中活跃用户状态监控。\n定时任务：在线（添加、修改、删除)任务调度包含执行结果日志。\n代码生成：前后端代码的生成（java、html、xml、sql）支持CRUD下载 。\n系统接口：根据业务代码自动生成相关的api接口文档。\n服务监控：监视当前系统CPU、内存、磁盘、堆栈等相关信息。\n在线构建器：拖动表单元素生成相应的HTML代码。\n连接池监视：监视当前系统数据库连接池状态，可进行分析SQL找出系统性能瓶颈。\n在线体验\nadmin/admin123\n陆陆续续收到一些打赏，为了更好的体验已用于演示服务器升级。谢谢各位小伙伴。\n演示地址：http://ruoyi.vip\n文档地址：http://doc.ruoyi.vip\n\n演示图\n	\n	\n	\n	\n	', '3333', 0, 0, 0, 0, '1', 0, 0, 1, '2020-09-20 23:37:45', '2020-09-20 23:37:45');
INSERT INTO `t_blog` VALUES ('69', 1, 2, 'http://youyasumi-oss.oss-cn-beijing.aliyuncs.com/318598cd-6937-4e8a-971c-25292fae4f97.png', '22222', '```\r\n/*\r\n * 通过SQL搜索博客\r\n * @param params\r\n */\r\n@Test\r\n    void contextLoads() {\r\n        String code = MD5Utils.code(&quot;123456&quot;);\r\n        System.out.println(&quot;------------------&quot;);\r\n        System.out.println(code);\r\n    }\r\n```', '4444444444444', 3, 1, 1, 1, '1', 5, 0, 1, '2020-09-17 12:51:54', '2020-09-17 12:51:54');

-- ----------------------------
-- Table structure for t_blog_tags
-- ----------------------------
DROP TABLE IF EXISTS `t_blog_tags`;
CREATE TABLE `t_blog_tags`  (
  `blogs_id` int(11) NOT NULL,
  `tags_id` int(11) NOT NULL,
  INDEX `FK5feau0gb4lq47fdb03uboswm8`(`tags_id`) USING BTREE,
  INDEX `FKh4pacwjwofrugxa9hpwaxg6mr`(`blogs_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_blog_tags
-- ----------------------------
INSERT INTO `t_blog_tags` VALUES (1, 1);
INSERT INTO `t_blog_tags` VALUES (1, 2);
INSERT INTO `t_blog_tags` VALUES (1, 3);
INSERT INTO `t_blog_tags` VALUES (3, 1);
INSERT INTO `t_blog_tags` VALUES (3, 2);
INSERT INTO `t_blog_tags` VALUES (3, 3);
INSERT INTO `t_blog_tags` VALUES (4, 1);
INSERT INTO `t_blog_tags` VALUES (4, 2);
INSERT INTO `t_blog_tags` VALUES (4, 3);
INSERT INTO `t_blog_tags` VALUES (5, 1);
INSERT INTO `t_blog_tags` VALUES (6, 1);
INSERT INTO `t_blog_tags` VALUES (7, 1);
INSERT INTO `t_blog_tags` VALUES (8, 1);
INSERT INTO `t_blog_tags` VALUES (9, 1);
INSERT INTO `t_blog_tags` VALUES (10, 1);
INSERT INTO `t_blog_tags` VALUES (11, 1);
INSERT INTO `t_blog_tags` VALUES (12, 1);
INSERT INTO `t_blog_tags` VALUES (13, 1);
INSERT INTO `t_blog_tags` VALUES (17, 1);
INSERT INTO `t_blog_tags` VALUES (18, 1);
INSERT INTO `t_blog_tags` VALUES (19, 1);
INSERT INTO `t_blog_tags` VALUES (20, 1);
INSERT INTO `t_blog_tags` VALUES (21, 1);
INSERT INTO `t_blog_tags` VALUES (22, 1);
INSERT INTO `t_blog_tags` VALUES (23, 1);
INSERT INTO `t_blog_tags` VALUES (24, 1);
INSERT INTO `t_blog_tags` VALUES (26, 1);
INSERT INTO `t_blog_tags` VALUES (27, 1);
INSERT INTO `t_blog_tags` VALUES (27, 2);
INSERT INTO `t_blog_tags` VALUES (27, 3);
INSERT INTO `t_blog_tags` VALUES (28, 1);
INSERT INTO `t_blog_tags` VALUES (28, 2);
INSERT INTO `t_blog_tags` VALUES (28, 3);
INSERT INTO `t_blog_tags` VALUES (29, 1);
INSERT INTO `t_blog_tags` VALUES (29, 2);
INSERT INTO `t_blog_tags` VALUES (29, 3);
INSERT INTO `t_blog_tags` VALUES (30, 1);
INSERT INTO `t_blog_tags` VALUES (30, 2);
INSERT INTO `t_blog_tags` VALUES (30, 3);
INSERT INTO `t_blog_tags` VALUES (31, 1);
INSERT INTO `t_blog_tags` VALUES (31, 2);
INSERT INTO `t_blog_tags` VALUES (31, 3);
INSERT INTO `t_blog_tags` VALUES (36, 1);
INSERT INTO `t_blog_tags` VALUES (36, 2);
INSERT INTO `t_blog_tags` VALUES (36, 3);
INSERT INTO `t_blog_tags` VALUES (37, 1);
INSERT INTO `t_blog_tags` VALUES (37, 2);
INSERT INTO `t_blog_tags` VALUES (37, 3);
INSERT INTO `t_blog_tags` VALUES (62, 1);
INSERT INTO `t_blog_tags` VALUES (62, 2);
INSERT INTO `t_blog_tags` VALUES (62, 3);
INSERT INTO `t_blog_tags` VALUES (63, 1);
INSERT INTO `t_blog_tags` VALUES (63, 2);
INSERT INTO `t_blog_tags` VALUES (63, 3);
INSERT INTO `t_blog_tags` VALUES (64, 1);
INSERT INTO `t_blog_tags` VALUES (64, 2);
INSERT INTO `t_blog_tags` VALUES (64, 3);
INSERT INTO `t_blog_tags` VALUES (65, 1);
INSERT INTO `t_blog_tags` VALUES (35, 2);
INSERT INTO `t_blog_tags` VALUES (66, 1);
INSERT INTO `t_blog_tags` VALUES (69, 2);
INSERT INTO `t_blog_tags` VALUES (70, 3);
INSERT INTO `t_blog_tags` VALUES (72, 1);
INSERT INTO `t_blog_tags` VALUES (71, 1);
INSERT INTO `t_blog_tags` VALUES (73, 1);
INSERT INTO `t_blog_tags` VALUES (68, 1);
INSERT INTO `t_blog_tags` VALUES (67, 1);

-- ----------------------------
-- Table structure for t_comment
-- ----------------------------
DROP TABLE IF EXISTS `t_comment`;
CREATE TABLE `t_comment`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_comment_id` int(11) NULL DEFAULT NULL COMMENT '一级评论的id',
  `parent_comment_id` int(11) NULL DEFAULT NULL COMMENT '上一级的评论id',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '回复者的用户id',
  `user_avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '回复者的头像',
  `to_user_id` int(11) NULL DEFAULT NULL COMMENT '被回复者的用户id',
  `to_user_avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '被回复者的头像',
  `content` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评论内容',
  `blog_id` int(11) NULL DEFAULT NULL COMMENT '博客id',
  `likes` int(11) NULL DEFAULT NULL COMMENT '点赞数',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '审核状态 0:删除的 1：激活的 2：冻结的 3：置顶的',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `type` tinyint(1) NULL DEFAULT NULL COMMENT '评论类型： 0评论 1点赞',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 82 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_comment
-- ----------------------------
INSERT INTO `t_comment` VALUES (37, NULL, NULL, 1, 'http://youyasumi-oss.oss-cn-beijing.aliyuncs.com/76e11fce-e7fd-4985-84ec-2332b9dfef84.png', NULL, NULL, '11111111', 67, 0, 0, '2020-09-15 23:17:14', '2020-09-15 23:17:14', 0);
INSERT INTO `t_comment` VALUES (39, 37, 37, 512, 'http://youyasumi-oss.oss-cn-beijing.aliyuncs.com/76deb68e-ae07-4598-9ce9-affcc44bbd7e.png', 1, 'http://youyasumi-oss.oss-cn-beijing.aliyuncs.com/76e11fce-e7fd-4985-84ec-2332b9dfef84.png', '222222222222222222222', 67, 0, 0, '2020-09-15 23:57:32', '2020-09-15 23:57:32', 0);
INSERT INTO `t_comment` VALUES (42, 37, 39, 491, 'http://youyasumi-oss.oss-cn-beijing.aliyuncs.com/1d48261a-ad64-48e1-b4ff-fdd2ec4e55e4.png', 512, 'http://youyasumi-oss.oss-cn-beijing.aliyuncs.com/76deb68e-ae07-4598-9ce9-affcc44bbd7e.png', '有点丑', 67, 0, 0, '2020-09-16 01:14:20', '2020-09-16 01:14:20', 0);
INSERT INTO `t_comment` VALUES (78, NULL, NULL, 1, 'http://youyasumi-oss.oss-cn-beijing.aliyuncs.com/76e11fce-e7fd-4985-84ec-2332b9dfef84.png', NULL, NULL, '22222222', 67, 0, 0, '2020-09-20 11:55:53', '2020-09-20 11:55:53', 0);
INSERT INTO `t_comment` VALUES (79, NULL, NULL, 1, 'http://youyasumi-oss.oss-cn-beijing.aliyuncs.com/76e11fce-e7fd-4985-84ec-2332b9dfef84.png', NULL, NULL, '沙发', 67, 0, 1, '2020-09-20 23:51:26', '2020-09-20 23:51:26', 0);
INSERT INTO `t_comment` VALUES (80, NULL, NULL, 515, 'http://youyasumi-oss.oss-cn-beijing.aliyuncs.com/8b049500-24b4-467c-91d5-8678e97d602b.png', NULL, NULL, '666', 67, 0, 0, '2020-09-20 23:58:19', '2020-09-20 23:58:19', 0);
INSERT INTO `t_comment` VALUES (81, 79, 79, 515, 'http://youyasumi-oss.oss-cn-beijing.aliyuncs.com/8b049500-24b4-467c-91d5-8678e97d602b.png', 1, 'http://youyasumi-oss.oss-cn-beijing.aliyuncs.com/76e11fce-e7fd-4985-84ec-2332b9dfef84.png', '举报了', 67, 0, 1, '2020-09-20 23:58:51', '2020-09-20 23:58:51', 0);

-- ----------------------------
-- Table structure for t_comment_report
-- ----------------------------
DROP TABLE IF EXISTS `t_comment_report`;
CREATE TABLE `t_comment_report`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `report_user_id` int(11) NULL DEFAULT NULL COMMENT '举报人id',
  `report_to_user_id` int(11) NULL DEFAULT NULL COMMENT '被举报人id',
  `report_comment_id` int(11) NULL DEFAULT NULL COMMENT '被举报的评论id',
  `reason` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '举报原因',
  `progress_status` tinyint(2) NOT NULL COMMENT '进展状态: 0 未查看   1: 已查看  2：已处理',
  `create_time` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '举报时间',
  `update_time` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 56 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_comment_report
-- ----------------------------
INSERT INTO `t_comment_report` VALUES (51, 1, 512, 39, NULL, 0, '2020-09-19 21:45:39', '2020-09-19 21:45:39');
INSERT INTO `t_comment_report` VALUES (52, 1, 512, 39, NULL, 0, '2020-09-20 06:56:36', '2020-09-20 06:56:36');
INSERT INTO `t_comment_report` VALUES (53, 1, 491, 42, NULL, 0, '2020-09-20 06:56:38', '2020-09-20 06:56:38');
INSERT INTO `t_comment_report` VALUES (54, 1, 491, 42, NULL, 0, '2020-09-20 06:57:15', '2020-09-20 06:57:15');
INSERT INTO `t_comment_report` VALUES (55, 1, 512, 39, NULL, 0, '2020-09-20 06:57:41', '2020-09-20 06:57:41');

-- ----------------------------
-- Table structure for t_tag
-- ----------------------------
DROP TABLE IF EXISTS `t_tag`;
CREATE TABLE `t_tag`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_tag
-- ----------------------------
INSERT INTO `t_tag` VALUES (1, '动漫', '2020-08-03 23:40:21', '2020-08-03 23:40:21');
INSERT INTO `t_tag` VALUES (2, '新闻', '2020-08-03 23:40:21', '2020-08-03 23:40:21');
INSERT INTO `t_tag` VALUES (3, '娱乐', '2020-08-03 23:40:21', '2020-08-03 23:40:21');
INSERT INTO `t_tag` VALUES (4, 'asdasda', '2020-08-05 12:54:22', '2020-08-05 12:54:22');

-- ----------------------------
-- Table structure for t_tag_copy1
-- ----------------------------
DROP TABLE IF EXISTS `t_tag_copy1`;
CREATE TABLE `t_tag_copy1`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_type
-- ----------------------------
DROP TABLE IF EXISTS `t_type`;
CREATE TABLE `t_type`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_type
-- ----------------------------
INSERT INTO `t_type` VALUES (1, 'java', '2020-08-03 22:42:39', '2020-08-03 22:42:51');
INSERT INTO `t_type` VALUES (2, 'css', '2020-08-03 22:42:39', '2020-08-03 22:42:51');
INSERT INTO `t_type` VALUES (3, 'html', '2020-08-03 22:42:39', '2020-08-03 22:42:51');
INSERT INTO `t_type` VALUES (4, 'spring', '2020-08-03 22:42:39', '2020-08-03 22:42:51');
INSERT INTO `t_type` VALUES (5, 'springboot', '2020-08-03 22:42:39', '2020-08-03 22:42:51');
INSERT INTO `t_type` VALUES (6, '', '2020-08-05 12:52:47', '2020-08-05 12:52:47');
INSERT INTO `t_type` VALUES (7, 'aaa', '2020-08-05 12:54:15', '2020-08-05 12:54:15');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名（登录用的是这个）',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `nick_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `sex` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别（男 0 女 1 ）',
  `age` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '年龄',
  `phone` char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系方式',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `login_time` datetime(0) NULL DEFAULT NULL COMMENT '最新登录时间',
  `ps` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '备注/前言',
  `status` char(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账号状态：（0：禁用  1启动）',
  `qq群` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'qq群',
  `qq` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'qq',
  `wchat` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信号',
  `github` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'github',
  `gitee` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'gitee',
  `ali_pay` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付宝赞赏码',
  `weixin_pay` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信赞赏码',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 516 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'zhengjin', 'uSDLLCz0MugGUB2lo94DeA==', '只是开发', '0', '1', '13534659297', '891627097@qq.com', 'http://youyasumi-oss.oss-cn-beijing.aliyuncs.com/76e11fce-e7fd-4985-84ec-2332b9dfef84.png', '2020-05-30 22:55:50', '2020-07-18 23:24:30', '2020-05-14 22:55:56', '<h2>前言</h2>\r\n\r\n<p>目前博客源码已经开源至码云和Github中，感兴趣的小伙伴可以star关注一下下~</p>\r\n\r\n<p>Gitee地址：<a href=\"https://gitee.com/moxi159753/mogu_blog_v2\">https://gitee.com/moxi159753/mogu_blog_v2</a></p>\r\n\r\n<p>Github地址：<a href=\"https://github.com/moxi624/mogu_blog_v2\">https://github.com/moxi624/mogu_blog_v2</a></p>\r\n\r\n<hr />\r\n<p>&nbsp;</p>\r\n\r\n<h2>关于我</h2>\r\n\r\n<p>许志翔，目前就读于桂林电子科技大学，是一名研二的学生，所属计算机信息与安全学院，研究方向是教育大数据，是一名&quot;不顾正业&quot;的研究僧，沉迷于Java和Vue技术开发，梦想着进入BATJ，也将一直为此不断努力了~</p>\r\n\r\n<p>正宗95后，爱编程，爱旅游，爱生活，爱锻炼，从刚研究生入学后，就坚持着两件事，一个是写代码，一个就是每天5公里慢跑</p>\r\n\r\n<pre>\r\n<code>意志力和天生才华，都是人们在事实发生了之后再去赋予某个人的优点：杰森是位不可思议的网球选手，因此，他一定生下来就具有这种才华；杰尼年复一年地练习拉小提琴，每天坚持几个小时，因此，他一定有着令人难以置信的意志力。\r\n\r\n——《刻意练习：从新手到大师》</code></pre>\r\n\r\n<p>最近可能随着研究生生涯过半，已经要着手开始写小论文的事情了，博客项目的更新估计会变的比较缓慢，应该不会存在大版本的更新迭代了</p>\r\n\r\n<p>不过目前博客项目的技术功能也编写的差不多，可能还要做的就是SEO优化，后面一段时间应该注重于网站的稳定性和BUG的解决，要是小伙伴在使用的时候，发现了什么问题，欢迎私聊我，或者在QQ群里提出~</p>\r\n\r\n<p>在今年的8、9月份，也该着手于校招了，后面应该更多的时间沉淀在Java的基础学习了，当然如果有老哥有内推名额的话，欢迎推荐我一下下....&nbsp; &nbsp;卑微.jpg&nbsp;</p>\r\n\r\n<p><img src=\"http://image.moguit.cn/52552ed0efb245a9a67d5c9928d72e14\" /></p>\r\n\r\n<p>回顾2019年，每天也会在项目中，花费一些时间去提交代码，可能有的时候是增加了新功能，有的时候是解决一个BUG，到现在已经成为了一个习惯了，因此博客项目中也添加了比较多的有趣的功能，比如这个文章贡献度</p>\r\n\r\n<p><img src=\"http://image.moguit.cn/aaff67a315c547c5964f0aebb4e8ce23\" /></p>\r\n\r\n<p>哈哈哈，其实它和码云上的代码贡献度是一样的，每次发表一篇博客，就会标记出个点，点越大说明该天发表博客越多，可能是因为自己有些强迫症的原因，不过我也希望能够借此来激励自己养成每天写博客的习惯，通过分享自己学习到的东西，来和各位IT的前辈们共同进步。</p>\r\n\r\n<p>当然闲暇之余也会玩玩游戏，有空的时候会在酒馆搓搓炉石~，玩玩农药，有喜欢的小伙伴也可以一起，虽然我贼菜</p>\r\n\r\n<hr />\r\n<h2>项目起因</h2>\r\n\r\n<p>本博客项目由我和几个小伙伴参与开发的，最开始的搭建蘑菇博客的初心是为了巩固和学习Java开发的一些知识，因此项目的技术选型都是比较新颖的技术，可能这些技术并不一定适用于博客系统，但是我也想着能尽可能把更多的技术融合进来，毕竟通过自己手把手的操作一遍，也能够算是入门了。我也很庆幸我成功将自己的项目开源出来了，并且坚持下来，蘑菇博客起源是2018年9月，到想在也已经度过了1年半的岁月。我也从最开始只会一点点Jsp和Servlet就出去找工作的傻小子慢慢成长了，在读研之前，我也在公司里呆过，算上来好像将近快一年，很感谢之前在公司里的同事和领导，是他们带我入门企业级的项目开发，让我养成了很多Java项目开发的规范。</p>\r\n\r\n<p>起初项目开源在码云上，没有多少人关注，所以自己也是坚持做自己的喜欢的事，有的时候是看看论文，有的时候敲敲代码，在2019年12月14日，蘑菇博客被码云推荐了，上了首页</p>\r\n\r\n<p><img src=\"http://image.moguit.cn/49865d11fd4c4b289d87bf305b2dde0a\" /></p>\r\n\r\n<p>&nbsp;</p>\r\n\r\n<p>然后项目的关注度就开始上升了，有些小伙伴就开始关注项目的运行和部署了，所以我也花费了一些时间，整理了博客的开发、运行、部署的文档，希望每个小伙伴都能够通过本项目一起学习</p>\r\n\r\n<p>目前蘑菇博客已经有300star了，很高兴大家对蘑菇博客项目的认可</p>\r\n\r\n<p><img src=\"http://image.moguit.cn/f55b31b7e00b42eda71e88105c4e147a\" /></p>\r\n\r\n<p>同时因为更新比较勤快，项目也在码云&nbsp; 博客&nbsp; &nbsp;关键字搜索的第一个，不过未来要走得路还很长，我也希望能够认识更多志同道合的小伙伴，然后一起学习和交流</p>\r\n\r\n<p><img src=\"http://image.moguit.cn/36588a0c8bf04e9bb103eac0f432bfa7\" /></p>\r\n', '1', NULL, NULL, NULL, NULL, NULL, 'http://youyasumi-oss.oss-cn-beijing.aliyuncs.com/d08c50ea2dfd0a9708a085fe39b536f.jpg', 'http://youyasumi-oss.oss-cn-beijing.aliyuncs.com/98c069c9fc9526057d555d26273d899.png');
INSERT INTO `user` VALUES (491, '小锦哥', 'smuPic7KWvM1gZ2R5apmiQ==', '开发者', '0', '1', '111', '222', 'http://youyasumi-oss.oss-cn-beijing.aliyuncs.com/1d48261a-ad64-48e1-b4ff-fdd2ec4e55e4.png', '2020-07-13 00:00:51', '2020-09-16 01:09:57', '2020-07-13 00:00:51', '333', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (496, 'dev1', 'kN28UowZdTQpjPbRx0L3tg==', '开发者2', '0', '1', '111', '222', 'http://youyasumi-oss.oss-cn-beijing.aliyuncs.com/76e11fce-e7fd-4985-84ec-2332b9dfef84.png', '2020-07-13 00:00:51', '2020-07-13 00:00:51', '2020-07-13 00:00:51', '333', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (497, 'dev12', 'kN28UowZdTQpjPbRx0L3tg==', '开发者2', '0', '1', '111', '222', 'http://youyasumi-oss.oss-cn-beijing.aliyuncs.com/76e11fce-e7fd-4985-84ec-2332b9dfef84.png', '2020-07-13 00:00:51', '2020-07-13 00:00:51', '2020-07-13 00:00:51', '333', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (498, 'dev123', 'kN28UowZdTQpjPbRx0L3tg==', '开发者2', '0', '1', '111', '222', 'http://youyasumi-oss.oss-cn-beijing.aliyuncs.com/76e11fce-e7fd-4985-84ec-2332b9dfef84.png', '2020-07-13 00:00:51', '2020-07-13 00:00:51', '2020-07-13 00:00:51', '333', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (499, 'dev1234', 'kN28UowZdTQpjPbRx0L3tg==', '开发者2', '0', '1', '111', '222', 'http://youyasumi-oss.oss-cn-beijing.aliyuncs.com/76e11fce-e7fd-4985-84ec-2332b9dfef84.png', '2020-07-13 00:00:51', '2020-07-13 00:00:51', '2020-07-13 00:00:51', '333', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (500, 'dev12345', 'kN28UowZdTQpjPbRx0L3tg==', '开发者2', '0', '1', '111', '222', 'http://youyasumi-oss.oss-cn-beijing.aliyuncs.com/76e11fce-e7fd-4985-84ec-2332b9dfef84.png', '2020-07-13 00:00:51', '2020-07-13 00:00:51', '2020-07-13 00:00:51', '333', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (501, 'dev123456', 'kN28UowZdTQpjPbRx0L3tg==', '开发者2', '0', '1', '111', '222', 'http://youyasumi-oss.oss-cn-beijing.aliyuncs.com/76e11fce-e7fd-4985-84ec-2332b9dfef84.png', '2020-07-13 00:00:51', '2020-07-13 00:00:51', '2020-07-13 00:00:51', '333', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (502, 'dev1234567', 'kN28UowZdTQpjPbRx0L3tg==', '开发者2', '0', '1', '111', '222', 'http://youyasumi-oss.oss-cn-beijing.aliyuncs.com/76e11fce-e7fd-4985-84ec-2332b9dfef84.png', '2020-07-13 00:00:51', '2020-07-13 00:00:51', '2020-07-13 00:00:51', '333', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (503, 'dev12345678', 'kN28UowZdTQpjPbRx0L3tg==', '开发者2', '0', '1', '111', '222', 'http://youyasumi-oss.oss-cn-beijing.aliyuncs.com/76e11fce-e7fd-4985-84ec-2332b9dfef84.png', '2020-07-13 00:00:51', '2020-07-13 00:00:51', '2020-07-13 00:00:51', '333', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (504, 'dev123456789', 'kN28UowZdTQpjPbRx0L3tg==', '开发者2', '0', '1', '111', '222', 'http://youyasumi-oss.oss-cn-beijing.aliyuncs.com/76e11fce-e7fd-4985-84ec-2332b9dfef84.png', '2020-07-13 00:00:51', '2020-07-13 00:00:51', '2020-07-13 00:00:51', '333', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (505, 'dev12345678910', 'kN28UowZdTQpjPbRx0L3tg==', '开发者2', '0', '1', '111', '222', 'http://youyasumi-oss.oss-cn-beijing.aliyuncs.com/76e11fce-e7fd-4985-84ec-2332b9dfef84.png', '2020-07-13 00:00:51', '2020-07-13 00:00:51', '2020-07-13 00:00:51', '333', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (506, 'dev1234567891011', 'kN28UowZdTQpjPbRx0L3tg==', '开发者2', '0', '1', '111', '222', 'http://youyasumi-oss.oss-cn-beijing.aliyuncs.com/76e11fce-e7fd-4985-84ec-2332b9dfef84.png', '2020-07-13 00:00:51', '2020-07-13 00:00:51', '2020-07-13 00:00:51', '333', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (507, 'dev123456789101112', 'kN28UowZdTQpjPbRx0L3tg==', '开发者2', '0', '1', '111', '222', 'http://youyasumi-oss.oss-cn-beijing.aliyuncs.com/76e11fce-e7fd-4985-84ec-2332b9dfef84.png', '2020-07-13 00:00:51', '2020-07-13 00:00:51', '2020-07-13 00:00:51', '333', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (508, '11111', '3ylHCH7AU0RG5tWUuYpv4g==', '2222', '0', '1', '3333', '4444', 'http://youyasumi-oss.oss-cn-beijing.aliyuncs.com/76e11fce-e7fd-4985-84ec-2332b9dfef84.png', '2020-08-02 18:21:10', '2020-08-02 18:21:10', '2020-08-02 18:21:10', '888', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (512, '鸭鸭吖', 'Z+icX1kViod/ksh+HA5igw==', '鸭鸭小仙女', '1', '1', '135959', '891627097@qq.com', 'http://youyasumi-oss.oss-cn-beijing.aliyuncs.com/86f01364-852b-4d01-8204-2d93c9a90aa9.png', '2020-09-06 16:43:00', '2020-09-20 23:53:46', '2020-09-06 16:43:00', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (515, '鸭鸭', 'gMOtXiUOh3mh5sMbQwOnBg==', '鸭鸭小仙女', '1', '18', '1546478', '456456', 'http://youyasumi-oss.oss-cn-beijing.aliyuncs.com/8b049500-24b4-467c-91d5-8678e97d602b.png', '2020-09-20 23:57:08', '2020-09-20 23:57:08', '2020-09-20 23:57:08', '女神', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
