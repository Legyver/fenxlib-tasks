package com.legyver.fenxlib.tasks.listener;

import com.legyver.core.exception.CoreException;
import com.legyver.fenxlib.factory.options.visitor.IconOptionVisitor;
import com.legyver.fenxlib.tasks.factory.icons.AbstractTaskStatusIconConfig;
import com.legyver.fenxlib.tasks.factory.options.TaskStatusIconConfig;
import com.legyver.util.nippe.Base;
import com.legyver.util.nippe.Step;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.Worker.State;
import javafx.scene.layout.StackPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.legyver.core.exception.CoreException.unwrap;
import static com.legyver.core.exception.CoreException.wrap;

public abstract class AbstractIconTaskStateListener<T extends TaskStatusIconConfig> implements ChangeListener<State> {

	protected static final Logger LOGGER = LoggerFactory.getLogger(IconChangingTaskStateListener.class);
	protected final AbstractTaskStatusIconConfig<T> taskStateListenerConfig;
	protected final Task task;
	protected final StackPane iconPlaceholder;
	private final boolean clearChildren;

	public AbstractIconTaskStateListener(AbstractTaskStatusIconConfig<T> taskStateListenerConfig, Task task, StackPane iconPlaceholder, boolean clearChildren) {
		this.taskStateListenerConfig = taskStateListenerConfig;
		this.task = task;
		this.iconPlaceholder = iconPlaceholder;
		this.clearChildren = clearChildren;
	}

	@Override
	public void changed(ObservableValue<? extends State> observable, State oldValue, State newValue) {
		try {
			unwrap(() -> new Step<>(new Step<>(new Base<>(taskStateListenerConfig),
					c -> c.getTaskStateIconConfigs().get(newValue)),
					c -> wrap(() -> {
						if (clearChildren) {
							iconPlaceholder.getChildren().clear();
						}
						c.getWidgetOptions(task).accept(getVisitor());
						return c;
			})).execute());
		} catch (CoreException corex) {
			LOGGER.error("Error thrown handling TaskStateChange", corex);
		}
	}

	protected abstract IconOptionVisitor getVisitor();

}
