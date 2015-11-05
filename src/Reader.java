import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by boggs on 9/19/15.
 */
public class Reader {
    public String[] nodeTags = {"title", "author", "month", "body"};

    public ArrayList<File> getFiles() {
        ArrayList<File> files = new ArrayList<File>();
//        listFiles("xml", files);
        listFiles("news", files);
        return files;
    }

    public void listFiles(String directoryName, ArrayList<File> files) {
        File directory = new File(directoryName);

        // get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList) {
            if (file.isFile()) {
                files.add(file);
            } else if (file.isDirectory()) {
                listFiles(file.getAbsolutePath(), files);
            }
        }
    }

    public String getArticle(File file) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }

            br.close();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<String> getNodes(File file) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        Document document = null;
        NodeList nodeList = null;
        ArrayList<String> words = new ArrayList<String>();

        try {
            builder = factory.newDocumentBuilder();
            document = builder.parse(file);
            nodeList = document.getElementsByTagName("article");
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                for(int j = 0; j < nodeTags.length; j++) {
                    try {
                        words.addAll(Arrays.asList(filterWords(element.getElementsByTagName(nodeTags[j]).item(0).getTextContent().split(" "))));
                    } catch(NullPointerException e) {
                    }
                }
            }
        }
        return words;
    }

    public ArrayList<String> getWords(String line) {
        ArrayList<String> words = new ArrayList<>();
        words.addAll(Arrays.asList(line.split(" ")));
        return words;
    }

    public String[] filterWords(String[] strings) {
        ArrayList<String> stringList = new ArrayList<String>(Arrays.asList(strings));
        for(int i = 0; i < stringList.size(); i++) {
            boolean valid = true;
            String test = stringList.get(i);
            if(test.length() <= 0)
                valid = false;
            if(!test.matches("[a-zA-Z]+"))
                valid = false;
            if(!valid) {
                stringList.remove(i);
                i--;
            }
        }
        return stringList.toArray(new String[0]);
    }
}
