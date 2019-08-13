import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author guang
 * @since 2019-08-13 15:02
 */
public class ArgsAnalysisTest {

    String commandArgs = "-l -p 8080 -d /usr/logs";
    private CommandLinePraser commandLinePraser;

    @Before
    public void before() {
        commandLinePraser = new CommandLinePraser(commandArgs);
    }

    @Test
    public void test_get_l() {
        Object aBoolean = commandLinePraser.get("l");
        assertTrue((Boolean) aBoolean);
    }

    @Test
    public void test_get_p(){
       Object port =  commandLinePraser.get("p");
       assertEquals((Integer)port,new Integer(8080));
    }

}
