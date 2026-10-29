package br.com.ifba.infrastructure.dao;
import br.com.ifba.infrastructure.exception.FalhaPersistenciaException;
import br.com.ifba.infrastructure.entity.PersistenceEntity;
import java.util.List;

// contrato de acesso a dados: vale para qualquer entidade que herde de PersistenceEntity.
// toda operacao que falhar lanca FalhaPersistenciaException
public interface GenericIDao<Entity extends PersistenceEntity> {

    // grava uma entidade nova no banco
    void save(Entity entidade);

    // grava as alteracoes de uma entidade que ja existe e devolve a versao atualizada
    Entity update(Entity entidade);

    // remove a entidade do banco
    void delete(Entity entidade);

    // busca pelo id; lanca a excecao tambem quando nao encontra
    Entity findById(Long id);

    // devolve todas as entidades
    List<Entity> findAll();
}