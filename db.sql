
create database shopping_cart;

use shopping_cart;

create table users(
	user_id bigint not null primary key auto_increment,
    username varchar(255) not null,
    password varchar(255) not null
); 

insert into users(username, password) 
	values("john_doe", "john123");

select * from users;

create table products(
	
    product_id bigint not null primary key auto_increment,
    product_name varchar(255) not null,
    product_desc varchar(255) not null,
    amount double not null
     
);

select * from products;

create table carts(
	
    cart_id bigint not null primary key auto_increment,
    user_id bigint not null,
    total_amount double,
    
    constraint fk_user_cart
    foreign key (user_id)
    references users(user_id)
    
);

drop table carts;
select * from carts;

create table cart_items( 
	
    cartitem_id bigint not null primary key auto_increment,
    quantity int not null, 
    product_id int not null,
    cart_id bigint not null,
    
    constraint fk_product
    foreign key (product_id)
    references products(product_id),
    
    constraint fk_cart
    foreign key (cart_id)
    references carts(cart_id)
    on delete cascade
    
);

select * from cart_items;


