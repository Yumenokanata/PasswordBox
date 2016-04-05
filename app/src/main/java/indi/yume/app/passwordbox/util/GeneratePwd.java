package indi.yume.app.passwordbox.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import rx.functions.Func0;

/**
 * Created by bush2 on 2016/4/4.
 */
public class GeneratePwd {
    private static final Random random = new Random(0);

    public static String getPwd(int length,
                                boolean hasBigger,
                                boolean hasSmall,
                                boolean hasNumber,
                                boolean hasSpecial) {
        int size = 0;
        if(hasBigger) size++;
        if(hasSmall) size++;
        if(hasNumber) size++;
        if(hasSpecial) size++;
        if(size == 0) {
            hasSmall = true;
            size = 1;
        }

        List<Integer> list = getRandomList(length - size, size);
        List<Integer> randomNumList = new ArrayList<>();
        int index = 0;
        if(hasBigger) {
            randomNumList.add(list.get(index) + 1);
            index++;
        } else {
            randomNumList.add(0);
        }
        if(hasSmall) {
            randomNumList.add(list.get(index) + 1);
            index++;
        } else {
            randomNumList.add(0);
        }
        if(hasNumber) {
            randomNumList.add(list.get(index) + 1);
            index++;
        } else {
            randomNumList.add(0);
        }
        if(hasSpecial) {
            randomNumList.add(list.get(index) + 1);
            index++;
        } else {
            randomNumList.add(0);
        }

        return getPwd(randomNumList.get(0),
                randomNumList.get(1),
                randomNumList.get(2),
                randomNumList.get(3));
    }

    public static List<Integer> getRandomList(int sum, int size) {
        if(size <= 0)
            return new ArrayList<>();
        if(size == 1) {
            List<Integer> list = new ArrayList<>();
            list.add(sum);
            return list;
        }

        List<Integer> randomList = new ArrayList<>();
        sum = Math.max(sum, size);

        for(int i = 0; i < size - 1; i++)
            randomList.add(sum <= 0 ? 0 : random.nextInt(sum));

        Collections.sort(randomList);

        List<Integer> list = new ArrayList<>();
        int lastNum = 0;
        int nextNum = randomList.get(0);
        for(int i = 1; i < size; i++) {
            list.add(nextNum - lastNum);
            if(i >= randomList.size())
                break;
            lastNum = nextNum;
            nextNum = randomList.get(i);
        }
        list.add(sum - nextNum);

        return list;
    }

    public static String getPwd(int biggerAtoZNum,
                                int smallAtoZNum,
                                int numberNum,
                                int specialNum) {
        StringBuilder pwd = new StringBuilder(biggerAtoZNum + smallAtoZNum + numberNum + specialNum);
        pwd.append(getString(biggerAtoZNum, GeneratePwd::getBiggerAtoZ))
                .append(getString(smallAtoZNum, GeneratePwd::getSmallAtoZ))
                .append(getString(numberNum, GeneratePwd::getNumber))
                .append(getString(specialNum, GeneratePwd::getSpecial));

        List<Character> pwdList = new ArrayList<>();
        for(char c : pwd.toString().toCharArray())
            pwdList.add(c);

        Collections.shuffle(pwdList, random);
        StringBuilder pwdBuilder = new StringBuilder();
        for(Character c : pwdList)
            pwdBuilder.append(c);
        return pwdBuilder.toString();
    }

    private static String getString(int length, Func0<Character> getChar) {
        StringBuilder randomString = new StringBuilder(length);
        for(int i = 0; i < length; i++)
            randomString.append(getChar.call());

        return randomString.toString();
    }

    public static char getSmallAtoZ() {
        return (char) ('a' + nextAbsInt() % ('z' - 'a'));
    }

    public static char getBiggerAtoZ() {
        return (char) ('A' + nextAbsInt() % ('Z' - 'A'));
    }

    public static char getNumber() {
        return (char) (random.nextInt(10) + '0');
    }

    public static char getSpecial() {
        return Constants.SPECIAL_CHARS[random.nextInt(Constants.SPECIAL_CHARS.length)];
    }

    private static int nextAbsInt() {
        return Math.abs(random.nextInt());
    }
}
