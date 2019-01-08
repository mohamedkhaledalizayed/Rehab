package smile.khaled.mohamed.rehab.viewmodel.both;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import smile.khaled.mohamed.rehab.repository.both.SignInRepository;
import smile.khaled.mohamed.rehab.service.requests.both.SignInData;
import smile.khaled.mohamed.rehab.service.responses.both.signin.SignInResponse;

public class SignInViewModel extends AndroidViewModel {

    private SignInRepository repository;
    public SignInViewModel(@NonNull Application application) {
        super(application);
        repository=new SignInRepository(application);
    }

    public void signIn(String username,String password){
        selected.setValue(repository.sigiIn(username,password));
    }

    private final MutableLiveData<SignInResponse> selected = new MutableLiveData<SignInResponse>();

    public void select(SignInResponse item) {
        selected.setValue(item);
    }

    public LiveData<SignInResponse> getSelected(String username,String password) {
        signIn(username,password);
        return selected;
    }
}
