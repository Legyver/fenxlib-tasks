package com.legyver.fenxlib.tasks.listener;

import com.legyver.fenxlib.factory.options.visitor.IconOptionVisitor;
import com.legyver.fenxlib.tasks.factory.icons.TaskStateIconListenerConfig;
import javafx.concurrent.Task;
import javafx.scene.layout.StackPane;

public class IconBlendingTaskStateListener extends AbstractIconTaskStateListener {

	public IconBlendingTaskStateListener(TaskStateIconListenerConfig taskStateListenerConfig, Task task, StackPane iconPlaceholder) {
		super(taskStateListenerConfig, task, iconPlaceholder, false);
	}

	@Override
	protected IconOptionVisitor getVisitor() {
		return new IconBlendingVisitor(iconPlaceholder);
	}

}
