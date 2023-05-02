import java.util.Scanner;

public class BASE64 
{    private static String getInput()
    {
        System.out.flush();
        Scanner input = new Scanner(System.in);
        String data = input.nextLine();
        return data;
    }
    private static void print(String data)
    {
        System.out.print(data);
        return;
    }
    public static void main(String[] args) 
    {
        BASE2 base2 = new BASE2();
    }
}
