package com.ftn.sbnz.kjar.model.level_1;

import java.util.Objects;

public class KeywordInteraction {

    private Long userId;
    private Long movieId;

    private String keyword;

    private InteractionType type;

    private Integer rating; // only for REVIEW

    public enum InteractionType {
        LIKE,
        DISLIKE,
        REVIEW
    }

    public KeywordInteraction() {
    }

    // Constructor for LIKE/DISLIKE
    public KeywordInteraction(Long userId, Long movieId, String keyword, InteractionType type) {
        this.userId = userId;
        this.movieId = movieId;
        this.keyword = keyword;
        this.type = type;
        this.rating = null;
    }

    // Constructor for REVIEW
    public KeywordInteraction(Long userId, Long movieId, String keyword, Integer rating) {
        this.userId = userId;
        this.movieId = movieId;
        this.keyword = keyword;
        this.type = InteractionType.REVIEW;
        this.rating = rating;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public InteractionType getType() {
        return type;
    }

    public void setType(InteractionType type) {
        this.type = type;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "KeywordInteraction{" +
                "userId=" + userId +
                ", movieId=" + movieId +
                ", keyword='" + keyword + '\'' +
                ", type=" + type +
                ", rating=" + rating +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof KeywordInteraction))
            return false;
        KeywordInteraction that = (KeywordInteraction) o;
        return Objects.equals(userId, that.userId)
                && Objects.equals(movieId, that.movieId)
                && Objects.equals(keyword, that.keyword)
                && type == that.type
                && Objects.equals(rating, that.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, movieId, keyword, type, rating);
    }
}