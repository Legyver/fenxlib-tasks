package com.legyver.fenxlib.tasks;

import com.legyver.core.exception.CoreException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public enum QueueState {
	INSTANCE;
	private final MutexCount outstanding = new MutexCount();
	private final BooleanProperty complete = new SimpleBooleanProperty();
	private final BooleanProperty errors = new SimpleBooleanProperty();

	QueueState() {
		complete.bind(outstanding.emptyProperty());
	}

	public void register() throws CoreException {
		outstanding.increment();
	}

	public void completed() throws CoreException {
		outstanding.decrement();
	}

	public ReadOnlyBooleanProperty completeProperty() {
		return complete;
	}

	public BooleanProperty errorsProperty() {
		return errors;
	}
}
