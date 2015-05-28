package io.scheduler.gui;

import java.awt.Component;
import java.util.List;

public interface Factory {
	
	public List<?> get();
	Object generateObject();
	Component generateComponent(Object o);
	public void removeFromDB(Object o);
}
