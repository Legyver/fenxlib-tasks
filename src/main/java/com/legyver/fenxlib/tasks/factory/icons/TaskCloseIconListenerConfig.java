package com.legyver.fenxlib.tasks.factory.icons;

import com.legyver.fenxlib.factory.options.TooltipIconOptions;
import com.legyver.fenxlib.tasks.factory.options.TaskStatusIconActionConfig;
import com.legyver.fenxlib.tasks.factory.options.TaskStatusIconConfig;
import javafx.concurrent.Worker.State;
import javafx.scene.effect.BlendMode;

public class TaskCloseIconListenerConfig extends AbstractTaskStatusIconConfig<TaskStatusIconConfig<TooltipIconOptions>> {

	public TaskCloseIconListenerConfig() {
		super();
		taskStateIconConfigs.put(State.CANCELLED, new TaskStatusIconActionConfig(defaultIcon(BlendMode.DARKEN), cancelTask(null)));
		taskStateIconConfigs.put(State.FAILED, new TaskStatusIconActionConfig(defaultIcon(BlendMode.DARKEN), cancelTask(null)));
		taskStateIconConfigs.put(State.RUNNING, new TaskStatusIconActionConfig(defaultIcon(BlendMode.LIGHTEN), cancelTask(null)));//lighten during running
		taskStateIconConfigs.put(State.READY, new TaskStatusIconActionConfig(defaultIcon(BlendMode.DARKEN), cancelTask(null)));
		taskStateIconConfigs.put(State.SUCCEEDED, new TaskStatusIconActionConfig(defaultIcon(BlendMode.DARKEN), cancelTask(null)));
	}

	private static TooltipIconOptions defaultIcon(BlendMode blendMode) {
		return new TooltipIconOptions("close, remove, times", "#999999", "Dismiss", null, 15, blendMode);
	}
}
