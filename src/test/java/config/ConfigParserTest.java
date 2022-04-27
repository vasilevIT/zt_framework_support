package config;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ConfigParserTest {

    @Test
    public void parseTest() {
        ConfigParser parser = new ConfigParser();

        // Case
        String configContent = "";
        Map<String, String> expectedMap = new HashMap<String, String>();

        Map<String, String> result = parser.parse(configContent);
        assertEquals(expectedMap, result);

        // Case
        configContent = "<?php exit; ?>";
        expectedMap = new HashMap<String, String>();

        result = parser.parse(configContent);
        assertEquals(expectedMap, result);

        // Case
        configContent = "<?php exit; ?>\n" +
                "chmod 511";
        expectedMap = new HashMap<String, String>();
        expectedMap.put("chmod", "");

        result = parser.parse(configContent);
        assertEquals(expectedMap, result);

        // Case
        configContent = "<?php exit; ?>\n" +
                "chmod 511\n";
        expectedMap = new HashMap<String, String>();
        expectedMap.put("chmod", "");

        result = parser.parse(configContent);
        assertEquals(expectedMap, result);

        // Case
        configContent = "<?php exit; ?>\n" +
                "chmod 511\n" +
                "debug 1";
        expectedMap = new HashMap<String, String>();
        expectedMap.put("chmod", "");
        expectedMap.put("debug", "");

        result = parser.parse(configContent);
        assertEquals(expectedMap, result);

        // Case
        configContent = "<?php exit; ?>\n" +
                "chmod 511\n" +
                "debug 1\n" +
                "[shop]\n" +
                "   base_uri    'shop'\n";
        expectedMap = new HashMap<String, String>();
        expectedMap.put("chmod", "");
        expectedMap.put("debug", "");
        expectedMap.put("shop", "base_uri");
        expectedMap.put("shop.base_uri", "");

        result = parser.parse(configContent);
        assertEquals(expectedMap, result);

        // Case
        configContent = "<?php exit; ?>\n" +
                "chmod 511\n" +
                "debug 1\n" +
                "[shop]\n" +
                "   base_uri    'shop'\n" +
                "   brands->allow    [11800=>[11840=>166]]\n";
        expectedMap = new HashMap<String, String>();
        expectedMap.put("chmod", "");
        expectedMap.put("debug", "");
        expectedMap.put("shop", "base_uri");
        expectedMap.put("shop.base_uri", "");
        expectedMap.put("shop.brands", "allow");
        expectedMap.put("shop.brands.allow", "");

        result = parser.parse(configContent);
        assertEquals(expectedMap, result);

        // Case
        configContent = "<?php exit; ?>\n" +
                "chmod 511\n" +
                "debug 1\n" +
                "[shop]\n" +
                "   base_uri    'shop'\n" +
                "   brands->allow    [11800=>[11840=>166]]\n" +
                "brands [11800=>[11840=>166]]\n";
        expectedMap = new HashMap<String, String>();
        expectedMap.put("chmod", "");
        expectedMap.put("debug", "");
        expectedMap.put("shop", "base_uri");
        expectedMap.put("shop.base_uri", "");
        expectedMap.put("shop.brands", "allow");
        expectedMap.put("shop.brands.allow", "");
        expectedMap.put("brands", "");

        result = parser.parse(configContent);
        assertEquals(expectedMap, result);
    }
}
