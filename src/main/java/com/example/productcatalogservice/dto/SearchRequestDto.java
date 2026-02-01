package com.example.productcatalogservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SearchRequestDto {

    private String query;
    private Integer pageNo;
    private Integer pageSize;
    private List<SortParam> sortParams = new ArrayList<>();

}
