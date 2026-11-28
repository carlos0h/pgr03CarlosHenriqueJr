package br.com.ifba;

import br.com.ifba.login.view.Login;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import lombok.extern.slf4j.Slf4j;

// ponto de entrada: o Spring varre o pacote br.com.ifba e monta as camadas sozinho
@SpringBootApplication
@Slf4j
public class ProjetoApplication {

    public static void main(String[] args) {
        usarNimbus();
        try {
            ConfigurableApplicationContext context = new SpringApplicationBuilder(ProjetoApplication.class)
                    .headless(false) // obrigatorio em desktop: avisa que havera interface grafica
                    .run(args);

            Login tela = context.getBean(Login.class); // a tela vem pronta do conteiner
            tela.setVisible(true);
            log.info("Aplicação iniciada: tela de login aberta");
        } catch (RuntimeException e) {
            log.error("Erro ao iniciar a aplicação", e);
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Não foi possível iniciar a aplicação. Verifique a conexão com o banco e o application.properties.",
                    "Erro", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
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
            log.error("Não foi possível aplicar o Nimbus", ex);
        }
    }
}