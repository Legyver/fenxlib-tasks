package com.legyver.fenxlib.tasks.factory.options;

import com.legyver.fenxlib.factory.options.TooltipIconOptions;

import java.util.EnumMap;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class TaskStateListenerConfig {
	private final EnumMap<Worker.State, TaskStatusIconConfig> taskStateIconConfigs = new EnumMap<>(Worker.State.class);

	public TaskStateListenerConfig() {
		taskStateIconConfigs.put(State.SUCCEEDED, new TaskStatusIconConfig(new TooltipIconOptions("check-circle", "#4d804d", "Completed", null, 15), noop(null)));
		taskStateIconConfigs.put(State.CANCELLED, new TaskStatusIconConfig(new TooltipIconOptions("ban", "#999999", "Cancelled", null, 15), noop(null)));
		taskStateIconConfigs.put(State.FAILED, new TaskStatusIconConfig(new TooltipIconOptions("exclamation-circle", "#b31a1a", "Failed", null, 15), noop(null)));
		taskStateIconConfigs.put(State.RUNNING, new TaskStatusIconConfig(new TooltipIconOptions("minus-circle", "#b31a1a", "Cancel task", null, 15), cancelTask(null)));
		taskStateIconConfigs.put(State.READY, new TaskStatusIconConfig(new TooltipIconOptions("clock2", "#b3ccff", "Cancel pending task", null, 15), cancelTask(null)));
	}
	public EnumMap<State, TaskStatusIconConfig> getTaskStateIconConfigs() {
		return taskStateIconConfigs;
	}

	private ActionEventHandlerProducer noop(ActionEvent event) {
		return (Task task) -> (e) -> {//noop
			System.out.println("clicked:noop");
		};
	}

	private ActionEventHandlerProducer cancelTask(ActionEvent event) {
		return (Task task) -> (e) -> {
			System.out.println("clicked:canceltask");
			task.cancel();
		};
	}

	@FunctionalInterface
	public interface ActionEventHandlerProducer {

		public EventHandler<ActionEvent> handler(Task task);
	}

}
