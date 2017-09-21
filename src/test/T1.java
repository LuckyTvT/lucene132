package test;

import dao.Dao;
import dao.DaoImpl;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;
import pojo.Book;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class T1 {
//    往索引库存入数据

    public static void main(String[] args) throws IOException {
//        1.采集数据
        Dao dao = new DaoImpl();
        List<Book> books = dao.findAll();
//        创建一个文档集合
        List<Document> documents = new ArrayList<>();
        for (Book book : books) {
//            2.创建文档对象
            Document document = new Document();
//            3.创建field域
            Field idField = new TextField("id", book.getId()+"", Field.Store.YES);
            Field nameField = new TextField("name", book.getName(), Field.Store.YES);
            Field priceField = new TextField("price", book.getPrice()+"", Field.Store.YES);
            Field picField = new TextField("pic", book.getPic(), Field.Store.YES);
            Field descField = new TextField("desc", book.getDescription(), Field.Store.YES);
//            将域对象放入document对象中
            document.add(idField);
            document.add(nameField);
            document.add(priceField);
            document.add(picField);
            document.add(descField);
//            将document对象添加进文档集合中
            documents.add(document);
        }
//        4.创建分词器对象
        IKAnalyzer analyzer = new IKAnalyzer();
//        5.创建写入配置对象IndexWriterConfig
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_4_10_3, analyzer);
//        6.创建写入目录对象
        Directory directory = FSDirectory.open(new File("E:/index"));
//        7.创建写入对象
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
//        8.将文档对象写入到索引库
        for (Document document : documents) {
            indexWriter.addDocument(document);
        }
//        9.释放资源
        indexWriter.close();
    }






}
