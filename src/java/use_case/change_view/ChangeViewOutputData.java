package use_case.change_view;

public class ChangeViewOutputData {
    private final String viewName;

    public ChangeViewOutputData(String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }
}
