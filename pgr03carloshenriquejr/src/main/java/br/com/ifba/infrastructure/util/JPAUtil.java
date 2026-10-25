package br.com.ifba.infrastructure.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.flywaydb.core.Flyway;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

// centraliza a fabrica: so existe uma para o programa inteiro
public class JPAUtil {

    private static EntityManagerFactory factory;

    private JPAUtil() {
    }

    // aplica as migrations e cria a fabrica; so faz isso uma vez
    public static synchronized void iniciar() {
        if (factory == null) {
            migrar();
            factory = Persistence.createEntityManagerFactory("matchrhPU");
        }
    }

    public static EntityManager getEntityManager() {
        iniciar();
        return factory.createEntityManager();
    }

    // roda as migrations pendentes antes do Hibernate conferir o schema
    private static void migrar() {
        Map<String, String> p = lerPropriedades();
        Flyway.configure()
                .dataSource(
                        p.get("jakarta.persistence.jdbc.url"),
                        p.get("jakarta.persistence.jdbc.user"),
                        p.get("jakarta.persistence.jdbc.password"))
                .load()
                .migrate();
    }

    // le as propriedades direto do persistence.xml, o mesmo arquivo que o Hibernate usa
    private static Map<String, String> lerPropriedades() {
        try (InputStream in = JPAUtil.class.getResourceAsStream("/META-INF/persistence.xml")) {
            if (in == null) {
                throw new IllegalStateException("persistence.xml não encontrado em META-INF.");
            }
            NodeList nos = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder().parse(in).getElementsByTagName("property");
            Map<String, String> propriedades = new HashMap<>();
            for (int i = 0; i < nos.getLength(); i++) {
                Element e = (Element) nos.item(i);
                propriedades.put(e.getAttribute("name"), e.getAttribute("value"));
            }
            return propriedades;
        } catch (IOException | ParserConfigurationException | SAXException e) {
            throw new IllegalStateException("Não foi possível ler o persistence.xml: " + e.getMessage(), e);
        }
    }
}