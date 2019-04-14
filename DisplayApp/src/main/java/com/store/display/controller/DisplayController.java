package com.store.display.controller;

import com.store.display.dto.ProductRawDTO;
import com.store.display.service.ProductService;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.io.*;
import java.util.LinkedList;

@ManagedBean
@SessionScoped
public class DisplayController implements Serializable, MessageListener {

    public DisplayController(ProductService productService){
        this.productService = productService;
    }
    public DisplayController(){

    }
    @EJB
    private ProductService productService;

    public LinkedList getProducts(){
        return productService.getProduct();
    }

    public void setProducts(LinkedList<ProductRawDTO> products) {
       this.productService.setProducts(products);
    }

    @Override
    public void onMessage(Message message) {
       productService.updateProducts();
    }
}