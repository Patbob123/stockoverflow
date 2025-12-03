package use_case.change_view;

import lombok.Getter;

@Getter
public class ChangeViewOutputData {
    private final String viewName;

    /**
     * Constructor for ChangeViewOutputData
     */
    public ChangeViewOutputData(String viewName) {
        this.viewName = viewName;
    }

}
