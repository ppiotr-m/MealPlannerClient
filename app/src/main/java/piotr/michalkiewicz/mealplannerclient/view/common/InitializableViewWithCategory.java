package piotr.michalkiewicz.mealplannerclient.view.common;

public interface InitializableViewWithCategory<T> {
    void initWithData(T data, String category);
}
