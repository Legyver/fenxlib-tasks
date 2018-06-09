package com.legyver.fenxlib.tasks.factory.icons;

import com.legyver.fenxlib.tasks.factory.options.TaskStatusIconConfig;
import java.util.EnumMap;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractTaskStatusIconConfig<T extends TaskStatusIconConfig> implements ITaskStateIconListenerConfig<T> {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractTaskStatusIconConfig.class);

	protected final EnumMap<Worker.State, T> taskStateIconConfigs = new EnumMap<>(Worker.State.class);

	public EnumMap<State, T> getTaskStateIconConfigs() {
		return taskStateIconConfigs;
	}

	@Override
	public T getTaskStateIconConfig(State newState) {
		return taskStateIconConfigs.get(newState);
	}

	protected ActionEventHandlerProducer noop(ActionEvent event) {
		return (Task task) -> (e) -> {//noop
			LOGGER.debug("clicked:noop");
		};
	}

	protected ActionEventHandlerProducer cancelTask(ActionEvent event) {
		return (Task task) -> (e) -> {
			LOGGER.debug("clicked:canceltask");
			task.cancel();
		};
	}

	/**
	 * Interface so we can abstractly declare the behavior of the EventHandler
	 * common to all tasks
	 */
	@FunctionalInterface
	public interface ActionEventHandlerProducer {

		EventHandler<ActionEvent> handler(Task task);
	}

}
