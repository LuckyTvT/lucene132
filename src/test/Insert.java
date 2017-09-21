package test;

import dao.DaoImpl;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;
import pojo.Book;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Insert {

    public static void main(String[] args) throws IOException {

//        创建索引库
//        采集数据
        DaoImpl dao = new DaoImpl();
        List<Book> list = dao.findAll();
//       创建文档对象
//        创建域对象并赋值
        List<Document> documents = new ArrayList<>();
        for (Book book : list) {
            Document document = new Document();
            Field idField = new StringField("id", book.getId()+"", Field.Store.YES);
            Field nameField = new TextField("name", book.getName(), Field.Store.YES);
            Field priceField = new FloatField("price",book.getPrice(), Field.Store.YES);
            Field picField = new StoredField("pic",book.getPic());
            Field descField = new TextField("desc",book.getDescription(), Field.Store.NO);
            document.add(idField);
            document.add(nameField);
            document.add(priceField);
            document.add(picField);
            document.add(descField);
            documents.add(document);
        }

//        指定索引库位置
        FSDirectory directory = FSDirectory.open(new File("E:/index"));
//        创建分词器
        IKAnalyzer analyzer = new IKAnalyzer();
//        创建写入对象的配置文件
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_4_10_3, analyzer);
//        创建写入对象
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
//        将文档数据写入索引库
        for (Document doc : documents) {
            indexWriter.addDocument(doc);
        }
        indexWriter.close();


    }

}
