package com.legyver.fenxlib.tasks;

import com.legyver.tuktukfx.adapter.JavaFxAdapter;
import com.legyver.tuktukfx.task.ITask;

public class NamedTask extends JavaFxAdapter {

	private final String taskName;

	public NamedTask(String taskName, ITask wrappedTask) {
		super(wrappedTask);
		this.taskName = taskName;
	}

	public String getTaskName() {
		return taskName;
	}

}
