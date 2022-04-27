package config;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class ConfigParser {

    public Map<String, String> parse(String configContent) {
        HashMap<String, String> result = new HashMap<>();

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

                    innerTagStack.add(lineTrimmed);
                    continue;
                }


                int firstSpaceIndex = lineTrimmed.indexOf("\t");
                if (firstSpaceIndex == -1) {
                    firstSpaceIndex = lineTrimmed.indexOf(" ");
                    if (firstSpaceIndex == -1) {
                        // wrong lines
                        continue;
                    }
                }
                int firstSpaceIndex2 = line.indexOf("\t");
                if (firstSpaceIndex2 != 0) {
                    firstSpaceIndex2 = line.indexOf(" ");
                }

                String key = lineTrimmed.substring(0, firstSpaceIndex).trim();

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

                        innerTagStack.add(currentInnerKey);

                        result.put(String.join(".", innerTagStack), nextInnerKey);
                    }

                    key = String.join(".", innerTagStack) + "." + keyInnerItems[keyInnerItems.length - 1];
                    result.put(key, "");

                    continue;
                }

                if (!innerTagStack.empty()) {
                    result.put(String.join(".", innerTagStack), key);
                    key = String.join(".", innerTagStack) + "." + key;
                }

                result.put(key, "");
            }
        }

        return result;
    }
}
