import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

/**
 * @author guang
 * @since 2019-08-13 15:03
 */
public class SchemaTest {

    String schema = "-l:bool,-p:int,-d:string";

    private SchemaParser schemaParser;

    @Before
    public void before() {
        schemaParser = new SchemaParser(this.schema);
    }

    @Test
    public void test_schema_is_null() {
        assertThrows(RuntimeException.class, () -> {
            new SchemaParser(null);
        });

    }

    @Test
    public void test_SchemaPraser_get_l() {
        Class aClass = schemaParser.get("l");
        assertEquals(aClass, Boolean.class);
    }

    @Test
    public void test_SchemaPraser_get_p() {
        Class aClass = schemaParser.get("p");
        assertEquals(aClass, Integer.class);
    }

    @Test
    public void test_SchemaPraser_get_d() {
        Class aClass = schemaParser.get("d");
        assertEquals(aClass, String.class);
    }

    @Test
    public void test_SchemaPraser_get_other() {
        Class aClass = schemaParser.get("other");
        assertNull(aClass);
    }

}
