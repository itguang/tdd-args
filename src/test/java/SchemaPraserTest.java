import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
