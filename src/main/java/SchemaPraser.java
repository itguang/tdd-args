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
        List<Schema> schemas = parseToList(schema);
        return schemas.stream()
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
                .map(schemaStr -> parseSchemaStr(schemaStr)).collect(Collectors.toList());
    }

    public Schema parseSchemaStr(String schemaStr) {

        String[] array = schemaStr.split(":");
        String name = array[0];
        String type = array[1];
        String defaultValue = array[2];

        Schema schema = new Schema();
        schema.setName(name.replace("-", ""));
        schema.setType(getType(type));
        schema.setDefaultValue(castDefaultValueByType(type, defaultValue));

        return schema;
    }

    public Class getType(String type) {
        switch (type) {
            case "bool":
                return Boolean.class;
            case "int":
                return Integer.class;
            case "string":
                return String.class;
            default:
                throw new RuntimeException("未能解析的类型: " + type);
        }
    }

    public Object castDefaultValueByType(String type, String defaultValue) {
        switch (type) {
            case "bool":
                return new Boolean(defaultValue);
            case "int":
                return Integer.valueOf(defaultValue);
            case "string":
                return "";
            default:
                throw new RuntimeException("未能解析的类型: " + type);
        }
    }

}
