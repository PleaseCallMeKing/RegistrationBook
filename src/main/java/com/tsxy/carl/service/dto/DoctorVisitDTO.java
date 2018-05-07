package com.tsxy.carl.service.dto;


import java.time.Instant;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the DoctorVisit entity.
 */
public class DoctorVisitDTO implements Serializable {

    private Long id;

    private ZonedDateTime visitData;

    private ZonedDateTime visitEndData;

    @Size(max = 50)
    private String createdBy;

    private Instant createdDate;

    @Size(max = 50)
    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private Long doctorId;

    private String doctorName;

    private Long roomId;

    private String roomName;

    private Integer roomNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getVisitData() {
        return visitData;
    }

    public void setVisitData(ZonedDateTime visitData) {
        this.visitData = visitData;
    }

    public ZonedDateTime getVisitEndData() {
        return visitEndData;
    }

    public void setVisitEndData(ZonedDateTime visitEndData) {
        this.visitEndData = visitEndData;
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

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long consultRoomId) {
        this.roomId = consultRoomId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Integer getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(Integer roomNo) {
        this.roomNo = roomNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DoctorVisitDTO doctorVisitDTO = (DoctorVisitDTO) o;
        if(doctorVisitDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), doctorVisitDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DoctorVisitDTO{" +
            "id=" + getId() +
            ", visitData='" + getVisitData() + "'" +
            ", visitEndData='" + getVisitEndData() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
