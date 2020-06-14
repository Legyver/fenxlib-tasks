package com.legyver.fenxlib.tasks.factory.icons;

import com.legyver.fenxlib.factory.options.TooltipIconOptions;
import com.legyver.fenxlib.tasks.factory.options.TaskStatusIconActionConfig;

import javafx.concurrent.Worker.State;

public class TaskStateIconListenerConfig extends AbstractTaskStatusIconConfig<TaskStatusIconActionConfig> {

	public TaskStateIconListenerConfig() {
		super();
		taskStateIconConfigs.put(State.SUCCEEDED, new TaskStatusIconActionConfig(new TooltipIconOptions("check-circle", "#4d804d", "Completed", null, 15), noop(null)));
		taskStateIconConfigs.put(State.CANCELLED, new TaskStatusIconActionConfig(new TooltipIconOptions("ban", "#999999", "Cancelled", null, 15), noop(null)));
		taskStateIconConfigs.put(State.FAILED, new TaskStatusIconActionConfig(new TooltipIconOptions("exclamation-circle", "#b31a1a", "Failed", null, 15), noop(null)));
		taskStateIconConfigs.put(State.RUNNING, new TaskStatusIconActionConfig(new TooltipIconOptions("minus-circle", "#b31a1a", "Cancel task", null, 15), cancelTask(null)));
		taskStateIconConfigs.put(State.READY, new TaskStatusIconActionConfig(new TooltipIconOptions("ban", "#999999", "Cancel pending task", null, 15), cancelTask(null)));
	}

}
