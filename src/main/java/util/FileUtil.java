package util;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.stream.Collectors;

public class FileUtil {

    public static String getFileNameByClassName(String className) {
        return className + ".inc.php";
    }

    public static String getFileNameByPath(String path) {
        return path + ".inc.php";
    }

    public static String getLibraryNameByPath(String path) {
        ClassNameGenerator generator = ClassNameGeneratorFactory.create("library");
        return generator.generate(path);
    }

    public static String getModuleNameByPath(String path) {
        ClassNameGenerator generator = ClassNameGeneratorFactory.create("module");
        return generator.generate(path);
    }

    /**
     * Return a clear path array to class. Clear empty strings and .inc.php extension of last part.
     *
     * @param parts
     * @return
     */
    @NotNull
    static String[] getClearPathParths(String parts) {
        String[] paths = parts.split("\\/");
        paths = Arrays.stream(paths).filter(x -> !x.isEmpty()).toArray(String[]::new);
        if (paths[paths.length - 1].contains(".inc.php")) {
            paths[paths.length - 1] = paths[paths.length - 1].replace(".inc.php", "");
        }
        return paths;
    }

    /**
     * Is current dir located inside the _lib directory.
     *
     * @param path
     * @return
     */
    public static boolean isInLibDirectory(String path) {
        String[] paths = path.split("\\/");
        return Arrays.stream(paths).anyMatch(x -> x.equals("_lib"));
    }
}
