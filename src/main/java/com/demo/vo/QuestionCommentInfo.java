package com.demo.vo;

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
public class QuestionCommentInfo implements Serializable {

    private static final long serialVersionUID = 1567748849212217913L;

    private Long questionId;

    private Long commentId;

    private Integer commentType;

    private String content;

    private String fromUserId;

    private String fromUserName;

    private String fromUserHead;

    private Date createDate;

    private String reader;

    private Boolean isCollect;

    private Boolean isPraise;

    private Long sortReader;

    private Boolean isTop;

    private Date topDate;

    private List<String> images;

    private Integer commentCount;

    private Boolean isComment;

    private Boolean isVip = false;

    private Integer vipLevel;

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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getReader() {
        return reader;
    }

    public void setReader(Long reader) {
        this.sortReader = reader;
        if(reader<=0){
            this.reader = "0";
        }else if(reader>0&&reader<1000){
            this.reader = String.valueOf(reader);
        }else if(reader>=1000&&reader<1100){
            long c = reader/1000;
            this.reader = c+"k";
        }else if(reader>=1100&&reader<10000){
            double c = (double)reader/1000;
            this.reader = String.format("%.1f",c)+"k";
        }else if(reader>=10000&&reader<1000000){
            long c = reader/1000;
            this.reader = c+"k";
        }else if(reader>=1000000&&reader<1100000){
            long c = reader/1000000;
            this.reader = c+"m";
        }else if(reader>=1100000&&reader<1000000000){
            double c = (double)reader/1000000;
            this.reader = String.format("%.1f",c)+"m";
        }else if(reader >= 1000000000){
            long c = reader/1000000;
            this.reader = c+"m";
        }
    }

    public Boolean getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(Boolean collect) {
        isCollect = collect;
    }

    public Boolean getIsPraise() {
        return isPraise;
    }

    public void setIsPraise(Boolean praise) {
        isPraise = praise;
    }

    public Integer getCommentType() {
        return commentType;
    }

    public String getContentNoImg(){
        content = content.replaceAll("<img[^>]*/>", "图片");
        content = content.replaceAll("图片","[图片]");
        return content;
    }

    public void setCommentType(Integer commentType) {
        this.commentType = commentType;
    }

    public Long SortReader() {
        return sortReader;
    }

    public Boolean getIsTop() {
        return isTop;
    }

    public void setIsTop(Boolean top) {
        isTop = top;
    }

    public Date getTopDate() {
        return topDate;
    }

    public void setTopDate(Date topDate) {
        this.topDate = topDate;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Boolean getIsComment() {
        return isComment;
    }

    public void setIsComment(Boolean comment) {
        isComment = comment;
    }

    public Boolean getIsVip() {
        return isVip;
    }

    public void setIsVip(Boolean vip) {
        isVip = vip;
    }

    public Integer getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(Integer vipLevel) {
        this.vipLevel = vipLevel;
    }
}
