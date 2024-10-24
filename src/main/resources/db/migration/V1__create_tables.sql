create table usuario(
	id int auto_increment primary key,
	nome varchar(100),
	email varchar(100),
	senha varchar(50)
);

create table estante(
	id int auto_increment primary key,
	usuario_id int references usuario(id)
);

create table livro(
	id int auto_increment primary key,
	estante_id int references estante(id),
	titulo varchar(255),
	nota int
);
