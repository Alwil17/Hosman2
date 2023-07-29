package com.dopediatrie.hosman.secretariat;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.Scanner;

public class Jambles {
    public Jambles() {
    }

    public static int doThing(String var0) {
        int var1 = 0;

        for(int var2 = 0; var2 < var0.length(); ++var2) {
            if (Character.isDigit(var0.charAt(var2))) {
                //System.out.println(var0.charAt(var2));
                ++var1;
            }
        }
        System.out.println("3 - "+var1);
        return var1;
    }

    public static String doOtherThing(String var0) {
        String var1 = var0.substring(0, 5);
        System.out.println(var1);
        return var1;
    }

    public static String jambles(String var0) {
        String var1 = "bad";
        String var2 = "doggo";
        if (var0.length() % 2 != 0) {
            System.out.println("failed 1");
            return var1;
        } else if (!var0.equals(var0.toLowerCase())) {
            System.out.println("failed 2");
            return var1;
        } else if (var0.length() >= 12 && var0.length() <= 20) {
            System.out.println("passed 1");
            if (doThing(var0) != 3) {
                System.out.println("failed 3");
                return var1;
            } else if (var0.charAt(var0.length() - 1) != 'q') {
                //1sdfghjertyu5g2q
                //1doggojertyu5g2q
                System.out.println("failed 4 - "+var0.charAt(var0.length() - 1));
                return var1;
            } else if (doOtherThing(var0).equals(var2)) {
                return var1;
            } else {
                var1 = "goood stuff";
                return var1;
            }
        } else {
            System.out.println("failed 6");
            return var1;
        }
    }

    public static void check(String var0, String var1) {
        if (var1 == "bad") {
            System.out.println("Oh no, not good...");
        } else if (var1 == "goood stuff") {
            System.out.println("Good job, submit: " + var0);
        }

    }

    public static void main(String[] var0) {
        Scanner var1 = new Scanner(System.in);
        System.out.print("                        (\n                          )     (\n                   ___...(-------)-....___\n               .-\"\"       )    (          \"\"-.\n         .-'``'|-._             )         _.-|\n        /  .--.|   `\"\"---...........---\"\"`   |\n       /  /    |                             |\n       |  |    |                             |\n        \\  \\   |                             |\n         `\\ `\\ |                             |\n           `\\ `|                             |\n           _/ /\\                             /\n          (__/  \\                           /\n       _..---\"\"` \\                         /`\"\"---.._\n    .-'           \\                       /          '-.\n   :               `-.__             __.-'              :\n   :                  ) \"\"---...---\"\" (                 :\n    '._               `\"--...___...--\"`              _.'\n  jgs \\\"\"--..__                              __..--\"\"/\n       '._     \"\"\"----.....______.....----\"\"\"     _.'\n          `\"\"--..,,_____            _____,,..--\"\"`\n                        `\"\"\"----\"\"\"`\n\n");
        System.out.println("Can you get past the jambles, Enter your key");
        String var2 = var1.next();
        String var3 = jambles(var2);
        check(var2, var3);
    }
}
