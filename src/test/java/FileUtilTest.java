import org.junit.Test;

import static org.junit.Assert.*;

import util.FileUtil;

public class FileUtilTest {

    @Test
    public void getClassNameByPathTest() {
        // case 1
        String path = "_modules/project/shop/idb/_lib/category";
        String libraryName = FileUtil.getLibraryNameByPath(path);
        assertEquals("Ожидалось совпадение классов", "U__shop_idb_category", libraryName);

        // case 2
        path = "_modules/project/shop/idb/_lib/lib/lib2/category";
        libraryName = FileUtil.getLibraryNameByPath(path);
        assertEquals("Ожидалось совпадение классов", "U__shop_idb__lib_lib2_category", libraryName);

        // case 3
        path = "_modules/sephora/shop/idb/_lib/category";
        libraryName = FileUtil.getLibraryNameByPath(path);
        assertEquals("Ожидалось совпадение классов", "U__shop_idb_category", libraryName);

        // case 4
        path = "_modules/sephora/shop/idb/_lib/lib/category";
        libraryName = FileUtil.getLibraryNameByPath(path);
        assertEquals("Ожидалось совпадение классов", "U__shop_idb__lib_category", libraryName);

        // case 5
        path = "/_modules/sephora/shop/idb/_lib/category";
        libraryName = FileUtil.getLibraryNameByPath(path);
        assertEquals("Ожидалось совпадение классов", "U__shop_idb_category", libraryName);

        // case 6
        path = "_modules/sephora/shop/idb/_lib/category.inc.php";
        libraryName = FileUtil.getLibraryNameByPath(path);
        assertEquals("Ожидалось совпадение классов", "U__shop_idb_category", libraryName);

        // case 7
        path = "/_modules/sephora/shop/idb/_lib/category.inc.php";
        libraryName = FileUtil.getLibraryNameByPath(path);
        assertEquals("Ожидалось совпадение классов", "U__shop_idb_category", libraryName);
    }
    @Test
    public void getModuleClassNameByPathTest() {
        // case 1
        String path = "_modules/project/shop/idb/_lib/category";
        String libraryName = FileUtil.getModuleNameByPath(path);
        assertEquals("Ожидалось совпадение классов", "", libraryName);

        // case 2
        path = "_modules/project/shop/idb/_lib/lib/lib2/category";
        libraryName = FileUtil.getModuleNameByPath(path);
        assertEquals("Ожидалось совпадение классов", "", libraryName);

        // case 3
        path = "_modules/sephora/shop/idb/_lib/category";
        libraryName = FileUtil.getModuleNameByPath(path);
        assertEquals("Ожидалось совпадение классов", "", libraryName);

        // case 4
        path = "_modules/sephora/shop/idb/_lib/lib/category";
        libraryName = FileUtil.getModuleNameByPath(path);
        assertEquals("Ожидалось совпадение классов", "", libraryName);

        // case 5
        path = "_modules/sephora/shop/idb/lib/category";
        libraryName = FileUtil.getModuleNameByPath(path);
        assertEquals("Ожидалось совпадение классов", "U_mod_shop_idb_lib_category", libraryName);

        // case 6
        path = "/_modules/sephora/shop/idb/lib/category";
        libraryName = FileUtil.getModuleNameByPath(path);
        assertEquals("Ожидалось совпадение классов", "U_mod_shop_idb_lib_category", libraryName);
        // case 7
        path = "/_modules/sephora/shop/idb/lib/category.inc.php";
        libraryName = FileUtil.getModuleNameByPath(path);
        assertEquals("Ожидалось совпадение классов", "U_mod_shop_idb_lib_category", libraryName);
    }
}
