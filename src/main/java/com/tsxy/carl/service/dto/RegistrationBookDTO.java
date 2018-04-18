package com.tsxy.carl.service.dto;


import java.time.Instant;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the RegistrationBook entity.
 */
public class RegistrationBookDTO implements Serializable {

    private Long id;

    private Long userId;

    @Size(max = 50)
    private String userName;

    private Long mobilePhone;

    @Size(max = 18)
    private String idCard;

    private Long deptId;

    @Size(max = 250)
    private String deptName;

    private Long doctorId;

    @Size(max = 50)
    private String doctorName;

    private Long consultId;

    @Size(max = 250)
    private String consultName;

    private Integer consultNo;

    private ZonedDateTime visitDateTime;

    @Size(max = 50)
    private String createdBy;

    private Instant createdDate;

    @Size(max = 50)
    private String lastModifiedBy;

    private Instant lastModifiedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(Long mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public Long getConsultId() {
        return consultId;
    }

    public void setConsultId(Long consultId) {
        this.consultId = consultId;
    }

    public String getConsultName() {
        return consultName;
    }

    public void setConsultName(String consultName) {
        this.consultName = consultName;
    }

    public Integer getConsultNo() {
        return consultNo;
    }

    public void setConsultNo(Integer consultNo) {
        this.consultNo = consultNo;
    }

    public ZonedDateTime getVisitDateTime() {
        return visitDateTime;
    }

    public void setVisitDateTime(ZonedDateTime visitDateTime) {
        this.visitDateTime = visitDateTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RegistrationBookDTO registrationBookDTO = (RegistrationBookDTO) o;
        if(registrationBookDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), registrationBookDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RegistrationBookDTO{" +
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
