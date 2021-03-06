package is.hi.hbv601g.myquizapp.networking;

public interface NetworkCallback<T> {

    void onSuccess(T result);

    void onFailure(String errorString);
}
