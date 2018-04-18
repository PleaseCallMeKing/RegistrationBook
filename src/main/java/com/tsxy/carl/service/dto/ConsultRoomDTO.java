package com.tsxy.carl.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ConsultRoom entity.
 */
public class ConsultRoomDTO implements Serializable {

    private Long id;

    @Size(max = 250)
    private String consultRoomName;

    private Integer consultRoomNo;

    @Size(max = 50)
    private String createdBy;

    private Instant createdDate;

    @Size(max = 50)
    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private Long deptId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConsultRoomName() {
        return consultRoomName;
    }

    public void setConsultRoomName(String consultRoomName) {
        this.consultRoomName = consultRoomName;
    }

    public Integer getConsultRoomNo() {
        return consultRoomNo;
    }

    public void setConsultRoomNo(Integer consultRoomNo) {
        this.consultRoomNo = consultRoomNo;
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

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long thirdLevelDepartmentId) {
        this.deptId = thirdLevelDepartmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ConsultRoomDTO consultRoomDTO = (ConsultRoomDTO) o;
        if(consultRoomDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), consultRoomDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConsultRoomDTO{" +
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
