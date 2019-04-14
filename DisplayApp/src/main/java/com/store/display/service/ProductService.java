package com.store.display.service;

import com.store.display.dto.ProductRawDTO;

import java.util.LinkedList;

public interface ProductService {

    LinkedList getProduct();
    void setProducts(LinkedList<ProductRawDTO> products);

    LinkedList updateProducts();
}
