package br.com.ifba.usuario.controller;

import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.service.UsuarioIService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

// recebe os pedidos da tela, monta o objeto e repassa ao service
@Controller
@RequiredArgsConstructor
public class UsuarioController implements UsuarioIController {

    private final UsuarioIService service;

    @Override
    public void cadastrar(String nome, String cpf, String email, String login, String senha) {
        Usuario usuario = new Usuario(nome, cpf, login, senha);
        usuario.setEmail(email);
        service.cadastrar(usuario);
    }

    @Override
    public void atualizar(Usuario original, String nome, String email, String login) {
        // monta uma copia com os dados novos: o objeto que a tela mostra so muda se der certo
        Usuario atualizado = new Usuario(nome, original.getCpf(), login, original.getSenha());
        atualizado.setId(original.getId());
        atualizado.setEmail(email);
        service.atualizar(atualizado);
    }

    @Override
    public void excluir(Usuario usuario) {
        service.excluir(usuario);
    }

    @Override
    public List<Usuario> listar() {
        return service.listar();
    }

    @Override
    public List<Usuario> buscarPorNome(String texto) {
        return service.buscarPorNome(texto);
    }
}