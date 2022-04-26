package util;

public class LibraryNameGenerator implements ClassNameGenerator {
    @Override
    public String generate(String path) {
        String[] paths = FileUtil.getClearPathParths(path);

        if (paths.length == 0) {
            System.err.println("Class path is empty!");
            return "";
        }

        if (paths[0].equals("_engine")) {
            // engine directory
            if (!FileUtil.isInLibDirectory(path)) {
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
}
