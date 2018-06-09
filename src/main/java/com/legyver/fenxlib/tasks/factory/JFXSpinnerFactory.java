package com.legyver.fenxlib.tasks.factory;

import com.jfoenix.controls.JFXSpinner;
import com.legyver.core.exception.CoreException;
import com.legyver.fenxlib.factory.NodeFactory;
import com.legyver.fenxlib.locator.LocationContext;
import com.legyver.fenxlib.factory.options.SpinnerIconOptions;

public class JFXSpinnerFactory implements NodeFactory<JFXSpinner> {
	private SpinnerIconOptions spinnerIconOptions;

	public JFXSpinnerFactory(SpinnerIconOptions spinnerIconOptions) {
		this.spinnerIconOptions = spinnerIconOptions;
	}

	@Override
	public JFXSpinner makeNode(LocationContext lc) throws CoreException {
		JFXSpinner spinner = new JFXSpinner();
		spinner.getStyleClass().add(spinnerIconOptions.getCssClass());
		spinner.setRadius(spinnerIconOptions.getRadius());

		return spinner;
	}

}
