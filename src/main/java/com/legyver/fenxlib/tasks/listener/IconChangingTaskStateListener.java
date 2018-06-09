package com.legyver.fenxlib.tasks.listener;

import com.legyver.fenxlib.factory.options.visitor.IconOptionVisitor;
import com.legyver.fenxlib.locator.LocationContext;
import com.legyver.fenxlib.tasks.factory.icons.AbstractTaskStatusIconConfig;
import com.legyver.fenxlib.tasks.factory.icons.TaskStateIconListenerConfig;
import javafx.concurrent.Task;
import javafx.scene.layout.StackPane;

public class IconChangingTaskStateListener extends AbstractIconTaskStateListener {

	private final LocationContext locationContext;

	public IconChangingTaskStateListener(AbstractTaskStatusIconConfig taskStateListenerConfig, Task task, StackPane iconPlaceholder, LocationContext locationContext) {
		super(taskStateListenerConfig, task, iconPlaceholder, true);
		this.locationContext = locationContext;
	}

	@Override
	protected IconOptionVisitor getVisitor() {
		return new IconCreationVisitor(task, iconPlaceholder, locationContext);
	}

}
