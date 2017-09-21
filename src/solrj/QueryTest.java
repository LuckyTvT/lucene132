package solrj;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class QueryTest {

    @Test
    public void queryT1() throws SolrServerException {
        HttpSolrServer httpSolrServer = new HttpSolrServer("http://localhost:8090/solr");
//        创建一个条件查询对象
        SolrQuery solrQuery = new SolrQuery();
//        添加查询条件
        solrQuery.setQuery("台灯");
//        添加过滤条件
        solrQuery.setFilterQueries("product_name:台灯");
//        数字可以设置价格区间   注意这里TO 两个字母必须大写
        solrQuery.setFilterQueries("product_price:{5 TO 20}");
//        排序条件  根据第一个参数来排序， 第二个参数设置升序或降序
        solrQuery.setSort("product_price", SolrQuery.ORDER.asc);
//        分页处理




//        沙发撒
        solrQuery.setStart(0);
        solrQuery.setRows(10);
//        结果中域的列表
        solrQuery.setFields("id","product_name","product_price","product_catalog_name","product_picture");
//        设置默认搜索域
        solrQuery.set("df","product_keywords");
//        设置高亮显示
        solrQuery.setHighlight(true);
//        设置高亮显示的域
        solrQuery.addHighlightField("product_name");
//        设置高亮的前后缀
        solrQuery.setHighlightSimplePre("<span style='color=red'>");
        solrQuery.setHighlightSimplePost("</post>");
//        执行查询
        QueryResponse response = httpSolrServer.query(solrQuery);
//        获取结果集
        SolrDocumentList results = response.getResults();
//        获取全部结果集的个数
        System.out.println("查询到的结果条数："+results.getNumFound());
//        处理结果集
        for (SolrDocument result : results) {
            System.out.println(result.get("id"));
//            取高亮显示
            String productName = "";
//            获取高亮的map   格式是这样的
//            highlighting：Map<K(id):V(Map<K(域名):V(高亮内容，是个数组)>)>
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
            List<String> list = highlighting.get(result.get("id")).get(result.get("product_name"));
            if(list!=null){
                productName = list.get(0);
            }else{
                productName = (String) result.get("product_name");
            }
            System.out.println(productName);
            System.out.println(result.get("product_price"));
            System.out.println(result.get("product_catalog_name"));
            System.out.println(result.get("product_picture"));

        }


    }







}
