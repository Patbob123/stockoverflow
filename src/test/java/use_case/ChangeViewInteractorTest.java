package use_case;

import interface_adapter.change_view.ChangeViewState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.change_view.ChangeViewInteractor;
import use_case.change_view.ChangeViewOutputBoundary;
import use_case.change_view.ChangeViewOutputData;

import static org.junit.jupiter.api.Assertions.*;

class TestChangeViewOutputBoundary implements ChangeViewOutputBoundary {

    String lastView = null;

    @Override
    public void prepareView(ChangeViewOutputData outputData) {
        lastView = outputData.getViewName();
    }

    @Override
    public interface_adapter.ViewModel<?> getViewModel(String viewName) {
        return null;
    }
}

public class ChangeViewInteractorTest {

    private ChangeViewInteractor interactor;
    private TestChangeViewOutputBoundary mockPresenter;
    private ChangeViewState state;

    @BeforeEach
    void setup() {
        mockPresenter = new TestChangeViewOutputBoundary();
        state = new ChangeViewState();
        interactor = new ChangeViewInteractor(mockPresenter, state);
    }

    @Test
    void testChangeToView() {
        interactor.execute("MainMenu");
        assertEquals("MainMenu", mockPresenter.lastView);
    }

    @Test
    void testGoBack() {
        interactor.execute("MainMenu");
        interactor.execute("PortfolioView");
        interactor.execute("");
        assertEquals("MainMenu", mockPresenter.lastView);
    }
}