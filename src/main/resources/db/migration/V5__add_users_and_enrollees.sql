INSERT INTO `adcom`.`user` (`user_id`, `name`, `surname`, `email`, `date_of_birth`) VALUES ('1', 'Дима', 'kholodok', 'dm.kholodok@gmail.com', '1998-01-01');
INSERT INTO `adcom`.`user` (`user_id`, `name`, `surname`, `email`, `date_of_birth`) VALUES ('2', 'elena', 'kholodok', 'el.kholodok@gmail.com', '1999-02-01');
INSERT INTO `adcom`.`user` (`user_id`, `name`, `surname`, `email`, `date_of_birth`) VALUES ('3', 'stas', 'shishov', 'shish@mail.ru', '1999-02-03');

INSERT INTO `adcom`.`user_auth` (`user_id`, `login`, `password`) VALUES ('1', '1', '$2a$10$lozKpbqiJDVPOWbzTjb/BuDqfmYF8rFSUzrM0XXjJhq9BeTzooPMK');
INSERT INTO `adcom`.`user_role` (`user_id`, `role_id`)
	VALUES
		('1',
		(select role_id from role where name = 'ROLE_ADMIN'));

INSERT INTO `adcom`.`user_auth` (`user_id`, `login`, `password`) VALUES ('2', '2', '$2a$10$pMoFeKS1kYxqs/bMR6Ykk.0rtTSMYZ5tw28RknzLqBgy.H7UO.5A.');
INSERT INTO `adcom`.`user_role` (`user_id`, `role_id`)
	VALUES
		('2',
		(select role_id from role where name = 'ROLE_OPERATOR'));

INSERT INTO `adcom`.`user_auth` (`user_id`, `login`, `password`) VALUES ('3', '3', '$2a$10$F06XVtqwXE35Mdtc4/IvSOsQ/FfLVcO./CsUSEnSAh/9d8AH8wOOS');
INSERT INTO `adcom`.`user_role` (`user_id`, `role_id`)
	VALUES
		('3',
		(select role_id from role where name = 'ROLE_ENROLLEE'));

INSERT INTO `adcom`.`user` (`user_id`, `name`, `surname`, `email`, `date_of_birth`) VALUES ('4', 'Олег', 'Сцыкун', 'oleg123__@gmail.com', '1998-05-20');
INSERT INTO `adcom`.`user` (`user_id`, `name`, `surname`, `email`, `date_of_birth`) VALUES ('5', 'Dina', 'Familiya', 'dina123_q@gmail.com', '1998-05-21');
INSERT INTO `adcom`.`user` (`user_id`, `name`, `surname`, `email`, `date_of_birth`) VALUES ('6', 'Maksim', 'Semihodin', 'maks74@mail.ru', '1996-03-04');

INSERT INTO `adcom`.`user_auth` (`user_id`, `login`, `password`) VALUES ('4', '11', '$2a$10$lozKpbqiJDVPOWbzTjb/BuDqfmYF8rFSUzrM0XXjJhq9BeTzooPMK');
INSERT INTO `adcom`.`user_role` (`user_id`, `role_id`)
VALUES
	('4',
	 (select role_id from role where name = 'ROLE_OPERATOR'));

INSERT INTO `adcom`.`user_auth` (`user_id`, `login`, `password`) VALUES ('5', '22', '$2a$10$pMoFeKS1kYxqs/bMR6Ykk.0rtTSMYZ5tw28RknzLqBgy.H7UO.5A.');
INSERT INTO `adcom`.`user_role` (`user_id`, `role_id`)
VALUES
	('5',
	 (select role_id from role where name = 'ROLE_ENROLLEE'));

INSERT INTO `adcom`.`user_auth` (`user_id`, `login`, `password`) VALUES ('6', '33', '$2a$10$F06XVtqwXE35Mdtc4/IvSOsQ/FfLVcO./CsUSEnSAh/9d8AH8wOOS');
INSERT INTO `adcom`.`user_role` (`user_id`, `role_id`)
VALUES
	('6',
	 (select role_id from role where name = 'ROLE_ENROLLEE'));


INSERT INTO `adcom`.`enrollee_data` (`user_id`) VALUES ('3');
INSERT INTO `adcom`.`enrollee_data` (`user_id`) VALUES ('5');
INSERT INTO `adcom`.`enrollee_data` (`user_id`) VALUES ('6');
