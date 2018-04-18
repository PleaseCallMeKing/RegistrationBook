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
 * 三级科室
 * @author Carl Wang
 */
@ApiModel(description = "三级科室 @author Carl Wang")
@Entity
@Table(name = "third_level_department")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ThirdLevelDepartment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 科室名称
     */
    @NotNull
    @Size(max = 250)
    @ApiModelProperty(value = "科室名称", required = true)
    @Column(name = "dept_name", length = 250, nullable = false)
    private String deptName;

    /**
     * 科室英文名
     */
    @Size(max = 250)
    @ApiModelProperty(value = "科室英文名")
    @Column(name = "dept_english_name", length = 250)
    private String deptEnglishName;

    /**
     * 是否可预约
     */
    @ApiModelProperty(value = "是否可预约")
    @Column(name = "appointmentable")
    private Boolean appointmentable;

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

    @OneToMany(mappedBy = "dept")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ConsultRoom> rooms = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "third_level_department_doctor",
               joinColumns = @JoinColumn(name="third_level_departments_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="doctors_id", referencedColumnName="id"))
    private Set<Doctor> doctors = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "third_level_department_second_level_dept",
               joinColumns = @JoinColumn(name="third_level_departments_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="second_level_depts_id", referencedColumnName="id"))
    private Set<SecondLevelDepartment> secondLevelDepts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeptName() {
        return deptName;
    }

    public ThirdLevelDepartment deptName(String deptName) {
        this.deptName = deptName;
        return this;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptEnglishName() {
        return deptEnglishName;
    }

    public ThirdLevelDepartment deptEnglishName(String deptEnglishName) {
        this.deptEnglishName = deptEnglishName;
        return this;
    }

    public void setDeptEnglishName(String deptEnglishName) {
        this.deptEnglishName = deptEnglishName;
    }

    public Boolean isAppointmentable() {
        return appointmentable;
    }

    public ThirdLevelDepartment appointmentable(Boolean appointmentable) {
        this.appointmentable = appointmentable;
        return this;
    }

    public void setAppointmentable(Boolean appointmentable) {
        this.appointmentable = appointmentable;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public ThirdLevelDepartment createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public ThirdLevelDepartment createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public ThirdLevelDepartment lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public ThirdLevelDepartment lastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Set<ConsultRoom> getRooms() {
        return rooms;
    }

    public ThirdLevelDepartment rooms(Set<ConsultRoom> consultRooms) {
        this.rooms = consultRooms;
        return this;
    }

    public ThirdLevelDepartment addRooms(ConsultRoom consultRoom) {
        this.rooms.add(consultRoom);
        consultRoom.setDept(this);
        return this;
    }

    public ThirdLevelDepartment removeRooms(ConsultRoom consultRoom) {
        this.rooms.remove(consultRoom);
        consultRoom.setDept(null);
        return this;
    }

    public void setRooms(Set<ConsultRoom> consultRooms) {
        this.rooms = consultRooms;
    }

    public Set<Doctor> getDoctors() {
        return doctors;
    }

    public ThirdLevelDepartment doctors(Set<Doctor> doctors) {
        this.doctors = doctors;
        return this;
    }

    public ThirdLevelDepartment addDoctor(Doctor doctor) {
        this.doctors.add(doctor);
        doctor.getDepts().add(this);
        return this;
    }

    public ThirdLevelDepartment removeDoctor(Doctor doctor) {
        this.doctors.remove(doctor);
        doctor.getDepts().remove(this);
        return this;
    }

    public void setDoctors(Set<Doctor> doctors) {
        this.doctors = doctors;
    }

    public Set<SecondLevelDepartment> getSecondLevelDepts() {
        return secondLevelDepts;
    }

    public ThirdLevelDepartment secondLevelDepts(Set<SecondLevelDepartment> secondLevelDepartments) {
        this.secondLevelDepts = secondLevelDepartments;
        return this;
    }

    public ThirdLevelDepartment addSecondLevelDept(SecondLevelDepartment secondLevelDepartment) {
        this.secondLevelDepts.add(secondLevelDepartment);
        secondLevelDepartment.getDepts().add(this);
        return this;
    }

    public ThirdLevelDepartment removeSecondLevelDept(SecondLevelDepartment secondLevelDepartment) {
        this.secondLevelDepts.remove(secondLevelDepartment);
        secondLevelDepartment.getDepts().remove(this);
        return this;
    }

    public void setSecondLevelDepts(Set<SecondLevelDepartment> secondLevelDepartments) {
        this.secondLevelDepts = secondLevelDepartments;
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
        ThirdLevelDepartment thirdLevelDepartment = (ThirdLevelDepartment) o;
        if (thirdLevelDepartment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), thirdLevelDepartment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ThirdLevelDepartment{" +
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
