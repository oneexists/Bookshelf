drop database if exists bujo_test;
create database bujo_test;

use bujo_test;

create table app_user (
	app_user_id bigint primary key auto_increment,
    user_role int not null,
    username varchar(255) not null,
    `password` varchar(2056) not null,
    is_account_non_expired boolean not null,
    is_account_non_locked boolean not null,
    is_credentials_non_expired boolean not null,
    is_enabled boolean not null,
    constraint uk_app_user_username
		unique(username)
);

create table author (
	author_id bigint primary key auto_increment,
    `name` varchar(255) not null
);

create table book (
	book_id bigint primary key auto_increment,
    title varchar(200) not null,
    `language` varchar(30) null,
    pages int null,
    author_id bigint not null,
    app_user_id bigint not null,
    constraint fk_book_author_id
		foreign key (author_id)
		references author(author_id),
	constraint fk_book_app_user_id
		foreign key (app_user_id)
		references app_user(app_user_id)
);

create table reading_log (
	reading_log_id bigint primary key auto_increment,
    `start` date null,
    finish date null,
    book_id bigint not null,
    constraint fk_reading_log_book_id
		foreign key (book_id)
		references book(book_id)
);

delimiter //
create procedure set_known_good_state()
begin
	delete from book;
    alter table book auto_increment = 1;
	delete from reading_log;
    alter table reading_log auto_increment = 1;
	delete from author;
    alter table author auto_increment = 1;
	delete from app_user;
    alter table app_user auto_increment = 1;
    
    insert into app_user(user_role, username, `password`, is_account_non_expired, is_account_non_locked, is_credentials_non_expired, is_enabled) values
		(0, 'username', '$2a$10$bJ.Q1/9A/1i4LpO90CVnHO.DK464jvQnrXUo0QHJggWEhgLF3eElm', true, true, true, true);
        
	insert into author(`name`) values 
		('Leo Tolstoy'), 
        ('Albert Camus'),
        ('Henry David Thoreau'),
        ('Stephen King'),
        ('Kurt Vonnegut');
        
	insert into book(title, `language`, pages, author_id, app_user_id) values
		('War and Peace', 'English', 1296, 1, 1),
        ('L''étranger', 'French', 185, 2, 1),
        ('The Regulators', 'English', 512, 3, 1),
        ('Hearts in Atlantis', 'English', 640, 3, 1),
        ('Hocus Pocus', 'English', 322, 4, 1);
        
	insert into reading_log(`start`, finish, book_id) values 
		('2020-04-01', '2020-04-18', 2);
end //
delimiter ;