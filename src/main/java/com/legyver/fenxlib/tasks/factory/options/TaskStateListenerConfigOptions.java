package com.legyver.fenxlib.tasks.factory.options;

import com.legyver.fenxlib.tasks.factory.icons.TaskCloseIconListenerConfig;
import com.legyver.fenxlib.tasks.factory.icons.TaskSpinnerIconListenerConfig;
import com.legyver.fenxlib.tasks.factory.icons.TaskStateIconListenerConfig;

public class TaskStateListenerConfigOptions {
	private final TaskStateIconListenerConfig taskStatusActionConfig;
	private final TaskSpinnerIconListenerConfig taskStatusSpinnerConfig;
	private final TaskCloseIconListenerConfig taskDismissIconConfig;

	public TaskStateListenerConfigOptions(TaskStateIconListenerConfig taskStatusActionConfig, TaskSpinnerIconListenerConfig taskStatusSpinnerConfig, TaskCloseIconListenerConfig taskDismissIconConfig) {
		this.taskStatusActionConfig = taskStatusActionConfig;
		this.taskStatusSpinnerConfig = taskStatusSpinnerConfig;
		this.taskDismissIconConfig = taskDismissIconConfig;
	}

	public TaskStateListenerConfigOptions() {
		this(new TaskStateIconListenerConfig(), new TaskSpinnerIconListenerConfig(), new TaskCloseIconListenerConfig());
	}

	public TaskStateIconListenerConfig getTaskStatusActionConfig() {
		return taskStatusActionConfig;
	}

	public TaskSpinnerIconListenerConfig getTaskStatusSpinnerConfig() {
		return taskStatusSpinnerConfig;
	}

	public TaskCloseIconListenerConfig getTaskDismissIconConfig() {
		return taskDismissIconConfig;
	}

}
