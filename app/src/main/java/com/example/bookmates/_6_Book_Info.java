package com.example.bookmates;

public class _6_Book_Info {
    private String name_of_book;
    private String edition_of_book;
    private String price_of_book;
    private String location_of_book;
    private String number;
    private String branch;
    private String image;
    private String key;
    private String id;

    public String getKey() {
        return key;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setKey(String key) {
        this.key = key;
    }

    _6_Book_Info(){}

    public _6_Book_Info(String name_of_book, String edition_of_book, String branch, String price_of_book, String location_of_book,String number,String image,String key,String id) {
        this.name_of_book = name_of_book;
        this.edition_of_book = edition_of_book;
        this.price_of_book = price_of_book;
        this.branch= branch;
        this.location_of_book = location_of_book;
        this.number= number;
        this.image=image;
        this.key=key;
        this.id=id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNumber() {
        return number;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName_of_book() {
        return name_of_book;
    }

    public void setName_of_book(String name_of_book) {
        this.name_of_book = name_of_book;
    }

    public String getEdition_of_book() {
        return edition_of_book;
    }

    public void setEdition_of_book(String edition_of_book) {
        this.edition_of_book = edition_of_book;
    }

    public String getPrice_of_book() {
        return price_of_book;
    }

    public void setPrice_of_book(String price_of_book) {
        this.price_of_book = price_of_book;
    }

    public String getLocation_of_book() {
        return location_of_book;
    }

    public void setLocation_of_book(String location_of_book) {
        this.location_of_book = location_of_book;
    }
}
