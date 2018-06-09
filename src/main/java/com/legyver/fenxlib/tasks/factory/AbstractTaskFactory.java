package com.legyver.fenxlib.tasks.factory;

import com.legyver.core.exception.CoreException;
import com.legyver.fenxlib.factory.options.BorderPaneInitializationOptions;
import com.legyver.fenxlib.locator.DefaultLocationContext;
import com.legyver.fenxlib.locator.LocationContext;
import com.legyver.fenxlib.locator.LocationContextDecorator;
import com.legyver.fenxlib.locator.query.ComponentQuery;
import com.legyver.fenxlib.locator.query.QueryableComponentRegistry;
import com.legyver.fenxlib.tasks.NamedTask;
import com.legyver.fenxlib.tasks.factory.options.TaskStateListenerConfigOptions;
import com.legyver.fenxlib.util.GuiUtil;
import com.legyver.tuktukfx.adapter.TaskStatusAdapter;
import com.legyver.tuktukfx.task.ITask;
import java.util.Optional;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

public class AbstractTaskFactory {
	private final TaskStateListenerConfigOptions taskStateListenerConfig;

	public AbstractTaskFactory(TaskStateListenerConfigOptions taskStateListenerConfig) {
		this.taskStateListenerConfig = taskStateListenerConfig;
	}

	public AbstractTaskFactory() {
		this(new TaskStateListenerConfigOptions());
	}

	protected void bindStatus(NamedTask task) throws CoreException {
		QueryableComponentRegistry registry = GuiUtil.getComponentRegistry();
		LocationContext taskPanel = new LocationContextDecorator(new DefaultLocationContext(BorderPaneInitializationOptions.REGION_RIGHT));
		taskPanel.setName(TaskPanelFactory.TASKS_PANE_TITLE);
		Optional<ListView> tasksQuery = new ComponentQuery.QueryBuilder(registry)
				.inRegion(BorderPaneInitializationOptions.REGION_RIGHT)
				.inSubRegion(TaskPanelFactory.TASKS_PANE_TITLE)
				.named(TaskPanelFactory.TASKS_LIST).execute();
		ListView tasks = tasksQuery.get();

		TaskViewFactory taskViewFactory = new TaskViewFactory(tasks, task, taskStateListenerConfig);
		GridPane taskView = taskViewFactory.makeNode(taskPanel);
		tasks.getItems().add(taskView);
		tasks.minWidthProperty().bind(taskView.widthProperty().multiply(1.1));
	}

	public void makeDummyTask() throws CoreException {
		NamedTask fxTask = new NamedTask("Dummy Task", (ITask) (TaskStatusAdapter tsa) -> null //noop
		);
		bindStatus(fxTask);
	}

}
