package com.tsxy.carl.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * 评论回复
 * @author Carl Wang
 */
@ApiModel(description = "评论回复 @author Carl Wang")
@Entity
@Table(name = "content_comment_reply")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ContentCommentReply implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
     * 回复者Id
     */
    @NotNull
    @ApiModelProperty(value = "回复者Id", required = true)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * 回复者姓名
     */
    @Size(max = 50)
    @ApiModelProperty(value = "回复者姓名")
    @Column(name = "user_name", length = 50)
    private String userName;

    /**
     * 回复内容
     */
    @NotNull
    @Size(max = 2048)
    @ApiModelProperty(value = "回复内容", required = true)
    @Column(name = "content", length = 2048, nullable = false)
    private String content;

    @Size(max = 50)
    @Column(name = "created_by", length = 50)
    private String createdBy;

    @Column(name = "created_date")
    private Instant createdDate;

    @Size(max = 50)
    @Column(name = "last_modified_by", length = 50)
    private String lastModifiedBy;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @ManyToOne
    private ContentComment contentComment;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getContentId() {
        return contentId;
    }

    public ContentCommentReply contentId(Long contentId) {
        this.contentId = contentId;
        return this;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public String getContentName() {
        return contentName;
    }

    public ContentCommentReply contentName(String contentName) {
        this.contentName = contentName;
        return this;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public Long getUserId() {
        return userId;
    }

    public ContentCommentReply userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public ContentCommentReply userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public ContentCommentReply content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public ContentCommentReply createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public ContentCommentReply createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public ContentCommentReply lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public ContentCommentReply lastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public ContentComment getContentComment() {
        return contentComment;
    }

    public ContentCommentReply contentComment(ContentComment contentComment) {
        this.contentComment = contentComment;
        return this;
    }

    public void setContentComment(ContentComment contentComment) {
        this.contentComment = contentComment;
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
        ContentCommentReply contentCommentReply = (ContentCommentReply) o;
        if (contentCommentReply.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contentCommentReply.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContentCommentReply{" +
            "id=" + getId() +
            ", contentId='" + getContentId() + "'" +
            ", contentName='" + getContentName() + "'" +
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
