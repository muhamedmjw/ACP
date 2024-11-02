package model;

public class Rating {
    private int ratingId;
    private int userId;
    private int postId;
    private double ratingValue;
    private String review;

    // Constructor
    public Rating(int ratingId, int userId, int postId, double ratingValue, String review) {
        this.ratingId = ratingId;
        this.userId = userId;
        this.postId = postId;
        this.ratingValue = ratingValue;
        this.review = review;
    }

    // Getters and Setters
    public int getRatingId() {
        return ratingId;
    }

    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public double getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(double ratingValue) {
        this.ratingValue = ratingValue;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    // toString method for easy printing
    @Override
    public String toString() {
        return "Rating [RatingID=" + ratingId + ", UserID=" + userId + ", PostID=" + postId 
               + ", RatingValue=" + ratingValue + ", Review=" + review + "]";
    }
}
