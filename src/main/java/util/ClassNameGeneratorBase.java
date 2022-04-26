package util;

abstract public class ClassNameGeneratorBase implements ClassNameGenerator {

    protected boolean isPathValidForClass(String[] parts) {
        if (parts.length == 0) {
            System.err.println("Class path is empty!");
            return false;
        }

        // One these directories can include class files.
        if (parts[0].equals("_engine") || parts[0].equals("_modules")) {
            return true;
        }

        return false;
    }
}
