package com.store.display.dto;

import com.store.display.service.ProductService;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class ProductRawDTO {
    private static final Logger log = Logger.getLogger(ProductRawDTO.class);

    private int id;
    private String name;
    private String description;
    private Double price;
    private int weight;
    private int volume;
    private int amount;
    private int minPlayerAmount;
    private int maxPlayerAmount;
    private String imageSource;
    private String uploadFile;
    private String category;
    private String imageFile;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getMinPlayerAmount() {
        return minPlayerAmount;
    }

    public void setMinPlayerAmount(int minPlayerAmount) {
        this.minPlayerAmount = minPlayerAmount;
    }

    public int getMaxPlayerAmount() {
        return maxPlayerAmount;
    }

    public void setMaxPlayerAmount(int maxPlayerAmount) {
        this.maxPlayerAmount = maxPlayerAmount;
    }

    public String getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(String uploadFile) {
        this.uploadFile = uploadFile;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        log.info("======in set file===========");
        log.info(imageFile);
        try {
            File imagesDir = new File(System.getProperty("jboss.server.data.dir"), "images");
            log.info(System.getProperty("jboss.server.data.dir"));
            imagesDir.mkdir();
            ByteArrayInputStream bis = new ByteArrayInputStream(Base64.getDecoder().decode(imageFile));
            BufferedImage bImage2 = ImageIO.read(bis);
            ImageIO.write(bImage2, "png", new File(imagesDir,this.id + ".png"));
            this.imageFile = imagesDir.getAbsolutePath()+"\\" + this.id + ".png";
        }catch (Exception e){
            this.imageFile = "";
            log.info(e.getMessage());
        }
        log.info("=================");

    }
}
