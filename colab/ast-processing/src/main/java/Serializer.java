import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import org.eclipse.jdt.core.dom.CompilationUnit;

public static class Serializer {

    public static void Serialize(CompilationUnit unit) {

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream("models/java_for_lstm/normalized.txt"))) {

            oos.writeObject(unit);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
