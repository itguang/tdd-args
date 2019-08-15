import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * @author guang
 * @since 2019-08-15 10:21
 */
public class CommandParserTest {

    String commandLine = "-l true -p 8080 -d /usr/logs";
    String schema = "-l:bool:false,-p:int:0,-d:string:''";
    private CommandParser commandParser;

    @Before
    public void before() {
        commandParser = new CommandParser(schema);
    }

    @Test
    public void test_isValue() {
        Assert.assertFalse(commandParser.isValue("-p"));
        Assert.assertTrue(commandParser.isValue("p"));
    }

    @Test
    public void test_castValueByName() {
        Object value = commandParser.castValueByName("l", "true");
        Assert.assertTrue((Boolean) value);

    }

    @Test
    public void test_getDefaultValueByName() {
        Object l = commandParser.getDefaultValueByName("l");
        Assert.assertFalse((Boolean) l);
    }

    @Test
    public void test_isLast() {
        String[] array = {"-l", "-p", "-d"};
        Assert.assertFalse(commandParser.isLast(array, 0));
        Assert.assertTrue(commandParser.isLast(array, 2));
    }

    public void test_hasNext() {
        String[] array = {"-l", "-p", "-d"};
        Assert.assertTrue(commandParser.hasNext(array, 0));
        Assert.assertFalse(commandParser.hasNext(array, 2));

    }

    @Test
    public void test_parseToList_has_value() {
        List<Command> commands = commandParser.parseToList("-l true -p 8080 -d /usr/logs");
        Assert.assertEquals(commands.size(), 3);
    }

    @Test
    public void test_parseToList_no_value() {
        List<Command> commands = commandParser.parseToList("-l -p -d");
        Assert.assertEquals(commands.size(), 3);
    }

    @Test
    public void test_parseToMap_has_value() {
        Map<String, Command> commandMap = commandParser.parseToMap("-l true -p 8080 -d /usr/logs");
        Assert.assertEquals(commandMap.size(), 3);

        Command command = commandMap.get("l");
        Assert.assertTrue((Boolean) command.getValue());

        Command commandP = commandMap.get("p");
        Assert.assertEquals(commandP.getValue(), new Integer(8080));

        Command commandD = commandMap.get("d");
        Assert.assertEquals(commandD.getValue(), "/usr/logs");

    }
}
