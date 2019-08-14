import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

/**
 * @author guang
 * @since 2019-08-14 19:23
 */
public class SchemaPraserTest {

    String schema = "-l:bool:false,-p:int:0,-d:string:''";
    private SchemaPraser schemaPraser;
    private Map<String, Schema> parseResult;

    @Before
    public void before() {
        schemaPraser = new SchemaPraser();
        parseResult = schemaPraser.parseToMap(schema);
    }

    @Test
    public void test_parse() {
        Schema schema = parseResult.get("l");
        Assert.assertTrue(schema.getName().equals("l"));

    }

}
