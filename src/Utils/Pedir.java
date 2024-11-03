package Utils;

import java.util.Scanner;

public class Pedir {

    public static String pedirString(String mensaje){
        System.out.println(mensaje);
        return new Scanner(System.in).nextLine();
    }

    public static int pedirInt(String mensaje){
        System.out.println(mensaje);
        return new Scanner(System.in).nextInt();
    }
}
