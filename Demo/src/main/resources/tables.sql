CREATE TABLE `students` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一 ID',
  `name` varchar(10) NOT NULL COMMENT '姓名',
  `card` text COMMENT '身份证',
  `gender` tinyint(1) NOT NULL COMMENT '性别',
  `birthday` timestamp NULL DEFAULT NULL COMMENT '生日',
  `height` double DEFAULT NULL COMMENT '身高',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `teachers` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一 ID',
  `name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `age` int(3) DEFAULT NULL COMMENT '年龄',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
