CREATE TABLE `dt_salesman` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '业务人员ID',
	`user_id` bigint(20) NOT NULL COMMENT '用户ID',
	`commission` decimal(11,2) DEFAULT NULL COMMENT '佣金',
	`receiving_limit` INT DEFAULT NULL COMMENT '接单上限',
  `association_status` char(1) DEFAULT '1' COMMENT '关联状态（0已关联 1未关联 2申请中）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务人员信息表';

CREATE TABLE `dt_salesman_leader` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '业务组长ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `commission` decimal(11,2) DEFAULT NULL COMMENT '佣金',
  `receiving_limit` int(11) DEFAULT NULL COMMENT '接单上限',
  `association_status` char(1) DEFAULT '1' COMMENT '关联状态（0已关联 1未关联）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='业务组长信息表';

CREATE TABLE `team_association_application` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '申请ID',
	`applicant_id` bigint(20) NOT NULL COMMENT '申请人Id(userId)',
	`approver_id` bigint(20) NOT NULL COMMENT '审核人Id(salesManId)',
	`dept_id` bigint(20) NOT NULL COMMENT '申请归属部门id',
	 `association_status` char(1) DEFAULT '1' COMMENT '关联状态（0已关联 1未关联 2申请中）',
	`create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='团队关联申请表';

CREATE TABLE `dt_business_task_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `task_id` bigint(20) NOT NULL COMMENT '任务主表id',
  `task_no` varchar(16) NOT NULL COMMENT '任务编码',
  `order_date` date NOT NULL COMMENT '订单日期',
  `picture_url` varchar(128) NOT NULL COMMENT '图片(path,Expires,Signature)',
  `platform_url` varchar(512) NOT NULL COMMENT ' 链接',
  `shop_name` varchar(64) NOT NULL COMMENT '店铺名',
  `call_center` varchar(64) DEFAULT NULL COMMENT '客服',
  `unit_price` decimal(11,2) DEFAULT '0.00' COMMENT '单价/元',
  `unit_price_remark` varchar(128) DEFAULT NULL COMMENT '备注',
  `special_remarks` varchar(128) DEFAULT NULL COMMENT '特殊备注',
  `keyword` varchar(128) DEFAULT NULL COMMENT '关键字',
  `promoters_modify_unit_price` decimal(11,2) DEFAULT '0.00' COMMENT '地推员修改的单价/元',
  `promoters_unit_price_remark` varchar(128) DEFAULT NULL COMMENT '地推员备注',
  `commission` decimal(9,2) DEFAULT '0.00' COMMENT '佣金',
  `platform_nickname` varchar(64) DEFAULT NULL COMMENT '平台昵称',
  `salesman_commit_url` varchar(128) DEFAULT NULL COMMENT '业务员提交图片ossparam((path,Expires,Signature))',
  `salesman_leader_user_id` bigint(20) DEFAULT NULL COMMENT '业务组长的userid',
  `salesman_user_id` bigint(20) DEFAULT NULL COMMENT '业务员userid',
  `merchant_user_id` bigint(20) DEFAULT NULL COMMENT '商家userid',
  `group_company_remark` varchar(128) DEFAULT NULL COMMENT '集团备注',
  `status` char(1) NOT NULL DEFAULT '1' COMMENT '订单状态 0 完成 1 待完成 2 停用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=utf8 COMMENT='商业任务信息明细表';


CREATE TABLE `dt_business_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `required_completion_date` datetime NOT NULL COMMENT '任务要求完成日期',
  `group_allocate_time` datetime DEFAULT NULL COMMENT '集团分配时间',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门id',
  `order_status` char(1) NOT NULL COMMENT '订单状态（0 完成 1 待完成 2 停用）',
  `allocate_time` datetime DEFAULT NULL COMMENT '分配时间',
  `feedback_picture_url1` varchar(128) DEFAULT NULL COMMENT '反馈图片url1(path,Expires,Signature)',
  `feedback_picture_url2` varchar(128) DEFAULT NULL COMMENT '反馈图片url2(path,Expires,Signature)',
  `feedback_picture_url3` varchar(128) DEFAULT NULL COMMENT '反馈图片url3(path,Expires,Signature)',
  `feedback_picture_url4` varchar(128) DEFAULT NULL COMMENT '反馈图片url4(path,Expires,Signature)',
  `feedback_picture_url5` varchar(128) DEFAULT NULL COMMENT '反馈图片url5(path,Expires,Signature)',
  `completion_time` datetime DEFAULT NULL COMMENT '完成时间',
  `create_time` datetime NOT NULL COMMENT '生成时间',
  `remark` varchar(128) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COMMENT='商业任务信息表';

CREATE TABLE `dt_merchant` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商家ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `commission` decimal(11,2) DEFAULT NULL COMMENT '佣金',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='商家信息表';

CREATE TABLE `dt_merchant_user_shopname` (
  `merchant_user_id` bigint(20) NOT NULL COMMENT '商家userid',
  `shop_name` varchar(64) NOT NULL COMMENT '店铺名',
  PRIMARY KEY (`merchant_user_id`,`shop_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商家店铺关联表';


alter table sys_user modify column  `user_type` varchar(2) DEFAULT '00' COMMENT '用户类型（00系统用户 01业务组长 02业务员 03商家）';

alter table sys_dept add column `leader_id` bigint(20) DEFAULT NULL COMMENT '负责人id（user_id）' AFTER `order_num`;


