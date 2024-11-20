create table TB_ESTADO(
	ID serial primary key,
	NOME varchar (25) not null,
	UF char (2) not null,
	REGIAO varchar (25) not null
)

create table TB_CIDADE(
	ID serial primary key,
	FK_ESTADO integer,
		foreign key (FK_ESTADO)
		references TB_ESTADO(ID),
	NOME varchar (255) not null	
)

create table TB_ENDERECO(
	ID serial primary key,
	CEP varchar (8) not null,
	LOGRADOURO varchar (255) not null,
	NUMERO integer not null,
	COMPLEMENTO varchar (100),
	FK_CIDADE integer,
		foreign key (FK_CIDADE)
		references TB_CIDADE(ID)
)

create table TB_PESSOA(
	ID serial primary key,
	NOME varchar (255) not null,
	DATA_CONSTITUICAO date not null,
	EMAIL varchar (255),
	TELEFONE bigint not null unique,
	FK_ENDERECO integer,
		foreign key (FK_ENDERECO)
		references TB_ENDERECO(ID),
	STATUS boolean not null
)

create table TB_PESSOA_JURIDICA(
	ID serial primary key,
	CNPJ varchar (14) not null unique,
	FK_PESSOA integer,
		foreign key (FK_PESSOA)
		references TB_PESSOA(ID)
)

create table TB_CARGO(
	ID serial primary key,
	NOME varchar (255) not null unique,
	REMUNERACAO numeric (10,2) not null
)

create table TB_PESSOA_FISICA(
	ID serial primary key,
	CPF varchar (11) not null unique,
	FK_PESSOA integer,
		foreign key (FK_PESSOA)
		references TB_PESSOA(ID),
	FK_CARGO integer,
		foreign key (FK_CARGO)
		references TB_CARGO(ID)
)

drop table TB_PESSOA, TB_PESSOA_JURIDICA, TB_CARGO, TB_PESSOA_FISICA