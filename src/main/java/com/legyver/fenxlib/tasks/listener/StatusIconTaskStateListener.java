package com.legyver.fenxlib.tasks.listener;

import com.legyver.core.exception.CoreException;
import com.legyver.fenxlib.factory.SvgIconFactory;
import com.legyver.fenxlib.factory.decorator.ButtonIconDecorator;
import com.legyver.fenxlib.factory.decorator.ButtonTooltipDecorator;
import com.legyver.fenxlib.factory.options.TooltipIconOptions;
import com.legyver.fenxlib.locator.LocationContext;
import com.legyver.fenxlib.tasks.factory.options.TaskStateListenerConfig;
import com.legyver.util.nippe.Base;
import com.legyver.util.nippe.Step;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.legyver.core.exception.CoreException.unwrap;
import static com.legyver.core.exception.CoreException.wrap;


public class StatusIconTaskStateListener implements ChangeListener<Worker.State> {

	private static final Logger LOGGER = LoggerFactory.getLogger(StatusIconTaskStateListener.class);
	private final TaskStateListenerConfig taskStateListenerConfig;
	private final StackPane iconPlaceholder;
	private final LocationContext locationContext;
	private final Task task;

	public StatusIconTaskStateListener(TaskStateListenerConfig taskStateListenerConfig, Task task, StackPane iconPlaceholder, LocationContext locationContext) {
		this.taskStateListenerConfig = taskStateListenerConfig;
		this.task = task;
		this.iconPlaceholder = iconPlaceholder;
		this.locationContext = locationContext;
	}

	@Override
	public void changed(ObservableValue<? extends State> observable, State oldValue, State newValue) {
		try {
			unwrap(() -> new Step<>(new Step<>(new Step<>(new Base<>(taskStateListenerConfig),
					args -> args.getTaskStateIconConfigs().get(newValue)),
					args -> args.getTooltipIconOptions(task)),
					args -> wrap(() -> {
						iconPlaceholder.getChildren().clear();
						iconPlaceholder.getChildren().add(makeButton(locationContext, args));
						return args;
					})).execute());
		} catch (CoreException corex) {
			LOGGER.error("Error thrown handling TaskStateChange", corex);
		}
	}

	private Button makeButton(LocationContext lc, TooltipIconOptions statusIcon) throws CoreException {
		return new ButtonTooltipDecorator(statusIcon.getToolTip(), new ButtonIconDecorator(statusIcon.getOnClick(), new SvgIconFactory(statusIcon))).makeNode(lc);
	}

}
