package G13.pst.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class File {

    public static String[] getLines(String fileName) throws IOException {
        ArrayList<String> linesList = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(linesList::add);
        }
        String[] t = new String[]{};
        return linesList.toArray(t);
    }
}
