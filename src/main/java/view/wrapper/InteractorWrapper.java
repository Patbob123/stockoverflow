package view.wrapper;

import use_case.InputBoundary;
import use_case.OutputBoundary;

public interface InteractorWrapper<I extends InputBoundary, O extends OutputBoundary> {
    I create(O outputBoundary);
}
