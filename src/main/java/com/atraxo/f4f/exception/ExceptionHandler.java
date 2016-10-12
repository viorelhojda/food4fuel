package com.atraxo.f4f.exception;

import java.util.Iterator;

import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.event.ExceptionQueuedEvent;

import org.apache.log4j.Logger;

import com.atraxo.f4f.util.WebPage;

public class ExceptionHandler extends ExceptionHandlerWrapper {

	private final javax.faces.context.ExceptionHandler wrapped;
	private static final Logger LOGGER = Logger.getLogger(ExceptionHandler.class);
	
	public ExceptionHandler(final javax.faces.context.ExceptionHandler wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public javax.faces.context.ExceptionHandler getWrapped() {
		return this.wrapped;
	}

	@Override
	public void handle(){
		
		for (final Iterator<ExceptionQueuedEvent> it = getUnhandledExceptionQueuedEvents().iterator(); it.hasNext();) {
			Throwable t = it.next().getContext().getException();

			LOGGER.error("Faces Exception", t);
			
			if (t instanceof Exception) {
				try {
					WebPage.redirectToPage(WebPage.PAGE_ERROR);
				} finally {
					it.remove();
				}
			}
		
		}
		getWrapped().handle();
	}


}
