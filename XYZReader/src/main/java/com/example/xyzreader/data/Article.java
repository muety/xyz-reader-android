package com.example.xyzreader.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Article implements Parcelable {
    private long id;
    private String title, author, thumbUrl, photoUrl, body;
    private Date publishedDate;

    public Article() {
    }

    public Article(long id, String title, String author, String thumbUrl, String photoUrl, String body, Date publishedDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.thumbUrl = thumbUrl;
        this.photoUrl = photoUrl;
        this.body = body;
        this.publishedDate = publishedDate;
    }

    public Article(Parcel in) {
        id = in.readLong();
        title = in.readString();
        author = in.readString();
        thumbUrl = in.readString();
        photoUrl = in.readString();
        body = in.readString();
        publishedDate = new Date(in.readLong());
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    @Override
    public int describeContents() {
        return (int) id;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(title);
        parcel.writeString(author);
        parcel.writeString(thumbUrl);
        parcel.writeString(photoUrl);
        parcel.writeString(body);
        parcel.writeLong(publishedDate.getTime());
    }
}
