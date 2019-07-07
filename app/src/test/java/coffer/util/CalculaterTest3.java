package coffer.util;

import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * @author：张宝全
 * @date：2019-06-28
 * @Description： 参数化测试,允许开发人员使用不同的值反复运行同一个测试
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */

@RunWith(Parameterized.class)
public class CalculaterTest3 {

    private int expected;
    private int a;
    private int b;

    // 创建一个静态方法生成并返回测试数据，并注明@Parameters注解
    @Parameterized.Parameters //创建并返回测试数据
    public static Collection params(){
        return Arrays.asList(new Integer[][]{{3,1,2},{5,2,3}});
    }

    //接收并存储（实例化）测试数据
    public CalculaterTest3(int expected, int a, int b) {
        this.expected = expected;
        this.a = a;
        this.b = b;
    }

    @Test//使用测试数据测试
    public void sum() throws Exception {
        Calculater calculater = new Calculater();
        System.out.println("parameters : " + a + " + " + b);
        int result = calculater.sum(a, b);
        assertEquals(expected, result);
    }

}
