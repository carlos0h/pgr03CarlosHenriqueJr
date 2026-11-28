package br.com.ifba.infrastructure.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

// superclasse de todas as entidades: guarda o id, que o banco gera sozinho
@MappedSuperclass
public abstract class PersistenceEntity {

    // o setter fica: o UsuarioController monta a copia da edicao com setId, e os testes tambem usam
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;
}