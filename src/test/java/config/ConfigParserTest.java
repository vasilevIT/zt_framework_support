package config;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ConfigParserTest {

    @Test
    public void parseTest() {
        ConfigParser parser = new ConfigParser();

        // Case
        String configContent = "";
        Map<String, List<String>> expectedMap = new HashMap<String, List<String>>();

        Map<String, List<String>> result = parser.parse(configContent);
        assertEquals(expectedMap, result);

        // Case
        configContent = "<?php exit; ?>";
        expectedMap = new HashMap<String, List<String>>();

        result = parser.parse(configContent);
        assertEquals(expectedMap, result);



        // Case
        configContent = "<?php exit; ?>\n" +
                "chmod 511";
        expectedMap = new HashMap<String, List<String>>();
        expectedMap.put("chmod", new ArrayList<String>());

        result = parser.parse(configContent);
        assertEquals(expectedMap, result);

        // Case
        configContent = "<?php exit; ?>\n" +
                "chmod 511\n";
        expectedMap = new HashMap<String, List<String>>();
        expectedMap.put("chmod", new ArrayList<String>());

        result = parser.parse(configContent);
        assertEquals(expectedMap, result);

        // Case
        configContent = "<?php exit; ?>\n" +
                "chmod 511\n" +
                "debug 1";
        expectedMap = new HashMap<String, List<String>>();
        expectedMap.put("chmod", new ArrayList<String>());
        expectedMap.put("debug", new ArrayList<String>());

        result = parser.parse(configContent);
        assertEquals(expectedMap, result);

        // Case
        configContent = "<?php exit; ?>\n" +
                "chmod 511\n" +
                "debug 1\n" +
                "[shop]\n" +
                "   base_uri    'shop'\n";
        expectedMap = new HashMap<String, List<String>>();
        expectedMap.put("chmod", new ArrayList<String>());
        expectedMap.put("debug", new ArrayList<String>());

        List<String> listShopValue = new ArrayList<String>();
        listShopValue.add("base_uri");
        expectedMap.put("shop", listShopValue);
        expectedMap.put("shop.base_uri", new ArrayList<String>());

        result = parser.parse(configContent);
        assertEquals(expectedMap, result);

        // Case
        configContent = "<?php exit; ?>\n" +
                "chmod 511\n" +
                "debug 1\n" +
                "[shop]\n" +
                "   base_uri    'shop'\n" +
                "   brands->allow    [11800=>[11840=>166]]\n";
        expectedMap = new HashMap<String, List<String>>();
        expectedMap.put("chmod", new ArrayList<String>());
        expectedMap.put("debug", new ArrayList<String>());
        listShopValue = new ArrayList<String>();
        listShopValue.add("base_uri");
        listShopValue.add("brands");
        expectedMap.put("shop", listShopValue);
        expectedMap.put("shop.base_uri", new ArrayList<String>());
        List<String> listShopBrandsValue = new ArrayList<String>();
        listShopBrandsValue.add("allow");
        expectedMap.put("shop.brands", listShopBrandsValue);
        expectedMap.put("shop.brands.allow", new ArrayList<String>());

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
        expectedMap = new HashMap<String, List<String>>();
        expectedMap.put("chmod", new ArrayList<String>());
        expectedMap.put("debug", new ArrayList<String>());
        listShopValue = new ArrayList<String>();
        listShopValue.add("base_uri");
        listShopValue.add("brands");
        expectedMap.put("shop", listShopValue);
        expectedMap.put("shop.base_uri", new ArrayList<String>());

        listShopBrandsValue = new ArrayList<String>();
        listShopBrandsValue.add("allow");
        expectedMap.put("shop.brands", listShopBrandsValue);
        expectedMap.put("shop.brands.allow", new ArrayList<String>());
        expectedMap.put("brands", new ArrayList<String>());

        result = parser.parse(configContent);
        assertEquals(expectedMap, result);

        // Case
        configContent = "<?php exit; ?>\n" +
                "chmod 511\n" +
                "debug 1\n" +
                "[shop]\n" +
                "   base_uri    'shop'\n" +
                "   base_uri2    'shop'\n";
        expectedMap = new HashMap<String, List<String>>();
        expectedMap.put("chmod", new ArrayList<String>());
        expectedMap.put("debug", new ArrayList<String>());
        listShopValue = new ArrayList<String>();
        listShopValue.add("base_uri");
        listShopValue.add("base_uri2");
        expectedMap.put("shop", listShopValue);
        expectedMap.put("shop.base_uri", new ArrayList<String>());
        expectedMap.put("shop.base_uri2", new ArrayList<String>());

        result = parser.parse(configContent);
        assertEquals(expectedMap, result);

        // Case
        configContent = "<?php exit; ?>\n" +
                "chmod 511\n" +
                "debug 1\n" +
                "[shop]\n" +
                "   base_uri    'shop'\n" +
                "   base_uri2    'shop'\n"+
                "[any]\n" +
                "   base_uri2    'shop'\n";
        expectedMap = new HashMap<String, List<String>>();
        expectedMap.put("chmod", new ArrayList<String>());
        expectedMap.put("debug", new ArrayList<String>());
        listShopValue = new ArrayList<String>();
        listShopValue.add("base_uri");
        listShopValue.add("base_uri2");

        List<String> listAnyValue = new ArrayList<>();
        listAnyValue.add("base_uri2");

        expectedMap.put("shop", listShopValue);
        expectedMap.put("shop.base_uri", new ArrayList<String>());
        expectedMap.put("shop.base_uri2", new ArrayList<String>());
        expectedMap.put("any", listAnyValue);
        expectedMap.put("any.base_uri2", new ArrayList<String>());

        result = parser.parse(configContent);
        assertEquals(expectedMap, result);
    }
}
