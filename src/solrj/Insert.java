package solrj;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;

public class Insert {
    @Test
//    添加/修改
    public void insert() throws IOException, SolrServerException {
//        创建HttpSolrServer对象，通过它和solr服务器建立连接。
        HttpSolrServer httpSolrServer = new HttpSolrServer("http://localhost:8090/solr");
//        创建SolrInputDocument对象，然后通过它来添加域
        SolrInputDocument solrInputDocument = new SolrInputDocument();
        solrInputDocument.addField("id","2");
        solrInputDocument.addField("name","dog");
//        设置该文档的权重
        solrInputDocument.setDocumentBoost(44f);
//        把solrInputDocument对象添加进索引库中
        httpSolrServer.add(solrInputDocument);
//        提交
        httpSolrServer.commit();
    }

    @Test
    public void delete() throws IOException, SolrServerException {
//        根据id删除索引数据

//        根据条件删除（如果是  *:* 就表示全部删除，）
//        创建httpSolrServer，通过它连接solr服务器
        HttpSolrServer httpSolrServer = new HttpSolrServer("http://localhost:8090/solr");
        httpSolrServer.deleteById("1");
//        httpSolrServer.deleteByQuery("name:desc");
        httpSolrServer.commit();
    }

    @Test
    public void search() throws SolrServerException {
//        创建搜索对象
        SolrQuery query = new SolrQuery();
//        设置搜索条件
        query.setQuery("*:*");
//        创建httpSolrServer，用它来连接solr服务器
        HttpSolrServer httpSolrServer = new HttpSolrServer("http://localhost:8090/solr");
//        发起搜索请求
        QueryResponse response = httpSolrServer.query(query);
//        处理搜索结果  这是一个集合
        SolrDocumentList results = response.getResults();
//        getNumFound可以获取结果集的数据条数，因为结果集是个list集合，所以用size也可以获取数量
        System.out.println("搜索到的结果总数："+results.getNumFound());
        for (SolrDocument result : results) {
            System.out.println(result.get("id"));
            System.out.println(result.get("name"));
        }

    }


    @Test
    public void searcht() throws SolrServerException {
        SolrQuery solrQuery = new SolrQuery();
        HttpSolrServer httpSolrServer = new HttpSolrServer("http://localhost:8090/solr");
        solrQuery.setQuery("id:*");
        QueryResponse response = httpSolrServer.query(solrQuery);
        SolrDocumentList results = response.getResults();
        for (SolrDocument result : results) {
            System.out.println(result.get("id"));
        }
    }


    @Test
    public void testt() throws IOException, SolrServerException {
//        insert
        HttpSolrServer httpSolrServer = new HttpSolrServer("http://localhost:8090/solr");
        SolrInputDocument solrInputFields = new SolrInputDocument();
        solrInputFields.addField("id","3");
        solrInputFields.addField("name","Goffy");
        httpSolrServer.add(solrInputFields);
        httpSolrServer.commit();
    }



}
