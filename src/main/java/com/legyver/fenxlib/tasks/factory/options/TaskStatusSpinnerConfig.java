package com.legyver.fenxlib.tasks.factory.options;

import com.legyver.fenxlib.factory.options.IconWidgetOptions;
import javafx.concurrent.Task;

public class TaskStatusSpinnerConfig<T extends IconWidgetOptions> extends TaskStatusIconConfig<T> {

	public TaskStatusSpinnerConfig(T options) {
		super(options);
	}

	@Override
	public T getWidgetOptions(Task task) {
		return options;
	}

}
