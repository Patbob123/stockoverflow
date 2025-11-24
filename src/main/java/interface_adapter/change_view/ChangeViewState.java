package interface_adapter.change_view;

import java.util.Stack;

public class ChangeViewState {
    private final Stack<String> screenStack;

    public ChangeViewState() {
        this.screenStack = new Stack<>();
    }

    public void pushView(String viewName) {
        // maybe i should just reset the whole thing when mainmenu appears
        if (screenStack.isEmpty() || !screenStack.peek().equals(viewName)) {
            screenStack.push(viewName);
        }
    }

    public String popView() {
        if (screenStack.size() > 1) {
            screenStack.pop();
            return screenStack.peek();
        }
        return screenStack.isEmpty() ? null : screenStack.peek();
    }

    public String getCurrentView() {
        return screenStack.isEmpty() ? null : screenStack.peek();
    }

    public boolean canGoBack() {
        return screenStack.size() > 1;
    }
}