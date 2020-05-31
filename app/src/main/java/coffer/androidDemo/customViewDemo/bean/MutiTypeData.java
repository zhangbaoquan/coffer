package coffer.androidDemo.customViewDemo.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author：张宝全
 * @date：2020/5/27
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class MutiTypeData {

    public int code;

    public List<Data> data;

    public static final class Data{
        public String type;
        public ArrayList<Baby> baby;
    }

    public static final class Baby{
        public String url;
        public String title;
        public String desc;
    }

}
