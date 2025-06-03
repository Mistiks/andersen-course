package main.java.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileWriterClassLoader extends ClassLoader {

    private static final String LOADER_PATH = System.getProperty("user.dir") + File.separator + "external";
    public FileWriterClassLoader() {
        super();
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class<?> loadedClass = findLoadedClass(name);

        if (loadedClass == null) {
            try {
                loadedClass = super.loadClass(name, false);
            } catch (ClassNotFoundException ex) {
                loadedClass = load(name);
            }
        }

        if (resolve) {
            resolveClass(loadedClass);
        }

        return loadedClass;
    }

    public Class<?> load(String name) throws ClassNotFoundException {
        String filePath = LOADER_PATH + File.separator + name + ".class";
        byte[] binaryClassData;

        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            binaryClassData = fileInputStream.readAllBytes();
            return defineClass(name, binaryClassData, 0, binaryClassData.length);
        } catch (IOException ex) {
            throw new ClassNotFoundException("Can't load class " + name + " from the file. With error " + ex.getMessage());
        }
    }
}
