package app.wrapper;

public interface DirectlyCreateFactory<V, T> {
    V create(T something);
}
