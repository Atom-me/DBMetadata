#数据库元数据

本工具可用于数据库表和字段的查询，以及数据库元数据的进一步使用。

#本工具目前分为两部分

##DBMetadata-core

数据库元数据核心部分，该部分完全独立，不依赖任何第三方，获取元数据部分的代码参考了MyBatis Generator。

如果你要连接数据库，需要有该数据库的JDBC驱动。

###使用方法：

###1. 引入jar包或者Maven依赖：

```xml
<dependency>
    <groupId>com.github.abel533</groupId>
    <artifactId>DBMetadata-core</artifactId>
    <version>0.1.0</version>
</dependency>
```

###2. 使用方法

首先创建一个`SimpleDataSource`:
```java
SimpleDataSource dataSource = new SimpleDataSource(
        Dialect.MYSQL,
        "jdbc:mysql://localhost:3306/test",
        "root",
        ""
);
```
除了上面这种方式外，还可以使用`SimpleDataSource(Dialect dialect, DataSource dataSource)`这个构造方法，直接使用其他的`DataSource`。

然后就是用创建好的`dataSource`去创建`DBMetadataUtils`:

```java
DBMetadataUtils dbMetadataUtils = new DBMetadataUtils(dataSource);
```

创建一个`DatabaseConfig`,调用`introspectTables(config)`方法获取数据库表的元数据：
```java
DatabaseConfig config = new DatabaseConfig("mydb", null);
List<IntrospectedTable> list = dbMetadataUtils.introspectTables(config);
```

这里需要注意`DatabaseConfig`，他有下面三个构造方法：

- DatabaseConfig()

- DatabaseConfig(String catalog, String schemaPattern)

- DatabaseConfig(String catalog, String schemaPattern, String tableNamePattern)

一般情况下我们需要设置`catalog`和`schemaPatter`，还可以设置`tableNamePattern`来限定要获取的表。

其中`schemaPatter`和`tableNamePattern`都支持sql的`%`和`_`匹配。

获取数据库表的元数据后，我们就可以利用这些数据了。

下面代码是简单的将这些信息输出到控制台：

```java
for (IntrospectedTable table : list) {
    System.out.println(table.getName() + ":");
    for (IntrospectedColumn column : table.getAllColumns()) {
        System.out.println(column.getName() + " - " +
                column.getJdbcTypeName() + " - " +
                column.getJavaProperty() + " - " +
                column.getJavaProperty() + " - " +
                column.getFullyQualifiedJavaType().getFullyQualifiedName() + " - " +
                column.getRemarks());
    }
}
```

##DBMetadata-swing

这个子项目也算是一个对**DBMetadata-core**的使用，通过上述工具获取元数据后，使用swing界面展示数据，并且可以通过查询来筛选符合要求的数据。

这个项目除了实现基本的表和字段查询外，还算是一个基于界面使用该工具的基础，你可以在该项目基础上增加其他功能。

###启动

运行`com.github.abel533.Launch`即可启动本项目。

本项目提供打包好的程序可供直接使用。

下载地址:待补充

###界面预览

待补充