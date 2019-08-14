import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author guang
 * @since 2019-08-14 18:55
 */
public class SchemaPraser {

    /**
     * 把 schema 解析为 Map 结构
     *
     * @param schema
     * @return Map, key 为 Schema.name , value 为 Schema
     */
    public Map<String, Schema> parseToMap(String schema) {
        return parseToList(schema).stream()
                .collect(Collectors.toMap(Schema::getName, Function.identity()));
    }

    /**
     * 把 schema 解析为 List<Schema>
     *
     * @param schema
     * @return
     */
    public List<Schema> parseToList(String schema) {

        return Arrays.asList(StringUtils.split(schema, ","))
                .stream()
                .map(schemaStr -> parseSchemaStr(schema)).collect(Collectors.toList());
    }

    private Schema parseSchemaStr(String schemaStr) {

        String[] array = schemaStr.split(":");
        String name = array[0];
        String type = array[1];
        String defaultValue = array[2];

        Schema schema = new Schema();
        schema.setName(name);
        schema.setType(getType(type));
        schema.setDefaultValue(getDefaultValue(defaultValue));

        return schema;
    }

    private Class getType(String type) {
        return null;
    }

    private Object getDefaultValue(String defaultValue) {
        return null;
    }

}
