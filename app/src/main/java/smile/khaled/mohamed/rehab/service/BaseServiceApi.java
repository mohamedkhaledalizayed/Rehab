package smile.khaled.mohamed.rehab.service;

public class BaseServiceApi {

    public static ServiceApi service;

    public static synchronized ServiceApi getInstance(){
        if (service==null){
             service= RetrofitModule.getInstance().getService();
        }

        return service;
    }
}
