package com.tsxy.carl.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * 评论
 * @author Carl Wang
 */
@ApiModel(description = "评论 @author Carl Wang")
@Entity
@Table(name = "content_comment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ContentComment extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 内容类型
     */
    @Size(max = 50)
    @ApiModelProperty(value = "内容类型")
    @Column(name = "content_type", length = 50)
    private String contentType;

    /**
     * 内容Id
     */
    @ApiModelProperty(value = "内容Id")
    @Column(name = "content_id")
    private Long contentId;

    /**
     * 内容名
     */
    @Size(max = 250)
    @ApiModelProperty(value = "内容名")
    @Column(name = "content_name", length = 250)
    private String contentName;

    /**
     * 楼层
     */
    @ApiModelProperty(value = "楼层")
    @Column(name = "floor_number")
    private Integer floorNumber;

    /**
     * 评论者Id
     */
    @NotNull
    @ApiModelProperty(value = "评论者Id", required = true)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * 评论者姓名
     */
    @Size(max = 50)
    @ApiModelProperty(value = "评论者姓名")
    @Column(name = "user_name", length = 50)
    private String userName;

    /**
     * 评论内容
     */
    @NotNull
    @Size(max = 2048)
    @ApiModelProperty(value = "评论内容", required = true)
    @Column(name = "content", length = 2048, nullable = false)
    private String content;

    @OneToMany(mappedBy = "contentComment")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ContentCommentReply> replys = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContentType() {
        return contentType;
    }

    public ContentComment contentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getContentId() {
        return contentId;
    }

    public ContentComment contentId(Long contentId) {
        this.contentId = contentId;
        return this;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public String getContentName() {
        return contentName;
    }

    public ContentComment contentName(String contentName) {
        this.contentName = contentName;
        return this;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public Integer getFloorNumber() {
        return floorNumber;
    }

    public ContentComment floorNumber(Integer floorNumber) {
        this.floorNumber = floorNumber;
        return this;
    }

    public void setFloorNumber(Integer floorNumber) {
        this.floorNumber = floorNumber;
    }

    public Long getUserId() {
        return userId;
    }

    public ContentComment userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public ContentComment userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public ContentComment content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<ContentCommentReply> getReplys() {
        return replys;
    }

    public ContentComment replys(Set<ContentCommentReply> contentCommentReplies) {
        this.replys = contentCommentReplies;
        return this;
    }

    public ContentComment addReplys(ContentCommentReply contentCommentReply) {
        this.replys.add(contentCommentReply);
        contentCommentReply.setContentComment(this);
        return this;
    }

    public ContentComment removeReplys(ContentCommentReply contentCommentReply) {
        this.replys.remove(contentCommentReply);
        contentCommentReply.setContentComment(null);
        return this;
    }

    public void setReplys(Set<ContentCommentReply> contentCommentReplies) {
        this.replys = contentCommentReplies;
    }

    public ContentComment createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public ContentComment createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public ContentComment lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public ContentComment lastModifiedDate(Instant lastModifiedDate) {
        this.setLastModifiedDate(lastModifiedDate);
        return this;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ContentComment contentComment = (ContentComment) o;
        if (contentComment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contentComment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContentComment{" +
            "id=" + getId() +
            ", contentType='" + getContentType() + "'" +
            ", contentId='" + getContentId() + "'" +
            ", contentName='" + getContentName() + "'" +
            ", floorNumber='" + getFloorNumber() + "'" +
            ", userId='" + getUserId() + "'" +
            ", userName='" + getUserName() + "'" +
            ", content='" + getContent() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
