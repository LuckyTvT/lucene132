package test;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

public class T2 {
//    全文检索  搜索部分
    public static void main(String[] args) throws ParseException, IOException {
//        1.创建分词器
        IKAnalyzer analyzer = new IKAnalyzer();
//        2.创建搜索解析器对象 并定义默认查询的域
        QueryParser queryParser = new QueryParser("name", analyzer);
//        3.根据搜索解析器创建条件查询对象Query
//        参数为查询条件，如果没有冒号左边的东西，就默认用搜索解析器对象中设置的默认查询的域
//        如果设置冒号左边的域，就覆盖默认值
        Query query = queryParser.parse("desc:java");
//        4.获取索引库流对象
        Directory directory = FSDirectory.open(new File("E:/index"));
//        5.通过流对象创建读取对象
        IndexReader reader = DirectoryReader.open(directory);
//        6.通过读对象来创建搜索对象
        IndexSearcher searcher = new IndexSearcher(reader);
//        7.通过查询对象来查询数据，会 返回TopDocs类的集合  这个类对象是坐标数组
//        参数说明： 1.查询对象，   2.查询信息条数
        TopDocs topDocs = searcher.search(query, 2);
        int hits = topDocs.totalHits;
        System.out.println("查询到的总条数："+hits);
//        8.处理结果集，获得坐标数组
        ScoreDoc[] docs = topDocs.scoreDocs;
        for (ScoreDoc doc : docs) {
            int docId = doc.doc;
//            9.根据获得的id来查询文档数据
            Document document = searcher.doc(docId);
//            10.输出文档数据
            System.out.println(document.get("id"));
            System.out.println(document.get("name"));
            System.out.println(document.get("price"));
            System.out.println(document.get("pic"));
//            System.out.println(document.get("desc"));
        }

//        11.关流
        reader.close();

    }

}
