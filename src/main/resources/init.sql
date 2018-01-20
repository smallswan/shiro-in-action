CREATE TABLE `zln_user` (
  `id` bigint(20) NOT NULL  COMMENT '数据ID',
  `username` varchar(20) DEFAULT NULL COMMENT '用户名（系统自动生成编号）',
  `password` varchar(150) DEFAULT NULL COMMENT '登录密码',
  `pwd_salt` varchar(150) DEFAULT NULL COMMENT '登录密码盐',
  `mobile` char(11) DEFAULT NULL COMMENT '手机号码',
  `email` varchar(30) DEFAULT NULL COMMENT '电子邮箱',
  `user_status` char(1) DEFAULT NULL COMMENT '用户状态：0 正常，1 冻结， 2 删除',
  `register_time` datetime DEFAULT NULL COMMENT '注册时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最近登录时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='用户表';



CREATE TABLE `zln_role` (
  `id` bigint(20) NOT NULL  COMMENT '角色id',
  `role` varchar(30) DEFAULT NULL COMMENT '角色名',
  `description` varchar(60) DEFAULT NULL COMMENT '角色描述',
  `available` char(1) DEFAULT NULL COMMENT '是否可用:0 不可用，1可用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQ_role` (`role`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='角色，shiro概念';



CREATE TABLE `zln_permission` (
  `id` bigint(20) NOT NULL  COMMENT '权限id',
  `permission` varchar(30) DEFAULT NULL COMMENT '权限',
  `description` varchar(60) DEFAULT NULL COMMENT '权限描述',
  `available` char(1) DEFAULT NULL COMMENT '是否可用:0 不可用，1可用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQ_permission` (`permission`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='权限表，shiro概念';


CREATE TABLE `zln_role_permission` (
  `id` bigint(20) NOT NULL ,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `permission_id` bigint(20) DEFAULT NULL COMMENT '权限ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `AK_Union_UQ_role_id_permission_id` (`role_id`,`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表、权限表的关联表';



CREATE TABLE `zln_user_role` (
  `id` bigint(20) NOT NULL ,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户表主键',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色表主键',
  PRIMARY KEY (`id`),
  UNIQUE KEY `Union_UQ_user_id_role_id` (`user_id`,`role_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='用户表、角色表的关联表';