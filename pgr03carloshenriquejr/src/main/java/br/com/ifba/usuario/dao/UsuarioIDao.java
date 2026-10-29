package br.com.ifba.usuario.dao;

import br.com.ifba.infrastructure.dao.GenericIDao;
import br.com.ifba.usuario.entity.Usuario;
import java.util.List;

// contrato do DAO de usuario: as cinco operacoes do GenericIDao mais as buscas do dominio
public interface UsuarioIDao extends GenericIDao<Usuario> {

    // busca pelo login; lanca UsuarioNaoEncontradoException quando nao existe
    Usuario findByLogin(String login);

    // usuarios cujo nome contem o texto, sem diferenciar maiuscula de minuscula
    List<Usuario> findByNome(String texto);
}