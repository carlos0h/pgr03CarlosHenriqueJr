# SOLID.md - auditoria do MatchRH

## Task 01 - Um exemplo de cada princípio

### S - Responsabilidade única: `UsuarioService.java` (linha 11)
A classe só decide as regras de negócio do usuário (campos obrigatórios, CPF, senha, palavra proibida, login único). Gravar no banco é do DAO e receber o pedido da tela é do Controller. O único motivo para ela mudar é uma regra de negócio mudar.

### O - Aberto/fechado: `UsuarioDao.java` (linha 13)
`UsuarioDao extends GenericDao<Usuario>`. Uma entidade nova ganha o CRUD estendendo o `GenericDao`, sem editar nenhuma linha dele. O `UsuarioDao` só acrescenta o que é do domínio (`findByLogin`, `findByNome`, `loginEmUso`).

### L - Substituição de Liskov: `FakeUsuarioDao.java` (linha 10) e `UsuarioServiceTest.java` (linha 20)
O `FakeUsuarioDao` implementa o `UsuarioIDao` com uma lista em memória e entra no lugar do `UsuarioDao` (`new UsuarioService(dao)`, linha 20 do teste). O `UsuarioService` funciona igual com os dois e os 13 testes dele passam sem banco.

### I - Segregação de interfaces: `Autenticavel.java` (linhas 8 e 9) e `AutenticacaoService.java` (linha 10)
`Autenticavel` tem um único método, `autenticar`. O `AutenticacaoService.processarAutenticacao` recebe só `Autenticavel` e não depende de mais nada do `Usuario`.

### D - Inversão de dependência: `UsuarioService.java` (linhas 14 e 16)
O campo e o construtor recebem o `UsuarioIDao`, a interface, e nunca o `UsuarioDao`. Quem escolhe a implementação é a `Aplicacao`, que monta as camadas.

## Task 02 - Violações encontradas

As linhas das violações 1 e 4 são de antes das correções da Task 03.

### 1. S - `JPAUtil.java` (linhas 18, 29, 39 e 51)
A classe tinha três motivos para mudar: guardar a fábrica do JPA (linha 29), aplicar as migrations do Flyway (`migrar`, linha 39) e ler o `persistence.xml` (`lerPropriedades`, linha 51). Trocar o jeito de ler a configuração ou a ferramenta de migration mexia numa classe que também cuidava da fábrica. **Corrigida na Task 03.**

### 2. S e D - `Login.java` (linhas 163 a 165)
A tela cria um `Usuario` com login e senha escritos no código ("carlos" e "12345") e decide sozinha se o acesso é liberado. Isso é uma decisão de negócio dentro da view (S), e a tela depende da entidade concreta e de dados fixos, em vez de pedir ao Controller (D). Não corrigi porque a correção muda o comportamento: o acesso passaria a depender de um usuário cadastrado no banco.

### 3. O - `ValidadorUsuario.java` (linhas 12 a 15)
A lista de palavras proibidas está dentro do método `palavraProibida`. Para proibir uma palavra nova é preciso editar o método, ou seja, o código é modificado em vez de estendido. Como o método é `static`, também não dá para trocar as regras sem mexer na classe.

### 4. L - `Usuario.java` (linha 107)
O `equals` usava `getClass() != obj.getClass()`. Um `Candidato` com o mesmo CPF de um `Usuario` não era igual a ele, embora tivesse o mesmo `hashCode`. Código que trata um `Candidato` como `Usuario` (por exemplo `lista.contains`) se comportava diferente do esperado: o filho não substituía o pai nessa comparação. **Corrigida na Task 03.**

### 5. I - `TelaCadastroUsuario.java` (linhas 16, 20 e 25)
A tela recebe o `UsuarioIController` inteiro (cadastrar, atualizar, excluir, listar e buscar), mas só usa o `cadastrar` e entrega o controller à `Login`. Ela depende de métodos que não usa.

## Task 03 - Correções

### `JPAUtil` (princípio S)
Separei os três trabalhos em três classes, cada uma com um motivo para mudar:
- `ConfiguracaoBanco`: só lê a URL, o usuário e a senha do `persistence.xml`.
- `MigracaoBanco`: só aplica as migrations do Flyway, recebendo a `ConfiguracaoBanco` pelo construtor.
- `JPAUtil`: só guarda a fábrica do JPA e entrega `EntityManager`.

O comportamento é o mesmo: o Flyway valida as migrations, o Hibernate sobe e a tela de Login abre.

### `Usuario.equals` (princípio L)
Troquei `getClass() != obj.getClass()` por `!(obj instanceof Usuario)`, e a identidade continua sendo o CPF. Agora um `Candidato` ou `Recrutador` com o mesmo CPF é igual a um `Usuario`. O teste `candidatoComMesmoCpfDeUmUsuarioEIgualAEle` falhou antes da correção e passa depois.
