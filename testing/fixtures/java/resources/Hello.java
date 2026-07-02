import java.io.IOException;
import java.io.InputStream;

public class Hello {
    public static void main(String[] args) {
        String text = "Hello World.";
        try {
            InputStream stream = Hello.class.getResourceAsStream("/greeting.txt");
            if (stream != null) {
                text = new String(stream.readAllBytes());
            }
        } catch (IOException e) {
            // do nothing, default text is good enough
        }
        System.out.println(text);
    }
}