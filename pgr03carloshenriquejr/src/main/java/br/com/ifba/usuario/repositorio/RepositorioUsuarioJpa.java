package br.com.ifba.usuario.repositorio;

import br.com.ifba.usuario.exception.UsuarioNaoEncontradoException;
import br.com.ifba.infrastructure.util.JPAUtil;
import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.exception.LoginDuplicadoException;
import jakarta.persistence.EntityManager;
import java.util.List;

// repositorio que grava e le usuarios no PostgreSQL via JPA
public class RepositorioUsuarioJpa {

    public void cadastrar(Usuario usuario) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // confere se o login ja existe antes de gravar
            Long quantidade = em.createQuery(
                    "select count(u) from Usuario u where u.login = :login", Long.class)
                    .setParameter("login", usuario.getLogin())
                    .getSingleResult();
            if (quantidade > 0) {
                throw new LoginDuplicadoException(usuario.getLogin());
            }

            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            // se a transacao chegou a abrir, desfaz
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public List<Usuario> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("select u from Usuario u order by u.nome", Usuario.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }


    public void atualizar(Usuario usuario) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            if (em.find(Usuario.class, usuario.getId()) == null) {
                throw new UsuarioNaoEncontradoException(usuario.getId());
            }
            // o login nao pode ser de OUTRO usuario
            Long quantidade = em.createQuery(
                    "select count(u) from Usuario u where u.login = :login and u.id <> :id", Long.class)
                    .setParameter("login", usuario.getLogin())
                    .setParameter("id", usuario.getId())
                    .getSingleResult();
            if (quantidade > 0) {
                throw new LoginDuplicadoException(usuario.getLogin());
            }

            em.getTransaction().begin();
            em.merge(usuario);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public void remover(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Usuario usuario = em.find(Usuario.class, id);
            if (usuario == null) {
                throw new UsuarioNaoEncontradoException(id);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public List<Usuario> buscarPorNome(String texto) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                    "select u from Usuario u where lower(u.nome) like :texto order by u.nome", Usuario.class)
                    .setParameter("texto", "%" + texto.toLowerCase() + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }
}