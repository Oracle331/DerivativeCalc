import jxl.Workbook;
import jxl.write.*;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Scanner;

public class DerivCalculator {

    public static double inc;

    public static void main(String[] args){
        // variables
        String userName = new com.sun.security.auth.module.NTSystem().getName();
        WritableWorkbook derivative;
        Scanner sc = new Scanner(System.in);
        String fileName, function;
        double low, high;

        // input
        System.out.print("Enter a file name to output to(One word): ");
        fileName = "C:\\Users\\" + userName + "\\Documents\\" + sc.next() + ".xls";
        System.out.print("Using x as the variable, enter a function(): ");
        function = sc.next();
        System.out.print("Please enter the range you would like to calculate the derivative for...\n" +
                "Low: ");
        low = sc.nextInt();
        System.out.print("High: ");
        high = sc.nextInt();

        inc = (high - low) / 1000;
        double xStart = low;



        // write to excel
        try {
            derivative = Workbook.createWorkbook(new File(fileName));
            WritableSheet wsheet = derivative.createSheet("Derivative", 0);
            Label label;
            label = new Label(0, 0, "x");
            wsheet.addCell(label);
            label = new Label(1, 0, "f(x) = " + function);
            wsheet.addCell(label);
            label = new Label(2, 0, "f¹(x)");
            wsheet.addCell(label);


            for(int i = 0; i < 3; i++){
                for(int j = 1; j <= 1000; j++) {
                    if(i == 0) {
                        label = new Label(i, j, makeString(xStart));
                        wsheet.addCell(label);
                        xStart += inc;
                    } else if(i == 1){
                        label = new Label(i, j, makeString(function(function, xStart)));
                        wsheet.addCell(label);
                        xStart += inc;
                    } else if(i == 2){
                        label = new Label(i, j, makeString(derived(function, xStart)));
                        wsheet.addCell(label);
                        xStart += inc;
                    }
                }
                xStart = low;
            }
            derivative.write();
            derivative.close();
            System.out.println("Done.");

        }catch(Exception e){
            System.out.println("\n"+e);
        }
    }
    public static double function(String function, double x){

        int count = -1;
        double y = 1;
        String formula = function;

        formula = formula.replaceAll("\\s","");
        String[] values = formula.split("(?<=[-+*/^])|(?=[-+*/^])");


        for(String a : values){
            count++;
            if(a.equals("x")){
                if(count == 0)
                    y = x;
                else{
                    if(values[count-1].equals("*"))
                        y *= x;
                    else if(values[count-1].equals("/"))
                        y /= x;
                    else if(values[count-1].equals("+"))
                        y += x;
                    else if(values[count-1].equals("-"))
                        y -= x;
                }
            }
            else if(a.equals("*")) {
                continue;
            }else if(a.equals("/")) {
                continue;
            }else if(a.equals("+")) {
                continue;
            }else if(a.equals("-")) {
                continue;
            } else if(Double.parseDouble(a) > 0){
                if(count == 0)
                    y = Double.parseDouble(a);
                else{
                    if(values[count-1].equals("*"))
                        y *= Double.parseDouble(a);
                    else if (values[count-1].equals("/"))
                        y /= Double.parseDouble(a);
                    else if (values[count-1].equals("+"))
                        y += Double.parseDouble(a);
                    else if (values[count-1].equals("-"))
                        y -= Double.parseDouble(a);

                }
            }

        }

        return y;
    }
    public static double derived(String function, double x){

        double y;

        y = ((function(function, x + inc) - function(function, x)) / inc) - inc;

        return y;
    }
    public static String makeString(double y){
        DecimalFormat df = new DecimalFormat("#.####");
        return df.format(y) + "";
    }
}
