package com.legyver.fenxlib.tasks.listener;

import com.jfoenix.controls.JFXSpinner;
import com.legyver.core.exception.CoreException;
import com.legyver.fenxlib.factory.SvgIconFactory;
import com.legyver.fenxlib.factory.decorator.ButtonIconDecorator;
import com.legyver.fenxlib.factory.decorator.ButtonTooltipDecorator;
import com.legyver.fenxlib.factory.options.SimpleIconOptions;
import com.legyver.fenxlib.factory.options.SpinnerIconOptions;
import com.legyver.fenxlib.factory.options.TooltipIconOptions;
import com.legyver.fenxlib.factory.options.visitor.IconOptionVisitor;
import com.legyver.fenxlib.locator.LocationContext;
import com.legyver.fenxlib.tasks.factory.JFXSpinnerFactory;
import javafx.concurrent.Task;
import javafx.scene.layout.StackPane;

public class IconCreationVisitor implements IconOptionVisitor {

	private final StackPane iconPlaceholder;
	private final LocationContext locationContext;
	private final Task task;

	public IconCreationVisitor(Task task, StackPane iconPlaceholder, LocationContext locationContext) {
		this.iconPlaceholder = iconPlaceholder;
		this.locationContext = locationContext;
		this.task = task;
	}

	@Override
	public void visit(SimpleIconOptions sio) throws CoreException {
		iconPlaceholder.getChildren().add(new SvgIconFactory(sio).makeNode(locationContext));
	}

	@Override
	public void visit(TooltipIconOptions tio) throws CoreException {
		iconPlaceholder.getChildren().add(
				new ButtonTooltipDecorator(tio.getToolTip(),
						new ButtonIconDecorator(tio.getOnClick(),
								new SvgIconFactory(tio))).makeNode(locationContext));
	}

	@Override
	public void visit(SpinnerIconOptions sio) throws CoreException {
		JFXSpinner spinner = new JFXSpinnerFactory(sio).makeNode(locationContext);
		iconPlaceholder.getChildren().add(spinner);
		spinner.progressProperty().bind(task.progressProperty());
	}

}
