package com.cguillaume.omxcontrol;

import java.util.ArrayList;
import java.util.List;

import com.cguillaume.omxcontrol.websocket.WebSocketActionWrapper;
import com.cguillaume.omxcontrol.websocket.WebSocketManager;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class Playlist {

	@Inject
	private transient Omx omx;
	@Inject
	private transient WebSocketManager webSocketManager;

	private boolean playOnAdd = true;
	private List<String> list = new ArrayList<>();
	private int current = -1;
	
	public void add(String track) {
		list.add(track);
		webSocketManager.sendToAll(new WebSocketActionWrapper("playlistUpdated", this));
		if (playOnAdd && !omx.isAlive())
			omx.startPlaying(next());
	}
	
	public boolean hasNext() {
		return list.size() > current + 1;
	}
	
	public String next() {
		current++;
		webSocketManager.sendToAll(new WebSocketActionWrapper("updateCurrent", current));
		return list.get(current);
	}

	public List<String> getList() {
		return list;
	}

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
		webSocketManager.sendToAll(new WebSocketActionWrapper("updateCurrent", current));
		omx.startPlaying(list.get(current));
	}
}
