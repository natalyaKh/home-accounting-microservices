package crawlerservice.dao;

import com.google.gson.Gson;
import crawlerservice.model.Product;
import crawlerservice.prop.ConfigProps;
import org.elasticsearch.action.admin.indices.refresh.RefreshRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.AbstractQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class QueryDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueryDAO.class);


    private final RestHighLevelClient client;
    private final SearchSourceBuilder sourceBuilder;
    private final ConfigProps props;
    private final Gson gson;

    @Autowired
    public QueryDAO(RestHighLevelClient client, SearchSourceBuilder sourceBuilder,
                    ConfigProps props, Gson gson) {
        this.client = client;
        this.sourceBuilder = sourceBuilder;
        this.props = props;
        this.gson = gson;
    }

    /**
     * @param product
     * @return
     */
    public String indexRequest(final Product product) {

        try {
            final IndexRequest indexRequest = new IndexRequest(props.getIndex().getName())
                    .id(product.getId())
                    .source(XContentType.JSON, "title", product.getTitle(),
                            "category", product.getCategory(),
                            "subcategory", product.getSubcategory(),
                            "rub", product.getRub(),
                            "cop", product.getCop());

            final IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);
            return response.getId();

        } catch (Exception ex) {
            LOGGER.error("The exception was thrown in createIndex method.", ex);
        }

        return null;
    }

    /**
     * @param product
     * @return
     */
    public String updateDocument(Product product) {

        try {
            UpdateRequest request = new UpdateRequest(props.getIndex().getName(), product.getId())
                    .doc(gson.toJson(product), XContentType.JSON);
            UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
            return response.getId();
        } catch (Exception ex) {
            LOGGER.error("The exception was thrown in updateDocument method.", ex);
        }

        return null;
    }

    /**
     * @return
     */
    public List<Product> matchAllQuery() {

        List<Product> result = new ArrayList<>();

        try {
            refreshRequest();
            result = getDocuments(QueryBuilders.matchAllQuery());
        } catch (Exception ex) {
            LOGGER.error("The exception was thrown in matchAllQuery method.", ex);
        }

        return result;
    }

    /**
     * @param query
     * @return
     */
    public List<Product> wildcardQuery(String query) {

        List<Product> result = new ArrayList<>();

        try {
            result = getDocuments(QueryBuilders.queryStringQuery("*" + query.toLowerCase() + "*"));
        } catch (Exception ex) {
            LOGGER.error("The exception was thrown in wildcardQuery method.", ex);
        }

        return result;
    }

    /**
     * @param id
     * @throws IOException
     */
    public void deleteDocument(String id) {
        try {
            final DeleteRequest deleteRequest = new DeleteRequest(props.getIndex().getName(), id);
            client.delete(deleteRequest, RequestOptions.DEFAULT);
        } catch (Exception ex) {
            LOGGER.error("The exception was thrown in deleteDocument method.", ex);
        }
    }

    /**
     * @return
     */
    private SearchRequest getSearchRequest() {
        SearchRequest searchRequest = new SearchRequest(props.getIndex().getName());
        searchRequest.source(sourceBuilder);
        return searchRequest;
    }

    /**
     * @param builder
     * @return
     * @throws IOException
     */
    private List<Product> getDocuments(AbstractQueryBuilder builder) throws IOException {
        List<Product> result = new ArrayList<>();

        sourceBuilder.query(builder);
        SearchRequest searchRequest = getSearchRequest();

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            Product product = gson.fromJson(hit.getSourceAsString(), Product.class);
            product.setId(hit.getId());
            result.add(product);
        }

        return result;
    }

    public void refreshRequest() throws IOException {
        final RefreshRequest refreshRequest = new RefreshRequest(props.getIndex().getName());
        client.indices().refresh(refreshRequest, RequestOptions.DEFAULT);
    }
}


