package com.lsy.jstorm.hbase;

import com.lsy.jstorm.ConfigUtils;
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by lisiyu on 2016/11/5.
 */
public class HBaseClient {
    private static Logger logger = LoggerFactory.getLogger(HBaseClient.class);

//    static Configuration conf = HbaseSessionFactory.getHbaseConf();
    static Configuration conf = HBaseConfiguration.create();

    static {
//        conf.set("hbase.zookeeper.quorum", "localhost:2181");
//        conf.set("hbase.zookeeper.quorum", "172.27.36.85:2181/rt/hbase");
        InputStream confResourceAsInputStream = conf.getConfResourceAsInputStream("hbase-site.xml");
        int available = 0;
        try {
            available = confResourceAsInputStream.available();
        } catch (Exception e) {
            //for debug purpose
            logger.debug("configuration files not found locally");
        } finally {
            IOUtils.closeQuietly(confResourceAsInputStream);
        }
        if (available == 0 ) {
            conf = new Configuration();
            conf.addResource("core-site.xml");
            conf.addResource("hbase-site.xml");
            conf.addResource("hdfs-site.xml");
        }
    }
    static HBaseAdmin admin = null;
    static {
        try {
            admin = new HBaseAdmin(conf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println(exists("ttt"));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //判断表是否存在
    public static boolean exists(String tablename) throws IOException {
        if(admin.tableExists(tablename)) {
            return true;
        } else {
            return false;
        }
    }

    //create
    public static void create(String tablename, String columnFamily) throws Exception {

        if(admin.tableExists(tablename)) {
            System.out.println("Table:" + tablename + " already exists!");
            System.out.println("Create table aborted!");
        } else {
            HTableDescriptor tableDesc = new HTableDescriptor(tablename);
            tableDesc.addFamily(new HColumnDescriptor(columnFamily));
            admin.createTable(tableDesc);
            System.out.println("Table:" + tablename + " created successfully!");
        }
    }

    /*
     * 创建表
     *
     * @tableName 表名
     *
     * @family 列族列表
     */
    public static void creatTable(String tableName, String[] family, int minVersions, int maxVersions)
            throws Exception {
        HTableDescriptor desc = new HTableDescriptor(tableName);
        for (int i = 0; i < family.length; i++) {
            desc.addFamily(new HColumnDescriptor(family[i]).setVersions(minVersions, maxVersions));
        }
        if (admin.tableExists(tableName)) {
            System.out.println("table Exists!");
            System.exit(0);
        } else {
            admin.createTable(desc);
            System.out.println("create table Success!");
        }
    }

    //delete表
    public static void deleteTable(String tablename) throws IOException {
        if(admin.tableExists(tablename)) {
            try {
                admin.disableTable(tablename);
                admin.deleteTable(tablename);
                System.out.println("Table:" + tablename + " deleted!");
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("Table:" + tablename + " doesn't exist!");
        }
    }

    //put
    public static void put(String tablename, String row, String columnFamily, String column, String data) throws Exception {
        HTable table = new HTable(conf, tablename);
        Put p1 = new Put(Bytes.toBytes(row));
        p1.add(Bytes.toBytes(columnFamily), Bytes.toBytes(column), Bytes.toBytes(data));
        table.put(p1);
        System.out.println("put '"+row+"','"+columnFamily+":"+column+"','"+data+"'");
    }

    //append
    public static void append(String tablename, String row, String columnFamily, String column, String data) throws Exception {
        HTable table = new HTable(conf, tablename);
        Append append = new Append(Bytes.toBytes(row));
        append.add(Bytes.toBytes(columnFamily), Bytes.toBytes(column),Bytes.toBytes(data));
        table.append(append);
        System.out.println("append '"+row+"','"+columnFamily+":"+column+"','"+data+"'");
    }

    /*
     * 删除指定行的指定列
     *
     * @tableName 表名
     *
     * @rowKey rowKey
     *
     * @familyName 列族名
     *
     * @columnName 列名
     */
    public static void deleteColumn(String tableName, String rowKey,
                                    String falilyName, String columnName) throws IOException {
        HTable table = new HTable(conf, Bytes.toBytes(tableName));
        Delete deleteColumn = new Delete(Bytes.toBytes(rowKey));
        deleteColumn.deleteColumns(Bytes.toBytes(falilyName),
                Bytes.toBytes(columnName));
        table.delete(deleteColumn);
        System.out.println(falilyName + ":" + columnName + "is deleted!");
    }

    /*
     * 更新表中的某一列
     *
     * @tableName 表名
     *
     * @rowKey rowKey
     *
     * @familyName 列族名
     *
     * @columnName 列名
     *
     * @value 更新后的值
     */
    public static void updateTable(String tableName, String rowKey,
                                   String familyName, String columnName, String value)
            throws IOException {
        HTable table = new HTable(conf, Bytes.toBytes(tableName));
        Put put = new Put(Bytes.toBytes(rowKey));
        put.add(Bytes.toBytes(familyName), Bytes.toBytes(columnName),
                Bytes.toBytes(value));
        table.put(put);
        System.out.println("update table Success!");
    }

    //get
    public static Result getMaxVersion(String tablename, String row) throws IOException {
        HTable table = new HTable(conf, tablename);
        Get g = new Get(Bytes.toBytes(row));
        g.setMaxVersions();
        Result result = table.get(g);
        System.out.println("Get: " + result);
        return result;
    }

    /*
     * 查询表中的某行某一列
     *
     * @tableName 表名
     *
     * @rowKey rowKey
     */
    public static Result getResultByColumn(String tableName, String rowKey,
                                           String familyName, String columnName) throws IOException {
        HTable table = new HTable(conf, Bytes.toBytes(tableName));
        Get get = new Get(Bytes.toBytes(rowKey));
        get.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(columnName)); // 获取指定列族和列修饰符对应的列
        Result result = table.get(get);
        /*for (KeyValue kv : result.list()) {
            System.out.println("family:" + Bytes.toString(kv.getFamily()));
            System.out
                    .println("qualifier:" + Bytes.toString(kv.getQualifier()));
            System.out.println("value:" + Bytes.toString(kv.getValue()));
            System.out.println("Timestamp:" + kv.getTimestamp());
            System.out.println("-------------------------------------------");
        }*/
        return result;

    }
}
