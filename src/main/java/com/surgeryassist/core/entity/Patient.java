package com.surgeryassist.core.entity;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(schema = "SurgeryAssist", name = "patient")
@Configurable
public class Patient {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "patient_id")
	private Integer patientId;

	@Version
	@Column(name = "version")
	private Integer version;
	
	@OneToMany(mappedBy = "patientId")
    private Set<Bookings> bookingss;
    
    @ManyToOne
    @JoinColumn(name = "insurance_code", referencedColumnName = "insurance_id", nullable = false)
    private InsuranceType insuranceCode;
    
    @ManyToOne
    @JoinColumn(name = "surgery_type_code", referencedColumnName = "surgery_id", nullable = false)
    private SurgeryType surgeryTypeCode;
    
    @Column(name = "first_name", length = 100)
    private String firstName;
    
    @Column(name = "last_name", length = 100)
    private String lastName;
    
    @Column(name = "created_by")
    private Integer createdBy;
    
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar createdDate;
    
    @Column(name = "modified_by")
    private Integer modifiedBy;
    
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar modifiedDate;
	
	@PersistenceContext
    transient EntityManager entityManager;
    
    public static final EntityManager entityManager() {
        EntityManager em = new Patient().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long countPatients() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Patient o", Long.class).getSingleResult();
    }
    
    public static List<Patient> findAllPatients() {
        return entityManager().createQuery("SELECT o FROM Patient o", Patient.class).getResultList();
    }
    
    public static Patient findPatient(Integer patientId) {
        if (patientId == null) return null;
        return entityManager().find(Patient.class, patientId);
    }
    
    public static List<Patient> findPatientEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Patient o", Patient.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Patient merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Patient merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public Integer getPatientId() {
		return this.patientId;
	}

	public void setPatientId(Integer id) {
		this.patientId = id;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * @return the bookingss
	 */
	public Set<Bookings> getBookingss() {
		return bookingss;
	}

	/**
	 * @param bookingss the bookingss to set
	 */
	public void setBookingss(Set<Bookings> bookingss) {
		this.bookingss = bookingss;
	}

	/**
	 * @return the insuranceCode
	 */
	public InsuranceType getInsuranceCode() {
		return insuranceCode;
	}

	/**
	 * @param insuranceCode the insuranceCode to set
	 */
	public void setInsuranceCode(InsuranceType insuranceCode) {
		this.insuranceCode = insuranceCode;
	}

	/**
	 * @return the surgeryTypeCode
	 */
	public SurgeryType getSurgeryTypeCode() {
		return surgeryTypeCode;
	}

	/**
	 * @param surgeryTypeCode the surgeryTypeCode to set
	 */
	public void setSurgeryTypeCode(SurgeryType surgeryTypeCode) {
		this.surgeryTypeCode = surgeryTypeCode;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the createdBy
	 */
	public Integer getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the createdDate
	 */
	public Calendar getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Calendar createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the modifiedBy
	 */
	public Integer getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * @return the modifiedDate
	 */
	public Calendar getModifiedDate() {
		return modifiedDate;
	}

	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(Calendar modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
