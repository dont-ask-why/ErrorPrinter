import java.awt.*;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.*;
import java.io.PrintWriter;
import java.io.StringWriter;

public class Main {

    /**
     * This function fundamentally prints the text provided. It is not necessary for it to print an error.
     * The text is not formatted but will be split onto severall pages.
     * @param printedText the text to be printed.
     */
    public static void printError(String printedText){
        JTextPane jtp = new JTextPane();
        jtp.setBackground(Color.white);
        jtp.setText(printedText);
        boolean show = false;
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        PrintService service = null;
        for(PrintService printService : printServices){

            /*
             *  TODO: change the name of the printer
             *  You can use a for each loop with "getName" being called
             *  thus you can see on the console which name you should use
             */
            //System.out.println(printService.getName());

            if(printService.getName().equals("YOUR PRINTER NAME HERE")){
                System.out.println("Regular printer Found");
                service = printService;
                break;
            }
        }
        if (service == null){
            for(PrintService printService : printServices){
                if(printService.getName().equals("Microsoft Print to PDF")){
                    System.out.println("PDF printer Found");
                    service = printService;
                    break;
                }
            }
        }
        if(service == null){
            show = true;
            System.out.println("No predefined printer found. Please select one from the window.");
        }
        try {
            jtp.print(null, null, show, service, null, show);
        } catch (java.awt.print.PrinterException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * A basic recursion function which should generate a StackOverflowError thus creating a long
     * print to be done by the printError method.
     * @param i basic value to stop a - theoretically - endless recursion.
     */
    public static void recursion(int i){
        if(i < 1000){
            recursion(i);
        }
    }

    /**
     * The main method. Here it generates a basic error, either by a recursion being called to many times
     * or an exception by the parseInt method.
     * @param args
     */
    public static void main(String[] args){
        try{
            //recursion(1);
            int i = Integer.parseInt("a");
        } catch (Throwable e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            printError(sw.toString());
        }
    }
}