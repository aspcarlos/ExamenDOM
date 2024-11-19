import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;

// ======================================== EJERCICIO 1 =====================================================
public class ejercicio1 {
    public static void main(String[] args) {
        try {
            // Crea un objeto File para referenciar el archivo XML
            File inputFile = new File("C:\\Users\\Carlos\\Desktop\\ExamenDOM\\src\\arbol.xml");

            // Inicializa el parser de XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(inputFile);
            doc.getDocumentElement().normalize();

            // Imprime el elemento raíz
            System.out.println("Elemento raíz: " + doc.getDocumentElement().getNodeName());

            // Obtén todos los nodos hijos del elemento raíz
            NodeList listaNodos = doc.getDocumentElement().getChildNodes();

            // Recorre todos los nodos del árbol
            recorrerNodos(listaNodos, 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // recorrer todos los nodos
    private static void recorrerNodos(NodeList listaNodos, int nivel) {
        for (int i = 0; i < listaNodos.getLength(); i++) {
            Node nodo = listaNodos.item(i);

            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                Element elemento = (Element) nodo;

                // Imprime el nombre del nodo
                imprimirNivel(nivel);
                System.out.println("Nodo: " + nodo.getNodeName());

                // Imprime los atributos del nodo, si tiene
                if (elemento.hasAttributes()) {
                    NamedNodeMap atributos = elemento.getAttributes();
                    for (int j = 0; j < atributos.getLength(); j++) {
                        Node atributo = atributos.item(j);
                        imprimirNivel(nivel + 1);
                        System.out.println("Atributo: " + atributo.getNodeName() + " = " + atributo.getNodeValue());
                    }
                }

                // Llama a los hijos del nodo
                if (nodo.hasChildNodes()) {
                    recorrerNodos(nodo.getChildNodes(), nivel + 1);
                }
            }
        }
    }

    // imprimir la indentación según el nivel
    private static void imprimirNivel(int nivel) {
        for (int j = 0; j < nivel; j++) {
            System.out.print("\t");
        }
    }
}
