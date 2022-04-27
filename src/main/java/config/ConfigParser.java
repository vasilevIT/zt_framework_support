package config;

import java.util.*;

public class ConfigParser {

    public Map<String, List<String>> parse(String configContent) {
        HashMap<String, List<String>> result = new HashMap<String, List<String>>();

        String[] configLines = configContent.split("\n");
        Stack<String> innerTagStack = new Stack<String>();
        if (configLines.length > 1) {
            for (int i = 1; i < configLines.length; i++) {
                String line = configLines[i];
                String lineTrimmed = line.trim();
                if (lineTrimmed.matches("\\[.+\\]")) {
                    lineTrimmed = lineTrimmed.replace("[", "");
                    lineTrimmed = lineTrimmed.replace("]", "");
                    lineTrimmed = lineTrimmed.trim();

                    innerTagStack = new Stack<String>();
                    innerTagStack.add(lineTrimmed);
                    continue;
                }

                if (isCommentedLine(lineTrimmed)) {
                    // its a comment
                    continue;
                }

                if (isLocalRemoteValueDeclaration(lineTrimmed)) {
                    // its a local/remote value declaration
                    continue;
                }

                String nextLine = null;
                if ((i + 1) < configLines.length) {
                    nextLine = configLines[i + 1].trim();
                }
                boolean isKeyWithLocalRemoteValueDeclaration = isKeyWithLocalRemoteValueDeclaration(lineTrimmed, nextLine);
                int firstSpaceIndex = findFirstSpaceIndex(lineTrimmed);
                if (firstSpaceIndex == -1 && !isKeyWithLocalRemoteValueDeclaration) {
                    // wrong lines
                    continue;
                }

                int firstSpaceIndex2 = findFirstSpaceIndex(line);

                String key;
                if (isKeyWithLocalRemoteValueDeclaration) {
                    key = lineTrimmed;
                } else {
                    key = lineTrimmed.substring(0, firstSpaceIndex).trim();
                }

                if (firstSpaceIndex2 != 0 && !innerTagStack.empty()) {
                    // Set stack empty. Out gone from inner block
                    innerTagStack = new Stack<>();
                }

                if (key.contains("->")) {
                    // Short hierarchy notation. Item has inner elements.
                    String[] keyInnerItems = key.split("->");

                    for (int j = 0; j < keyInnerItems.length - 1; j++) {
                        String currentInnerKey = keyInnerItems[j];
                        String nextInnerKey = keyInnerItems[j + 1];

                        // Add to parent item
                        if (!innerTagStack.empty()) {
                            String join = String.join(".", innerTagStack);
                            List<String> list;

                            if (result.get(join) == null) {
                                list = new ArrayList<>();
                            } else {
                                list = result.get(join);
                            }
                            list.add(currentInnerKey);
                            result.put(join, list);
                        }

                        innerTagStack.add(currentInnerKey);

                        String join = String.join(".", innerTagStack);
                        List<String> list;

                        if (result.get(join) == null) {
                            list = new ArrayList<>();
                        } else {
                            list = result.get(join);
                        }
                        list.add(nextInnerKey);
                        result.put(join, list);
                    }

                    key = String.join(".", innerTagStack) + "." + keyInnerItems[keyInnerItems.length - 1];
                    result.put(key, new ArrayList<>());

                    continue;
                }

                if (!innerTagStack.empty()) {

                    String join = String.join(".", innerTagStack);
                    List<String> list;
                    if (result.get(join) == null) {
                        list = new ArrayList<>();
                    } else {
                        list = result.get(join);
                    }
                    list.add(key);
                    result.put(join, list);
                    key = String.join(".", innerTagStack) + "." + key;
                }

                result.put(key, new ArrayList<>());
            }
        }

        return result;
    }

    private boolean isKeyWithLocalRemoteValueDeclaration(String line, String lineNext) {
        if (findFirstSpaceIndex(line) != -1) {
            // There are no spaces here
            return  false;
        }

        if (lineNext != null) {
            return lineNext.indexOf("l ") == 0 || lineNext.indexOf("r ") == 0 || lineNext.indexOf("l\t") == 0 || lineNext.indexOf("r\t") == 0;
        }
        return false;
    }

    private int findFirstSpaceIndex(String line) {
        int firstSpaceIndex2 = line.indexOf("\t");
        if (firstSpaceIndex2 != 0) {
            firstSpaceIndex2 = line.indexOf(" ");
        }
        return firstSpaceIndex2;
    }

    private boolean isLocalRemoteValueDeclaration(String lineTrimmed) {
        return lineTrimmed.indexOf("l ") == 0 || lineTrimmed.indexOf("r ") == 0;
    }

    private boolean isCommentedLine(String lineTrimmed) {
        return lineTrimmed.indexOf("#") == 0;
    }
}
