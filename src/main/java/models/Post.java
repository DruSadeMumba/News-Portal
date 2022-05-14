package models;

import java.util.Objects;

public class Post {
    private int id;
    private int userId;
    private String createdBy;
    private String content;
    private long createdat;

    public Post(int userId, String createdBy, String content, long createdat) {
        this.userId = userId;
        this.createdBy = createdBy;
        this.content = content;
        this.createdat = createdat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id == post.id && userId == post.userId && createdat == post.createdat && Objects.equals(createdBy, post.createdBy) && Objects.equals(content, post.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, createdBy, content, createdat);
    }
}
