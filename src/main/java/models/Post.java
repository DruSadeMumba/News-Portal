package models;

import java.text.SimpleDateFormat;
import java.util.Objects;

public class Post implements Comparable <Post>{
    private int id;
    private int userId;
    private String createdBy;
    private String content;
    private long createdat;
    public String type;

    //getters
    public int getId() {return id;}
    public int getUserId() {return userId;}
    public String getCreatedBy() {return createdBy;}
    public String getContent() {return content;}
    public long getCreatedat() {return createdat;}
    public String getFormattedCreatedAt() {
        return new SimpleDateFormat("MM/dd/yyyy @ K:mm a").format(createdat);
    }
    public String getType() {return type;}

    //setters
    public void setId(int id) {this.id = id;}
    public void setUserId(int userId) {this.userId = userId;}
    public void setCreatedBy(String createdBy) {this.createdBy = createdBy;}
    public void setContent(String content) {this.content = content;}
    public void setCreatedat(long createdat) {this.createdat = createdat;}
    public void setFormattedCreatedAt() {
        String formattedCreatedAt = new SimpleDateFormat("MM/dd/yyyy @ K:mm a").format(this.createdat);
    }
    public void setType(String type) {this.type = type;}

    public Post(int userId, String createdBy, String content, String type) {
        this.userId = userId;
        this.createdBy = createdBy;
        this.content = content;
        this.createdat = System.currentTimeMillis();
        setFormattedCreatedAt();
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id == post.id &&
                userId == post.userId &&
                createdat == post.createdat &&
                Objects.equals(createdBy, post.createdBy) &&
                Objects.equals(content, post.content) &&
                Objects.equals(type, post.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, createdBy, content, createdat, type);
    }

    public int compareTo(Post postObject) {
        return Long.compare(this.createdat, postObject.createdat);
    }

}
