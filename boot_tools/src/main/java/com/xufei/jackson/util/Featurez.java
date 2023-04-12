package com.xufei.jackson.util;

public enum Featurez {
    // Low-level I/O handling features:支持低级I/O操作特性
    /**
     * 自动关闭源：默认true_启用（即：解析json字符串后，自动关闭输入流）
     * 该特性，决定了解析器是否可以自动关闭非自身的底层输入源
     * 1.禁用：应用程序将分开关闭底层的{@link InputStream} and {@link Reader}
     * 2.启用：解析器将关闭上述对象，其自身也关闭，此时input终止且调用{@link JsonParser#close}
     */
    AUTO_CLOSE_SOURCE(true),

    /**
     * Support for non-standard data format constructs：支持非标准数据格式的json
     * 该特性，决定了解析器是否可以解析含有Java/C++注释样式的JSON串(如：/*或//的注释符)
     * 默认false：不解析含有注释符（即：true时能解析含有注释符的json串）
     * 注意：该属性默认是false，因此必须显式允许，即通过JsonParser.Feature.ALLOW_COMMENTS 配置为true。
     */
    ALLOW_COMMENTS(false),

    /**
     * 默认false：不解析含有另外注释符
     * 该特性，决定了解析器是否可以解析含有以"#"开头并直到一行结束的注释样式（这样的注释风格通常也用在脚本语言中）
     * 注意：标准的json字符串格式没有含有注释符（非标准），然而则经常使用<br>
     */
    ALLOW_YAML_COMMENTS(false),

    /**
     * 这个特性决定parser是否能解析属性名字没有加双引号的json串（这种形式在Javascript中被允许，但是JSON标准说明书中没有）。
     *（默认情况下，标准的json串里属性名字都需要用双引号引起来。比如：{age:12, name:"曹操"}非标准的json串，默认不能解析）
     * 注意：由于JSON标准上需要为属性名称使用双引号，所以这也是一个非标准特性，默认是false的。
     * 同样，需要设置JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES为true，打开该特性。
     *
     */
    ALLOW_UNQUOTED_FIELD_NAMES(false),

    /**
     * 默认false：不解析含有单引号的字符串或字符
     * 该特性，决定了解析器是否可以解析单引号的字符串或字符(如：单引号的字符串，单引号'\'')
     * 注意：可作为其他可接受的标记，但不是JSON的规范
     */
    ALLOW_SINGLE_QUOTES(false),

    /**
     * 允许：默认false不解析含有结束语控制字符
     * 该特性，决定了解析器是否可以解析结束语控制字符(如：ASCII<32，包含制表符\t、换行符\n和回车\r)
     * 注意：设置false（默认）时，若解析则抛出异常;若true时，则用引号即可转义
     */
    ALLOW_UNQUOTED_CONTROL_CHARS(false),

    /**
     * 可解析反斜杠引用的所有字符，默认：false，不可解析
     */
    ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER(false),

    /**
     * 可解析以"0"为开头的数字(如: 000001)，解析时则忽略0，默认：false，不可解析，若有则抛出异常
     */
    ALLOW_NUMERIC_LEADING_ZEROS(false),

    /**
     * 可解析非数值的数值格式（如：正无穷大，负无穷大，Integer或浮点数类型属性赋值NaN的JSON串）
     * 该特性允许parser可以识别"Not-a-Number" (NaN)标识集合作为一个合法的浮点数。
     * 默认：false，不能解析
     */
    ALLOW_NON_NUMERIC_NUMBERS(false),

    /**
     * 默认：false，不检测JSON对象重复的字段名，即：相同字段名都要解析
     * true时，检测是否有重复字段名，若有，则抛出异常{@link JsonParseException}
     * 注意：检查时，解析性能下降，时间超过一般情况的20-30%
     */
    STRICT_DUPLICATE_DETECTION(false),

    /**
     * 默认：false，底层的数据流(二进制数据持久化，如：图片，视频等)全部被output，若读取一个位置的字段，则抛出异常
     * true时，则忽略未定义
     */
    IGNORE_UNDEFINED(false),

    /**
     * 默认：false，JSON数组中不解析漏掉的值，若有，则会抛出异常{@link JsonToken#VALUE_NULL}
     * true时，可解析["value1",,"value3",]最终为["value1", null, "value3", null]空值作为null
     */
    ALLOW_MISSING_VALUES(false);

    Featurez(boolean b) {
    }
}