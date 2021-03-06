package com.legyver.fenxlib.tasks.factory.options;

import com.legyver.fenxlib.factory.options.IconWidgetOptions;
import javafx.concurrent.Task;

public class TaskStatusIconConfig<T extends IconWidgetOptions> {

	protected final T options;

	public TaskStatusIconConfig(T options) {
		this.options = options;
	}

	public T getWidgetOptions(Task task) {
		return options;
	}

}
