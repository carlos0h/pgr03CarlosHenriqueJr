package br.com.ifba.usuario.dao;

import br.com.ifba.infrastructure.dao.GenericDao;
import br.com.ifba.infrastructure.exception.FalhaPersistenciaException;
import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.exception.UsuarioNaoEncontradoException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.function.Supplier;

// DAO de usuario: herda o CRUD do GenericDao e acrescenta as buscas do dominio
public class UsuarioDao extends GenericDao<Usuario> implements UsuarioIDao {

    public UsuarioDao() {
        super(Usuario.class);
    }

    // usado nos testes, que trocam a origem do EntityManager
    public UsuarioDao(Supplier<EntityManager> fabrica) {
        super(Usuario.class, fabrica);
    }

    @Override
    public Usuario findByLogin(String login) {
        EntityManager em = abrir();
        List<Usuario> resultado;
        try {
            resultado = em.createQuery("select u from Usuario u where u.login = :login", Usuario.class)
                    .setParameter("login", login)
                    .getResultList();
        } catch (RuntimeException e) {
            throw new FalhaPersistenciaException("Não foi possível buscar o usuário.", e);
        } finally {
            em.close();
        }
        if (resultado.isEmpty()) {
            throw new UsuarioNaoEncontradoException(login);
        }
        return resultado.get(0);
    }

    @Override
    public List<Usuario> findByNome(String texto) {
        EntityManager em = abrir();
        try {
            return em.createQuery(
                    "select u from Usuario u where lower(u.nome) like :texto order by u.nome", Usuario.class)
                    .setParameter("texto", "%" + texto.toLowerCase() + "%")
                    .getResultList();
        } catch (RuntimeException e) {
            throw new FalhaPersistenciaException("Não foi possível buscar os usuários.", e);
        } finally {
            em.close();
        }
    }

    // confere se o login ja existe; idIgnorado serve para a edicao nao se contar a si mesma
    @Override
    public boolean loginEmUso(String login, Long idIgnorado) {
        EntityManager em = abrir();
        try {
            String jpql = "select count(u) from Usuario u where u.login = :login";
            if (idIgnorado != null) {
                jpql += " and u.id <> :id";
            }
            TypedQuery<Long> consulta = em.createQuery(jpql, Long.class)
                    .setParameter("login", login);
            if (idIgnorado != null) {
                consulta.setParameter("id", idIgnorado);
            }
            return consulta.getSingleResult() > 0;
        } catch (RuntimeException e) {
            throw new FalhaPersistenciaException("Não foi possível verificar o login.", e);
        } finally {
            em.close();
        }
    }
}