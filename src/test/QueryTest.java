package test;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;

import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

public class QueryTest {

    public static void main(String[] args) throws IOException, ParseException {
//        Query query = new TermQuery(new Term("id", "3"));
        IKAnalyzer analyzer = new IKAnalyzer();
        QueryParser queryParser = new QueryParser("name",analyzer);
        Query query = queryParser.parse("22");

        doSearch(query);
    }

    @Test
    public void testTermQuery() throws IOException {
        Query termQuery = new TermQuery(new Term("name","cat"));
        doSearch(termQuery);
    }

    @Test
    public void numericRangeQueryTest() throws IOException {
        Query query = NumericRangeQuery.newFloatRange("price", 54f, 66f, true, false);
        doSearch(query);

    }

    @Test
    public void booleanQueryTest() throws IOException {
//        Query query1 = NumericRangeQuery.newFloatRange("price", 54f, 66f, true, false);
        Query query3 = new TermQuery(new Term("id", "2"));
        Query query2 = new TermQuery(new Term("desc", "java"));
        BooleanQuery booleanQuery = new BooleanQuery();
        booleanQuery.add(query3, BooleanClause.Occur.SHOULD);
        booleanQuery.add(query2, BooleanClause.Occur.MUST);
        doSearch(booleanQuery);
    }

    @Test
    public void testMultiFieldQueryParser() throws ParseException, IOException {
        IKAnalyzer analyzer = new IKAnalyzer();
//        添加查询条件   查询条件之间是OR连接
        String[] fields = {"id","name"};
        MultiFieldQueryParser multiFieldQueryParser = new MultiFieldQueryParser(fields, analyzer);
        Query query = multiFieldQueryParser.parse("2");
        System.out.println(query);
        doSearch(query);
    }


    public static void doSearch(Query query) throws IOException {
        FSDirectory directory = FSDirectory.open(new File("E:/index"));
        IndexReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopDocs topDocs = searcher.search(query, 3);
        System.out.println("查询到的总记录数："+topDocs.totalHits);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            int docId = scoreDoc.doc;
            Document document = searcher.doc(docId);
            System.out.println(document.get("id"));
        }
        reader.close();
    }




}
