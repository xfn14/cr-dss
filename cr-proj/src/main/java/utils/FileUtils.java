package utils;

import java.io.*;

public class FileUtils {
    public static void objectToFile(Object o, String path) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(path);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(o);
        objectOutputStream.close();
        fileOutputStream.close();
    }

    public static Object fileToObject(String path) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(path);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        try {
            return objectInputStream.readObject();
        } finally {
            objectInputStream.close();
            fileInputStream.close();
        }
    }
}
