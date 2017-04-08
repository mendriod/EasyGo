package mendroid.easygo;


/**
 * Created by umayaeswaran on 31-10-2016.
 */

public interface BaseFragmentPresenter<T> {
    void init(T view, BaseActivity baseActivity);
}