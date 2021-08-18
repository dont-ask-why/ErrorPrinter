import javax.print.PrintService;
import javax.swing.*;
import java.awt.*;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
import java.util.List;

public final class PrintUtility {

    /**
     * Retrieve a Print Service with a name containing the specified PrinterName; will return null if not found.
     *
     * @return
     */
    public static PrintService findPrintService(String printerName) {

        printerName = printerName.toLowerCase();

        PrintService service = null;

        // Get array of all print services
        PrintService[] services = PrinterJob.lookupPrintServices();

        // Retrieve a print service from the array
        for (int index = 0; service == null && index < services.length; index++) {

            if (services[index].getName().toLowerCase().indexOf(printerName) >= 0) {
                service = services[index];
            }
        }

        // Return the print service
        return service;
    }

    /**
     * Retrieves a List of Printer Service Names.
     *
     * @return List
     */
    public static List<String> getPrinterServiceNameList() {

        // get list of all print services
        PrintService[] services = PrinterJob.lookupPrintServices();
        List<String> list = new ArrayList<String>();

        for (int i = 0; i < services.length; i++) {
            list.add(services[i].getName());
        }

        return list;
    }

    /**
     * Retrieves an Array of Printer Service Names.
     *
     * @return String array with all printer names
     */
    public static String[] getPrinterServiceNameArray() {

        // get list of all print services
        PrintService[] services = PrinterJob.lookupPrintServices();
        String[] list = new String[services.length];

        for (int i = 0; i < services.length; i++) {
            list[i] = services[i].getName();
        }

        return list;
    }

    /**
     * Prints the defined content to the defined printer.
     *
     * @param content of the printed page
     * @param printerName of the printer where the content should be printed
     */
    public static void printString(String content, String printerName){
        PrintService printService = findPrintService(printerName);

        JTextPane jtp = new JTextPane();
        jtp.setBackground(Color.white);
        jtp.setText(content);

        try {
            jtp.print(null, null, false, printService, null, false);
        } catch (java.awt.print.PrinterException ex) {
            // do nothing
        }

    }

    /**
     * Utility class; no construction!
     */
    private PrintUtility() {}
}