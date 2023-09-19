// 예제 14-6 JDOM으로 XML 읽기: 자바 버전

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ReadXmlFile {
    public static void main(String[] args) {
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File("path_to_file"); // 파일 경로로 바꿔야 함
        try {
            Document document = (Document) builder.build(xmlFile);
            Element rootNode = document.getRootElement();
            List list = rootNode.getChildren("staff");
            for (int i = 0; i < list.size(); i++) {
                Element node = (Element) list.get(i);
                System.out.println("First Name : " + node.getChildText("firstname"));
                System.out.println("\tLast Name : " + node.getChildText("lastname"));
                System.out.println("\tNick Name : " + node.getChildText("email"));
                System.out.println("\tSalary : " + node.getChildText("salary"));
            }
        } catch (IOException io) {
            System.out.println(io.getMessage());
        } catch (JDOMException jdomex) {
            System.out.println(jdomex.getMessage());
        }
    }
}