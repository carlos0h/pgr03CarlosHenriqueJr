package br.com.ifba.usuario.repositorio;

import br.com.ifba.usuario.entity.Usuario;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;

// roda o repositorio de verdade contra um banco em memoria (H2): testa as consultas, nao o PostgreSQL
@DataJpaTest
class UsuarioRepositoryTest {

    private final UsuarioRepository repository;

    UsuarioRepositoryTest(@Autowired UsuarioRepository repository) {
        this.repository = repository;
    }

    private Usuario salvar(String nome, String cpf, String login) {
        Usuario usuario = new Usuario(nome, cpf, login, "123456");
        usuario.setEmail(login + "@email.com");
        return repository.save(usuario);
    }

    @Test
    void existsByLoginDizSeOLoginExiste() {
        salvar("Carlos", "12345678901", "carlos");

        assertTrue(repository.existsByLogin("carlos"));
        assertFalse(repository.existsByLogin("ana"));
    }

    @Test
    void existsByLoginAndIdNotIgnoraOProprioUsuario() {
        Usuario carlos = salvar("Carlos", "12345678901", "carlos");
        Usuario ana = salvar("Ana", "98765432100", "ana");

        assertFalse(repository.existsByLoginAndIdNot("carlos", carlos.getId())); // o login e dele mesmo
        assertTrue(repository.existsByLoginAndIdNot("carlos", ana.getId()));     // outro usuario usa esse login
    }

    @Test
    void buscaPorNomeIgnoraMaiusculasEOrdenaPorNome() {
        salvar("Carlos Souza", "11111111111", "carlos");
        salvar("Ana Carla", "22222222222", "ana");
        salvar("Bruno", "33333333333", "bruno");

        List<Usuario> resultado = repository.findByNomeContainingIgnoreCaseOrderByNomeAsc("CARL");

        assertEquals(2, resultado.size());
        assertEquals("Ana Carla", resultado.get(0).getNome());
        assertEquals("Carlos Souza", resultado.get(1).getNome());
    }

    @Test
    void buscarPorNomeOuLoginEncontraPelosDoisCamposEOrdenaPorNome() {
        salvar("Maria", "11111111111", "mari");
        salvar("Zeca Mario", "22222222222", "zeca");   // "mari" so no nome
        salvar("Joao", "33333333333", "marilia");      // "mari" so no login
        salvar("Bruno", "44444444444", "bruno");       // nao tem "mari"

        List<Usuario> resultado = repository.buscarPorNomeOuLogin("MARI");

        assertEquals(3, resultado.size());
        assertEquals("Joao", resultado.get(0).getNome());
        assertEquals("Maria", resultado.get(1).getNome());
        assertEquals("Zeca Mario", resultado.get(2).getNome());
    }
}