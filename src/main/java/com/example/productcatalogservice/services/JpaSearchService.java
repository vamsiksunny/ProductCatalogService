package com.example.productcatalogservice.services;

import com.example.productcatalogservice.dto.SortParam;
import com.example.productcatalogservice.dto.SortType;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JpaSearchService implements ISearchService {

    @Autowired
    private ProductRepo productRepo;

    @Override
    public Page<Product> searchProducts(String query, Integer pageNo, Integer pageSize, List<SortParam> sortParams) {
//        Sort sort = Sort.by("createdAt").descending();
//        Sort sortByPrice = Sort.by("price").ascending().and(sort);

        Sort sort = null;
        if (!sortParams.isEmpty()) {
            if (sortParams.getFirst().getSortType().equals(SortType.ASC)) {
                sort = Sort.by(sortParams.getFirst().getParamName()).ascending();
            } else {
                sort = Sort.by(sortParams.getFirst().getParamName()).descending();
            }
            for (int i = 1; i < sortParams.size(); i++) {
                SortParam sortParam = sortParams.get(i);
                if (sortParam.getSortType().equals(SortType.ASC)) {
                    sort.and(Sort.by(sortParam.getParamName()).ascending());
                } else {
                    sort.and(Sort.by(sortParam.getParamName()).descending());
                }
            }
        }

        return productRepo.findByName(query, PageRequest.of(pageNo, pageSize, sort));
    }
}
