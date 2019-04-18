package com.example.service.impl;

import com.example.common.ResultEnums;
import com.example.common.ResultResponse;
import com.example.dto.ProductCategoryDto;
import com.example.dto.ProductInfoDto;
import com.example.entity.ProductCategory;
import com.example.entity.ProductInfo;
import com.example.repository.ProductCategoryRepository;
import com.example.repository.ProductInfoRepository;
import com.example.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Override
    public ResultResponse queryList() {
        List<ProductCategory> all = productCategoryRepository.findAll();
        //将all转换成dto
        List<ProductCategoryDto> productCategoryDtoList
                = all.stream().map(productCategory ->
                ProductCategoryDto.build(productCategory))
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(all)){
            return ResultResponse.fail();
        }

        List<Integer> typeList =
                productCategoryDtoList.stream().map(productCategoryDto ->
                        productCategoryDto.getCategoryType())
                        .collect(Collectors.toList());
        //根据typeList查询商品列表
        List<ProductInfo> productInfoList
                = productInfoRepository.findByProductStatusAndCategoryTypeIn(ResultEnums.PRODUCT_UP.getCode(), typeList);

        //对productCategoryDtoList集合进行遍历 取出每个商品的类目编号 设置到对应的目录中
        //将productInfo设置到foods中
        //过滤： 不同的type进行不同的封装
        //将productInfo转换成dto
        List<ProductCategoryDto> productCategoryDtos = productCategoryDtoList.parallelStream().map(productCategoryDto -> {
            productCategoryDto.setProductInfoDtoList(productInfoList.stream()
                    .filter(productInfo -> productInfo.getCategoryType() == productCategoryDto.getCategoryType())
                    .map(productInfo -> ProductInfoDto.build(productInfo)).collect(Collectors.toList()));
            return productCategoryDto;
        }).collect(Collectors.toList());
        return ResultResponse.success(productCategoryDtos);
    }

}
