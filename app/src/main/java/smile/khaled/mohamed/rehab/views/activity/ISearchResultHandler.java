package smile.khaled.mohamed.rehab.views.activity;

public interface ISearchResultHandler {
    void onResevationClick(String id);
    void onShareClick(String id);
    void addFavourite(String id);
    void deleteFavourite(String id);
}
