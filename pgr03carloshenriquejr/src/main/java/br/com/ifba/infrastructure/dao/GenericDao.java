package br.com.ifba.infrastructure.dao;

import br.com.ifba.infrastructure.entity.PersistenceEntity;
import br.com.ifba.infrastructure.exception.FalhaPersistenciaException;
import br.com.ifba.infrastructure.util.JPAUtil;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.function.Supplier;

// DAO que serve para qualquer entidade: o tipo chega pelo construtor
public class GenericDao<Entity extends PersistenceEntity> implements GenericIDao<Entity> {

    private final Class<Entity> tipo;
    private final Supplier<EntityManager> fabrica;

    // uso normal: o EntityManager vem do JPAUtil
    public GenericDao(Class<Entity> tipo) {
        this(tipo, JPAUtil::getEntityManager);
    }

    // permite trocar a origem do EntityManager (os testes usam isso)
    public GenericDao(Class<Entity> tipo, Supplier<EntityManager> fabrica) {
        this.tipo = tipo;
        this.fabrica = fabrica;
    }

    // se nao der pra abrir (banco fora do ar, por exemplo), lanca a excecao propria
    public EntityManager abrir() {
        try {
            return fabrica.get();
        } catch (RuntimeException e) {
            throw new FalhaPersistenciaException("Não foi possível conectar ao banco de dados.", e);
        }
    }

    @Override
    public void save(Entity entidade) {
        EntityManager em = abrir();
        try {
            em.getTransaction().begin();
            em.persist(entidade);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new FalhaPersistenciaException("Não foi possível salvar o registro.", e);
        } finally {
            em.close();
        }
    }

    @Override
    public Entity update(Entity entidade) {
        EntityManager em = abrir();
        try {
            em.getTransaction().begin();
            if (em.find(tipo, entidade.getId()) == null) {
                throw new FalhaPersistenciaException("O registro não existe mais.");
            }
            Entity atualizada = em.merge(entidade);
            em.getTransaction().commit();
            return atualizada;
        } catch (FalhaPersistenciaException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new FalhaPersistenciaException("Não foi possível atualizar o registro.", e);
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(Entity entidade) {
        EntityManager em = abrir();
        try {
            em.getTransaction().begin();
            Entity existente = em.find(tipo, entidade.getId());
            if (existente == null) {
                throw new FalhaPersistenciaException("O registro não existe mais.");
            }
            em.remove(existente);
            em.getTransaction().commit();
        } catch (FalhaPersistenciaException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new FalhaPersistenciaException("Não foi possível remover o registro.", e);
        } finally {
            em.close();
        }
    }

    @Override
    public Entity findById(Long id) {
        EntityManager em = abrir();
        Entity encontrada;
        try {
            encontrada = em.find(tipo, id);
        } catch (RuntimeException e) {
            throw new FalhaPersistenciaException("Não foi possível buscar o registro.", e);
        } finally {
            em.close();
        }
        if (encontrada == null) {
            throw new FalhaPersistenciaException("Registro com id " + id + " não encontrado.");
        }
        return encontrada;
    }

    @Override
    public List<Entity> findAll() {
        EntityManager em = abrir();
        try {
            return em.createQuery("select e from " + tipo.getSimpleName() + " e order by e.id", tipo)
                    .getResultList();
        } catch (RuntimeException e) {
            throw new FalhaPersistenciaException("Não foi possível listar os registros.", e);
        } finally {
            em.close();
        }
    }
}