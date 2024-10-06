package com.fraddy.goldenbanana.dto.response.brand;


import lombok.Data;

@Data
public class BrandSearchResponse {
    private Long id;
    private String name;
    private String code;
    private String description;
}
