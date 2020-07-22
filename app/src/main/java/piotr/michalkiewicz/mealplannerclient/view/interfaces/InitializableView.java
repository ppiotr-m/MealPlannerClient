package piotr.michalkiewicz.mealplannerclient.view.interfaces;

public interface InitializableView<T> {
    //  frameNr is a temporary thing, will be deleted after real implementation of HomeScreen is done
    void initWithData(T data, int frameNr);
}
