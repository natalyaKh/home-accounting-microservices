package crawlerservice.util;

/*
 * Created by hakdogan on 9.05.2018
 */


import crawlerservice.dao.QueryDAO;
import crawlerservice.prop.ConfigProps;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
//@Profile({"production", "docker", "test"})
public class IndexConfigurator {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexConfigurator.class);

    private final RestHighLevelClient client;
    private final ConfigProps props;

    public IndexConfigurator(final RestHighLevelClient client, final ConfigProps props) {
        this.client = client;
        this.props = props;
    }

    @PostConstruct
    private void createIndexWithMapping() {

        try {

            final GetIndexRequest request = new GetIndexRequest(props.getIndex().getName());
            final boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);

            if (!exists) {

                final CreateIndexRequest indexRequest = new CreateIndexRequest(props.getIndex().getName());
                indexRequest.settings(Settings.builder()
                        .put("index.number_of_shards", props.getIndex().getShard())
                        .put("index.number_of_replicas", props.getIndex().getReplica())
                );

                final CreateIndexResponse createIndexResponse = client.indices().create(indexRequest, RequestOptions.DEFAULT);
                if (createIndexResponse.isAcknowledged() && createIndexResponse.isShardsAcknowledged()) {
                    LOGGER.info("{} index created successfully", props.getIndex().getName());
                } else {
                    LOGGER.debug("Failed to create {} index", props.getIndex().getName());
                }

                final PutMappingRequest mappingRequest = new PutMappingRequest(props.getIndex().getName());
                final XContentBuilder builder = XContentFactory.jsonBuilder();

                builder.startObject();
                {
                    builder.startObject("properties");
                    {
                        builder.startObject("id");
                        {
                            builder.field("type", "text");
                        }
                        builder.endObject();

                        builder.startObject("title");
                        {
                            builder.field("type", "text");
                        }
                        builder.endObject();

                        builder.startObject("category");
                        {
                            builder.field("type", "text");
                        }
                        builder.endObject();

                        builder.startObject("subcategory");
                        {
                            builder.field("type", "text");
                        }
                        builder.endObject();

                        builder.startObject("rub");
                        {
                            builder.field("type", "long");
                        }
                        builder.endObject();

                        builder.startObject("cop");
                        {
                            builder.field("type", "long");
                        }
                        builder.endObject();
                    }
                    builder.endObject();
                }
                builder.endObject();
                mappingRequest.source(builder);
                final AcknowledgedResponse putMappingResponse = client.indices().putMapping(mappingRequest, RequestOptions.DEFAULT);

                if (putMappingResponse.isAcknowledged()) {
                    LOGGER.info("Mapping of {} was successfully created", props.getIndex().getName());
                } else {
                    LOGGER.debug("Creating mapping of {} failed", props.getIndex().getName());
                }
            }
        } catch (Exception ex) {
            LOGGER.error("An exception was thrown in createIndexWithMapping method.", ex);
        }
    }
}
