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
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

public class Search {

    public static void main(String[] args) throws ParseException, IOException {
//        创建分词器
        IKAnalyzer analyzer = new IKAnalyzer();
//        创建查询解析对象  并设置默认搜索域和分词器
        QueryParser queryParser = new QueryParser("name",analyzer);
//        创建搜索条件对象
        Query query = queryParser.parse("desc:java");
//        获取索引库位置
        FSDirectory directory = FSDirectory.open(new File("e:/index"));
//        创建读对象
        IndexReader reader = DirectoryReader.open(directory);
//        利用读对象创建搜索对象
        IndexSearcher searcher = new IndexSearcher(reader);
//        设置查询条件及，查询结果数量
        TopDocs topDocs = searcher.search(query, 3);
        System.out.println("数据总条数："+topDocs.totalHits);
//        获取结果集
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
//        处理结果集
        for (ScoreDoc scoreDoc : scoreDocs) {
//            根据结果集中的每个元素，获取文档id
            int docId = scoreDoc.doc;
//            根据文档id查询文档信息
            Document doc = searcher.doc(docId);
//            输出文档信息
//            getField可以显示当前域是否分词，是否索引，是否存储的信息
//            get只根据域名获取域中对应的值
            System.out.println("id==="+doc.getField("id"));
            System.out.println("name==="+doc.getField("name"));
            System.out.println("pic==="+doc.getField("pic"));
            System.out.println("price==="+doc.getField("price"));
        }
//        释放资源
        reader.close();

    }



}
