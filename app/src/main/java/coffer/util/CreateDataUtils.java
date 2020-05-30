package coffer.util;

import java.util.ArrayList;
import java.util.List;

import coffer.customViewDemo.bean.MutiTypeData;

/**
 * @author：张宝全
 * @date：2020/5/27
 * @Description： 数据生产类
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class CreateDataUtils {

    /**
     * 返回多类型的数据
     * @return 多类型数据
     */
    public static MutiTypeData getMutiData(){
        MutiTypeData mutiTypeData = new MutiTypeData();
        List<MutiTypeData.Data> dataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            MutiTypeData.Data data = new MutiTypeData.Data();
            if (i == 0){
                data.type = "banner";
                ArrayList<MutiTypeData.Baby> babyList = new ArrayList<>();
                MutiTypeData.Baby baby = new MutiTypeData.Baby();
                baby.url = "http://book.img.ireader.com/idc_1/f_webp/d6395c13/group61/M00/9E/4C/" +
                        "CmQUOF4fz5iEOabgAAAAAOIsmL0128642244.jpg?v=5GEDyHpb&t=CmQUOF4fz5g";
                baby.title = "";
                baby.desc = "";
                babyList.add(baby);
                data.baby = babyList;
            }else if (i == 1){
                data.type = "head";
                ArrayList<MutiTypeData.Baby> babyList = new ArrayList<>();
                for (int j= 0; j < 3; j++) {
                    MutiTypeData.Baby baby = new MutiTypeData.Baby();
                    baby.url = "http://book.img.ireader.com/idc_1/f_webp/9f231564/group61/M00/1C/0E/" +
                            "CmQUOF7NCdiEJSz-AAAAAGryIKs808989816.jpg?v=DOgyZd_o&t=CmQUOF7NCdg.";
                    baby.title = "banner"+j;
                    baby.desc = "banner 描述"+j;
                    babyList.add(baby);
                }
                data.baby = babyList;
            }else if (i == 3 || i == 5 || i == 7){
                data.type = "horizon";
                ArrayList<MutiTypeData.Baby> babyList = new ArrayList<>();
                for (int j = 0; j < 2; j++) {
                    MutiTypeData.Baby baby = new MutiTypeData.Baby();
                    baby.url = "http://book.img.ireader.com/idc_1/f_webp/da3f2189/group61/M00/1C/68/" +
                            "CmQUOF7NRsuEc5LdAAAAAAVwQow948452623.jpg?v=E2ItkY58&t=CmQUOF7NRss.";
                    baby.title = "横排title" + j;
                    baby.desc =  "横排desc" + j;
                    babyList.add(baby);
                }
                data.baby = babyList;
            }else if (i == 2 || i == 9){
                data.type = "grid";
                ArrayList<MutiTypeData.Baby> babyList = new ArrayList<>();
                for (int j = 0; j < 3; j++) {
                    MutiTypeData.Baby baby = new MutiTypeData.Baby();
                    baby.url = "http://book.img.ireader.com/idc_1/f_webp/88be5495/group61/M00/1C/01/" +
                            "CmQUOV7M8emEdI6uAAAAAD4A-FU451451262.jpg?v=0txlUaws&t=CmQUOV7M8ek.";
                    baby.title = "网格title" + j;
                    baby.desc = "网格desc" + j;
                    babyList.add(baby);
                }
                data.baby = babyList;
            }else if (i == 8){
                data.type = "portrait";
                ArrayList<MutiTypeData.Baby> babyList = new ArrayList<>();
                for (int j = 0; j < 25; j++) {
                    MutiTypeData.Baby baby = new MutiTypeData.Baby();
                    baby.url = "http://book.img.ireader.com/idc_1/f_webp/b34970c9/group61/M00/19/32/" +
                            "CmQUOV7Km1-EL9scAAAAAM-YplM477552621.jpg?v=rMnJxlD_&t=CmQUOV7Km18.";
                    baby.title = "竖排title" + j;
                    baby.desc = "竖排desc" + j;
                    babyList.add(baby);
                }
                data.baby = babyList;
            }
            dataList.add(data);
        }
        mutiTypeData.data = dataList;
        return mutiTypeData;
    }
}
