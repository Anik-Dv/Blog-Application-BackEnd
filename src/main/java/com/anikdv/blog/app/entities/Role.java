package com.anikdv.blog.app.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * This is a Role Entity
 */
@Entity
@Table(name = "Role")
@Data
public class Role implements Serializable {
	private static final long serialVersionUID = 3361066834306567663L;

	@Id
	@Column(name = "ROLEID")
	private Integer id;

	@Column(name = "ROLENAME")
	private String name;

	@ManyToOne
	private User user;
}
