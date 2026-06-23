package org.socymet.seguridad

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.socymet.proveedor.Deposito
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@EqualsAndHashCode(includes='username')
@ToString(includes='username', includeNames=true, includePackage=false)
class SecUser implements Serializable {

	private static final long serialVersionUID = 1

	Deposito deposito
	String nombre
	String username
	String password
	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

	SecUser(Deposito deposito, String nombre, String username, String password) {
		this()
		this.deposito = deposito
		this.nombre = nombre
		this.username = username
		this.password = password
	}

	Set<SecRole> getAuthorities() {
		SecUserSecRole.findAllBySecUser(this)*.secRole
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = new BCryptPasswordEncoder().encode(password)
	}

	static constraints = {
		deposito nullable: false
		nombre blank: false, nullable: false
		username blank: false, unique: true
		password blank: false
	}

	static mapping = {
		password column: '`password`'
	}
}
