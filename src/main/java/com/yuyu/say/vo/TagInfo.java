package com.yuyu.say.vo;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA
 *
 * @author: yangzhizhuang
 * @date: 2019/7/25
 * @description:
 */
public class TagInfo implements Serializable {

    private static final long serialVersionUID = 7646261173145741000L;


    private Long id;

    private Long questionId;

    private String tagName;

    private String tagIcon;

    private Integer sort;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagIcon() {
        return tagIcon;
    }

    public void setTagIcon(String tagIcon) {
        this.tagIcon = tagIcon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagInfo tagInfo = (TagInfo) o;
        return Objects.equals(id, tagInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Integer sort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }
}
