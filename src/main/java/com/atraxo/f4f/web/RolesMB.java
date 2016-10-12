package com.atraxo.f4f.web;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.atraxo.f4f.facade.RightFacade;
import com.atraxo.f4f.facade.RoleFacade;
import com.atraxo.f4f.facade.RoleRightFacade;
import com.atraxo.f4f.facade.UserFacade;
import com.atraxo.f4f.model.permission.Right;
import com.atraxo.f4f.model.permission.Role;
import com.atraxo.f4f.model.permission.RoleRight;
import com.atraxo.f4f.model.user.User;
import com.atraxo.f4f.util.Constants;
import com.atraxo.f4f.util.MessageBundleUtils;

@ViewScoped
@ManagedBean
public class RolesMB extends AbstractMB{
	private static final long serialVersionUID = 4322556519360216327L;
	private static final Logger LOGGER = Logger.getLogger(RolesMB.class);

	private List<Right> rights;
	private List<Role> roles;

	private UserFacade userFacade;
	private RightFacade rightFacade;
	private RoleFacade roleFacade;
	private RoleRightFacade roleRightFacade;

	private Role role;

	private transient Map<String, Map<Right, Map<Role, Boolean>>> roleRightMap;

	public RolesMB() {
		super();

		userFacade = new UserFacade();
		rightFacade = new RightFacade();
		roleFacade = new RoleFacade();
		roleRightFacade = new RoleRightFacade();
		roleRightMap = new HashMap<>();

		refreshRoleRightMap();
	}

	public void submit(){
		updateRoleRights();
		refreshRoleRightMap();
	}

	public void createRole(){
		try{
			roleFacade.create(role);

			refreshRoleRightMap();

			String msgOk = Constants.MSG_CREATE_OK;
			displayInfoMessageToUser(MessageBundleUtils.getMessage(msgOk));
		}catch (Exception e) {
			String msgNotOk = Constants.MSG_CREATE_NOT_OK;
			displayErrorMessageToUser(MessageBundleUtils.getMessage(msgNotOk));
			LOGGER.error("Could not create role", e);
		}
	}

	public void updateRole(){
		try{
			roleFacade.update(role);

			refreshRoleRightMap();

			String msgOk = Constants.MSG_UPDATE_OK;
			displayInfoMessageToUser(MessageBundleUtils.getMessage(msgOk));
		}catch (Exception e) {
			String msgNotOk = Constants.MSG_UPDATE_NOT_OK;
			displayErrorMessageToUser(MessageBundleUtils.getMessage(msgNotOk));
			LOGGER.error("Could not update role", e);
		}
	}

	public void deleteRole(Role role){
		try{
			//set role = null to all users with this role
			for( User u : userFacade.listAllByRole(role.getId()) ){
				u.setRole(null);
				userFacade.update(u);
			}

			//delete the specified role
			roleFacade.delete(role);

			refreshRoleRightMap();

			String msgOk =Constants.MSG_DELETE_OK;
			displayInfoMessageToUser(MessageBundleUtils.getMessage(msgOk));
		}catch (Exception e) {
			String msgNotOk = Constants.MSG_DELETE_NOT_OK;
			displayErrorMessageToUser(MessageBundleUtils.getMessage(msgNotOk));
			LOGGER.error("Could not update role", e);
		}
	}

	public void refreshRoleRightMap(){
		roleRightMap.clear();
		roles = roleFacade.listAllActive();
		for( Right refreshRight : getRights() ){
			for( Role refreshRole : getRoles() ){
				RoleRight roleRight = roleRightFacade.findByRoleAndRight(refreshRole.getId(), refreshRight.getId());
				boolean hasRight = roleRight != null ? true : false;

				Map<Role, Boolean> roleMap = new HashMap<>();
				roleMap.put(refreshRole, hasRight);

				Map<Right, Map<Role, Boolean>> rightMap = new HashMap<>();
				rightMap.put(refreshRight, roleMap);

				roleRightMap.put(refreshRole.getCode().concat(refreshRight.getCode().name()), rightMap);
			}
		}
	}

	public void updateRoleRights(){
		try{
			for ( Iterator<String> map = roleRightMap.keySet().iterator(); map.hasNext();){
				String permission = map.next();

				Map<Right, Map<Role, Boolean>> rightMap = roleRightMap.get(permission);
				for ( Iterator<Right> right = rightMap.keySet().iterator(); right.hasNext();){
					Right currentRight = right.next();

					persistRoleRight(rightMap, currentRight);
				}
			}

			String msgOk = Constants.MSG_UPDATE_OK;
			displayInfoMessageToUser(MessageBundleUtils.getMessage(msgOk));
		}catch (Exception e) {
			String msgNotOk = Constants.MSG_UPDATE_NOT_OK;
			displayErrorMessageToUser(MessageBundleUtils.getMessage(msgNotOk));
			LOGGER.error("Could not update role right", e);
		}
	}

	public void persistRoleRight(Map<Right, Map<Role, Boolean>> rightMap, Right currentRight){
		Map<Role, Boolean> rolesMap = rightMap.get(currentRight);
		for ( Iterator<Role> updateRole = rolesMap.keySet().iterator(); updateRole.hasNext();){
			Role currentRole = updateRole.next();

			Boolean roleRightValue = rolesMap.get(currentRole);
			RoleRight roleRight = roleRightFacade.findByRoleAndRight(currentRole.getId(), currentRight.getId());

			boolean update = false;
			if( roleRightValue && roleRight == null ){
				roleRight = new RoleRight();
				roleRight.setRight(currentRight);
				roleRight.setRole(currentRole);

				currentRole.getRights().add(roleRight);
				update = true;
			}

			if( !roleRightValue && roleRight != null ){
				currentRole.getRights().remove(roleRight);
				update = true;
			}

			if( update ){
				roleFacade.update(currentRole);
			}
		}
	}

	public List<Right> getRights() {
		if( rights == null ){
			rights = rightFacade.listAll();
		}
		return rights;
	}

	public void setRights(List<Right> rights) {
		this.rights = rights;
	}

	public List<Role> getRoles() {
		if( roles == null ){
			roles = roleFacade.listAllActive();
		}
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public UserFacade getUserFacade() {
		return userFacade;
	}

	public RightFacade getRightFacade() {
		return rightFacade;
	}

	public RoleFacade getRoleFacade() {
		return roleFacade;
	}

	public RoleRightFacade getRoleRightFacade() {
		return roleRightFacade;
	}

	public Map<String, Map<Right, Map<Role, Boolean>>> getRoleRightMap() {
		return roleRightMap;
	}

	public void setRoleRightMap(Map<String, Map<Right, Map<Role, Boolean>>> roleRightMap) {
		this.roleRightMap = roleRightMap;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public void instantiateNewRole(){
		role = new Role();
	}


}
