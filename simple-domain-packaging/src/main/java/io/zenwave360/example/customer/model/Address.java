package io.zenwave360.example.customer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.*;
import java.time.*;
import java.util.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/** */
@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "address")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Address implements Serializable {

	@java.io.Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	@SequenceGenerator(name = "sequenceGenerator")
	@Column(name = "id")
	private Long id;

	@Version
	private Integer version;

	/** street javadoc comment */
	@NotNull
	@Column(name = "street", nullable = false)
	private String street;

	/** city javadoc comment */
	@Column(name = "city")
	private String city;

	/** state javadoc comment */
	@Column(name = "state")
	private String state;

	/** zip javadoc comment */
	@Column(name = "zip")
	private String zip;

	/** address type is an enum */
	@NotNull
	@Column(name = "type", nullable = false)
	@Enumerated(EnumType.STRING)
	private AddressType type;

	@NotNull
	@MapsId
	@JoinColumn(name = "id")
	@OneToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private Customer customer;

	/*
	 * https://vladmihalcea.com/the-best-way-to-implement-equals-hashcode-and-tostring-
	 * with-jpa-and-hibernate/
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Address)) {
			return false;
		}
		Address other = (Address) o;
		return getId() != null && getId().equals(other.getId());
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

}
