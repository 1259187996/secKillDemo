package com.yuyu.say.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @author: yangzhizhuang
 * @date: 2019/7/25
 * @description:
 */
public class CommentInfo implements Serializable {

    private static final long serialVersionUID = 1567748849212217913L;

    private Long questionId;

    private Long commentId;

    private Integer commentType;

    private Integer status;

    private Integer auditStatus;

    private Integer fromUserType;

    private String content;

    private List<String> images;

    private String fromUserId;

    private String fromUserName;

    private String fromUserHead;

    private String toUserHead;

    private String toUserName;

    private String toUserId;

    private Integer falseReader;

    private Date createDate;

    private Long reader;

    private Boolean isTop;

    private Date topDate;

    private Boolean essenceStatus;

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Integer getCommentType() {
        return commentType;
    }

    public void setCommentType(Integer commentType) {
        this.commentType = commentType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Integer getFromUserType() {
        return fromUserType;
    }

    public void setFromUserType(Integer fromUserType) {
        this.fromUserType = fromUserType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getFromUserHead() {
        return fromUserHead;
    }

    public void setFromUserHead(String fromUserHead) {
        this.fromUserHead = fromUserHead;
    }

    public String getToUserHead() {
        return toUserHead;
    }

    public void setToUserHead(String toUserHead) {
        this.toUserHead = toUserHead;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public Integer getFalseReader() {
        return falseReader;
    }

    public void setFalseReader(Integer falseReader) {
        this.falseReader = falseReader;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getReader() {
        return reader;
    }

    public void setReader(Long reader) {
        this.reader = reader;
    }

    public Boolean getTop() {
        return isTop;
    }

    public void setTop(Boolean top) {
        isTop = top;
    }

    public Date getTopDate() {
        return topDate;
    }

    public void setTopDate(Date topDate) {
        this.topDate = topDate;
    }

    public Boolean getEssenceStatus() {
        return essenceStatus;
    }

    public void setEssenceStatus(Boolean essenceStatus) {
        this.essenceStatus = essenceStatus;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
