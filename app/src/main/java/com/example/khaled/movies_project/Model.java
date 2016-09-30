package com.example.khaled.movies_project;

import java.util.ArrayList;

/**
 * Created by khaled on 20/09/2016.
 */
public class Model {


    private String poster;
    private String overview;
    private String orignal_title;
    private String relase_date;
    private double vote_average;
    private ArrayList<String>review;

    private String id;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getReview() {
        return review;
    }

    public void setReview(ArrayList<String> review) {
        this.review = review;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOrignal_title() {
        return orignal_title;
    }

    public void setOrignal_title(String orignal_title) {
        this.orignal_title = orignal_title;
    }

    public String getRelase_date() {
        return relase_date;
    }

    public void setRelase_date(String relase_date) {
        this.relase_date = relase_date;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }
}




