package com.store.display;

public class ProductRawDTO {
    private int id;
    private String name;
    private String description;
    private int price;
    private int weight;
    private int volume;
    private int amount;
    private int minPlayerAmount;
    private int maxPlayerAmount;
    private String imageSource;
    private String uploadFile;
    private String category;


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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
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
}
