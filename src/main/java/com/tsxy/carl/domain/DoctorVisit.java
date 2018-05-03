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
 * 医生出诊
 * @author Carl Wang
 */
@ApiModel(description = "医生出诊 @author Carl Wang")
@Entity
@Table(name = "doctor_visit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DoctorVisit extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 出诊时间
     */
    @ApiModelProperty(value = "出诊时间")
    @Column(name = "visit_data")
    private ZonedDateTime visitData;

    /**
     * 出诊结束时间
     */
    @ApiModelProperty(value = "出诊结束时间")
    @Column(name = "visit_end_data")
    private ZonedDateTime visitEndData;

    @ManyToOne
    private Doctor doctor;

    @ManyToOne
    private ConsultRoom room;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getVisitData() {
        return visitData;
    }

    public DoctorVisit visitData(ZonedDateTime visitData) {
        this.visitData = visitData;
        return this;
    }

    public void setVisitData(ZonedDateTime visitData) {
        this.visitData = visitData;
    }

    public ZonedDateTime getVisitEndData() {
        return visitEndData;
    }

    public DoctorVisit visitEndData(ZonedDateTime visitEndData) {
        this.visitEndData = visitEndData;
        return this;
    }

    public void setVisitEndData(ZonedDateTime visitEndData) {
        this.visitEndData = visitEndData;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public DoctorVisit doctor(Doctor doctor) {
        this.doctor = doctor;
        return this;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public ConsultRoom getRoom() {
        return room;
    }

    public DoctorVisit room(ConsultRoom consultRoom) {
        this.room = consultRoom;
        return this;
    }

    public void setRoom(ConsultRoom consultRoom) {
        this.room = consultRoom;
    }

    public DoctorVisit createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public DoctorVisit createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public DoctorVisit lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public DoctorVisit lastModifiedDate(Instant lastModifiedDate) {
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
        DoctorVisit doctorVisit = (DoctorVisit) o;
        if (doctorVisit.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), doctorVisit.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DoctorVisit{" +
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
