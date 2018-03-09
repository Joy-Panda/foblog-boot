/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.5.28 : Database - foblog
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`foblog` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `foblog`;

/*Table structure for table `t_article` */

DROP TABLE IF EXISTS `t_article`;

CREATE TABLE `t_article` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `title` varchar(70) NOT NULL COMMENT '标题',
  `summary` varchar(600) DEFAULT NULL COMMENT '概要',
  `content` text COMMENT '内容',
  `category_ids` varchar(20) DEFAULT NULL COMMENT '类别id,多个,用/分开',
  `tag_ids` varchar(20) DEFAULT NULL COMMENT '标签id,多个,用,分开',
  `status` int(1) NOT NULL COMMENT '文章状态：0-草稿，1-博文',
  `insert_time` datetime DEFAULT NULL COMMENT '撰写时间',
  `update_time` datetime DEFAULT NULL,
  `pub_time` datetime DEFAULT NULL COMMENT '发布时间',
  `hits` int(10) DEFAULT '0',
  `code` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE,
  UNIQUE KEY `article_code` (`code`) USING BTREE,
  KEY `index_update` (`update_time`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `t_article` */

insert  into `t_article`(`id`,`title`,`summary`,`content`,`category_ids`,`tag_ids`,`status`,`insert_time`,`update_time`,`pub_time`,`hits`,`code`) values (1,'测试','测试','测试测试','1','1,',1,'2018-01-12 20:35:23','2018-01-12 20:35:23','2018-01-10 12:28:23',11,'521012310281218'),(2,'测试','hello','测试','1','1,',1,'2018-01-12 20:35:23','2018-01-19 20:35:23',NULL,0,NULL),(3,'测试二下','hello word 西红柿','测试二下','1','1,',1,'2018-01-12 20:35:23','2018-01-12 20:37:23',NULL,2,NULL);

/*Table structure for table `t_authors` */

DROP TABLE IF EXISTS `t_authors`;

CREATE TABLE `t_authors` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `account` varchar(20) NOT NULL COMMENT '账户名',
  `password` varchar(32) NOT NULL COMMENT '账户密码',
  `pen_name` varchar(20) DEFAULT NULL COMMENT '笔名',
  `email` varchar(30) DEFAULT NULL COMMENT '常用邮箱',
  `profile` text COMMENT '个人介绍',
  `verification_code` varchar(32) DEFAULT NULL COMMENT '验证码',
  `verif_code_deadline` datetime DEFAULT NULL COMMENT '验证码截止时间',
  `security_question_id` int(10) DEFAULT NULL COMMENT '密保问题id',
  `user_status` int(1) DEFAULT NULL COMMENT '用户状态:0禁用，1启用',
  `other_info` varchar(500) DEFAULT NULL COMMENT '其他信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `t_authors` */

insert  into `t_authors`(`id`,`account`,`password`,`pen_name`,`email`,`profile`,`verification_code`,`verif_code_deadline`,`security_question_id`,`user_status`,`other_info`) values (1,'admin','202CC9B0383B17524DC1086113E42C55','zhaopan','zhaopan@163.com','hahahahaha',NULL,NULL,NULL,1,NULL);

/*Table structure for table `t_category` */

DROP TABLE IF EXISTS `t_category`;

CREATE TABLE `t_category` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `name` varchar(30) NOT NULL COMMENT '名称',
  `status` tinyint(1) DEFAULT '0' COMMENT '展示状态',
  `pub_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `code` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `category_code` (`code`) USING BTREE,
  KEY `index_update` (`update_time`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Data for the table `t_category` */

insert  into `t_category`(`id`,`description`,`name`,`status`,`pub_time`,`update_time`,`code`) values (1,'Java基础知识','Java基础',1,NULL,'2018-01-11 17:21:16','JBase'),(2,'Java高级特性','Java高级',1,NULL,'2018-01-11 17:21:16','JAdvanced'),(3,'JVM解析','JVM解析',1,NULL,'2018-01-11 12:21:16','JVM'),(4,'测试一下','测试',0,NULL,'2018-01-12 20:35:23',NULL),(5,'测','测试',0,NULL,'2018-01-12 22:35:23',NULL),(6,'测试','测试',0,NULL,'2018-01-12 23:35:23',NULL),(7,'测试','测试',0,NULL,'2018-01-12 23:35:23',NULL),(8,'哈哈','哈哈',0,NULL,'2018-01-12 23:37:23',NULL);

/*Table structure for table `t_friendlink` */

DROP TABLE IF EXISTS `t_friendlink`;

CREATE TABLE `t_friendlink` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `description` varchar(1024) DEFAULT NULL,
  `website` varchar(60) NOT NULL,
  `hits` int(10) DEFAULT '0',
  `priority` int(2) DEFAULT NULL,
  `web_author_name` varchar(20) DEFAULT NULL,
  `web_author_email` varchar(30) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `add_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_update` (`update_time`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

/*Data for the table `t_friendlink` */

insert  into `t_friendlink`(`id`,`name`,`description`,`website`,`hits`,`priority`,`web_author_name`,`web_author_email`,`update_time`,`add_time`) values (16,'程序员DD博客','本博客主要维护人翟永超 ，毕业于东华大学硕士研究生，现任职于永辉云创架构师，主要负责基于Spring Cloud的微服务架构落地。《Spring Cloud微服务实战》作者，SpringCloud中文社区创始人（bbs.springcloud.com.cn），Spring4All社区联合发起人（spring4all.com）。','http://blog.didispace.com/',2,1,'翟永超','didi@potatomato.club','2018-01-11 17:21:16','2018-01-09 15:29:16'),(17,'Spring Cloud周立','Spring Cloud与Docker微服务架构实战》作者\nSpring4All社区发起人之一','http://www.itmuch.com/',1,2,'周立','eacdy0000@126.com','2018-01-11 17:21:16','2018-01-09 20:22:26'),(18,'测试','测试一下','http://www.baidu.com',1,NULL,'222',NULL,'2018-01-12 20:35:23',NULL);

/*Table structure for table `t_guest` */

DROP TABLE IF EXISTS `t_guest`;

CREATE TABLE `t_guest` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `email` varchar(30) DEFAULT NULL COMMENT '通讯邮箱',
  `nickname` varchar(30) NOT NULL COMMENT '昵称',
  `personal_website` varchar(50) DEFAULT NULL COMMENT '个人网址',
  `access_ip` varchar(30) DEFAULT NULL COMMENT '访问IP',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `t_guest` */

insert  into `t_guest`(`id`,`email`,`nickname`,`personal_website`,`access_ip`) values (1,'zhaopan@gomeholdings.com','zhaopan','http://github.com',NULL);

/*Table structure for table `t_message` */

DROP TABLE IF EXISTS `t_message`;

CREATE TABLE `t_message` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `parent_id` int(10) DEFAULT NULL COMMENT '父id',
  `content` varchar(600) NOT NULL COMMENT '内容',
  `user_type` int(1) NOT NULL COMMENT '留言作者类别（author作者，guest访客）',
  `author_id` int(10) NOT NULL COMMENT '作者id',
  `article_id` int(10) NOT NULL COMMENT '所属文章id',
  `update_time` datetime DEFAULT NULL,
  `pub_time` datetime DEFAULT NULL COMMENT '发表时间',
  `block_id` int(10) DEFAULT NULL COMMENT '所在文章的评论区域属于第几块',
  `support_count` int(10) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `index_update` (`update_time`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `t_message` */

insert  into `t_message`(`id`,`parent_id`,`content`,`user_type`,`author_id`,`article_id`,`update_time`,`pub_time`,`block_id`,`support_count`) values (1,0,'<p><a href=\"mailto:zhaopan@gomeholdings.com\">zhaopan@gomeholdings.com</a></p>\n',0,1,1,'2018-01-12 20:35:23','2018-01-10 12:29:05',NULL,0),(2,0,'<p>测试一下</p>\n',0,1,1,'2018-01-12 20:35:23','2018-01-10 12:29:23',NULL,0);

/*Table structure for table `t_project` */

DROP TABLE IF EXISTS `t_project`;

CREATE TABLE `t_project` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `introduction` varchar(1000) DEFAULT NULL COMMENT '介绍',
  `article_url` varchar(200) DEFAULT NULL,
  `down_url` varchar(100) DEFAULT NULL,
  `hits` int(10) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `pub_time` datetime DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_update` (`update_time`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `t_project` */

insert  into `t_project`(`id`,`name`,`introduction`,`article_url`,`down_url`,`hits`,`update_time`,`pub_time`,`status`) values (1,'测试一下','测试一下','http://baidu.com','http://baidu.com',NULL,'2018-01-12 20:35:23',NULL,1);

/*Table structure for table `t_recommend` */

DROP TABLE IF EXISTS `t_recommend`;

CREATE TABLE `t_recommend` (
  `id` bigint(18) NOT NULL AUTO_INCREMENT,
  `title` varchar(150) DEFAULT NULL COMMENT '标题',
  `has_content` int(1) DEFAULT '0' COMMENT '是否包含内容',
  `article_url` varchar(300) DEFAULT NULL COMMENT '文章对应的url',
  `content_id` int(10) DEFAULT NULL COMMENT '对应的内容id，前提为has_content为true，即1',
  `hits` int(10) DEFAULT '0' COMMENT '点击量',
  `update_time` datetime DEFAULT NULL,
  `pub_time` datetime DEFAULT NULL COMMENT '发布时间',
  `summary` varchar(300) DEFAULT NULL COMMENT '简要说明',
  PRIMARY KEY (`id`),
  KEY `index_update` (`update_time`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `t_recommend` */

insert  into `t_recommend`(`id`,`title`,`has_content`,`article_url`,`content_id`,`hits`,`update_time`,`pub_time`,`summary`) values (1,'使用 Istio治理微服务入门',0,'http://www.servicemesh.cn/?/article/33',NULL,0,'2018-01-11 17:21:16','2018-01-10 12:40:16','Istio 是一个由 IBM、Google 以及 Lyft 联合推出的开源软件，以无痛方式为运行在 Kubernetes 上的微服务提供流量管理，访问策略管理以及监控等功能。'),(2,'微服务“新秀”之Service Mesh',0,'http://blog.csdn.net/zvayivqt0ufji/article/details/78351355',NULL,0,'2018-01-11 17:21:16','2018-01-10 12:42:15','本文出自于ADDOPS团队，该文章的译者霍明明参与了360 HULK云平台容器化及虚拟化平台相关服务建设，对微服务有着独到的见解。今天的主角Istio是Google/IBM/Lyft联合开发的开源项目，估计很多同学在此之前可能完全没有听过这个名字，请不必介意，因为Istio出世也才五个月而已。让我们跟着作者一起揭开Service Mesh的神秘面纱。'),(3,'测试一下',0,NULL,NULL,0,'2018-01-12 17:42:15','2018-01-12 12:42:15',NULL);

/*Table structure for table `t_security_question` */

DROP TABLE IF EXISTS `t_security_question`;

CREATE TABLE `t_security_question` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_type` int(1) NOT NULL COMMENT '用户类别（管理员，作者）',
  `question01` varchar(30) NOT NULL,
  `answer01` varchar(20) NOT NULL,
  `question02` varchar(30) DEFAULT NULL,
  `answer02` varchar(20) DEFAULT NULL,
  `question03` varchar(30) DEFAULT NULL,
  `answer03` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_security_question` */

/*Table structure for table `t_tag` */

DROP TABLE IF EXISTS `t_tag`;

CREATE TABLE `t_tag` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '标签名称',
  `insert_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_update` (`update_time`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `t_tag` */

insert  into `t_tag`(`id`,`name`,`insert_time`,`update_time`) values (1,'springcloud',NULL,'2018-01-11 17:21:16'),(2,'springboot',NULL,'2018-01-11 17:21:16'),(3,'mybatis',NULL,'2018-01-18 20:35:23'),(4,'测试',NULL,'2018-01-12 20:35:23');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
