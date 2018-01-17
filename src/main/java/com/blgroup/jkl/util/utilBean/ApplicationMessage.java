package com.blgroup.jkl.util.utilBean;

import java.io.Serializable;
import java.util.Arrays;



/**
 * An object of this class should hold one message. A message consists of a key
 * and an array of values in form of objects. The actual text of the message may
 * be configured in a properties file. This class uses 
 * {@link java.text.MessageFormat} to create a message. The values are
 * considered as the place-holders. E.g.
 * <p>
 * key=The item '{0}' is invalid because '{1}' does not exist.
 * </p>
 * Here {0} and {1} are the place-holders.
 * 
 * @author Feelyn
 * @version 1.0
 * 
 */
public class ApplicationMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private Enum<?> key = null;

    private Object[] values = null;
    
    private Enum<Severity> severity = Severity.ERROR;
    
    public static enum Severity {
    	INFO,
    	WARNING,
    	ERROR,
    	FATAL
    }

    /**
     * Creates a new <code>ApplicationMessage</code> instance from a message key
     * <p>
     * </p>
     * 
     * @param key
     *            The message key
     */
    public ApplicationMessage(Enum<?> key) {
        this.key = key;
    }

    /**
     * Creates a new <code>ApplicationMessage</code> instance from a message key
     * and a place-holder value
     * <p>
     * </p>
     * 
     * @param key
     *            The message key
     * @param value
     *            The place-holder value
     */
    public ApplicationMessage(Enum<?> key, Object value) {
        this.key = key;
        this.values = new Object[1];
        this.values[0] = value;
    }

    /**
     * Creates a new <code>ApplicationMessage</code> instance from a message key
     * and an array of place-holder values
     * <p>
     * </p>
     * 
     * @param key
     *            The message key
     * @param values
     *            The array of place-holder values
     */
    public ApplicationMessage(Enum<?> key, Object... values) {
        this.key = key;
        this.values = values;
    }
    
    public ApplicationMessage(Enum<?> key, Severity severity, Object... values) {
        this.key = key;
        this.values = values;
        this.severity = severity;
    }

    /**
     * Returns the key of this object
     * <p>
     * </p>
     * 
     * @return key
     */
    public Enum<?> getKey() {
        return key;
    }

    /**
     * Returns the array of place-holder values of this object
     * <p>
     * </p>
     * 
     * @return Values
     */
    public Object[] getValues() {
        return values;
    }
    
    public void setValues(Object[] values) {
        this.values=values;
    }
    

	public void setSeverity(Enum<Severity> severity) {
		this.severity = severity;
	}

	public Enum<Severity> getSeverity() {
		return severity;
	}
	
	@Override
	public String toString() {
		return String.valueOf(key)+":" + Arrays.toString(values); //TODO need to process via MessageFormat
	}

}