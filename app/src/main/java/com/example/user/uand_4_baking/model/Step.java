package com.example.user.uand_4_baking.model;

public class Step {

    private int id;

    private String shortDescription;

    private String description;

    private String videoURL;

    private String thumbnailURL;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public String toString() {
        return "is=" + id + ", short description=" + shortDescription + ", description=" + description + ", videoURL=" + videoURL + ", thumbnailURL=" + thumbnailURL;
    }
}
