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
 * 医生
 * @author Carl Wang
 */
@ApiModel(description = "医生 @author Carl Wang")
@Entity
@Table(name = "doctor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Doctor extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 绑定userId
     */
    @NotNull
    @ApiModelProperty(value = "绑定userId", required = true)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * 姓名
     */
    @NotNull
    @Size(max = 50)
    @ApiModelProperty(value = "姓名", required = true)
    @Column(name = "full_name", length = 50, nullable = false)
    private String fullName;

    /**
     * 性别
     */
    @NotNull
    @Size(max = 10)
    @ApiModelProperty(value = "性别", required = true)
    @Column(name = "gender", length = 10, nullable = false)
    private String gender;

    /**
     * 民族
     */
    @Size(max = 250)
    @ApiModelProperty(value = "民族")
    @Column(name = "nationality", length = 250)
    private String nationality;

    /**
     * 图片路径
     */
    @Size(max = 256)
    @ApiModelProperty(value = "图片路径")
    @Column(name = "imgurl", length = 256)
    private String imgurl;

    /**
     * 专业
     */
    @Size(max = 1000)
    @ApiModelProperty(value = "专业")
    @Column(name = "profession", length = 1000)
    private String profession;

    /**
     * 手机号
     */
    @Size(max = 50)
    @ApiModelProperty(value = "手机号")
    @Column(name = "mobilephone", length = 50)
    private String mobilephone;

    /**
     * 是否启用
     */
    @NotNull
    @ApiModelProperty(value = "是否启用", required = true)
    @Column(name = "active", nullable = false)
    private Boolean active;


    @ManyToMany(mappedBy = "doctors")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ThirdLevelDepartment> depts = new HashSet<>();

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

    public Doctor userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public Doctor fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public Doctor gender(String gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public Doctor nationality(String nationality) {
        this.nationality = nationality;
        return this;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getImgurl() {
        return imgurl;
    }

    public Doctor imgurl(String imgurl) {
        this.imgurl = imgurl;
        return this;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getProfession() {
        return profession;
    }

    public Doctor profession(String profession) {
        this.profession = profession;
        return this;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public Doctor mobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
        return this;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public Boolean isActive() {
        return active;
    }

    public Doctor active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<ThirdLevelDepartment> getDepts() {
        return depts;
    }

    public Doctor depts(Set<ThirdLevelDepartment> thirdLevelDepartments) {
        this.depts = thirdLevelDepartments;
        return this;
    }

    public Doctor addDept(ThirdLevelDepartment thirdLevelDepartment) {
        this.depts.add(thirdLevelDepartment);
        thirdLevelDepartment.getDoctors().add(this);
        return this;
    }

    public Doctor removeDept(ThirdLevelDepartment thirdLevelDepartment) {
        this.depts.remove(thirdLevelDepartment);
        thirdLevelDepartment.getDoctors().remove(this);
        return this;
    }

    public void setDepts(Set<ThirdLevelDepartment> thirdLevelDepartments) {
        this.depts = thirdLevelDepartments;
    }

    public Doctor createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public Doctor createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public Doctor lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public Doctor lastModifiedDate(Instant lastModifiedDate) {
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
        Doctor doctor = (Doctor) o;
        if (doctor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), doctor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Doctor{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", fullName='" + getFullName() + "'" +
            ", gender='" + getGender() + "'" +
            ", nationality='" + getNationality() + "'" +
            ", imgurl='" + getImgurl() + "'" +
            ", profession='" + getProfession() + "'" +
            ", mobilephone='" + getMobilephone() + "'" +
            ", active='" + isActive() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
