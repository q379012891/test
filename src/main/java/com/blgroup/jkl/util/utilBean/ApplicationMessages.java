package com.blgroup.jkl.util.utilBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <p>
 * This class encapsulates application or business messages.
 * </p>
 * An object of this class hold one or more than one messages.
 * 
 * <p>
 * Each individual message is described by an {@link ApplicationMessage} object,
 * which contains a message key (to be looked up in an appropriate message
 * resources database), and up to four place-holder arguments used for
 * parametric substitution in the resulting message.
 * </p>
 * </p>
 * 
 * @author Feelyn
 * @version 1.0
 * 
 */
public class ApplicationMessages implements Serializable {
    /**
     * <p>
     * </p>
     */
    private static final long serialVersionUID = 1L;

    private List<ApplicationMessage> messages = null;

    private Object context;
    /**
     * <p>
     * Create an empty <code>ApplicationMessages</code> object.
     * </p>
     */
    public ApplicationMessages() {
        super();
        this.messages = new ArrayList<ApplicationMessage>();
    }

    /**
     * <p>
     * Create an <code>ApplicationMessages</code> object with the given
     * <code>ApplicationMessage</code> object.
     * </p>
     * <p>
     * </p>
     * 
     * @param message
     *            The message to be initially added to this object.
     */
    public ApplicationMessages(ApplicationMessage message) {
        this();
        addApplicationMessage(message);
    }

    /**
     * Create an <code>ApplicationMessages</code> object initialized with the
     * given list of <code>ApplicationMessage</code> objects.
     * <p>
     * </p>
     * 
     * @param messages
     *            List of <code>ApplicationMessage</code> objects
     */
    public ApplicationMessages(List<ApplicationMessage> messages) {
        this();
        addApplicationMessage(messages);
    }

    /**
     * Create an <code>ApplicationMessages</code> object initialized with the
     * given list of <code>ApplicationMessage</code> objects.
     * <p>
     * </p>
     * 
     * @param messages
     *            List of <code>ApplicationMessage</code> objects
     */
    public ApplicationMessages(ApplicationMessages messages) {
        this();
        addApplicationMessage(messages);
    }

    /**
     * <p>
     * Create an <code>ApplicationMessages</code> object initialized with the
     * given message key.
     * </p>
     * Inside, the constructor will create a new <code>ApplicationMessage</code>
     * and add to this object.
     * <p>
     * </p>
     * 
     * @param key
     *            A message key
     */
    public ApplicationMessages(Enum<?> key) {
        this(key, null);
    }

    /**
     * <p>
     * Create an <code>ApplicationMessages</code> object initialized with the
     * given message key and place-holder arguments.
     * </p>
     * Inside, the constructor will create a new <code>ApplicationMessage</code>
     * and add to this object.
     * <p>
     * </p>
     * 
     * @param key
     *            A message key
     * 
     * @param values
     *            The place-holder arguments
     */
    public ApplicationMessages(Enum<?> key, Object[] values) {
        this();
        addApplicationMessage(new ApplicationMessage(key, values));
    }

    /**
     * Add a <code>ApplicationMessage</code> to this object.
     * <p>
     * </p>
     * 
     * @param message
     *            An <code>ApplicationMessage</code> instance
     */
    public void addApplicationMessage(ApplicationMessage message) {
        this.messages.add(message);
    }

    /**
     * Add all <code>ApplicationMessage</code> objects from the given messages
     * object to the existing messages.
     * <p>
     * </p>
     * 
     * @param messages
     *            An <code>ApplicationMessages</code> object whose individual
     *            messages would be added to this object.
     */
    public void addApplicationMessage(ApplicationMessages messages) {
        if (messages == null)
            return;
        for (ApplicationMessage message : messages.getAllMessages()) {
            addApplicationMessage(message);
        }
    }

    /**
     * Add a list of <code>ApplicationMessage</code> to this object.
     * <p>
     * </p>
     * 
     * @param messages
     *            List of <code>ApplicationMessage</code>
     */
    public void addApplicationMessage(List<ApplicationMessage> messages) {
        this.messages.addAll(messages);
    }

    /**
     * Retrieves the list of <code>ApplicationMessage</code> objects wrapped by
     * this object
     * <p>
     * </p>
     * 
     * @return A list of <code>ApplicationMessage</code>
     */
    public List<ApplicationMessage> getAllMessages() {
        return messages;
    }
    
    /**
     * <p>Retrieves the list of ApplicationMessage keys associated with this 
     * ApplicationMessages and returns to the caller</p>
     * 
     * @return
     */
    public List<Enum<?>> getKeys() {
        List<Enum<?>> keys = null;
        List<ApplicationMessage> messages = getAllMessages();
        if (messages != null && !messages.isEmpty()) {
            keys = new ArrayList<Enum<?>>();
            for (ApplicationMessage message : messages){
                keys.add(message.getKey());
            }
        }
        return keys;
    }
    
    @Override
    public String toString() {
    	List<ApplicationMessage> messages = getAllMessages();
    	StringBuilder sb = new StringBuilder();
        if (messages != null && !messages.isEmpty()) {
            for (ApplicationMessage message : messages){
            	sb.append(message);
            	sb.append("/n");
             }
        }
        return sb.toString();
    }

	public void setContext(Object context) {
		this.context = context;
	}

	public Object getContext() {
		return context;
	}
}
