package com.atraxo.f4f.service.email;

import java.math.BigDecimal;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.atraxo.f4f.facade.UserFacade;
import com.atraxo.f4f.model.job.EmailTypeEnum;
import com.atraxo.f4f.model.permission.RightEnum;
import com.atraxo.f4f.model.restaurant.Restaurant;
import com.atraxo.f4f.model.user.User;
import com.atraxo.f4f.util.DateUtils;
import com.atraxo.f4f.util.RestaurantMenuHelper;
import com.atraxo.f4f.xml.EmailXML;
import com.atraxo.f4f.xml.XMLParser;

public class ProcessJobEmailService {

	private static final Logger LOGGER = Logger.getLogger(ProcessJobEmailService.class);

	private static ProcessJobEmailService service;

	private UserFacade userFacade = new UserFacade();

	private ProcessJobEmailService(){

	}

	public static ProcessJobEmailService getInstance(){
		if ( service == null ) {
			service = new ProcessJobEmailService();
		}
		return service;
	}


	/**
	 * @param restaurant
	 * @param foodOrderText
	 */
	public void sendOrderFoodMail(Restaurant restaurant, String foodOrderText) {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START sendOrderFoodMail()");
		}

		XMLParser parser = new XMLParser();

		EmailXML emailXml = parser.parseEmailXml(EmailTypeEnum.ORDER_FOOD.getType());

		HashMap<String, String> contentMap = new HashMap<>();
		contentMap.put("todayDate", DateUtils.currentDateAsText());
		contentMap.put("restaurantName", restaurant.getName());
		contentMap.put("food", foodOrderText);

		String ccEmails = RestaurantMenuHelper.getEmailForUsers(userFacade.getByRight(RightEnum.ORDER_FOOD));

		WsClientEmail.sendMail(restaurant.getEmail(), emailXml.getSender(), ccEmails, 
				emailXml.getBcc(),emailXml.getActualSubject(contentMap),emailXml.getActualContent(contentMap));
		
