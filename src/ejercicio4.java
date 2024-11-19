
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

// este codigo esta copiado y pegado del ejercicio 3 para ahorrar tiempo, pero con pequeñas modificaciones para ajustarse a lo
// que pide el ejercicio 4, que es guardar los cambios hechos en el xml, pero en un nuevo archivo, en vez de en el archivo original.
public class ejercicio4 {
    public static class Main {
        public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
            try {
                // Ruta al archivo XML original
                String filePath = "C:\\Users\\Carlos\\Desktop\\ExamenDOM\\src\\arbol.xml";
                File inputFile = new File(filePath);

                // Inicializa el parser de XML
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(inputFile);
                doc.getDocumentElement().normalize();

                // Datos de ejemplo para agregar un nuevo hijo
                String nombreABuscar = "Sofía";
                String nombreHijo = "Eva";
                String apellido1 = "Gómez";
                String apellido2 = "López";
                String nacimiento = "2070";

                // Llamada a la función para agregar el nuevo hijo
                agregarHijoYGuardarEnNuevoArchivo(doc, nombreABuscar, nombreHijo, apellido1, apellido2, nacimiento);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static void agregarHijoYGuardarEnNuevoArchivo(Document doc, String nombre, String nombreHijo, String apellido1, String apellido2, String nacimiento) {
            // Encuentra todos los nodos <hijo>
            NodeList listaHijos = doc.getElementsByTagName("hijo");
            boolean encontrado = false;

            for (int i = 0; i < listaHijos.getLength(); i++) {
                Node hijo = listaHijos.item(i);

                if (hijo.getNodeType() == Node.ELEMENT_NODE) {
                    Element elementoHijo = (Element) hijo;

                    // Verifica si el nodo tiene el nombre buscado
                    if (elementoHijo.getAttribute("nombre").equalsIgnoreCase(nombre)) {
                        encontrado = true;

                        // Obtiene el nodo <hijos>, o lo crea si no existe
                        Node hijosNode = null;
                        NodeList hijosLista = elementoHijo.getElementsByTagName("hijos");
                        if (hijosLista.getLength() > 0) {
                            hijosNode = hijosLista.item(0);
                        } else {
                            hijosNode = doc.createElement("hijos");
                            elementoHijo.appendChild(hijosNode);
                        }

                        // Crea un nuevo nodo <hijo>
                        Element nuevoHijo = doc.createElement("hijo");
                        nuevoHijo.setAttribute("nombre", nombreHijo);
                        nuevoHijo.setAttribute("apellido1", apellido1);
                        nuevoHijo.setAttribute("apellido2", apellido2);
                        nuevoHijo.setAttribute("nacimiento", nacimiento);

                        // Añade el nuevo hijo al nodo <hijos>
                        hijosNode.appendChild(nuevoHijo);
                        System.out.println("Se ha agregado un nuevo hijo a " + nombre);
                        break;
                    }
                }
            }

            if (!encontrado) {
                System.out.println("No se encontró a la persona con nombre: " + nombre);
            } else {
                // Guardamos los cambios en un nuevo archivo (cambiamos el codigo del ejercicio3)
                guardarXMLEnNuevoArchivo(doc, "C:\\Users\\Carlos\\Desktop\\ExamenDOM\\src\\nuevo_arbol.xml");
            }
        }

        // ================================== EJERCICIO 4 =======================================
        // Metodo para guardar el archivo XML modificado en un nuevo archivo
        public static void guardarXMLEnNuevoArchivo(Document doc, String nuevoPath) {
            try {
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File(nuevoPath));
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.transform(source, result);
                System.out.println("Archivo XML guardado en: " + nuevoPath);
            } catch (TransformerException e) {
                e.printStackTrace();
            }
        }
    }
}


