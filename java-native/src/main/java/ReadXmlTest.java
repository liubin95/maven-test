import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;

/**
 * ReadXmlTest.
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2022-04-01 : base version.
 */
public class ReadXmlTest {

  public static void main(String[] args) throws DocumentException {
    final File file = new File("D:\\tmp\\settings.xml");
    SAXReader reader = new SAXReader();
    Document document = reader.read(file);
    final Element rootElement = document.getRootElement();
    rootElement
        .elementIterator()
        .forEachRemaining(
            it -> {
              it.attributes()
                  .forEach(itAtt -> System.out.println(itAtt.getName() + "\t" + itAtt.getValue()));
              it.elementIterator()
                  .forEachRemaining(
                      itSon ->
                          System.out.println(
                              "\t" + itSon.getName() + "\t" + itSon.getStringValue()));
            });
  }
}
