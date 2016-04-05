package indi.yume.app.passwordbox;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import indi.yume.app.passwordbox.util.CheckPwdUtil;
import indi.yume.app.passwordbox.util.GeneratePwd;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testCheckPwd() {
        check("12345678");
        check("12345");
        check("962854");
        check("abcde");
        check("abcd1234");
        check("AbCd1234");
        check("AbcD");
        check("abcd");
        check("bush");
        check("Bush");
    }

    private void check(String pwd) {
        System.out.println("level " + CheckPwdUtil.checkPwdStrong(pwd, 6, 8) + " | pwd: " + pwd);
    }

    @Test
    public void testRandomList() {
        for(int i = 1; i < 13; i++) {
            List<Integer> list = GeneratePwd.getRandomList(8, 8);
            int sum = 0;
            for(int j : list)
                sum += j;

            System.out.println("size: " + i);
            Assert.assertEquals(sum, 8);
        }
    }

    @Test
    public void testRandomPwd() {
        for(int i = 0; i < 10; i++) {
//            System.out.println(GeneratePwd.getPwd(i + 8, true, true, true, true));
            System.out.println(GeneratePwd.getPwd(i + 8, true, true, true, false));
            System.out.println(GeneratePwd.getPwd(i + 8, true, true, false, false));
            System.out.println(GeneratePwd.getPwd(i + 8, true, false, false, false));
            System.out.println(GeneratePwd.getPwd(i + 8, false, false, false, false));
        }
    }

    @Test
    public void testGenerateChar() {
        for(int i = 0; i < 20; i++) {
            char c = GeneratePwd.getSmallAtoZ();
            Assert.assertFalse(c + "", c <= 'z' && c >= 'a');
        }
    }
}