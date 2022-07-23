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
                "chmod\n" +
                "   l 1\n" +
                "   r 2\n";
        expectedMap = new HashMap<String, List<String>>();
        expectedMap.put("chmod", new ArrayList<String>());

        result = parser.parse(configContent);
        assertEquals(expectedMap, result);

        // Case
        configContent = "<?php exit; ?>\n" +
                "chmod\n" +
                "\tl 1\n" +
                "\tr 2\n";
        expectedMap = new HashMap<String, List<String>>();
        expectedMap.put("chmod", new ArrayList<String>());

        // Case
        configContent = "<?php exit; ?>\n" +
                "chmod\n" +
                " l 1\n" +
                " r 2\n";
        expectedMap = new HashMap<String, List<String>>();
        expectedMap.put("chmod", new ArrayList<String>());

        result = parser.parse(configContent);
        assertEquals(expectedMap, result);

        // Case
        configContent = "<?php exit; ?>\n" +
                "debug 1\n" +
                "chmod\n" +
                " l 1\n" +
                " r 2\n";
        expectedMap = new HashMap<String, List<String>>();
        expectedMap.put("debug", new ArrayList<String>());
        expectedMap.put("chmod", new ArrayList<String>());

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
                "# debug 1";
        expectedMap = new HashMap<String, List<String>>();
        expectedMap.put("chmod", new ArrayList<String>());

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

    @Test
    public void testWithRemote() {
        ConfigParser parser = new ConfigParser();

        Map<String, List<String>> expectedMap = new HashMap<String, List<String>>();

        Map<String, List<String>> result;
        // Case
        String configContent = "<?php exit; ?>\n" +
                "chmod\t511\n" +
                "dbupdated\t1630683690.0571\n" +
                "db_mssql_path\n" +
                "\tl\t'sqlsrv://zero:mI1sVyUNjGtkbw9FaQnv@194.41.52.100:9433/sephora_hub/?persistent=false&query_timeout=60'\n" +
                "\tr\t'mssql://zero:5fgQkoVN8Z0e@sephora_test/sephora_hub/?persistent=false'\n" +
                "db_path\n" +
                "\tl\t'mysqli://common_user:JshaJslapr1wqe@mysql/zt-ru_sephora/'\n" +
                "\tr\t'mysql://common_user:JshaJslapr1wqe@sephora-mysql:3311/zt-ru_sephora/'\n" +
                "debug\t1";
        expectedMap = new HashMap<String, List<String>>();
        expectedMap.put("chmod", new ArrayList<String>());
        expectedMap.put("dbupdated", new ArrayList<String>());
        expectedMap.put("db_mssql_path", new ArrayList<String>());
        expectedMap.put("db_path", new ArrayList<String>());
        expectedMap.put("debug", new ArrayList<String>());

        result = parser.parse(configContent);
        assertEquals(expectedMap, result);
    }
}
