package Ficheros;
import Utils.Constantes;
import entities.huerto.Estacion;
import entities.huerto.Semilla;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class XML {


    //Si el fichero XML existe, lo devuelve para trabajar con el, y si no, lo crea
    public static Document crearXML(){
        File file;
        DocumentBuilderFactory dbFactory;
        DocumentBuilder dBuilder;
        Document document=null;
        Element root;
        try {
            file = new File(Constantes.RUTA_XML);
            dbFactory = DocumentBuilderFactory.newInstance();
            dBuilder = dbFactory.newDocumentBuilder();

            if(file.exists()){

                document = dBuilder.parse(file);
                return document;
            }
            document = dBuilder.newDocument();
            root = document.createElement("semillas");
            document.appendChild(root);
            save(document,Constantes.RUTA_XML);  // Guardar el nuevo archivo XML con la raíz


        } catch (ParserConfigurationException | IOException | SAXException e) {
            System.out.println("Error en el constructor de XML");
        }
        save(document,Constantes.RUTA_XML);
        return document;
    }

    //Devuelve todas las semillas del fichero en una lista
    public static ArrayList<Semilla> getSemillas(Document document){

        document.getDocumentElement().normalize();
        NodeList nodeList = document.getElementsByTagName("semilla");
        ArrayList<Semilla> semillas = new ArrayList<>();
        Element element;
        Semilla s;

        for (int i = 0; i < nodeList.getLength(); i++) {

            element = (Element) nodeList.item(i);
            s=createSemillaFromElement(element);
            semillas.add(s);

        }
        return semillas;
    }

    //Devuelve un mapa con clave Estacion, y un arrayList para cada Estacion
    public static HashMap<Estacion, ArrayList<Semilla>> getSemillasEstacion(Document document) {

        document.getDocumentElement().normalize();

        NodeList nodeList = document.getElementsByTagName("semilla");

        HashMap<Estacion, ArrayList<Semilla>> semillas = new HashMap<>();


        for (int i = 0; i < nodeList.getLength(); i++) {

            Element element = (Element) nodeList.item(i);

            Semilla s = createSemillaFromElement(element);

            ArrayList<Estacion> estacionesAct = s.getEstaciones();

            for (Estacion estacion : estacionesAct) {
                semillas.putIfAbsent(estacion, new ArrayList<>());
                semillas.get(estacion).add(s);

            }

        }

        return semillas;

    }

    private static Semilla createSemillaFromElement(Element element) {

        // Aquí debes implementar la lógica para extraer los datos de 'element'

        // y crear un objeto Semilla. Por ejemplo:

        Semilla s=new Semilla();

        s.setId( Integer.parseInt(element.getAttribute("id")));
        s.setNombre(element.getElementsByTagName("nombre").item(0).getTextContent());
        s.setEstacion(s.toEstacion(element.getElementsByTagName("estacion").item(0).getTextContent()));
        s.setDiasCrecimiento(Integer.parseInt(element.getElementsByTagName("diasCrecimiento").item(0).getTextContent()));
        s.setPrecioCompra(Integer.parseInt(element.getElementsByTagName("precioCompraSemilla").item(0).getTextContent()));
        s.setPrecioVenta(Integer.parseInt(element.getElementsByTagName("precioVentaFruto").item(0).getTextContent()));
        s.setMaxFrutos(Integer.parseInt(element.getElementsByTagName("maxFrutos").item(0).getTextContent()));

        return s;
    }

    //Devuelve un Mapa con clave id, y valor la semilla asociada a ese Id
    public static HashMap<Integer,Semilla> getSemillasId(Document document) {

        document.getDocumentElement().normalize();
        NodeList nodeList = document.getElementsByTagName("semilla");
        HashMap<Integer,Semilla> semillas = new HashMap<>();
        Element element;
        Semilla s;
        int id;

        for (int i = 0; i < nodeList.getLength(); i++) {
            element = (Element) nodeList.item(i);
            s = createSemillaFromElement(element);
            id=s.getId();
            semillas.put(id,s);

            }

        return semillas;
    }

    public static void save(Document document,String filePath) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);
        } catch (TransformerException e) {
            System.out.println("Error en el Transformer al guardar el XML");
        }
    }
}

