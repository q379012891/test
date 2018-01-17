package com.blgroup.jkl.util.utilBean;

/**
 * This exception should be used in service layer. Recommend to extend this
 * exception with proper business context name;
 * 
 * @author Feelyn
 * 
 */
public class ServiceException extends BusinessException {
	private Object[] values;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static enum Message {
		
		VALIDATION_ERROR, DATA_ERROR, SYSTEM_ERROR, FUNCTIONAL_ERROR;
	}

	public ServiceException(Object... values) {
		super(Message.FUNCTIONAL_ERROR);
		this.values = values;
	}

	public ServiceException() {
		
	}

	public ServiceException(Enum<?> key) {
		super(key);
	}

	public ServiceException(Enum<?> key, Throwable rootCause) {
		super(key, rootCause);
	}

	public ServiceException(Enum<?> key, Object... values) {
		super(key, values);
	}

	public ServiceException(ApplicationMessage applicationMessage) {
		super(applicationMessage);
	}

	public ServiceException(ApplicationMessages applicationMessages) {
		super(applicationMessages);

	}

	public Object[] getValues() {
		return values;
	}

	public void setValues(Object[] values) {
		this.values = values;
	}

	

}
