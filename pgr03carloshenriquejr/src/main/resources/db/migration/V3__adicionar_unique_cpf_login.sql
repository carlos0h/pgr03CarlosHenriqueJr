ALTER TABLE usuarios ADD CONSTRAINT uk_usuarios_cpf UNIQUE (cpf);
ALTER TABLE usuarios ADD CONSTRAINT uk_usuarios_login UNIQUE (login);
