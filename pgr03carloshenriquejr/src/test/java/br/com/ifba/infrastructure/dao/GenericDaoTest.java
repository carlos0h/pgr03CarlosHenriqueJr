package br.com.ifba.infrastructure.dao;

import br.com.ifba.infrastructure.exception.FalhaPersistenciaException;
import br.com.ifba.usuario.entity.Usuario;
import jakarta.persistence.EntityManager;
import java.util.function.Supplier;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GenericDaoTest {

    // simula o banco fora do ar: nao consegue nem abrir o EntityManager
    private final Supplier<EntityManager> bancoForaDoAr = () -> {
        throw new IllegalStateException("banco fora do ar");
    };

    private GenericDao<Usuario> daoSemBanco() {
        return new GenericDao<>(Usuario.class, bancoForaDoAr);
    }

    private Usuario usuario() {
        Usuario u = new Usuario("Carlos", "12345678901", "carlos", "123456");
        u.setId(1L);
        return u;
    }

    @Test
    public void saveLancaExcecaoQuandoNaoConsegueConectar() {
        assertThrows(FalhaPersistenciaException.class, () -> daoSemBanco().save(usuario()));
    }

    @Test
    public void updateLancaExcecaoQuandoNaoConsegueConectar() {
        assertThrows(FalhaPersistenciaException.class, () -> daoSemBanco().update(usuario()));
    }

    @Test
    public void deleteLancaExcecaoQuandoNaoConsegueConectar() {
        assertThrows(FalhaPersistenciaException.class, () -> daoSemBanco().delete(usuario()));
    }

    @Test
    public void findByIdLancaExcecaoQuandoNaoConsegueConectar() {
        assertThrows(FalhaPersistenciaException.class, () -> daoSemBanco().findById(1L));
    }

    @Test
    public void findAllLancaExcecaoQuandoNaoConsegueConectar() {
        assertThrows(FalhaPersistenciaException.class, () -> daoSemBanco().findAll());
    }

    @Test
    public void excecaoExplicaOQueAconteceuEGuardaACausa() {
        FalhaPersistenciaException e = assertThrows(FalhaPersistenciaException.class,
                () -> daoSemBanco().findAll());

        assertEquals("Não foi possível conectar ao banco de dados.", e.getMessage());
        assertNotNull(e.getCause());
    }
}