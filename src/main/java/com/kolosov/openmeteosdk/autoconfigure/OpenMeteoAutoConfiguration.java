package com.kolosov.openmeteosdk.autoconfigure;

import com.kolosov.openmeteosdk.OpenMeteoService;
import com.kolosov.openmeteosdk.api.OpenMeteoAPIClient;
import com.kolosov.openmeteosdk.mapper.OpenMeteoMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@AutoConfiguration
@ConditionalOnClass({RestClient.class, HttpServiceProxyFactory.class})
public class OpenMeteoAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RestClient openMeteoRestClient() {
        return RestClient.create(OpenMeteoAPIClient.OPEN_METEO_API_URL);
    }

    @Bean
    @ConditionalOnMissingBean
    public OpenMeteoAPIClient openMeteoAPIClient(RestClient restClient) {
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory.builder()
                .exchangeAdapter(RestClientAdapter.create(restClient))
                .build();
        return httpServiceProxyFactory.createClient(OpenMeteoAPIClient.class);
    }

    @Bean
    @ConditionalOnMissingBean
    public OpenMeteoMapper openMeteoMapper() {
        return Mappers.getMapper(OpenMeteoMapper.class);
    }

    @Bean
    @ConditionalOnMissingBean
    public OpenMeteoService openMeteoService(OpenMeteoAPIClient client, OpenMeteoMapper mapper) {
        return new OpenMeteoService(client, mapper);
    }
}
