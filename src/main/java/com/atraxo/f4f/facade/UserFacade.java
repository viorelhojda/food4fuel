package com.atraxo.f4f.facade;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.atraxo.f4f.dao.UserDAO;
import com.atraxo.f4f.job.quartz.JobScheduler;
import com.atraxo.f4f.model.account.Account;
import com.atraxo.f4f.model.job.EmailTypeEnum;
import com.atraxo.f4f.model.job.ProcessEmailJob;
import com.atraxo.f4f.model.job.ProcessJobTypeEnum;
import com.atraxo.f4f.model.permission.RightEnum;
import com.atraxo.f4f.model.user.User;
import com.atraxo.f4f.util.UserUtils;
import com.atraxo.f4f.xml.EmailXML;
import com.atraxo.f4f.xml.XMLParser;

/**
 * @author vhojda
 *
 */
public class UserFacade implements Serializable {

	private static final long serialVersionUID = -2840627592541383687L;
	private static final Logger LOGGER = Logger.getLogger(UserFacade.class);

	private ProcessJobFacade processJobFacade = new ProcessJobFacade();

	private UserDAO userDAO = new UserDAO();

	/**
	 * @param user
	 */
	public void create(User user) {
		try {
			userDAO.beginTransaction();

			//make sure to encrypt the password
			user.getAccount().setPassword(UserUtils.encryptMD5Password(user.getAccount().getUsername()));
			userDAO.save(user);

			userDAO.commitAndCloseTransaction();

			sendWelcomeMail(user);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			LOGGER.error("ERROR:could not create the user!",e);
		}
	}

	/**
	 * @param userId
	 * @return
	 */
	public User find(Integer userId) {
		userDAO.beginTransaction();
		User user = userDAO.find(userId);
		userDAO.commitAndCloseTransaction();
		return user;
	}

	/**
	 * @param user
	 */
	private void sendWelcomeMail(User user) {
		ProcessEmailJob emailJob = new ProcessEmailJob();
		emailJob.setType(ProcessJobTypeEnum.EMAIL);
		emailJob.setMailType(EmailTypeEnum.WELCOME);
		emailJob.setStartDate(Calendar.getInstance().getTime());

		XMLParser parser = new XMLParser();

		Account account = user.getAccount();
		EmailXML emailXml = parser.parseEmailXml(emailJob.getMailType().getType());

		HashMap<String, String> contentMap = new HashMap<>();
		contentMap.put("firstName", account.getFirstName());
		contentMap.put("fullName", account.getFirstNameLastName());
		contentMap.put("username", account.getUsername());
		contentMap.put("password", account.getUsername());
		contentMap.put("email", account.getEmail());

		emailJob.setEmailReceiver(account.getEmail());
		emailJob.setEmailSender(emailXml.getSender());
		emailJob.setEmailCc(emailXml.getCc());
		emailJob.setEmailBcc(emailXml.getBcc());
		emailJob.setEmailSubject(emailXml.getActualSubject(contentMap));
		emailJob.setContent(emailXml.getActualContent(contentMap));

		processJobFacade.insert(emailJob);

		JobScheduler.getInstance().scheduleJob(emailJob);

	}

	/**
	 * @param user
	 */
	public void update(User user) {
		userDAO.beginTransaction();
		userDAO.update(user);
		userDAO.commitAndCloseTransaction();
	}

	/**
	 * @param user
	 */
	public void delete(User user) {
		userDAO.beginTransaction();
		userDAO.delete(user);
		userDAO.commitAndCloseTransaction();
	}

	/**
	 * @return
	 */
	public List<User> listAll(){
		userDAO.beginTransaction();
		List<User> allUsers = userDAO.findAll();
		userDAO.commitAndCloseTransaction();
		return allUsers;
	}

	/**
	 * @return
	 */
	public List<User> listAllActive(){
		userDAO.beginTransaction();
		List<User> allUsers = userDAO.findAllActive();
		userDAO.commitAndCloseTransaction();
		return allUsers;
	}

	/**
	 * @param roleID
	 * @return
	 */
	public List<User> listAllByRole(int roleID){
		userDAO.beginTransaction();
		List<User> allUsers = userDAO.findAllByRole(roleID);
		userDAO.commitAndCloseTransaction();

		return allUsers;
	}

	/**
	 * @param username
	 * @param password
	 * @return
	 * @throws IOException
	 */
	public User isValidLogin(String username, String password) throws IOException{
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START isValidLogin()");
		}

		userDAO.beginTransaction();
		User user = userDAO.findUserByUsername(username);
		userDAO.commitAndCloseTransaction();

		String encryptedPassword = UserUtils.encryptMD5Password(password);

		if ( !encryptedPassword.equals(user.getAccount().getPassword()) ) {
			return null;
		}

		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END isValidLogin()");
		}
		return user;
	}


	/**
	 * @param rightEnum
	 * @return
	 */
	public List<User> getByRight(RightEnum rightEnum){
		userDAO.beginTransaction();
		List<User> users = userDAO.findByRight(rightEnum);
		userDAO.commitAndCloseTransaction();
		return users;
	}

	/**
	 * @param user
	 * @param oldPassword
	 * @param newPassword
	 * @throws UnsupportedEncodingException
	 */
	public String changePassword(User user, String oldPassword, String newPassword) throws UnsupportedEncodingException {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START changePassword()");
		}
		userDAO.beginTransaction();

		User dbUser = userDAO.find(user.getId());

		String newPasswordDB = UserUtils.encryptMD5Password(newPassword);
		dbUser.getAccount().setPassword(newPasswordDB);

		userDAO.update(dbUser);
		userDAO.commitAndCloseTransaction();

		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END changePassword()");
		}
		return newPasswordDB;
	}

	/**
	 * @param user
	 */
	public void logout(User user) {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START logout()");
		}

		userDAO.beginTransaction();

		User dbUser = userDAO.find(user.getId());
		dbUser.setLastLogin(Calendar.getInstance().getTime());
		userDAO.update(dbUser);

		userDAO.commitAndCloseTransaction();

		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END logout()");
		}
	}
}
