package util;

/**
 * Class respond for creatin class name generator.
 */
public class ClassNameGeneratorFactory {
    public static ClassNameGenerator create(String type) {
        if (type.equals("module")) {
            return new ModuleNameGenerator();
        }
        return new LibraryNameGenerator();
    }
}
