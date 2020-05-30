package coffer.util;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author：张宝全
 * @date：2019-06-28
 * @Description： 创建一个测试套件 SuiteTest ，以便将前面的测试类一起执行
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({CalculaterTest.class,CalculaterTest2.class})
public class SuiteTest {
}
