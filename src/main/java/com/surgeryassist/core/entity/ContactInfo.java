package com.surgeryassist.core.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(schema = "SurgeryAssist", name = "contact_info")
@Configurable
public class ContactInfo {
	
	@Column(name = "phone_number")
	private Integer phoneNumber;
	
	@Column(name = "fax_number")
	private Integer faxNumber;
	
	@Column(name = "secondary_email")
	private String secondaryEmail;
	
	@Column(name = "created_by", updatable = false)
    private Integer createdBy;

    @Column(name = "created_date", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date createdDate;

    @Column(name = "modified_by")
    private Integer modifiedBy;

    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date modifiedDate;

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new ContactInfo().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countContactInfoes() {
        return entityManager().createQuery("SELECT COUNT(o) FROM ContactInfo o", Long.class).getSingleResult();
    }

	public static List<ContactInfo> findAllContactInfoes() {
        return entityManager().createQuery("SELECT o FROM ContactInfo o", ContactInfo.class).getResultList();
    }

	public static ContactInfo findContactInfo(Integer contactInfoID) {
        if (contactInfoID == null) return null;
        return entityManager().find(ContactInfo.class, contactInfoID);
    }

	public static List<ContactInfo> findContactInfoEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM ContactInfo o", ContactInfo.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	@Transactional
    public void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }

	@Transactional
    public void remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            ContactInfo attached = ContactInfo.findContactInfo(this.contactInfoID);
            this.entityManager.remove(attached);
        }
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
    public ContactInfo merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        ContactInfo merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "contact_info_id")
    private Integer contactInfoID;

	@Version
    @Column(name = "version")
    private Integer version;

	public Integer getContactInfoID() {
        return this.contactInfoID;
    }

	public void setContactInfoID(Integer id) {
        this.contactInfoID = id;
    }

	public Integer getVersion() {
        return this.version;
    }

	public void setVersion(Integer version) {
        this.version = version;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public Integer getPhoneNumber() {
        return this.phoneNumber;
    }

	public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

	public Integer getFaxNumber() {
        return this.faxNumber;
    }

	public void setFaxNumber(Integer faxNumber) {
        this.faxNumber = faxNumber;
    }

	public String getSecondaryEmail() {
        return this.secondaryEmail;
    }

	public void setSecondaryEmail(String secondaryEmail) {
        this.secondaryEmail = secondaryEmail;
    }

	public Integer getCreatedBy() {
        return this.createdBy;
    }

	public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

	public Date getCreatedDate() {
        return this.createdDate;
    }

	public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

	public Integer getModifiedBy() {
        return this.modifiedBy;
    }

	public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

	public Date getModifiedDate() {
        return this.modifiedDate;
    }

	public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
