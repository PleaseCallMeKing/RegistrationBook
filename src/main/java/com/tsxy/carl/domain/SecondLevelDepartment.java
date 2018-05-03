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
 * 二级学科
 * @author Carl Wang
 */
@ApiModel(description = "二级学科 @author Carl Wang")
@Entity
@Table(name = "second_level_department")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SecondLevelDepartment extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 学科名
     */
    @Size(max = 255)
    @ApiModelProperty(value = "学科名")
    @Column(name = "dept_name", length = 255)
    private String deptName;

    /**
     * 学科英文名
     */
    @Size(max = 255)
    @ApiModelProperty(value = "学科英文名")
    @Column(name = "dept_english_name", length = 255)
    private String deptEnglishName;

    @ManyToMany(mappedBy = "secondLevelDepts")
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

    public String getDeptName() {
        return deptName;
    }

    public SecondLevelDepartment deptName(String deptName) {
        this.deptName = deptName;
        return this;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptEnglishName() {
        return deptEnglishName;
    }

    public SecondLevelDepartment deptEnglishName(String deptEnglishName) {
        this.deptEnglishName = deptEnglishName;
        return this;
    }

    public void setDeptEnglishName(String deptEnglishName) {
        this.deptEnglishName = deptEnglishName;
    }


    public Set<ThirdLevelDepartment> getDepts() {
        return depts;
    }

    public SecondLevelDepartment depts(Set<ThirdLevelDepartment> thirdLevelDepartments) {
        this.depts = thirdLevelDepartments;
        return this;
    }

    public SecondLevelDepartment addDept(ThirdLevelDepartment thirdLevelDepartment) {
        this.depts.add(thirdLevelDepartment);
        thirdLevelDepartment.getSecondLevelDepts().add(this);
        return this;
    }

    public SecondLevelDepartment removeDept(ThirdLevelDepartment thirdLevelDepartment) {
        this.depts.remove(thirdLevelDepartment);
        thirdLevelDepartment.getSecondLevelDepts().remove(this);
        return this;
    }

    public void setDepts(Set<ThirdLevelDepartment> thirdLevelDepartments) {
        this.depts = thirdLevelDepartments;
    }

    public SecondLevelDepartment createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public SecondLevelDepartment createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public SecondLevelDepartment lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public SecondLevelDepartment lastModifiedDate(Instant lastModifiedDate) {
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
        SecondLevelDepartment secondLevelDepartment = (SecondLevelDepartment) o;
        if (secondLevelDepartment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), secondLevelDepartment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SecondLevelDepartment{" +
            "id=" + getId() +
            ", deptName='" + getDeptName() + "'" +
            ", deptEnglishName='" + getDeptEnglishName() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
