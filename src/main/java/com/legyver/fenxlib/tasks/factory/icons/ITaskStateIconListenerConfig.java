package com.legyver.fenxlib.tasks.factory.icons;

import com.legyver.fenxlib.tasks.factory.options.TaskStatusIconConfig;
import javafx.concurrent.Worker.State;

interface ITaskStateIconListenerConfig<T extends TaskStatusIconConfig> {

	T getTaskStateIconConfig(State newState);

}
