package br.com.ifba.infrastructure.util;

import br.com.ifba.login.view.Login;
import br.com.ifba.usuario.controller.UsuarioController;
import br.com.ifba.usuario.controller.UsuarioIController;
import br.com.ifba.usuario.dao.UsuarioDao;
import br.com.ifba.usuario.service.UsuarioService;

// ponto de entrada: o unico lugar que monta as camadas (os new de dao, service e controller)
public class Aplicacao {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Aplicacao.class.getName());

    public static void main(String[] args) {
        usarNimbus();

        // sobe o banco e aplica as migrations antes de abrir qualquer tela
        try {
            JPAUtil.iniciar();
        } catch (RuntimeException e) {
            logger.log(java.util.logging.Level.SEVERE, "Erro ao iniciar o banco", e);
            javax.swing.JOptionPane.showMessageDialog(null, "Não foi possível iniciar o banco de dados. Verifique a conexão e as migrations.", "Erro", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        // injecao de dependencias: cada camada recebe a de baixo ja pronta
        UsuarioIController controller = new UsuarioController(new UsuarioService(new UsuarioDao()));

        java.awt.EventQueue.invokeLater(() -> new Login(controller).setVisible(true));
    }

    private static void usarNimbus() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
}