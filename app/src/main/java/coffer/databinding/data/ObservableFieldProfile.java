package coffer.databinding.data;

import androidx.databinding.ObservableInt;

/**
 * @author：张宝全
 * @date：2019-04-28
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class ObservableFieldProfile {


    private  String name;

    private  String lastName;

    private  ObservableInt likes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ObservableInt getLikes() {
        return likes;
    }

    public void setLikes(ObservableInt likes) {
        this.likes = likes;
    }


}
