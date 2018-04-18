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
 * 诊室
 * @author Carl Wang
 */
@ApiModel(description = "诊室 @author Carl Wang")
@Entity
@Table(name = "consult_room")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ConsultRoom implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 诊室名称
     */
    @Size(max = 250)
    @ApiModelProperty(value = "诊室名称")
    @Column(name = "consult_room_name", length = 250)
    private String consultRoomName;

    /**
     * 诊室编号
     */
    @ApiModelProperty(value = "诊室编号")
    @Column(name = "consult_room_no")
    private Integer consultRoomNo;

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
    private ThirdLevelDepartment dept;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConsultRoomName() {
        return consultRoomName;
    }

    public ConsultRoom consultRoomName(String consultRoomName) {
        this.consultRoomName = consultRoomName;
        return this;
    }

    public void setConsultRoomName(String consultRoomName) {
        this.consultRoomName = consultRoomName;
    }

    public Integer getConsultRoomNo() {
        return consultRoomNo;
    }

    public ConsultRoom consultRoomNo(Integer consultRoomNo) {
        this.consultRoomNo = consultRoomNo;
        return this;
    }

    public void setConsultRoomNo(Integer consultRoomNo) {
        this.consultRoomNo = consultRoomNo;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public ConsultRoom createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public ConsultRoom createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public ConsultRoom lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public ConsultRoom lastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public ThirdLevelDepartment getDept() {
        return dept;
    }

    public ConsultRoom dept(ThirdLevelDepartment thirdLevelDepartment) {
        this.dept = thirdLevelDepartment;
        return this;
    }

    public void setDept(ThirdLevelDepartment thirdLevelDepartment) {
        this.dept = thirdLevelDepartment;
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
        ConsultRoom consultRoom = (ConsultRoom) o;
        if (consultRoom.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), consultRoom.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConsultRoom{" +
            "id=" + getId() +
            ", consultRoomName='" + getConsultRoomName() + "'" +
            ", consultRoomNo='" + getConsultRoomNo() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
