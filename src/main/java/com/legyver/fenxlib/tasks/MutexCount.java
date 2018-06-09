package com.legyver.fenxlib.tasks;

import com.legyver.core.exception.CoreException;
import java.util.concurrent.Semaphore;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class MutexCount {
	private final BooleanProperty empty = new SimpleBooleanProperty();
	private int count = 0;
	private final Semaphore countMutex = new Semaphore(1);

	public void increment() throws CoreException {
		add(1);
	}

	public void decrement() throws CoreException {
		add(-1);
	}

	public void add(int value) throws CoreException {
		try {
			countMutex.acquire();
			count += value;
			if (count == 0) {
				empty.setValue(Boolean.TRUE);
			} else {
				empty.setValue(Boolean.FALSE);
			}
			countMutex.release();
		} catch (InterruptedException ex) {
			throw new CoreException("Unable to update queue count: ", ex);
		}
	}

	public ReadOnlyBooleanProperty emptyProperty() {
		return empty;
	}

}
