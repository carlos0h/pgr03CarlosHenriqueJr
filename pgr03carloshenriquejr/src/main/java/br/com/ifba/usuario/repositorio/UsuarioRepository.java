package br.com.ifba.usuario.repositorio;

import br.com.ifba.usuario.entity.Usuario;
import java.util.List;
import org.springframework.data.repository.Repository;


public interface UsuarioRepository extends Repository<Usuario, Long> {

    // id nulo insere; id existente atualiza
    <S extends Usuario> S save(S usuario);

    boolean existsById(Long id);

    void deleteById(Long id);

    List<Usuario> findAllByOrderByIdAsc();

    // consultas derivadas: o Spring monta o SQL a partir do NOME do metodo
    List<Usuario> findByNomeContainingIgnoreCaseOrderByNomeAsc(String texto);

    boolean existsByLogin(String login);

    boolean existsByLoginAndIdNot(String login, Long id);
}