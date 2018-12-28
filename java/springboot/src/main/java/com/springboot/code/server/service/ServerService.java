package com.springboot.code.server.service;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.springboot.basic.service.DefaultService;
import com.springboot.basic.support.CommonRequestAttributes;
import com.springboot.basic.utils.StringUtils;
import com.springboot.code.entity.ServerInfo;
import com.springboot.code.entity.User;
import com.springboot.code.server.vo.ServerVO;
import com.springboot.code.utils.DESCryptoUtils;

/**
* @author nott
* @version 创建时间：2018年12月28日上午11:02:13
* 类说明
*/
@Service(value="serviceService")
@Transactional(rollbackFor = Exception.class)
public class ServerService extends DefaultService<ServerInfo, String> {
	
	/**
	 * @param ip
	 * @return
	 */
	public ServerInfo findByIp(final String ip) {
		if (StringUtils.isBlank(ip)) {
			return null;
		}
		List<ServerInfo> serverInfos = findBy(new AssembleCriteriaParamsCallback() {
			@Override
			public DetachedCriteria assembleParams(DetachedCriteria criteria) {
				if(StringUtils.isNotBlank(ip)) {
					criteria.add(Restrictions.eq("ip", ip));				
				}
				criteria.add(Restrictions.eq("delete", Boolean.FALSE));
				return criteria;
			}
		});
		return serverInfos != null && !serverInfos.isEmpty() ? serverInfos.get(0) : null;
	}
	
	/**
	 * @param attributes
	 * @param model
	 * @param entity
	 * @return
	 */
	@Transactional(readOnly = false)
	public ServerInfo commithandler(CommonRequestAttributes attributes, Model model, User user, ServerVO serverVO) {
		if(StringUtils.isBlank(serverVO.getIp())) {
			return null;
		}
		ServerInfo temp = findByIp(serverVO.getIp());
		try {
			if (temp == null) {
				temp = new ServerInfo();
				BeanUtils.copyProperties(serverVO, temp);
			}
			temp.setModifydate(new Date());
			if (StringUtils.isNotBlank(serverVO.getUserPwd())) {
				temp.setUserPwd(DESCryptoUtils.encryptBasedDes(serverVO.getUserPwd()));
			}
			temp = merge(temp);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return temp;
	}
	
	/**
	 * @param attributes
	 * @param model
	 * @param entity
	 * @return
	 */
	@Transactional(readOnly = false)
	public ServerInfo commit(User user, ServerInfo serverInfo) {
		if(StringUtils.isBlank(serverInfo.getIp())) {
			return null;
		}
		ServerInfo temp = findBy(serverInfo.getId());
		try {
			if (temp == null) {
				temp = new ServerInfo();
				BeanUtils.copyProperties(serverInfo, temp);
			}
			temp.setModifydate(new Date());
			if (StringUtils.isNotBlank(serverInfo.getUserPwd())) {
				temp.setUserPwd(DESCryptoUtils.encryptBasedDes(serverInfo.getUserPwd()));
			}
			temp = merge(temp);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return temp;
	}
	
}
