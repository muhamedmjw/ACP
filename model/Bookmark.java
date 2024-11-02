package model;

public class Bookmark {
    private int bookmarkId;
    private int userId;
    private int postId;

    // Constructor
    public Bookmark(int bookmarkId, int userId, int postId) {
        this.bookmarkId = bookmarkId;
        this.userId = userId;
        this.postId = postId;
    }

    // Getters and Setters
    public int getBookmarkId() {
        return bookmarkId;
    }

    public void setBookmarkId(int bookmarkId) {
        this.bookmarkId = bookmarkId;
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

    // toString method for easy printing
    @Override
    public String toString() {
        return "Bookmark [BookmarkID=" + bookmarkId + ", UserID=" + userId + ", PostID=" + postId + "]";
    }
}
