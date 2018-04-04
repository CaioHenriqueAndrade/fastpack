
create schema fastpack;
use fastpack;

CREATE USER 'fastpack'@'localhost' IDENTIFIED BY '123456';
GRANT ALL PRIVILEGES ON * . * TO 'fastpack'@'localhost';
FLUSH PRIVILEGES;

use fastpack;
drop table usuario;
drop table pedido;
drop table Address;

use fastpack;

create table usuario (
id int primary key AUTO_INCREMENT,
status int,
tipo int,
cpf text,
password text
);


create table usuarioprestador (
	id int primary key,
	raio int,
	latitude double,
	longitude double,
	constraint id FOREIGN KEY (id)
	REFERENCES usuario (id)
);

create table pedido (
id int primary key AUTO_INCREMENT,
idPrestador int,
idAddressbusca int,
idAddressEntrega int,
valor int,
status int,
descPedido text,
horaPostado DATETIME DEFAULT CURRENT_TIMESTAMP,
horaPrazo DATETIME, 
horaRecebido DATETIME,
iduser int
);

create table address (
id int primary key AUTO_INCREMENT,
street text,
streetNumber int,
neighborhood text,
city text,
state text,
zipcode int,
country text,
latitude int,
longitude int,
complementary text,
idCriador int
);

