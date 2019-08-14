import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * @author guang
 * @since 2019-08-14 19:23
 */
public class SchemaPraserTest {

    String schema = "-l:bool:false,-p:int:0,-d:string:''";
    private SchemaPraser schemaPraser;

    @Before
    public void before() {
        schemaPraser = new SchemaPraser();
    }

    @Test
    public void test_parseToMap() {
        Map<String, Schema> map = schemaPraser.parseToMap("-l:bool:false,-p:int:0,-d:string:''");

        Schema schema = map.get("d");
        Assert.assertEquals(String.class, schema.getType());
        Assert.assertEquals("", schema.getDefaultValue());


    }

    @Test
    public void test_parseToList() {
        List<Schema> schemas = schemaPraser.parseToList("-l:bool:false,-p:int:0,-d:string:''");
        Assert.assertEquals(3, schemas.size());
    }

    @Test
    public void test_parseSchemaStr_l() {
        Schema schema = schemaPraser.parseSchemaStr("-l:bool:false");
        Assert.assertEquals("l", schema.getName());
        Assert.assertEquals(Boolean.class, schema.getType());
        Assert.assertEquals(false, schema.getDefaultValue());
    }

    @Test
    public void test_parseSchemaStr_p() {
        Schema schema = schemaPraser.parseSchemaStr("-p:int:0");
        Assert.assertEquals("p", schema.getName());
        Assert.assertEquals(Integer.class, schema.getType());
        Assert.assertEquals(0, schema.getDefaultValue());
    }

    @Test
    public void test_parseSchemaStr_d() {
        Schema schema = schemaPraser.parseSchemaStr("-d:string:''");
        Assert.assertEquals("d", schema.getName());
        Assert.assertEquals(String.class, schema.getType());
        Assert.assertEquals("", schema.getDefaultValue());
    }


    @Test
    public void test_getDefaultValueByType_bool_false() {
        Boolean l = (Boolean) schemaPraser.getDefaultValueByType("bool", "false");
        Assert.assertTrue(!l);
    }

    @Test
    public void test_getDefaultValueByType_int_0() {
        Integer p = (Integer) schemaPraser.getDefaultValueByType("int", "0");
        Assert.assertEquals(p, new Integer(0));
    }

    @Test
    public void test_getDefaultValueByType_string_() {
        String d = (String) schemaPraser.getDefaultValueByType("string", "");
        Assert.assertEquals(d, "");
    }

    @Test
    public void test_getType_bool() {
        Class aClass = schemaPraser.getType("bool");
        Assert.assertEquals(aClass, Boolean.class);
    }


    @Test
    public void test_getType_int() {
        Class aClass = schemaPraser.getType("int");
        Assert.assertEquals(aClass, Integer.class);
    }

    @Test
    public void test_getType_string() {
        Class aClass = schemaPraser.getType("string");
        Assert.assertEquals(aClass, String.class);
    }

    @Test(expected = RuntimeException.class)
    public void test_getType_unknow() {
        schemaPraser.getType("unkonw");
    }

}
