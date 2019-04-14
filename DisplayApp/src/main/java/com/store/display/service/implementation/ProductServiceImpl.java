package com.store.display.service.implementation;

import com.google.gson.Gson;
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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Hashtable;
import java.util.LinkedList;

@Stateless
public class ProductServiceImpl implements ProductService {

    @Inject
    private PushServiceImpl pushServiceImpl;

    private static final Logger LOG = Logger.getLogger(ProductServiceImpl.class);
    private LinkedList products;

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
            this.products = users;
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
        Hashtable<String, String> props = new Hashtable<String, String>();
        props.put("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        props.put("java.naming.provider.url", "tcp://localhost:61616");
        props.put("queue.js-queue", "my_jms_queue");
        props.put("connectionFactoryNam0es", "queueCF");
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

        } catch (NamingException e) {
            System.out.println(e.toString());
        } catch (JMSException e) {
            System.out.println(e.toString());
        }
    }

    @PreDestroy
    public void destroyReceiver() {
        try {
            receiver.close();
            session.close();
            connection.close();
        } catch (JMSException e) {
            System.out.println(e.toString());
        }
    }

}