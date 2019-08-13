import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author guang
 * @since 2019-08-13 15:17
 */
public class SchemaParser {

    Map<String, Class> map = new HashMap();

    public SchemaParser(String schema) {
        if (StringUtils.isEmpty(schema)) {
            throw new RuntimeException("schema 不能为空");
        }
        String[] array = StringUtils.split(schema, ",");
        for (int i = 0; i < array.length; i++) {
            String[] strings = StringUtils.split(array[i], ":");
            if (strings.length != 2) {
                throw new RuntimeException("schema 格式有误: " + array[i]);
            }
            map.put(strings[0].replace("-", ""), getType(strings[1]));
        }

    }

    public Class get(String agrs) {
        return map.get(agrs);
    }

    public Class getType(String key) {

        switch (key) {
            case "bool":
                return Boolean.class;
            case "int":
                return Integer.class;
            case "string":
                return String.class;
            default:
                throw new UnsupportedOperationException("不能解析: " + key);
        }

    }
}
