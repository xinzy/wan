package com.xinzy.java.wan.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Keep;

import java.util.List;

@Keep
public class Chapter implements Parcelable {

    private int id;
    private String name;
    private int order;
    private int parentChapterId;
    private int visible;
    private boolean userControlSetTop;
    private List<Chapter> children;

    public Chapter() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getOrder() {
        return order;
    }

    public int getParentChapterId() {
        return parentChapterId;
    }

    public int getVisible() {
        return visible;
    }

    public boolean isUserControlSetTop() {
        return userControlSetTop;
    }

    public List<Chapter> getChildren() {
        return children;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.order);
        dest.writeInt(this.parentChapterId);
        dest.writeInt(this.visible);
        dest.writeByte(this.userControlSetTop ? (byte) 1 : (byte) 0);
        dest.writeTypedList(this.children);
    }

    protected Chapter(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.order = in.readInt();
        this.parentChapterId = in.readInt();
        this.visible = in.readInt();
        this.userControlSetTop = in.readByte() != 0;
        this.children = in.createTypedArrayList(Chapter.CREATOR);
    }

    public static final Creator<Chapter> CREATOR = new Creator<Chapter>() {
        @Override
        public Chapter createFromParcel(Parcel source) {
            return new Chapter(source);
        }

        @Override
        public Chapter[] newArray(int size) {
            return new Chapter[size];
        }
    };
}
