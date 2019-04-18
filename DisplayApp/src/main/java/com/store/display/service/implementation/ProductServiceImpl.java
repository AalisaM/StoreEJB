package com.store.display.service.implementation;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.store.display.controller.DisplayController;
import com.store.display.dto.ProductRawDTO;
import com.store.display.service.ProductService;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.LinkedList;

@Stateless
public class ProductServiceImpl implements ProductService {
    private static final Logger log = Logger.getLogger(ProductService.class);

    @Inject
    private PushServiceImpl pushServiceImpl;

    private LinkedList<ProductRawDTO> products = new LinkedList();

    private QueueConnection connection;
    private QueueSession session;
    private QueueReceiver receiver;

    @Override
    public LinkedList<ProductRawDTO> getProduct(){
        return products;
    }

    @Override
    public void setProducts(LinkedList<ProductRawDTO> products) {
        this.products = products;
    }

    @Override
    public LinkedList updateProducts(){
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
            LinkedList<LinkedTreeMap> users = new Gson().fromJson(result, LinkedList.class);

            for (LinkedTreeMap product: users){
                ProductRawDTO p = new ProductRawDTO();
                log.info(product.get("imageFile"));
                p.setId((int) Math.floor((Double) product.get("id")));
                p.setImageFile((String) product.get("imageFile"));
                p.setDescription((String) product.get("desciption"));
                p.setPrice((Double) product.get("price"));
                p.setName((String) product.get("name"));
                this.products.add(p);
            }
            conn.disconnect();
            //this.products = users;
            log.info("IN UPDATE");
            log.info(Arrays.toString(users.toArray()));
        } catch (IOException e) {
            this.products = new LinkedList<ProductRawDTO>();
            //  e.printStackTrace();
        }
        pushServiceImpl.reload();
        return this.products;
    }

    @PostConstruct
    public void receive() {
        updateProducts();
        log.info("in receive");
        Hashtable<String, String> props = new Hashtable<String, String>();
        props.put("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        props.put("java.naming.provider.url", "tcp://localhost:61616");
        props.put("queue.js-queue", "my_jms_queue");
        props.put("connectionFactoryNames", "queueCF");
        try {
            Context context = new InitialContext(props);
            QueueConnectionFactory connectionFactory = (QueueConnectionFactory) context.lookup("queueCF");
            Queue queue = (Queue) context.lookup("js-queue");
            connection = connectionFactory.createQueueConnection();
            connection.start();
            session = connection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
            receiver = session.createReceiver(queue);
            receiver.setMessageListener(new DisplayController(this));
            System.out.println("receive");
            log.info("in trye receive");

        } catch (NamingException e) {
            log.info(e.toString());
        } catch (JMSException e) {
            log.info(e.toString());
        }
    }

    @PreDestroy
    public void destroyReceiver() {
        try {
            receiver.close();
            session.close();
            connection.close();
        } catch (JMSException e) {
            log.info(e.toString());
        }
    }


}