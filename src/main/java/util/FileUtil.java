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
        String[] paths = getClearPathParths(path);

        if (paths.length == 0) {
            System.err.println("Class path is empty!");
            return "";
        }

        if (paths[0].equals("_engine")) {
            // engine directory
            if (!isInLibDirectory(path)) {
                return "";
            }

            String className = "U_";
            // TODO Delete these code duplicate.
            for (int i = 2; i < paths.length; i++) {
                className += paths[i];
                if (i < paths.length - 1) {
                    className += "_";
                }
            }
            return className;
        }
        if (paths[0].equals("_modules")) {
            // _modules directory
            String className = "U__";
            // TODO Delete these code duplicate.
            for (int i = 2; i < paths.length; i++) {
                if (paths[i].equals("_lib")) {
                    if (i < paths.length - 2) {
                    } else {
                        continue;
                    }
                } else {
                    className += paths[i];
                }
                if (i < paths.length - 1) {
                    className += "_";
                }
            }

            return className;
        }
        return "";
    }

    public static String getModuleNameByPath(String path) {
        ClassNameGenerator generator = ClassNameGeneratorFactory.create("module");
        String[] paths = getClearPathParths(path);

        if (paths.length == 0) {
            System.err.println("Class path is empty!");
            return "";
        }

        if (paths[0].equals("_engine") || isInLibDirectory(path)) {
            return "";
        }

        if (paths[0].equals("_modules")) {
            // _modules directory
            String className = "U_mod_";
            for (int i = 2; i < paths.length; i++) {
                className += paths[i];
                if (i < paths.length - 1) {
                    className += "_";
                }
            }

            return className;
        }
        return "";
    }

    /**
     * Return a clear path array to class. Clear empty strings and .inc.php extension of last part.
     *
     * @param parts
     * @return
     */
    @NotNull
    private static String[] getClearPathParths(String parts) {
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