		LOGGER.info("ORDERED food from "+restaurant.getEmail()+":"+foodOrderText);
		
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END sendOrderFoodMail()");
		}
	}

	/**
	 * @param moneyText
	 */
	public void sendCollectMoneyMail(String moneyText) {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START sendCollectMoneyMail()");
		}

		XMLParser parser = new XMLParser();

		EmailXML emailXml = parser.parseEmailXml(EmailTypeEnum.COLLECT_MONEY.getType());

		HashMap<String, String> contentMap = new HashMap<>();
		contentMap.put("todayDate", DateUtils.currentDateAsText());
		contentMap.put("money", moneyText);

		String responsibleCollectMoneyEmails = RestaurantMenuHelper.getEmailForUsers(userFacade.getByRight(RightEnum.COLLECT_MONEY));

		WsClientEmail.sendMail(responsibleCollectMoneyEmails, emailXml.getSender(), emailXml.getCc(), 
				emailXml.getBcc(),emailXml.getActualSubject(contentMap),emailXml.getActualContent(contentMap));
		
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END sendCollectMoneyMail()");
		}
		
	}

	/**
	 * @param restaurant
	 * @param user
	 * @param moneyToPayup
	 */
	public void sendPayMoneyMail(Restaurant restaurant, User user, BigDecimal moneyToPayup) {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START sendPayMoneyMail()");
		}

		XMLParser parser = new XMLParser();

		EmailXML emailXml = parser.parseEmailXml(EmailTypeEnum.PAY_MONEY.getType());

		String responsibleCollectMoneyNames = RestaurantMenuHelper.getNamesForUsers(userFacade.getByRight(RightEnum.COLLECT_MONEY));
		
		HashMap<String, String> contentMap = new HashMap<>();
		contentMap.put("todayDate", DateUtils.currentDateAsText());
		contentMap.put("week", DateUtils.currentWeekAsText());
		contentMap.put("restaurant", restaurant.getName());
		contentMap.put("fullName", user.getAccount().getFirstNameLastName());
		contentMap.put("money", moneyToPayup.toString());
		contentMap.put("moneyCollector", responsibleCollectMoneyNames);
		
		WsClientEmail.sendMail(user.getAccount().getEmail(), emailXml.getSender(), emailXml.getCc(), 
				emailXml.getBcc(), emailXml.getActualSubject(contentMap), emailXml.getActualContent(contentMap));
		
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END sendPayMoneyMail()");
		}
	}

	/**
	 * @param user
	 */
	public void sendPreOrderMail(User user) {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START sendPreOrderMail()");
		}

		XMLParser parser = new XMLParser();

		EmailXML emailXml = parser.parseEmailXml(EmailTypeEnum.PRE_ORDER_FOOD.getType());

		HashMap<String, String> contentMap = new HashMap<>();
		contentMap.put("todayDate", DateUtils.currentDateAsText());
		contentMap.put("fullName", user.getAccount().getFirstNameLastName());
		
		WsClientEmail.sendMail(user.getAccount().getEmail(), emailXml.getSender(), emailXml.getCc(), 
				emailXml.getBcc(), emailXml.getActualSubject(contentMap), emailXml.getActualContent(contentMap));
		
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END sendPreOrderMail()");
		}
	}




	//	/**
	//	 * @param emailJob
	//	 * @param user
	//	 */
	//	public void sendMailWelcome(ProcessEmailJob emailJob, User user) {
	//		if ( LOGGER.isDebugEnabled() ){
	//			LOGGER.debug("START sendMailWelcome()");
	//		}
	//
	//		XMLParser parser = new XMLParser();
	//
	//		Account account = user.getAccount();
	//		try {
	//			EmailXML emailXml = parser.parseEmailXml(emailJob.getMailType().getType());
	//			
	//			HashMap<String, String> contentMap = new HashMap<>();
	//			contentMap.put("firstName", account.getFirstName());
	//			contentMap.put("fullName", account.getFirstNameLastName());
	//			contentMap.put("username", account.getUsername());
	//			contentMap.put("password", account.getPassword());
	//			contentMap.put("email", account.getEmail());
	//
	//			emailJob.setEmailSender(emailXml.getSender());
	//			emailJob.setEmailReceiver(account.getEmail());
	//			emailJob.setEmailCc(emailXml.getCc());
	//			emailJob.setEmailBcc(emailXml.getBcc());
	//			emailJob.setEmailSubject(emailXml.getActualSubject(contentMap));
	//			emailJob.setContent(emailXml.getActualContent(contentMap));
	//			processJobFacade.update(emailJob);
	//
	//			WsClientEmail.sendMail(emailJob.getEmailReceiver(), emailJob.getEmailSender(), 
	//					emailJob.getEmailCc(), emailJob.getEmailBcc(), emailJob.getEmailSubject(), emailJob.getContent());
	//
	//		}
	//		catch (Exception e) {
	//			LOGGER.error("Could not sent welcome mail", e);
	//		} 
	//
	//		if ( LOGGER.isDebugEnabled() ){
	//			LOGGER.debug("END sendMailWelcome()");
	//		}
	//	}

	//	public void sendMailError(ProcessJob job, String errorMessage, String errorStackTrace) {
	//		if ( LOGGER.isDebugEnabled() ){
	//			LOGGER.debug("START sendMailError()");
	//		}
	//
	//		XMLParser parser = new XMLParser();
	//
	//		try {
	//			EmailXML emailXml = parser.parseEmailXml(Constants.XML_EMAIL_ERROR);
	//			HashMap<String, String> contentMap = new HashMap<>();
	//			contentMap.put("errorMessage", errorMessage);
	//			contentMap.put("errorStackTrace", errorStackTrace);
	//			contentMap.put("jobID", job.getId()+"");
	//			contentMap.put("processType", job.getType().getName());
	//
	//
	//			String from = emailXml.getSender();
	//			String to = emailXml.getReceiver();
	//			String cc = emailXml.getCc();
	//			String bcc = emailXml.getBcc();
	//			String subject = emailXml.getSubject();
	//			String content = emailXml.getActualContent(contentMap);
	//
	//			WsClientEmail.sendMail(to, from,cc, bcc, subject, content);
	//		}
	//		catch (Exception e) {
	//			LOGGER.error("Could not sent error mail", e);
	//		} 
	//
	//		if ( LOGGER.isDebugEnabled() ){
	//			LOGGER.debug("END sendMailError()");
	//		}
	//	}

}
