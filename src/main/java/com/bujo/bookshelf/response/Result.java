package com.bujo.bookshelf.response;

import java.util.ArrayList;
import java.util.List;

/**
 * The Result class is used to store the outcome of an action. It contains an {@link ActionStatus}, list of messages,
 * and a payload of generic type T.
 * The messages list is used to store any messages related to the outcome of the action,
 * such as error messages or other information.
 *
 * @param <T> the payload of the result
 * @author skylar
 */
public class Result<T> {
	private ActionStatus status = ActionStatus.SUCCESS;
	private final List<String> messages = new ArrayList<>();
	private T payload;

	/**
	 * Add message to the messages list
	 *
	 * @param status {@link ActionStatus} of the action
	 * @param message message to be added
	 */
	public void addMessage(ActionStatus status, String message) {
		this.status = status;
		messages.add(message);
	}

	/**
	 * Check if the result is successful
	 *
	 * @return boolean indicating the outcome of the result
	 */
	public boolean isSuccess() {
		return messages.isEmpty();
	}
	
	public ActionStatus getStatus() {
		return status;
	}
	
	public List<String> getMessages() {
		return new ArrayList<>(messages);
	}
	
	public T getPayload() {
		return payload;
	}
	
	public void setPayload(T payload) {
		this.payload = payload;
	}
}
