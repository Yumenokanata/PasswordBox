package indi.yume.view.appkotlin.util

import java.util.ArrayList
import java.util.Collections
import java.util.Random

import rx.functions.Func0

/**
 * Created by bush2 on 2016/4/4.
 */
object GeneratePwd {
    private val random = Random(0)

    fun getPwd(length: Int,
               hasBigger: Boolean,
               hasSmall: Boolean,
               hasNumber: Boolean,
               hasSpecial: Boolean): String {
        var hasSmall = hasSmall
        var size = 0
        if (hasBigger) size++
        if (hasSmall) size++
        if (hasNumber) size++
        if (hasSpecial) size++
        if (size == 0) {
            hasSmall = true
            size = 1
        }

        val list = getRandomList(length - size, size)
        val randomNumList = ArrayList<Int>()
        var index = 0
        if (hasBigger) {
            randomNumList.add(list[index] + 1)
            index++
        } else {
            randomNumList.add(0)
        }
        if (hasSmall) {
            randomNumList.add(list[index] + 1)
            index++
        } else {
            randomNumList.add(0)
        }
        if (hasNumber) {
            randomNumList.add(list[index] + 1)
            index++
        } else {
            randomNumList.add(0)
        }
        if (hasSpecial) {
            randomNumList.add(list[index] + 1)
            index++
        } else {
            randomNumList.add(0)
        }

        return getPwd(randomNumList[0],
                randomNumList[1],
                randomNumList[2],
                randomNumList[3])
    }

    fun getRandomList(sum: Int, size: Int): List<Int> {
        if (size <= 0)
            return ArrayList()
        if (size == 1)
            return listOf(sum)

        val randomList = ArrayList<Int>()
        val sumItemNum = Math.max(sum, size)

        for (i in 0..size - 2)
            randomList.add(if (sumItemNum <= 0) 0 else random.nextInt(sumItemNum))

        Collections.sort(randomList)

        val list = ArrayList<Int>()
        var lastNum = 0
        var nextNum = randomList[0]
        for (i in 1..size - 1) {
            list += nextNum - lastNum
            if (i >= randomList.size)
                break
            lastNum = nextNum
            nextNum = randomList[i]
        }
        list += sumItemNum - nextNum

        return list
    }

    fun getPwd(biggerAtoZNum: Int,
               smallAtoZNum: Int,
               numberNum: Int,
               specialNum: Int): String {
        val pwd = StringBuilder(biggerAtoZNum + smallAtoZNum + numberNum + specialNum)
        pwd.apply {
            putChar(biggerAtoZNum) { biggerAtoZ }
            putChar(smallAtoZNum)  { smallAtoZ }
            putChar(numberNum)     { number }
            putChar(specialNum)    { special }
        }

        val pwdList = pwd.toList()
        Collections.shuffle(pwdList, random)
        return pwdList.joinToString(separator = "")
    }

    private fun StringBuilder.putChar(length: Int, getChar: () -> Char) =
            (0..length - 1).forEach { append(getChar()) }

    val smallAtoZ: Char
        get() = ('a' + nextAbsInt() % ('z' - 'a')).toChar()

    val biggerAtoZ: Char
        get() = ('A' + nextAbsInt() % ('Z' - 'A')).toChar()

    val number: Char
        get() = (random.nextInt(10) + '0'.toInt()).toChar()

    val special: Char
        get() = Constants.SPECIAL_CHARS[random.nextInt(Constants.SPECIAL_CHARS.size)]

    private fun nextAbsInt() = Math.abs(random.nextInt())
}
