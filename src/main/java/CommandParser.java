import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author guang
 * @since 2019-08-14 18:58
 */
public class CommandParser extends Command {

    private SchemaPraser schemaPraser;

    private String schemaStr;

    public CommandParser(String schemaStr) {
        schemaPraser = new SchemaPraser();
        this.schemaStr = schemaStr;
    }

    public List<Command> parseToList(String commandLine) {
        ArrayList<Command> commands = new ArrayList<>();
        String[] array = commandLine.split(" ");

        for (int i = 0; i < array.length; i++) {
            String name = array[i].replace("-", "");
            Command command = new Command();
            command.setName(name);
            if (hasNext(array, i) && !isValue(array[i + 1]) || isLast(array, i)) {
                // 如果没有值,设置默认值
                command.setValue(getDefaultValueByName(name));
            } else {
                // 有值
                command.setValue(castValueByName(name, array[i + 1]));
                i++;
            }
            commands.add(command);
        }

        return commands;
    }

    public boolean isLast(String[] array, int currentIndex) {
        return currentIndex == array.length - 1;
    }

    public boolean hasNext(String[] array, int currentIndex) {
        return currentIndex + 1 <= array.length - 1;
    }

    public Object castValueByName(String name, String value) {

        Schema schema = schemaPraser.parseToMap(schemaStr).get(name);
        try {
            return schema.getType().getConstructor(String.class).newInstance(value);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isValue(String name) {
        return name.indexOf("-") != 0;
    }

    public Object getDefaultValueByName(String name) {
        Map<String, Schema> schemaMap = schemaPraser.parseToMap(schemaStr);
        return schemaMap.get(name).getDefaultValue();
    }

    public Map<String, Command> parseToMap(String commandLine) {
        return parseToList(commandLine).stream()
                .collect(Collectors.toMap(Command::getName, Function.identity()));
    }

}
