package indi.yume.view.appkotlin

import org.junit.Assert
import org.junit.Test

import indi.yume.view.appkotlin.util.GeneratePwd

import org.junit.Assert.*

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
class ExampleUnitTest {
    @Test
    @Throws(Exception::class)
    fun addition_isCorrect() {
        assertEquals(4, (2 + 2).toLong())
    }

    @Test
    fun testRandomList() {
        for (i in 1..12) {
            val list = GeneratePwd.getRandomList(8, 8)
            var sum = 0
            for (j in list)
                sum += j

            println("size: " + i)
            Assert.assertEquals(sum.toLong(), 8)
        }
    }

    @Test
    fun testRandomPwd() {
        for (i in 0..9) {
            //            System.out.println(GeneratePwd.getPwd(i + 8, true, true, true, true));
            println(GeneratePwd.getPwd(i + 8, true, true, true, false))
            println(GeneratePwd.getPwd(i + 8, true, true, false, false))
            println(GeneratePwd.getPwd(i + 8, true, false, false, false))
            println(GeneratePwd.getPwd(i + 8, false, false, false, false))
        }
    }
}