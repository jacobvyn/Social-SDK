package com.jacob.getsocial.reddit.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class News implements Parcelable {
    public static final int TYPE_WEB = 1000;
    public static final int TYPE_IMAGE = 1001;

    private String mAuthor;
    private String mUrl;
    private int mLikes;
    private final String mThumbnail;
    private final String mTitle;
    private int mComments;
    private int mType = TYPE_IMAGE;

    public News(String author, String url, int comments, int likes, String thumbnail, String title) {
        mAuthor = author;
        mUrl = url;
        mComments = comments;
        mLikes = likes;
        mThumbnail = thumbnail;
        mTitle = title;
        setType();
    }

    protected News(Parcel in) {
        mAuthor = in.readString();
        mUrl = in.readString();
        mLikes = in.readInt();
        mThumbnail = in.readString();
        mTitle = in.readString();
        mComments = in.readInt();
        mType = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mAuthor);
        dest.writeString(mUrl);
        dest.writeInt(mLikes);
        dest.writeString(mThumbnail);
        dest.writeString(mTitle);
        dest.writeInt(mComments);
        dest.writeInt(mType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };

    private void setType() {
        if (mUrl.endsWith(".jpeg") || mUrl.endsWith(".jpg")) {
            mType = TYPE_IMAGE;
        } else {
            mType = TYPE_WEB;
        }
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public int getLikes() {
        return mLikes;
    }

    public void setLikes(int mLikes) {
        this.mLikes = mLikes;
    }

    public int getComments() {
        return mComments;
    }

    public void setComments(int mComments) {
        this.mComments = mComments;
    }

    public int getType() {
        return mType;
    }

    public void setType(int mType) {
        this.mType = mType;
    }

    public String getThumbnail() {
        return mThumbnail;
    }

    public String getTitle() {
        return mTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News news = (News) o;
        return Objects.equals(mAuthor, news.mAuthor) &&
                Objects.equals(mUrl, news.mUrl) &&
                Objects.equals(mThumbnail, news.mThumbnail) &&
                Objects.equals(mTitle, news.mTitle);
    }

    @Override
    public int hashCode() {

        return Objects.hash(mAuthor, mUrl, mThumbnail, mTitle);
    }

    public boolean isImage() {
        return getType() == News.TYPE_IMAGE;
    }

    public boolean isWeb() {
        return getType() == News.TYPE_WEB;
    }
}
