package com.legyver.fenxlib.tasks.factory;

import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.svg.SVGGlyph;
import com.legyver.core.exception.CoreException;
import com.legyver.fenxlib.factory.NodeFactory;
import com.legyver.fenxlib.factory.SpaceableFactory;
import com.legyver.fenxlib.factory.SvgIconFactory;
import com.legyver.fenxlib.factory.decorator.ButtonIconDecorator;
import com.legyver.fenxlib.factory.decorator.ButtonTooltipDecorator;
import com.legyver.fenxlib.factory.options.IconOptions;
import com.legyver.fenxlib.factory.options.TooltipIconOptions;
import com.legyver.fenxlib.locator.LocationContext;
import com.legyver.fenxlib.tasks.NamedTask;
import com.legyver.fenxlib.tasks.factory.options.TaskStateListenerConfig;
import com.legyver.fenxlib.tasks.listener.StatusIconTaskStateListener;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;

public class TaskViewFactory implements NodeFactory<StackPane>, SpaceableFactory {
	private final ListView tasks;
	private final NamedTask task;
	private final TaskStateListenerConfig taskStateListenerConfig;

	public TaskViewFactory(ListView tasks, NamedTask task, TaskStateListenerConfig taskStateListenerConfig) {
		this.tasks = tasks;
		this.task = task;
		this.taskStateListenerConfig = taskStateListenerConfig;
	}

	@Override
	public StackPane makeNode(LocationContext lc) throws CoreException {
		StackPane outer = new StackPane();

		GridPane pane = new GridPane();
		pane.maxHeight(50);
		pane.minHeight(45);
		pane.getStyleClass().add("task-view");
		outer.getChildren().add(pane);
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
		return outer;
	}

	private void layoutStatus(GridPane pane, LocationContext lc) throws CoreException {
		Label status = new Label();
		status.textProperty().bind(task.stateProperty().asString());
		pane.add(status, 1, 1);

		Label statusInfo = new Label();
		statusInfo.textProperty().bind(task.titleProperty());
		pane.add(statusInfo, 1, 2, 2, 1);

		StackPane statusAction = new StackPane();
		TooltipIconOptions options = taskStateListenerConfig.getTaskStateIconConfigs().get(Worker.State.READY).getTooltipIconOptions(task);
		Button dismiss = new ButtonTooltipDecorator(options.getToolTip(), new ButtonIconDecorator(options.getOnClick(), new SvgIconFactory(options))).makeNode(lc);
		statusAction.getChildren().add(dismiss);
		pane.add(statusAction, 3, 2);

		task.stateProperty().addListener(new StatusIconTaskStateListener(taskStateListenerConfig, task, statusAction, lc));
	}

	private void layoutSpinner(GridPane pane, LocationContext lc) throws CoreException {
		StackPane stack = new StackPane();
		SVGGlyph waiting = new SvgIconFactory(new IconOptions("clock2", "#999999", 25)).makeNode(lc);
		stack.getChildren().add(waiting);

		SVGGlyph cancelled = new SvgIconFactory(new IconOptions("ban", "#999999", 25)).makeNode(lc);

		JFXSpinner spinner = new JFXSpinner();
		spinner.getStyleClass().add("materialDesign-blue");
		spinner.setRadius(15);
		spinner.progressProperty().bind(task.progressProperty());

		task.stateProperty().addListener(new ChangeListener<Worker.State>() {
			@Override
			public void changed(ObservableValue<? extends State> observable, State oldValue, State newValue) {
				switch (newValue) {
					case RUNNING:
						stack.getChildren().clear();
						stack.getChildren().add(spinner);
						break;
					case CANCELLED:
						stack.getChildren().clear();
						stack.getChildren().add(cancelled);
						break;
					default://nochange
				}

			}
		});

		GridPane.setHalignment(pane, HPos.LEFT);
		GridPane.setValignment(pane, VPos.CENTER);
		pane.add(stack, 0, 0, 1, 4);
	}

	private void layoutTopline(GridPane pane, LocationContext lc) throws CoreException {
		Label taskName = new Label(task.getTaskName());
		pane.add(taskName, 1, 0, 2, 1);
		EventHandler<ActionEvent> dismissEvent = (ActionEvent event) -> {
			tasks.getItems().remove(pane);
		};
		Button dismiss = new ButtonTooltipDecorator("Dismiss", new ButtonIconDecorator(dismissEvent, new SvgIconFactory(new IconOptions("close, remove, times", "#999999", 15)))).makeNode(lc);
		dismiss.setAlignment(Pos.TOP_RIGHT);
		dismiss.disableProperty().bind(task.runningProperty());
		SVGGlyph icon = (SVGGlyph) dismiss.getGraphic();
		icon.blendModeProperty().set(BlendMode.DARKEN);
		task.stateProperty().addListener(new ChangeListener<Worker.State>() {
			@Override
			public void changed(ObservableValue<? extends State> observable, State oldValue, State newValue) {
				switch (newValue) {
					case RUNNING:
						icon.blendModeProperty().set(BlendMode.LIGHTEN);
						break;
					case SUCCEEDED:
					case CANCELLED:
						icon.blendModeProperty().set(BlendMode.DARKEN);
					default://do nothing
				}
			}
		});

		pane.add(dismiss, 3, 0);
	}


}
