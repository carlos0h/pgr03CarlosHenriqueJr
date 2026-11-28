# Atividade 24 - Lombok e log

## Evidência 1 - o código encolheu
Commit `1f9b488` (Task 02: `Usuario` e `PersistenceEntity` com Lombok). Saída de `git diff --stat 1f9b488~1 1f9b488`:

     .../java/br/com/ifba/infrastructure/entity/PersistenceEntity.java |  13 ++--
     .../src/main/java/br/com/ifba/usuario/entity/Usuario.java         | 119 +++++++++-------------------
     2 files changed, 39 insertions(+), 93 deletions(-)

**Linhas apagadas no total: 93.** Foram inseridas 39 (as anotações), então o saldo é de 54 linhas a menos.

## Evidência 2 - a suíte não percebeu
Todos os testes passam: `Tests run: 46, Failures: 0, Errors: 0, Skipped: 0` (print `BARRA VERDE.png`).

Nenhum arquivo de `src/test` mudou nesta branch. A saída de `git diff --stat main -- pgr03carloshenriquejr/src/test` é vazia.

## Decisões da Task 02
- O `id` da `PersistenceEntity` ficou com `@Getter` e `@Setter` (e não só `@Getter`): o `UsuarioController` usa `setId` para montar a cópia da edição, e `UsuarioServiceTest` e `FakeUsuarioDao` também usam. Como nenhum teste podia mudar, o setter ficou.
- O `@EqualsAndHashCode(exclude = "senha")` do slide compararia todos os campos menos a senha, e isso mudaria a regra de identidade por CPF da atividade 15. Usei `@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)` com `@EqualsAndHashCode.Include` no `cpf`. Um teste temporário (não commitado) confirmou que dois usuários com o mesmo CPF e nomes diferentes continuam iguais.
- O construtor de 4 argumentos do `Usuario` continua escrito à mão: o `@AllArgsConstructor` gera um de 6 (com `email` e `dataCadastro`), e o `Candidato`, o `Controller` e os testes usam o de 4.

## Evidência 3 - o sistema parou de gritar no console

Linhas de `logs/app.log` depois do roteiro (cadastro, login repetido, senha curta e banco parado). Níveis: DEBUG, INFO, WARN e ERROR. Nenhum println.

    13:14:34 INFO  ProjetoApplication : Starting ProjetoApplication using Java 25.0.3 with PID 2289 (/home/carlos/Área de trabalho/ATIVIDADES/pgr03carloshenriquejr/target/classes started by carlos in /home/carlos/Área de trabalho/ATIVIDADES/pgr03carloshenriquejr)
    13:14:34 DEBUG ProjetoApplication : Running with Spring Boot v3.5.15, Spring v6.2.19
    13:14:34 INFO  ProjetoApplication : No active profile set, falling back to 1 default profile: "default"
    13:14:36 WARN  Database : Flyway upgrade recommended: PostgreSQL 18.6 is newer than this version of Flyway and support has not been tested. The latest supported version of PostgreSQL is 17.
    13:14:38 INFO  ProjetoApplication : Started ProjetoApplication in 3.87 seconds (process running for 4.459)
    13:14:38 INFO  ProjetoApplication : Aplicação iniciada: tela de login aberta
    13:14:43 DEBUG UsuarioService : Listando usuários
    13:15:37 INFO  UsuarioService : Cadastrando usuário: azin
    13:15:37 DEBUG UsuarioService : Senha forte: true
    13:15:37 INFO  UsuarioService : Usuário cadastrado: azin
    13:15:38 DEBUG UsuarioService : Listando usuários
    13:15:41 INFO  UsuarioService : Cadastrando usuário: azin
    13:15:41 DEBUG UsuarioService : Senha forte: true
    13:15:41 WARN  UsuarioService : Login já existe: azin
    13:16:05 INFO  UsuarioService : Cadastrando usuário: azin
    13:16:05 DEBUG UsuarioService : Senha forte: false
    13:16:05 WARN  UsuarioService : Dados recusados para 'azin': Senha muito curta. Mínimo de 6 caracteres.

A senha usada no cadastro de teste aparece no arquivo de log inteiro 0 vez(es).
