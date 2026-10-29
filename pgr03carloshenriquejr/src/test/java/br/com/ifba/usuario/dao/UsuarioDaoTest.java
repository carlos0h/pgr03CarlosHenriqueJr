package br.com.ifba.usuario.dao;

import br.com.ifba.infrastructure.exception.FalhaPersistenciaException;
import br.com.ifba.usuario.entity.Usuario;
import jakarta.persistence.EntityManager;
import java.util.function.Supplier;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UsuarioDaoTest {

    private final Supplier<EntityManager> bancoForaDoAr = () -> {
        throw new IllegalStateException("banco fora do ar");
    };

    private final UsuarioDao dao = new UsuarioDao(bancoForaDoAr);

    @Test
    public void saveLancaExcecaoQuandoNaoConsegueConectar() {
        Usuario usuario = new Usuario("Ana", "98765432100", "ana", "654321");

        assertThrows(FalhaPersistenciaException.class, () -> dao.save(usuario));
    }

    @Test
    public void findByLoginLancaExcecaoQuandoNaoConsegueConectar() {
        assertThrows(FalhaPersistenciaException.class, () -> dao.findByLogin("ana"));
    }

    @Test
    public void findByNomeLancaExcecaoQuandoNaoConsegueConectar() {
        assertThrows(FalhaPersistenciaException.class, () -> dao.findByNome("an"));
    }
}