package com.xinzy.java.wan.entity;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;
import com.xinzy.mvvm.lib.util.Collections;

import java.util.ArrayList;
import java.util.List;

/**
 * 文章
 */
@Keep
public class Topic {
    private int id;
    private String author;
    private int chapterId;
    private String chapterName;

    private String desc;
    private String link;
    private String niceDate;

    private String title;
    private String projectLink;

    private int superChapterId;
    private String superChapterName;
    @SerializedName("envelopePic")
    private String cover;

    private int courseId;
    private boolean collect;
    private boolean fresh;
    private long publishTime;
    private int type;
    private int userId;
    private int visible;
    private int zan;

    private List<Tag> tags;

    public int getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public int getChapterId() {
        return chapterId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public String getDesc() {
        return desc;
    }

    public String getLink() {
        return link;
    }

    public String getNiceDate() {
        return niceDate;
    }

    public String getTitle() {
        return title;
    }

    public String getCover() {
        return cover;
    }

    public String getProjectLink() {
        return projectLink;
    }

    public int getSuperChapterId() {
        return superChapterId;
    }

    public String getSuperChapterName() {
        return superChapterName;
    }

    public int getCourseId() {
        return courseId;
    }

    public boolean isCollect() {
        return collect;
    }

    public boolean isFresh() {
        return fresh;
    }

    public boolean isTop() {
        return type == 1;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public int getType() {
        return type;
    }

    public int getUserId() {
        return userId;
    }

    public int getVisible() {
        return visible;
    }

    public int getZan() {
        return zan;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public boolean hasTag() {
        return Collections.isNotEmpty(tags);
    }

    public List<String> getTagTexts() {
        List<String> tags = new ArrayList<>();
        if (hasTag()) {
            this.tags.forEach(item -> tags.add(item.getName()));
        }
        return tags;
    }

    public String getCategory() {
        return superChapterName + " / " + chapterName;
    }
}
