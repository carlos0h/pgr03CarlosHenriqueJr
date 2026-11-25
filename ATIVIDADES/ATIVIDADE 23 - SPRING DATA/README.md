# Atividade 23 - Spring Data JPA

## Task 01 - Dependência e configuração
- `pom.xml`: entrou o `spring-boot-starter-data-jpa`, no lugar do `spring-boot-starter` e do `hibernate-core`. As versões passaram a ser escolhidas pelo Spring Boot 3.5.15. O Hibernate que veio é o 6.6.53.Final (o projeto usava o 7.4.2, que o Spring Boot 3.5 não suporta), e o Flyway também passou para a versão do Spring Boot.
- `application.properties`: `spring.jpa.hibernate.ddl-auto=validate` (o Flyway cria e altera as tabelas, o Hibernate só confere) e `spring.jpa.show-sql=true`.

## Task 02 - O que ficou sem dono
O commit `cfad7d3` só remove arquivos: o Spring Data gera o que o `GenericDao` e o `UsuarioDao` faziam à mão, e o Spring Boot passou a configurar o banco e rodar o Flyway sozinho. Saída de `git diff --stat HEAD~1 HEAD`:

     .../src/main/java/br/com/ifba/infrastructure/dao/GenericDao.java  | 134 -----------------------------
     .../src/main/java/br/com/ifba/infrastructure/dao/GenericIDao.java |  24 ------
     .../java/br/com/ifba/infrastructure/util/ConfiguracaoBanco.java   |  41 ---------
     .../java/br/com/ifba/infrastructure/util/InicializadorBanco.java  |  24 ------
     .../src/main/java/br/com/ifba/infrastructure/util/JPAUtil.java    |  34 --------
     .../main/java/br/com/ifba/infrastructure/util/MigracaoBanco.java  |  22 -----
     .../src/main/java/br/com/ifba/usuario/dao/UsuarioDao.java         |  81 -----------------
     .../src/main/java/br/com/ifba/usuario/dao/UsuarioIDao.java        |  18 ----
     pgr03carloshenriquejr/src/main/resources/META-INF/persistence.xml |  12 ---
     .../test/java/br/com/ifba/infrastructure/dao/GenericDaoTest.java  |  60 -----------------------
     .../src/test/java/br/com/ifba/usuario/dao/UsuarioDaoTest.java     |  34 -------
     11 files changed, 484 deletions(-)

**Total de linhas removidas: 484**, em 11 arquivos:
- 390 linhas de código de produção e configuração (`GenericDao`, `GenericIDao`, `UsuarioDao`, `UsuarioIDao`, `JPAUtil`, `ConfiguracaoBanco`, `MigracaoBanco`, `InicializadorBanco` e o `persistence.xml`);
- 94 linhas de teste (`GenericDaoTest` e `UsuarioDaoTest`), que testavam as classes apagadas.

## Task 03 - A suíte não muda
Antes do Spring Data eram 51 testes. Agora são 46, todos verdes (print `BUILD DEPOIS.png`): 9 testes saíram (`GenericDaoTest` com 6 e `UsuarioDaoTest` com 3, das classes apagadas) e 4 entraram (`UsuarioRepositoryTest`).

O `UsuarioServiceTest` **não foi alterado**: os mesmos 13 testes passam, sem subir o Spring. A regra "nenhuma classe de teste alterada" não vale ao pé da letra aqui, por dois motivos: os testes de DAO eram das classes apagadas, e o dublê `FakeUsuarioDao` precisou seguir o contrato novo (agora implementa `UsuarioRepository`, mantendo o nome e o pacote, e por isso o `UsuarioServiceTest` ficou intacto). Todas as outras classes de teste que sobraram estão iguais.

## Task 04 - Consulta derivada
No `UsuarioRepository`: `existsByLogin(login)` e `existsByLoginAndIdNot(login, id)`. Elas substituem o `loginEmUso`, que eu tinha escrito à mão com `count` e JPQL, e são usadas no `UsuarioService` para a regra de login único (no cadastro e na edição, onde o próprio usuário não conta como duplicado).

SQL gerado pelo Spring a partir do nome:

    select u1_0.id from usuarios u1_0 where u1_0.login=? fetch first ? rows only
    select u1_0.id from usuarios u1_0 where u1_0.login=? and u1_0.id<>? fetch first ? rows only

Testes: `UsuarioRepositoryTest` (`existsByLoginDizSeOLoginExiste` e `existsByLoginAndIdNotIgnoraOProprioUsuario`, contra um H2 em memória com `@DataJpaTest`) e os testes de login duplicado do `UsuarioServiceTest`.

## Task 05 - Consulta com @Query
`buscarPorNomeOuLogin(texto)`: usuários cujo nome **ou** login contém o texto, sem diferenciar maiúsculas, ordenados por nome. O nome derivado seria `findByNomeContainingIgnoreCaseOrLoginContainingIgnoreCaseOrderByNomeAsc`, ilegível, por isso usei `@Query` com JPQL. Ela ainda não está ligada à tela de busca.

Teste: `buscarPorNomeOuLoginEncontraPelosDoisCamposEOrdenaPorNome`, com um usuário que só tem o texto no nome, outro que só tem no login e outro que não tem. SQL gerado:

    select ... from usuarios u1_0 where lower(u1_0.nome) like lower(('%'||?||'%')) ... or lower(u1_0.login) like lower(('%'||?||'%')) ... order by u1_0.nome
