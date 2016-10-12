package com.atraxo.f4f;

import javax.faces.application.Application;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PostConstructApplicationEvent;
import javax.faces.event.PreDestroyApplicationEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

import com.atraxo.f4f.job.quartz.JobScheduler;

public class FacesAppListener implements SystemEventListener {

	public void processEvent(SystemEvent event) throws AbortProcessingException {
		if(event instanceof PostConstructApplicationEvent){
			JobScheduler.getInstance().start();
		}
	 
		if(event instanceof PreDestroyApplicationEvent){
			JobScheduler.getInstance().stop();
		}
	}

	public boolean isListenerForSource(Object source) {
		return source instanceof Application;
	}

}
