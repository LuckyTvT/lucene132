package test;

import dao.Dao;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

public class Update {
    public static void main(String[] args) throws IOException {
//        更新索引是先删除后添加
        IKAnalyzer analyzer = new IKAnalyzer();
//        获取索引库地址
        FSDirectory directory = FSDirectory.open(new File("e:/index"));
//        创建写入对象配置
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_4_10_3, analyzer);
//        创建写入对象
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
        Document document = new Document();
        document.add(new StoredField("id","7"));
        document.add(new TextField("name","java编2程思想", Field.Store.YES));
        document.add(new StoredField("pic","92934.jpg"));
        document.add(new LongField("price", (long) 71.5, Field.Store.YES));

//        根据new Term的域名和值找到相同的内容，先删除，然后再添加新数据
        indexWriter.updateDocument(new Term("name","java"),document);
        indexWriter.close();
    }
}
