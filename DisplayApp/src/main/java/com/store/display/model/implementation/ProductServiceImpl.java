package com.store.display.model.implementation;

import com.google.gson.Gson;
import com.store.display.ProductRawDTO;
import com.store.display.model.interfaces.ProductService;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;

@Stateless
public class ProductServiceImpl implements ProductService {

    private static final Logger LOG = Logger.getLogger(ProductServiceImpl.class);

    @Override
    public LinkedList<ProductRawDTO> getProduct(){
        LOG.info("in contracts");
        String URL = "http://localhost:8086/admin/statistics/restProduct";
        try {
            java.net.URL url = new URL(URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new IOException();
            }
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String result = br.readLine().replace("null","\"\"");
            LinkedList<ProductRawDTO> users = new Gson().fromJson(result,LinkedList.class);
            conn.disconnect();
            return users;
        } catch (IOException e) {
            return new LinkedList<ProductRawDTO>();
            //  e.printStackTrace();
        }
    }
}