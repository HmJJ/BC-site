package com.springboot.code.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.springboot.basic.entity.DefaultModel;

import lombok.Getter;
import lombok.Setter;

/**
* @author nott
* @version 创建时间：2018年12月28日上午10:48:07
* 类说明
*/
@Entity
@Table(name="TBL_USER_SERVERINFO")
public class ServerInfo extends DefaultModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3737585162510254776L;
	
	/**
	 * 服务器ip
	 */
	@Column(name="SERVER_IP",length = 32)
	@Getter
	@Setter
    private String ip;

	/**
	 * 服务器端口
	 */
	@Column(name="SERVER_PORT",length = 8)
	@Getter
	@Setter
    private Integer port;

	/**
	 * 服务器用户名
	 */
	@Column(name="SERVER_USERNAME",length = 64)
	@Getter
	@Setter
    private String userName;

	/**
	 * 服务器密码
	 */
	@Column(name="SERVER_USERPWD",length = 128)
	@Getter
	@Setter
    private String userPwd;
	
	/**
	 * 拥有该服务器的用户
	 */
	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinTable(name = "REL_USER_SERVERINFO",
		joinColumns        = {@JoinColumn(name = "SERVERINFO_ID", referencedColumnName = "ID") },
		inverseJoinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID") })
	@Getter
	@Setter
	private Set<User> users;
}
