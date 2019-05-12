package coffer.databinding.data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * @author：张宝全
 * @date：2019-04-28
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class ProfileLiveDataViewModel extends ViewModel {

    private final MutableLiveData mName = new MutableLiveData("Ada");
    private final MutableLiveData mLastName = new MutableLiveData("Lovelace");
    private final MutableLiveData mLikes = new MutableLiveData(0);
}
