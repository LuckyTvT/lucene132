package test;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;

public class Delete {

    public static void main(String[] args) throws IOException {
//        获取directory流对象
        FSDirectory directory = FSDirectory.open(new File("e:/index"));
//        创建写入对象配置
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_4_10_3, null);
//        创建写入对象
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
//        根据Term删除索引库  name:java  只删除文档，不删除索引
        indexWriter.deleteDocuments(new Term("name","java"));
//        删除全部  删除文档和索引
//        indexWriter.deleteAll();
        indexWriter.close();



    }




}
