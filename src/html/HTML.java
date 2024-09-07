package html;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.BadLocationException;
import java.io.*;
import java.util.Scanner;

public class HTML {

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);

        System.out.println("Ingrese el nombre del archivo HTML: ");
        String html = scn.nextLine();

        System.out.println("Ingrese la palabra a buscar: ");
        String keyword = scn.nextLine();

        try {
            FileReader reader = new FileReader(html);
            BufferedReader lector = new BufferedReader(reader);

            HTMLEditorKit.Parser parser = new ParserDelegator();
            HTMLDocument documento = new HTMLDocument();
            parser.parse(lector, documento.getReader(0), true);

            String content = documento.getText(0, documento.getLength());
            Buspalabra(content, keyword, html);
            
            lector.close();
        } catch (FileNotFoundException e) {
            System.out.println("El archivo HTML no fue encontrado.");
        } catch (IOException | BadLocationException e) {
            e.printStackTrace();
        }
    }

    public static void Buspalabra(String content, String keyword, String htmlFileName) {
        int index = content.indexOf(keyword);
        int count = 0;

        String logFileName = "file-" + keyword + ".log";
        try (BufferedWriter registro = new BufferedWriter(new FileWriter(logFileName))) {
            registro.write("Nombre del archivo HTML: " + htmlFileName + "\n");
            registro.write("Palabra clave: " + keyword + "\n");
            registro.write("Posiciones encontradas:\n");

            while (index >= 0) {
                count++;
                System.out.println("Ocurrencia " + count + " en la posición (caracteres): " + index);
                registro.write("Ocurrencia " + count + ": " + index + "\n");

                index = content.indexOf(keyword, index + 1);
            }

            if (count == 0) {
                System.out.println("La palabra clave no se encontro en el documento.");
                registro.write("La palabra clave no se encontro en el documento.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
