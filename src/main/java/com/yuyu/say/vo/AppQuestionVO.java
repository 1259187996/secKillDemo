package com.yuyu.say.vo;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @author: yangzhizhuang
 * @date: 2019/8/5
 * @description:
 */
public class AppQuestionVO implements Serializable {

    private static final long serialVersionUID = 6068822021607347847L;

    private Long questionId;

    private String userId;

    private String userName;

    private String userHead;

    private String content;

    private Integer answerNumber;

    @JSONField(serialize=false)
    private String reader;

    private Long sortReader;

    private Boolean hotStatus;

    private Boolean newStatus;

    private Integer auditStatus;

    private Date createDate;

    private List<TagInfo> tags;

    private QuestionCommentInfo commentInfo;

    private Boolean isTop;

    private Date topDate;

    private Date sortDate;

    private Long recommendId;

    private String title;

    private Long topicId;

    private Integer dayReader;

    private Integer weekReader;

    private Integer totalReader;

    private Boolean redPoint;

    private Date lastCommentTime;

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public String getContent() {
        if(recommendId!=null&&topicId!=null){
            if(topicId.compareTo(recommendId)==0){
                if(!StringUtils.isBlank(title)){
                    return title;
                }
            }
        }
        return content;
    }

    public String getContentNoImg(){
        if(recommendId!=null&&topicId!=null){
            if(topicId.compareTo(recommendId)==0){
                if(!StringUtils.isBlank(title)){
                    return title;
                }
            }
        }
        content = content.replaceAll("<img[^>]*/>", "图片");
//        content = HtmlUtil.getText(content).replaceAll(" ","");
        content = content.replaceAll("图片","[图片]");
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getAnswerNumber() {
        return answerNumber;
    }

    public void setAnswerNumber(Integer answerNumber) {
        this.answerNumber = answerNumber;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public List<TagInfo> getTags() {
        return tags;
    }

    public void setTags(List<TagInfo> tags) {
        this.tags = tags;
    }

    public QuestionCommentInfo getCommentInfo() {
        return commentInfo;
    }

    public void setCommentInfo(QuestionCommentInfo commentInfo) {
        this.commentInfo = commentInfo;
    }

    public String getReader() {
        if(reader==null){
            readerStr(this.sortReader);
        }
        return reader;
    }

    public void setReader(Long reader) {
        this.sortReader = reader;
        this.readerStr(reader);
    }

    public void readerStr(long reader){
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

    public Boolean getHotStatus() {
        return hotStatus;
    }

    public void setHotStatus(Boolean hotStatus) {
        this.hotStatus = hotStatus;
    }

    public Boolean getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(Boolean newStatus) {
        this.newStatus = newStatus;
    }

    public Long SortReader() {
        return sortReader;
    }

    public Long getSortReader() {
        return sortReader;
    }

    public void setSortReader(Long sortReader) {
        this.sortReader = sortReader;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Boolean isTop() {
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

    public Date sortDate() {
        return sortDate;
    }

    public void setSortDate(Date sortDate) {
        this.sortDate = sortDate;
    }

    public void setRecommendId(Long recommendId) {
        this.recommendId = recommendId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Integer getDayReader() {
        return dayReader;
    }

    public void setDayReader(Integer dayReader) {
        this.dayReader = dayReader;
    }

    public Integer getWeekReader() {
        return weekReader;
    }

    public void setWeekReader(Integer weekReader) {
        this.weekReader = weekReader;
    }

    public Integer getTotalReader() {
        return totalReader;
    }

    public void setTotalReader(Integer totalReader) {
        this.totalReader = totalReader;
    }

    public Boolean getRedPoint() {
        return redPoint;
    }

    public void setRedPoint(Boolean redPoint) {
        this.redPoint = redPoint;
    }

    public Date getLastCommentTime() {
        return lastCommentTime;
    }

    public void setLastCommentTime(Date lastCommentTime) {
        this.lastCommentTime = lastCommentTime;
    }
}
