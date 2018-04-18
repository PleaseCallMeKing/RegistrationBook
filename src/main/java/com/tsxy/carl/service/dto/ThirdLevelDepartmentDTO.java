package com.tsxy.carl.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ThirdLevelDepartment entity.
 */
public class ThirdLevelDepartmentDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 250)
    private String deptName;

    @Size(max = 250)
    private String deptEnglishName;

    private Boolean appointmentable;

    @Size(max = 50)
    private String createdBy;

    private Instant createdDate;

    @Size(max = 50)
    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private Set<DoctorDTO> doctors = new HashSet<>();

    private Set<SecondLevelDepartmentDTO> secondLevelDepts = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptEnglishName() {
        return deptEnglishName;
    }

    public void setDeptEnglishName(String deptEnglishName) {
        this.deptEnglishName = deptEnglishName;
    }

    public Boolean isAppointmentable() {
        return appointmentable;
    }

    public void setAppointmentable(Boolean appointmentable) {
        this.appointmentable = appointmentable;
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

    public Set<DoctorDTO> getDoctors() {
        return doctors;
    }

    public void setDoctors(Set<DoctorDTO> doctors) {
        this.doctors = doctors;
    }

    public Set<SecondLevelDepartmentDTO> getSecondLevelDepts() {
        return secondLevelDepts;
    }

    public void setSecondLevelDepts(Set<SecondLevelDepartmentDTO> secondLevelDepartments) {
        this.secondLevelDepts = secondLevelDepartments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ThirdLevelDepartmentDTO thirdLevelDepartmentDTO = (ThirdLevelDepartmentDTO) o;
        if(thirdLevelDepartmentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), thirdLevelDepartmentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ThirdLevelDepartmentDTO{" +
            "id=" + getId() +
            ", deptName='" + getDeptName() + "'" +
            ", deptEnglishName='" + getDeptEnglishName() + "'" +
            ", appointmentable='" + isAppointmentable() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
