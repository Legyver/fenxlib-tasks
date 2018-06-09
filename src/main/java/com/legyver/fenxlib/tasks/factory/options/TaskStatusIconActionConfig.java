package com.legyver.fenxlib.tasks.factory.options;

import com.legyver.fenxlib.factory.options.TooltipIconOptions;
import com.legyver.fenxlib.tasks.factory.icons.AbstractTaskStatusIconConfig.ActionEventHandlerProducer;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class TaskStatusIconActionConfig extends TaskStatusIconConfig<TooltipIconOptions> {

	private final ActionEventHandlerProducer handlerProducer;

	public TaskStatusIconActionConfig(TooltipIconOptions options, ActionEventHandlerProducer handlerProducer) {
		super(options);
		this.handlerProducer = handlerProducer;
	}

	@Override
	public TooltipIconOptions getWidgetOptions(Task task) {
		if (handlerProducer != null) {
			EventHandler<ActionEvent> handler = handlerProducer.handler(task);
			options.setOnClick(handler);
		}
		return options;
	}

}
