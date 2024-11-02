package model;
public class Post {
    private int postId;
    private int userId;
    private String listingType; // e.g., for-sale or for-rent
    private String type; // e.g., apartment, house, etc.
    private String title;
    private String description;
    private String country;
    private String city;
    private String address;
    private double price;
    private int bedrooms;
    private int bathrooms;
    private double area;
    private String status; // e.g., available, sold, etc.
    private String ownerContactInfo;

    // Constructor
    public Post(int postId, int userId, String listingType, String type, String title, String description,
                String country, String city, String address, double price, int bedrooms, int bathrooms,
                double area, String status, String ownerContactInfo) {
        this.postId = postId;
        this.userId = userId;
        this.listingType = listingType;
        this.type = type;
        this.title = title;
        this.description = description;
        this.country = country;
        this.city = city;
        this.address = address;
        this.price = price;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.area = area;
        this.status = status;
        this.ownerContactInfo = ownerContactInfo;
    }

    // Getters and Setters
    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getListingType() {
        return listingType;
    }

    public void setListingType(String listingType) {
        this.listingType = listingType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(int bedrooms) {
        this.bedrooms = bedrooms;
    }

    public int getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(int bathrooms) {
        this.bathrooms = bathrooms;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOwnerContactInfo() {
        return ownerContactInfo;
    }

    public void setOwnerContactInfo(String ownerContactInfo) {
        this.ownerContactInfo = ownerContactInfo;
    }

    // toString method for easy printing
    @Override
    public String toString() {
        return "Post [ID=" + postId + ", UserID=" + userId + ", ListingType=" + listingType + ", Type=" + type 
               + ", Title=" + title + ", Description=" + description + ", Location=" + address + ", " + city 
               + ", " + country + ", Price=" + price + ", Bedrooms=" + bedrooms + ", Bathrooms=" + bathrooms 
               + ", Area=" + area + " sq.ft., Status=" + status + ", Contact=" + ownerContactInfo + "]";
    }
}
