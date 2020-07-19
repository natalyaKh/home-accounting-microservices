package crawlerservice.prop;
/*
 * Created by hakdogan on 28/11/2017
 */

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.TypeVariable;

@Component
@ConfigurationProperties("crawler")
@Getter
@Setter
public class ConfigProps {

    @NestedConfigurationProperty
    private Clients clients = new Clients();

    @NestedConfigurationProperty
    private ElasticContainer elastic = new ElasticContainer();

    @NestedConfigurationProperty
    private Index index = new Index();


}
