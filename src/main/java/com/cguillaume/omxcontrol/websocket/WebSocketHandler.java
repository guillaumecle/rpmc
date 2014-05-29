package com.cguillaume.omxcontrol.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class WebSocketHandler {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public void handleDefault(String action, Object message) {
		logger.warn("No handler define for this action (" + action + ")");
	}
}