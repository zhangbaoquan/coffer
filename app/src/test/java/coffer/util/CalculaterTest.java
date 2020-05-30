package coffer.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author：张宝全
 * @date：2019-06-28
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class CalculaterTest {

    private Calculater mCalculator;
    private int a = 1;
    private int b = 3;

    // 该方法必须在类中的每个测试之前执行
    @Before //在测试开始之前回调的方法
    public void setUp() throws Exception{
        mCalculator = new Calculater();
    }

    @Test //我们需要测试的方法
    public void sum() {

        int result = mCalculator.sum(a, b);
        // 第一个参数："sum(a, b)" 打印的tag信息 （可省略）
        // 第二个参数： 3 期望得到的结果
        // 第三个参数  result：实际返回的结果
        // 第四个参数  0 误差范围（可省略）
        assertEquals("sum(a, b)",4,result,0);
    }

    @Test //我们需要测试的方法
    public void substract() {
        int result = mCalculator.substract(a, b);
        assertEquals(-3,result,2);
    }
}