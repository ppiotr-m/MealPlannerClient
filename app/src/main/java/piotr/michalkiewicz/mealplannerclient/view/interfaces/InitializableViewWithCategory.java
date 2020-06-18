package piotr.michalkiewicz.mealplannerclient.view.interfaces;

public interface InitializableViewWithCategory<T> {
    void initWithData(T data, String category);
}
