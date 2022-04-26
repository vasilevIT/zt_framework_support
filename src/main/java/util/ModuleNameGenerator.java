package util;

public class ModuleNameGenerator extends ClassNameGeneratorBase {
    @Override
    public String generate(String path) {
        String[] paths = FileUtil.getClearPathParths(path);

        if (!isPathValidForClass(paths)) {
            return "";
        }

        if (paths[0].equals("_engine") || FileUtil.isInLibDirectory(path)) {
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
}
