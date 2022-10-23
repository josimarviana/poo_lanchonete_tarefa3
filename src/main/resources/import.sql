insert into estado (id, nome) values (1, 'Minas Gerais');
insert into estado (id, nome) values (2, 'São Paulo');
insert into estado (id, nome) values (3, 'Ceará');

insert into cidade (id, nome, estado_id) values (1, 'Uberlândia', 1);
insert into cidade (id, nome, estado_id) values (2, 'Belo Horizonte', 1);
insert into cidade (id, nome, estado_id) values (3, 'São Paulo', 2);
insert into cidade (id, nome, estado_id) values (4, 'Campinas', 2);
insert into cidade (id, nome, estado_id) values (5, 'Fortaleza', 3);

insert into forma_pagamento (descricao) values ('Cartão de crédito');
insert into forma_pagamento (descricao) values ('Cartão de débito');
insert into forma_pagamento (descricao) values ('Dinheiro');
insert into forma_pagamento (descricao) values ('PIX');

insert ignore into cozinha (nome) values ('Quitandas');
insert ignore into cozinha (nome) values ('Hamburguerias');

insert into restaurante (nome, descricao, cozinha_id) values ("fatinha", "empadas x", 1);
insert into restaurante (nome, descricao, cozinha_id) values ("galpao", "galpao do hamburguer", 2);

insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3), (1, 4), (2, 4);

insert into cliente (cpf, nome, telefone, email, data_nascimento) values ('11111111111', 'Josimar Viana', '61998287070', 'josimar@gmail.com', '1997-11-28');
insert into cliente (cpf, nome, telefone, email, data_nascimento) values ('22222222222', 'Maria Silva', '22555554445', 'maria@gmail.com', '2000-11-28');
insert into cliente (cpf, nome, telefone, email, data_nascimento) values ('33333333333', 'Jose Pereira', '22555554446', 'jose@gmail.com', '2000-11-28');

insert into produto (nome, descricao, preco, restaurante_id) values ('Sanduiche Ixe', 'Sanduiche Ixe', 20, 2);
insert into produto (nome, descricao, preco, restaurante_id) values ('Sanduiche Promo', 'Sadcuiche Promo', 10, 2);
insert into produto (nome, descricao, preco, restaurante_id) values ('Empada Pimenta', 'Empada Pimenta', 30, 1);

insert into pedido (data_criacao, data_atualizacao, valor_total, cliente_id, restaurante_id) values (utc_timestamp, utc_timestamp, 20.00, 1, 1);
insert into pedido (data_criacao, data_atualizacao, valor_total, cliente_id, restaurante_id) values (utc_timestamp, utc_timestamp, 20.00, 2, 2);

insert into item_pedido (produto_id, quantidade, preco_unitario, preco_total) values (3, 1, 20, 20);
insert into item_pedido (produto_id, quantidade, preco_unitario, preco_total) values (2, 1, 10, 10);
insert into item_pedido (produto_id, quantidade, preco_unitario, preco_total) values (1, 1, 30, 30);

insert into pedido_item_pedido (pedido_id, item_pedido_id) values (1, 1);
insert into pedido_item_pedido (pedido_id, item_pedido_id) values (2, 2);
insert into pedido_item_pedido (pedido_id, item_pedido_id) values (2, 3);

update cliente set endereco_logradouro="x", endereco_numero="71", endereco_bairro="centro", endereco_cidade_id=1, endereco_cep="38600000" where id = 1;
update cliente set endereco_logradouro="y", endereco_numero="72", endereco_bairro="centro", endereco_cidade_id=2, endereco_cep="38600000" where id = 2;
update cliente set endereco_logradouro="z", endereco_numero="73", endereco_bairro="centro", endereco_cidade_id=3, endereco_cep="38600000" where id = 3;