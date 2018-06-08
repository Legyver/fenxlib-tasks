package com.legyver.fenxlib.tasks.factory.options;

import com.legyver.fenxlib.factory.options.TooltipIconOptions;
import com.legyver.fenxlib.tasks.factory.options.TaskStateListenerConfig.ActionEventHandlerProducer;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class TaskStatusIconConfig {

	private final TooltipIconOptions options;
	private final ActionEventHandlerProducer handlerProducer;

	public TaskStatusIconConfig(TooltipIconOptions options, ActionEventHandlerProducer handlerProducer) {
		this.options = options;
		this.handlerProducer = handlerProducer;
	}

	public TooltipIconOptions getTooltipIconOptions(Task task) {
		EventHandler<ActionEvent> handler = handlerProducer.handler(task);
		options.setOnClick(handler);
		return options;
	}
}
