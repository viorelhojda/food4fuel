package com.atraxo.f4f.web;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import com.atraxo.f4f.util.UserUtils;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;

@ApplicationScoped
@ManagedBean(name="applicationMB")
public class ApplicationMB extends AbstractMB{
	private static final long serialVersionUID = -5374444826773195516L;
	private static final Logger LOGGER = Logger.getLogger(ApplicationMB.class);
	
	private String applicationName = "";
	private String applicationVersion = "";
	private String applicationBuildDate = "";
	private String applicationBuildBy = "";
	private String applicationBuildWith = "";
	private String applicationBuildJDK = "";
	private List<String> applicationDependecies = new ArrayList<>();
	
	@PostConstruct
	public void init(){
		try {
			ServletContext application = (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
			InputStream inputStream = application.getResourceAsStream("/META-INF/MANIFEST.MF");
			Manifest manifest = new Manifest(inputStream);
			
			Attributes mainAttributes = manifest.getMainAttributes();
			
			String implementationVersion = mainAttributes.getValue("Implementation-Version");
			String implementationTitle = mainAttributes.getValue("Implementation-Title");
			String buildDate = mainAttributes.getValue("Build-Time");
			String buildBy = mainAttributes.getValue("Built-By");
			String buildWith = mainAttributes.getValue("Created-By");
			String buildJDK = mainAttributes.getValue("Build-Jdk");
			String dependecies = mainAttributes.getValue("Class-Path");
			
			if( implementationTitle != null ){
				applicationName = implementationTitle;
			}
			
			if ( implementationVersion != null ) {
				applicationVersion = implementationVersion;
			}
			
			if ( buildDate != null ){
				applicationBuildDate = buildDate;
			}
			
			if( buildBy != null ){
				applicationBuildBy = buildBy;
			}
			
			if( buildWith != null){
				applicationBuildWith = buildWith;
			}
			
			if( buildJDK != null ){
				applicationBuildJDK = buildJDK;
			}
			
			if( dependecies != null){
				String[] parts = dependecies.split(Pattern.quote(".jar "));
		        for(String element : parts){
		        	applicationDependecies.add(element);
		        }
			}
			
		} catch (IOException e) {
			LOGGER.error("Error initializing ApplicationMB", e);
		}
	}
	
	public String displayedApplicationName(){
		return getApplicationName();
	}
	
	public String getApplicationName() {
		return applicationName;
	}

	public String getApplicationVersion() {
		return applicationVersion;
	}

	public String getApplicationBuildDate() {
		return applicationBuildDate;
	}
	
	public String getApplicationBuildBy() {
		return applicationBuildBy;
	}
	
	public String getApplicationBuildWith() {
		return applicationBuildWith;
	}
	
	public String getApplicationBuildJDK() {
		return applicationBuildJDK;
	}
	
	public List<String> getApplicationDependecies() {
		return applicationDependecies;
	}

	public String getApplicationVersionedName(){
		return applicationName + " v" + applicationVersion;
	}
	public String getApplicationVersionedNameWithDate(){
		return applicationName + " version: " + applicationVersion + " from " + applicationBuildDate;
	}
	public String getApplicationVersionWithDate(){
		return "Version: " + applicationVersion + " from " + applicationBuildDate;
	}
	
	public void preProcessPDF(Object document) throws IOException{
		Document doc = (Document) document;
		doc.setPageSize(PageSize.A4.rotate());
	}
	
	public String formExportFileName(String param){
		return param + "_" + UserUtils.getCurrentLoggedUser().getAccount().getUsername() + "_" + DateMB.convertToDate(new Date());
	}
	
}
