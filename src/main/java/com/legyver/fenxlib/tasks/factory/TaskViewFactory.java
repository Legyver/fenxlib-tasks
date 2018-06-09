package com.legyver.fenxlib.tasks.factory;

import com.legyver.core.exception.CoreException;
import com.legyver.fenxlib.factory.NodeFactory;
import com.legyver.fenxlib.factory.SpaceableFactory;
import com.legyver.fenxlib.factory.SvgIconFactory;
import com.legyver.fenxlib.factory.decorator.ButtonIconDecorator;
import com.legyver.fenxlib.factory.decorator.ButtonTooltipDecorator;
import com.legyver.fenxlib.factory.options.IconOptions;
import com.legyver.fenxlib.factory.options.IconWidgetOptions;
import com.legyver.fenxlib.factory.options.TooltipIconOptions;
import com.legyver.fenxlib.locator.LocationContext;
import com.legyver.fenxlib.tasks.NamedTask;
import com.legyver.fenxlib.tasks.factory.icons.TaskCloseIconListenerConfig;
import com.legyver.fenxlib.tasks.factory.icons.TaskSpinnerIconListenerConfig;
import com.legyver.fenxlib.tasks.factory.icons.TaskStateIconListenerConfig;
import com.legyver.fenxlib.tasks.factory.options.TaskStateListenerConfigOptions;
import com.legyver.fenxlib.tasks.listener.IconChangingTaskStateListener;

import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;

public class TaskViewFactory implements NodeFactory<GridPane>, SpaceableFactory {
	private final ListView tasks;
	private final NamedTask task;
	private final TaskStateListenerConfigOptions taskStateListenerConfig;

	public TaskViewFactory(ListView tasks, NamedTask task, TaskStateListenerConfigOptions taskStateListenerConfig) {
		this.tasks = tasks;
		this.task = task;
		this.taskStateListenerConfig = taskStateListenerConfig;
	}

	@Override
	public GridPane makeNode(LocationContext lc) throws CoreException {
		GridPane pane = new GridPane();
		pane.maxHeight(50);
		pane.minHeight(45);
		pane.getStyleClass().add("task-view");
		StackPane.setAlignment(pane, Pos.CENTER);

		ColumnConstraints columnConstraintSpinner = new ColumnConstraints(35, 45, 70);
		ColumnConstraints columnConstraintMidL = new ColumnConstraints(100, 150, 200);
		ColumnConstraints columnConstraintMidR = new ColumnConstraints(20, 30, 40);
		ColumnConstraints columnConstraintClose = new ColumnConstraints(5, 25, 35);
		pane.getColumnConstraints().addAll(columnConstraintSpinner, columnConstraintMidL, columnConstraintMidR, columnConstraintClose);

		layoutTopline(pane, lc);
		layoutStatus(pane, lc);
		layoutSpinner(pane, lc);
		GridPane.setVgrow(pane, Priority.NEVER);
		GridPane.setHgrow(pane, Priority.NEVER);
		return pane;
	}

	private void layoutStatus(GridPane pane, LocationContext lc) throws CoreException {
		Label status = new Label();
		status.textProperty().bind(task.stateProperty().asString());
		pane.add(status, 1, 1);

		Label statusInfo = new Label();
		statusInfo.textProperty().bind(task.titleProperty());
		pane.add(statusInfo, 1, 2, 2, 1);

		StackPane statusAction = new StackPane();
		TaskStateIconListenerConfig taskStateIconListenerConfig = taskStateListenerConfig.getTaskStatusActionConfig();
		TooltipIconOptions options = taskStateIconListenerConfig.getTaskStateIconConfig(Worker.State.READY).getWidgetOptions(task);
		Button dismiss = new ButtonTooltipDecorator(options.getToolTip(), new ButtonIconDecorator(options.getOnClick(), new SvgIconFactory(options))).makeNode(lc);
		statusAction.getChildren().add(dismiss);
		pane.add(statusAction, 3, 2);

		task.stateProperty().addListener(new IconChangingTaskStateListener(taskStateIconListenerConfig, task, statusAction, lc));
	}

	private void layoutSpinner(GridPane pane, LocationContext lc) throws CoreException {
		StackPane stack = new StackPane();
		TaskSpinnerIconListenerConfig taskStateIconListenerConfig = taskStateListenerConfig.getTaskStatusSpinnerConfig();
		IconWidgetOptions options = taskStateIconListenerConfig.getTaskStateIconConfig(Worker.State.READY).getWidgetOptions(task);
		stack.getChildren().add(new SvgIconFactory((IconOptions) options).makeNode(lc));

		task.stateProperty().addListener(new IconChangingTaskStateListener(taskStateIconListenerConfig, task, stack, lc));

		GridPane.setHalignment(pane, HPos.LEFT);
		GridPane.setValignment(pane, VPos.CENTER);
		pane.add(stack, 0, 0, 1, 4);
	}

	private void layoutTopline(GridPane pane, LocationContext lc) throws CoreException {
		Label taskName = new Label(task.getTaskName());
		pane.add(taskName, 1, 0, 2, 1);
		EventHandler<ActionEvent> dismissTask = (ActionEvent event) -> {
			tasks.getItems().remove(pane);
		};
		TaskCloseIconListenerConfig taskStateIconListenerConfig = taskStateListenerConfig.getTaskDismissIconConfig();
		TooltipIconOptions options = taskStateIconListenerConfig.getTaskStateIconConfig(Worker.State.READY).getWidgetOptions(task);

		Button dismiss = new ButtonTooltipDecorator(options.getToolTip(), new ButtonIconDecorator(dismissTask, new SvgIconFactory(options))).makeNode(lc);
		dismiss.setAlignment(Pos.TOP_RIGHT);
		dismiss.disableProperty().bind(task.runningProperty());

		pane.add(dismiss, 3, 0);
	}


}
