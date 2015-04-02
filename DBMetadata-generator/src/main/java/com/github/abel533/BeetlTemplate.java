package com.github.abel533;

import com.github.abel533.database.Dialect;
import com.github.abel533.database.IntrospectedTable;
import com.github.abel533.database.SimpleDataSource;
import com.github.abel533.utils.DBMetadataUtils;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.FileResourceLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.List;

/**
 * @author liuzh
 */
public class BeetlTemplate {
    public static final String root = BeetlTemplate.class.getResource("/").getPath() + File.separator + "beetl";

    public static final FileResourceLoader resourceLoader = new FileResourceLoader(root, "UTF-8");

    public static Configuration cfg;

    static {
        try {
            cfg = Configuration.defaultConfiguration();
        } catch (IOException e) {
            cfg = null;
        }
    }

    public static final GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);

    /**
     * ����һ�����ݿ��html�ļ�
     *
     * @param tables
     * @param filePath
     * @param fileName
     * @return
     * @throws IOException
     */
    public static String exportDatabaseHtml(List<IntrospectedTable> tables, String filePath, String fileName) throws IOException {
        mkdirs(filePath);
        Template t = gt.getTemplate("datebase.html");
        t.binding("tables", tables);
        t.binding("fileName", fileName);
        String path = filePath + File.separator + fileName + ".html";
        render(t, path);
        return path;
    }

    /**
     * ����һ��Table��html�ļ�
     *
     * @param table
     * @return
     * @throws IOException
     */
    public static String exportTableHtml(IntrospectedTable table, String filePath, String fileName) throws IOException {
        mkdirs(filePath);
        Template t = gt.getTemplate("table.html");
        t.binding("table", table);
        t.binding("fileName", table.getName());
        String path = filePath + File.separator + fileName + ".html";
        render(t, path);
        return path;
    }

    /**
     * ����Ŀ¼
     *
     * @param filePath
     */
    private static void mkdirs(String filePath){
        File file = new File(filePath);
        if (!file.isDirectory() || !file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * ���浽ָ��λ��
     *
     * @param t
     * @param path
     * @throws IOException
     */
    private static void render(Template t, String path) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(path), "UTF-8");

        t.renderTo(writer);

        writer.close();
    }
}
