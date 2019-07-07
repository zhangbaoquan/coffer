package coffer.util;

import org.junit.Test;

/**
 * @author：张宝全
 * @date：2019-06-29
 * @Description： 预期的异常
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class ExcetionTest {

    String s;

    @Test(expected = NullPointerException.class)
    public void testException(){
        s.length();
    }
}
