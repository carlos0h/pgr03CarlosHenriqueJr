package br.com.ifba.infrastructure.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

// unica responsabilidade: ler os dados de conexao do persistence.xml
public class ConfiguracaoBanco {

    private final Map<String, String> propriedades;

    public ConfiguracaoBanco() {
        this.propriedades = lerPersistenceXml();
    }

    public String url() {
        return propriedades.get("jakarta.persistence.jdbc.url");
    }

    public String usuario() {
        return propriedades.get("jakarta.persistence.jdbc.user");
    }

    public String senha() {
        return propriedades.get("jakarta.persistence.jdbc.password");
    }

    private static Map<String, String> lerPersistenceXml() {
        try (InputStream in = ConfiguracaoBanco.class.getResourceAsStream("/META-INF/persistence.xml")) {
            if (in == null) {
                throw new IllegalStateException("persistence.xml não encontrado em META-INF.");
            }
            NodeList nos = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder().parse(in).getElementsByTagName("property");
            Map<String, String> lidas = new HashMap<>();
            for (int i = 0; i < nos.getLength(); i++) {
                Element e = (Element) nos.item(i);
                lidas.put(e.getAttribute("name"), e.getAttribute("value"));
            }
            return lidas;
        } catch (IOException | ParserConfigurationException | SAXException e) {
            throw new IllegalStateException("Não foi possível ler o persistence.xml: " + e.getMessage(), e);
        }
    }
}