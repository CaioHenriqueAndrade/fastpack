
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
nome text,
password text,
latitude double,
longitude double
);


create table usuarioprestador (
	id int primary key,
	raio int,
	precomedio int,
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
iduser int,
latitude double,
longitude double
);

create table address (
id int primary key AUTO_INCREMENT,
street text,
streetNumber text,
neighborhood text,
city text,
state text,
zipcode text,
country text,
latitude double,
longitude double,
complementary text,
idCriador int
);


