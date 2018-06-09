package com.legyver.fenxlib.tasks.factory.icons;

import com.legyver.fenxlib.factory.options.SimpleIconOptions;
import com.legyver.fenxlib.factory.options.SpinnerIconOptions;
import com.legyver.fenxlib.tasks.factory.options.TaskStatusIconConfig;
import com.legyver.fenxlib.tasks.factory.options.TaskStatusSpinnerConfig;
import javafx.concurrent.Worker.State;

public class TaskSpinnerIconListenerConfig extends AbstractTaskStatusIconConfig<TaskStatusIconConfig> {

	public TaskSpinnerIconListenerConfig() {
		super();
		taskStateIconConfigs.put(State.CANCELLED, new TaskStatusSpinnerConfig(new SimpleIconOptions("ban", "#999999", 25)));
		taskStateIconConfigs.put(State.FAILED, new TaskStatusSpinnerConfig(new SimpleIconOptions("exclamation-circle", "#b31a1a", 25)));
		taskStateIconConfigs.put(State.RUNNING, new TaskStatusSpinnerConfig(new SpinnerIconOptions("materialDesign-blue", 15)));
		taskStateIconConfigs.put(State.READY, new TaskStatusSpinnerConfig(new SimpleIconOptions("clock2", "#999999", 25)));
	}
}
