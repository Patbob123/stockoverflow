package view.wrapper;

public interface DirectlyCreateFactory<V,T> {
    V create(T t);
}
