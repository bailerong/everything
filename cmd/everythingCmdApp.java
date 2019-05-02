package com.lele.everything.cmd;

import com.lele.everything.Core.Condition;
import com.lele.everything.Core.EverythingPlusManager;
import com.lele.everything.Core.Thing;

import java.util.List;
import java.util.Scanner;


public class everythingCmdApp {

    private static Scanner scanner=new Scanner(System.in);

    public static void main(String[] args) {
        welcom();
        //统一调度器
        EverythingPlusManager manager=EverythingPlusManager.getInstance();
    //交互式
        interactive(manager);
    }
    private static void interactive(EverythingPlusManager manager){
        while(true){
            System.out.println("everything>>");
            String input=scanner.nextLine();

            switch (input){
                case "help":
                    help();
                    break;
                case"quit":
                    quit();
                    break;
                case"index":
                    manager.buildIndex();
                    break;
                    default:{
                        if(input.startsWith("search")){
                            String[] values=input.split(" ");
                            if(values.length>=2) {
                                Condition condition=new Condition();
                           String name=values[1];
                           condition.setName(name);
                                if (values.length >= 3) {
                              String filetype = values[2];
                                condition.setFileType(filetype.toUpperCase());
                                }
                               manager.search(condition).forEach(thing->{
                                   System.out.println(thing.getPath());
                               });

                                }

                            }else{
                                help();
                                continue;
                            }
                        }
                    }

            }
        }

    private static void search(EverythingPlusManager manager,Condition condition){
        System.out.println("检索功能");
        //统一调度器中的searchhelp
        List<Thing> thingList= manager.search(condition);
        for(Thing ting:thingList){
            System.out.println(ting.getPath());
        }

    }
    private static void index(EverythingPlusManager manager){
        new Thread(new Runnable() {
            @Override
            public void run() {
                manager.buildIndex();
            }
        });
    }
    private static void quit(){
        System.out.println("程序退出");
        return;
    }
    private static void welcom(){
        System.out.println("欢迎使用，Everything：");
    }
private static void help(){
    System.out.println("help==>帮助");
    System.out.println("index==>检索");
    System.out.println("search==>查询");
    System.out.println("quit===>退出");

}
}
