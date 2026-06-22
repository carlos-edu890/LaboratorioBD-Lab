-- 1. Inserindo Cidades
INSERT INTO cidade (id, uf, nome, estado) VALUES (1, 'MA', 'São Luís', 'Maranhão');
INSERT INTO cidade (id, uf, nome, estado) VALUES (2, 'MA', 'Imperatriz', 'Maranhão');

-- 2. Inserindo Distancia (Utilizando as chaves estrangeiras mapeadas no @JoinColumn)
INSERT INTO distancia (id, quilometros, adicionalkmRodado, cidade_origem_id, cidade_destino_id) VALUES (1, 600, 0.00, 1, 2);

-- 3. Inserindo Categoria de Frete
INSERT INTO categoria_frete (id, nome, descricao, percentualAdicional) VALUES (1, 'Normal', 'Sem taxa adicional', 0.0);

-- 4. Inserindo Filial e Tipo de Veículo
INSERT INTO filial (id, nome, endereco, telefone) VALUES (1, 'Matriz Central', 'Rua das Transportadoras, 100', '9899999999');
INSERT INTO tipo_veiculo (id, descricao, pesoMaximo) VALUES (1, 'Caminhão Baú', 5000.0);

-- 5. Inserindo Veiculo
INSERT INTO veiculo (id, numeroPlaca, pesoMaximo, filial_id, tipo_veiculo_id) VALUES (1, 'XYZ-1234', 5000, 1, 1);

-- 6. Inserindo Cliente (Estratégia JOINED exige inserção na classe pai primeiro)
INSERT INTO pessoa_fisica (id, nome, email, telefone, cpf) VALUES (1, 'João da Silva', 'joao@email.com', '9899999999', '12345678900');
INSERT INTO cliente (id, contato, ativo) VALUES (1, 'Contato Direto', 1);