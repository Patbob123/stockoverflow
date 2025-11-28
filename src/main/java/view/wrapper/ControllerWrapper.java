package view.wrapper;

import interface_adapter.AbsController;
import use_case.InputBoundary;

public interface ControllerWrapper<C extends AbsController, I extends InputBoundary> {
    C create(I inputBoundary);
}
