package interface_adapter.change_view;

import java.util.Stack;

/**
 * Keeps track of what views the user has visited.
 */
public class ChangeViewState {
    private final Stack<String> screenStack;

    /**
     * Constructor for ChangeViewState
     */
    public ChangeViewState() {
        this.screenStack = new Stack<>();
    }

    /**
     * Checks if there is a view in stack
     */
    public boolean canGoBack() {
        return screenStack.size() > 1;
    }

    /**
     * Adds a new view to the stack
     */
    public void pushView(String viewName) {
        if (screenStack.isEmpty() || !screenStack.peek().equals(viewName)) {
            screenStack.push(viewName);
        }
    }

    /**
     * Goes back one view
     */
    public String popView() {
        if (screenStack.size() > 1) {
            screenStack.pop();
            return screenStack.peek();
        }
        return screenStack.isEmpty() ? null : screenStack.peek();
    }
}
