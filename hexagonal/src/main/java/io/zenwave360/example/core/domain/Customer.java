package io.zenwave360.example.core.domain;

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
@Table(name = "customer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Customer implements Serializable {

	@java.io.Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	@SequenceGenerator(name = "sequenceGenerator")
	@Column(name = "id")
	private Long id;

	@Version
	private Integer version;

	/** username javadoc comment */
	@NotNull
	@Column(name = "username", nullable = false, unique = true)
	private String username;

	/** email javadoc comment */
	@NotNull
	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@NotNull
	@OneToOne(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Address address;

	// manage relationships
	public Customer setAddress(Address address) {
		this.address = address;
		address.setCustomer(this);
		return this;
	}

	/*
	 * https://vladmihalcea.com/the-best-way-to-implement-equals-hashcode-and-tostring-
	 * with-jpa-and-hibernate/
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Customer)) {
			return false;
		}
		Customer other = (Customer) o;
		return getId() != null && getId().equals(other.getId());
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

}
