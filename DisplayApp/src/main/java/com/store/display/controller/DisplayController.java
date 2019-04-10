package com.store.display.controller;

import com.store.display.ProductRawDTO;
import com.store.display.model.interfaces.ProductService;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.*;
import java.util.LinkedList;

@ManagedBean
@SessionScoped
public class DisplayController implements Serializable {

    private static final Logger LOG = Logger.getLogger(DisplayController.class);

    @EJB
    private ProductService productService;

    private LinkedList products;

    public LinkedList getProducts() throws IOException {
        final LinkedList contracts = productService.getProduct();
        return contracts;
    }

    public void setProducts(LinkedList<ProductRawDTO> products) {
        this.products = products;
    }
}