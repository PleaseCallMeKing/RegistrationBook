package com.tsxy.carl.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * 预约挂号
 * @author Carl Wang
 */
@ApiModel(description = "预约挂号 @author Carl Wang")
@Entity
@Table(name = "registration_book")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RegistrationBook implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 预约者Id
     */
    @ApiModelProperty(value = "预约者Id")
    @Column(name = "user_id")
    private Long userId;

    /**
     * 预约者姓名
     */
    @Size(max = 50)
    @ApiModelProperty(value = "预约者姓名")
    @Column(name = "user_name", length = 50)
    private String userName;

    /**
     * 预约者手机号
     */
    @ApiModelProperty(value = "预约者手机号")
    @Column(name = "mobile_phone")
    private Long mobilePhone;

    /**
     * 大陆身份证号
     */
    @Size(max = 18)
    @ApiModelProperty(value = "大陆身份证号")
    @Column(name = "id_card", length = 18)
    private String idCard;

    /**
     * 预约科室id
     */
    @ApiModelProperty(value = "预约科室id")
    @Column(name = "dept_id")
    private Long deptId;

    /**
     * 预约科室名称
     */
    @Size(max = 250)
    @ApiModelProperty(value = "预约科室名称")
    @Column(name = "dept_name", length = 250)
    private String deptName;

    /**
     * 预约医生id
     */
    @ApiModelProperty(value = "预约医生id")
    @Column(name = "doctor_id")
    private Long doctorId;

    /**
     * 预约医生姓名
     */
    @Size(max = 50)
    @ApiModelProperty(value = "预约医生姓名")
    @Column(name = "doctor_name", length = 50)
    private String doctorName;

    /**
     * 诊室Id
     */
    @ApiModelProperty(value = "诊室Id")
    @Column(name = "consult_id")
    private Long consultId;

    /**
     * 诊室名称
     */
    @Size(max = 250)
    @ApiModelProperty(value = "诊室名称")
    @Column(name = "consult_name", length = 250)
    private String consultName;

    /**
     * 诊室编号
     */
    @ApiModelProperty(value = "诊室编号")
    @Column(name = "consult_no")
    private Integer consultNo;

    /**
     * 就诊时间
     */
    @ApiModelProperty(value = "就诊时间")
    @Column(name = "visit_date_time")
    private ZonedDateTime visitDateTime;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public RegistrationBook userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public RegistrationBook userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getMobilePhone() {
        return mobilePhone;
    }

    public RegistrationBook mobilePhone(Long mobilePhone) {
        this.mobilePhone = mobilePhone;
        return this;
    }

    public void setMobilePhone(Long mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getIdCard() {
        return idCard;
    }

    public RegistrationBook idCard(String idCard) {
        this.idCard = idCard;
        return this;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Long getDeptId() {
        return deptId;
    }

    public RegistrationBook deptId(Long deptId) {
        this.deptId = deptId;
        return this;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public RegistrationBook deptName(String deptName) {
        this.deptName = deptName;
        return this;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public RegistrationBook doctorId(Long doctorId) {
        this.doctorId = doctorId;
        return this;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public RegistrationBook doctorName(String doctorName) {
        this.doctorName = doctorName;
        return this;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public Long getConsultId() {
        return consultId;
    }

    public RegistrationBook consultId(Long consultId) {
        this.consultId = consultId;
        return this;
    }

    public void setConsultId(Long consultId) {
        this.consultId = consultId;
    }

    public String getConsultName() {
        return consultName;
    }

    public RegistrationBook consultName(String consultName) {
        this.consultName = consultName;
        return this;
    }

    public void setConsultName(String consultName) {
        this.consultName = consultName;
    }

    public Integer getConsultNo() {
        return consultNo;
    }

    public RegistrationBook consultNo(Integer consultNo) {
        this.consultNo = consultNo;
        return this;
    }

    public void setConsultNo(Integer consultNo) {
        this.consultNo = consultNo;
    }

    public ZonedDateTime getVisitDateTime() {
        return visitDateTime;
    }

    public RegistrationBook visitDateTime(ZonedDateTime visitDateTime) {
        this.visitDateTime = visitDateTime;
        return this;
    }

    public void setVisitDateTime(ZonedDateTime visitDateTime) {
        this.visitDateTime = visitDateTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public RegistrationBook createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public RegistrationBook createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public RegistrationBook lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public RegistrationBook lastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
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
        RegistrationBook registrationBook = (RegistrationBook) o;
        if (registrationBook.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), registrationBook.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RegistrationBook{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", userName='" + getUserName() + "'" +
            ", mobilePhone='" + getMobilePhone() + "'" +
            ", idCard='" + getIdCard() + "'" +
            ", deptId='" + getDeptId() + "'" +
            ", deptName='" + getDeptName() + "'" +
            ", doctorId='" + getDoctorId() + "'" +
            ", doctorName='" + getDoctorName() + "'" +
            ", consultId='" + getConsultId() + "'" +
            ", consultName='" + getConsultName() + "'" +
            ", consultNo='" + getConsultNo() + "'" +
            ", visitDateTime='" + getVisitDateTime() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
