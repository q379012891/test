package com.blgroup.jkl.util.utilBean;

/**
 * <p>
 * This is the base class for all business exception or checked exceptions in
 * the application. A business exception should wrap {@link ApplicationMessages}.
 * A business exception can contain {@linkplain Severity}.
 * </p>
 * 
 * @author Feelyn
 * 
 */
public class BusinessException extends Exception {

	private static final long serialVersionUID = 1L;

	private ApplicationMessages messages;

	private Severity severity = Severity.ERROR;

	/**
	 * The severities for a business exception. Default is {@linkplain Severity.ERROR}
	 */
	public static enum Severity {
		WARNING, ERROR, FATAL
	}

	/**
	 * 
	 */
	protected BusinessException() {

	}

	/**
	 * 
	 * @param message
	 */
	protected BusinessException(String message) {
		super(message);
	}
	
	/**
	 * 
	 * @param message
	 * @param rootCause
	 */
	protected BusinessException(Throwable rootCause) {
		super(rootCause);
	}
	

	/**
	 * 
	 * @param message
	 * @param rootCause
	 */
	protected BusinessException(String message, Throwable rootCause) {
		super(message, rootCause);
	}

	/**
	 * If this exception is used for internationalization by keeping the message in resource bundle use this constructor.
	 * @param key The key of the message
	 */
	protected BusinessException(Enum<?> key) {
		super(new ApplicationMessage(key).toString());
		this.messages = new ApplicationMessages(key);
	}

	/**
	 * 
	 * @param key The key of the message
	 * @param rootCause The root cause of the exception
	 */
	protected BusinessException(Enum<?> key, Throwable rootCause) {
		super(new ApplicationMessage(key).toString(), rootCause);
		this.messages = new ApplicationMessages(key);
	}
	
    /**
     * 
     * Creates a new instance of BusinessException.  
     *  
     * @param applicationMessage
     * @param rootCause
     */
    protected BusinessException(ApplicationMessage applicationMessage, Throwable rootCause){
        
        super(applicationMessage.toString(), rootCause);
        this.messages = new ApplicationMessages(applicationMessage);
    }
    

	/**
	 * 
	 * @param key The key of the message
	 * @param values The place-holder values for the message of the exception
	 */
	protected BusinessException(Enum<?> key, Object... values) {
		super(new ApplicationMessage(key, values).toString());
		this.messages = new ApplicationMessages(key, values);
	}

	/**
	 * 
	 * @param applicationMessage
	 */
	protected BusinessException(ApplicationMessage applicationMessage) {
		super(applicationMessage.toString());
		this.messages = new ApplicationMessages(applicationMessage);
	}

	/**
	 * 
	 * @param applicationMessages
	 */
	protected BusinessException(ApplicationMessages applicationMessages) {
		super(applicationMessages.toString());
		this.messages = applicationMessages;
	}

	/**
	 * 
	 * @return
	 */
	public ApplicationMessages getApplicationMessages() {
		return messages;
	}

	/**
	 * 
	 * @param messages
	 */
	public void setApplicationMessages(ApplicationMessages messages) {
		this.messages = messages;
	}

	/**
	 * 
	 * @return
	 */
	public Severity getSeverity() {
		return severity;
	}

	/**
	 * 
	 * @param severity
	 */
	public void setSeverity(Severity severity) {
		this.severity = severity;
	}

}
