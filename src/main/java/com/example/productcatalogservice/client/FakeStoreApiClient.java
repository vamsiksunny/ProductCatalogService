package com.example.productcatalogservice.client;

import com.example.productcatalogservice.dto.FakeStoreProductDto;
import com.example.productcatalogservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class FakeStoreApiClient {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    public FakeStoreProductDto getProductById(Long id) {
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity =
                requestForEntity(HttpMethod.GET,"https://fakestoreapi.com/products/{id}",
                        null,
                        FakeStoreProductDto.class,
                        id);

        if(validateResponse(fakeStoreProductDtoResponseEntity)) {
            return fakeStoreProductDtoResponseEntity.getBody();
        }

        return null;
    }

    public FakeStoreProductDto[] getAllProducts() {
        ResponseEntity<FakeStoreProductDto[]> fakeStoreProductDtosResponseEntity =
                requestForEntity(HttpMethod.GET,"https://fakestoreapi.com/products",
                        null,
                        FakeStoreProductDto[].class
                );

        if (validate(fakeStoreProductDtosResponseEntity)) {
            return fakeStoreProductDtosResponseEntity.getBody();
        }
        return null;
    }

    public FakeStoreProductDto replaceProduct(Long id, FakeStoreProductDto input) {
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity =
                requestForEntity(HttpMethod.PUT,
                        "https://fakestoreapi.com/products/{id}",
                        input,
                        FakeStoreProductDto.class,
                        id);

        if (validateResponse(fakeStoreProductDtoResponseEntity)) {
            return fakeStoreProductDtoResponseEntity.getBody();
        }
        return null;
    }

    public <T> ResponseEntity<T> requestForEntity(HttpMethod httpMethod, String url, @Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }

    private Boolean validateResponse(ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity) {
        return fakeStoreProductDtoResponseEntity.hasBody() &&
                fakeStoreProductDtoResponseEntity.getStatusCode().
                        equals(HttpStatusCode.valueOf(200));
    }

    private boolean validate(ResponseEntity<FakeStoreProductDto[]> fakeStoreProductDtosResponseEntity) {
        return fakeStoreProductDtosResponseEntity.hasBody() &&
                fakeStoreProductDtosResponseEntity.getStatusCode().
                        equals(HttpStatusCode.valueOf(200));
    }

}
