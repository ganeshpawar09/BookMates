package com.example.bookmates;

public class _8_Flat_info {
     private String preferred;
     private String member_required;
     private String rent;
     private String bhk;
     private String furnished;
     private String location;
     private String number;
     private String key;
     private String id;

     public String getKey() {
          return key;
     }

     public void setKey(String key) {
          this.key = key;
     }

     public String getImage() {
          return image;
     }

     public void setImage(String image) {
          this.image = image;
     }

     private String image;
     public _8_Flat_info(){}

     public String getId() {
          return id;
     }

     public void setId(String id) {
          this.id = id;
     }

     public _8_Flat_info(String preferred, String member_required, String rent, String bhk, String furnished, String location, String number, String image, String key, String id) {
          this.preferred = preferred;
          this.member_required = member_required;
          this.rent = rent;
          this.bhk = bhk;
          this.furnished = furnished;
          this.location = location;
          this.number = number;
          this.image=image;
          this.key=key;
          this.id=id;
     }


     public String getPreferred() {
          return preferred;
     }

     public void setPreferred(String preferred) {
          this.preferred = preferred;
     }

     public String getMember_required() {
          return member_required;
     }

     public void setMember_required(String member_required) {
          this.member_required = member_required;
     }

     public String getRent() {
          return rent;
     }

     public void setRent(String rent) {
          this.rent = rent;
     }

     public String getBhk() {
          return bhk;
     }

     public void setBhk(String bhk) {
          this.bhk = bhk;
     }

     public String getFurnished() {
          return furnished;
     }

     public void setFurnished(String furnished) {
          this.furnished = furnished;
     }

     public String getLocation() {
          return location;
     }

     public void setLocation(String location) {
          this.location = location;
     }

     public String getNumber() {
          return number;
     }

     public void setNumber(String number) {
          this.number = number;
     }
}