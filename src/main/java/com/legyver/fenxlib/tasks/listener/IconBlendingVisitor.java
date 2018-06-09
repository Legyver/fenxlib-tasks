package com.legyver.fenxlib.tasks.listener;

import com.jfoenix.svg.SVGGlyph;
import com.legyver.core.exception.CoreException;
import com.legyver.fenxlib.factory.options.SimpleIconOptions;
import com.legyver.fenxlib.factory.options.SpinnerIconOptions;
import com.legyver.fenxlib.factory.options.TooltipIconOptions;
import com.legyver.fenxlib.factory.options.visitor.IconOptionVisitor;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class IconBlendingVisitor implements IconOptionVisitor {
	private final StackPane placeholder;

	public IconBlendingVisitor(StackPane placeholder) {
		this.placeholder = placeholder;
	}

	@Override
	public void visit(SimpleIconOptions sio) throws CoreException {
		Node icon = getChildIcon(placeholder);
		icon.blendModeProperty().set(sio.getBlendMode());
	}

	@Override
	public void visit(TooltipIconOptions tio) throws CoreException {
		Node icon = getChildIcon(placeholder);
		icon.blendModeProperty().set(tio.getBlendMode());
	}

	@Override
	public void visit(SpinnerIconOptions sio) throws CoreException {
		//noop
	}

	private Node getChildIcon(Node parent) {
		Node node = parent;
		if (parent instanceof Button) {
			node = ((Button) parent).getGraphic();
		} else if (parent instanceof Pane) {
			ObservableList<Node> childs = ((Pane) parent).getChildren();
			if (!childs.isEmpty()) {
				node = getChildIcon(childs.get(0));
			}
		}
		return node;
	}

}
