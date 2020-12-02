package com.imooc.mall.controller;


import com.imooc.mall.dao.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created on 2020-06-03
 */

public class ConditionDemo1 {

    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    void method1() throws InterruptedException {
        lock.lock();
        try{
            System.out.println("条件不满足，开始await: "+Thread.currentThread().getName());
            condition.await();
            System.out.println("条件满足了，开始执行后续的任务"+Thread.currentThread().getName());
        }finally {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("unlock method1: "+simpleDateFormat.format(System.currentTimeMillis()));
            lock.unlock();
        }
    }

    void method2() {
        lock.lock();
        try{
            System.out.println("准备工作完成，唤醒其他的线程"+Thread.currentThread().getName());
            condition.signal();
        }finally {
            lock.unlock();
        }
    }

    // 左对齐，删除空白行，去掉快手二字，去掉快手话题，去掉推广二字, 去掉艾特
    public String dealTitle(String title) {
        String ntitle = removeKsTag(title);
        ntitle = ntitle.replace("\r", "");
        ntitle = removeBlank(ntitle);
        ntitle = ntitle.replace("快手", "");
        ntitle = ntitle.replace("推广", "");
        ntitle = removeAt(ntitle);
        ntitle = removeFirstBack(ntitle);
        return ntitle;
    }

    String removeKsTag(String title) {
        String ntitle = "";
        int flag = 0;
        for (int i = 0; i < title.length(); i++) {
            if (title.charAt(i) == '#') {
                flag = 1;
            }
            if (flag == 1 && title.charAt(i) == ' ') {
                flag = 0;
                continue;
            }
            if (flag == 0) {
                ntitle += title.charAt(i);
            }
        }
        return ntitle;
    }

    String removeBlank(String title) {
        String ntitle = "";
        int flag = 0;
        for (int i = 0; i < title.length(); i++) {
            if (title.charAt(i) == '\n') {
                flag = 1;
            } else if (flag == 1) {
                if (title.charAt(i) == ' ' || title.charAt(i) == '\t') {
                    continue;
                } else {
                    flag = 0;
                }
            }
            ntitle += title.charAt(i);
        }
        String nntitle = "";
        for (int i = 0; i < ntitle.length(); i++) {
            if (ntitle.charAt(i) == '\n' && i + 1 < ntitle.length() && ntitle.charAt(i + 1) == '\n') {
                continue;
            }
            nntitle += ntitle.charAt(i);
        }
        return nntitle;
    }

    String removeAt(String title) {
        String ntitle = "";
        int flag = 0;
        for (int i = 0; i < title.length(); i++) {
            if (title.charAt(i) == '@') {
                flag = 1;
            }
            if (flag == 1 && title.charAt(i) == ')') {
                flag = 0;
                continue;
            }
            if (flag == 0) {
                ntitle += title.charAt(i);
            }
        }
        return ntitle;
    }

    String removeFirstBack(String s) {
        String ntitle = "";
        int fi = 0, ed = s.length();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '\n' || s.charAt(i) == ' ' || s.charAt(i) == '\t') {
                fi++;
            } else {
                break;
            }
        }
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == '\n' || s.charAt(i) == ' ' || s.charAt(i) == '\t') {
                ed--;
            } else {
                break;
            }
        }
        for (int i = fi; i < ed; i++) {
            ntitle += s.charAt(i);
        }
        return ntitle;
    }

    String removeKsTagWithKsColumn(String title) {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder hashtagBuilder = new StringBuilder();
        for(int i=0;i<title.length();i++){
            if(title.charAt(i) == '#'){
                hashtagBuilder.delete(0,hashtagBuilder.length());
                int j;
                for(j=i; j<title.length() && title.charAt(j) != ' ';j++){
                    hashtagBuilder.append(title.charAt(j));
                }
                if(!hashtagBuilder.toString().contains("快手")) {
                    stringBuilder.append(hashtagBuilder);
                }
                i = j;
            }
            else {
                stringBuilder.append(title.charAt(i));
            }
        }
        return stringBuilder.toString();
    }

    String removeMultipleBlank(String title){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0;i<title.length();i++){
            if(title.charAt(i) == ' ' || title.charAt(i) == '\t'){
                stringBuilder.append(' ');
                while (i<title.length()-1 && (title.charAt(i+1) == ' ' || title.charAt(i+1) == '\t')){
                    i++;
                }
            }
            else{
                stringBuilder.append(title.charAt(i));
            }
        }
        return stringBuilder.toString();
    }

    public String dealTitleV1(String title) {
        String ntitle = removeKsTagWithKsColumn(title);
        System.out.println(ntitle);
        ntitle = ntitle.replace("\r", "");
        ntitle = removeBlank(ntitle);
        ntitle = ntitle.replace("快手", "");
        ntitle = ntitle.replace("推广", "");
        ntitle = removeMultipleBlank(ntitle);
        ntitle = removeAt(ntitle);
        ntitle = removeFirstBack(ntitle);
        return ntitle;
    }

    private void changebool(int a,Boolean test){
        a = 233;
        test = false;
    }


    public static void main(String[] args) {
        Date date=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        long timeStamp = calendar.getTime().getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        System.out.println("NewUserInspireTask cal timestamp: {},day: {} "+timeStamp+" "+format.format(timeStamp));
    }

}
