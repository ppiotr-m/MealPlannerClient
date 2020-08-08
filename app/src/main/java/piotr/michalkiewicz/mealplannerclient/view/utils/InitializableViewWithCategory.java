package piotr.michalkiewicz.mealplannerclient.view.utils;

public interface InitializableViewWithCategory<T> {
    void initWithData(T data, String category);
}
