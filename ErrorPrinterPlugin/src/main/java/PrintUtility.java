import com.intellij.ui.JBColor;

import javax.print.*;
import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.print.PrinterJob;
import java.io.ByteArrayInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

/**
 * Utility class used for some basic methods to find print services or print text.
 * Not instantiatable as all methods are static.
 */
public final class PrintUtility {

    /**
     * Retrieve a Print Service with a name containing the specified PrinterName; will return null if not found.
     *
     * @return PrintService fitting to the given printer name. Null if none has been found.
     */
    public static PrintService findPrintService(String printerName) {

        printerName = printerName.toLowerCase();

        PrintService service = null;

        // Get array of all print services
        PrintService[] services = PrinterJob.lookupPrintServices();

        // Retrieve a print service from the array
        for (int index = 0; service == null && index < services.length; index++) {

            if (services[index].getName().toLowerCase().contains(printerName)) {
                service = services[index];
            }
        }

        // Return the print service
        return service;
    }

    /**
     * Retrieves an Array of Printer Service Names.
     *
     * @return String array with all printer names
     */
    public static String[] getPrinterServiceNameArray() {

        // get list of all print services
        PrintService[] services = PrinterJob.lookupPrintServices();
        String[] list = new String[services.length + 1];
        list[0] = "Not selected";
        for (int i = 0; i < services.length; i++) {
            list[i + 1] = services[i].getName();
        }

        return list;
    }

    /**
     * Prints the defined content to the defined printer.
     *
     * @param content     of the printed page
     * @param printerName of the printer where the content should be printed
     */
    public static void printString(String content, String printerName) {
        PrintService printService = findPrintService(printerName);

        JTextPane jtp = new JTextPane();
        jtp.setBackground(JBColor.WHITE);
        jtp.setForeground(JBColor.BLACK);
        jtp.setText(content);

        try {
            jtp.print(null, null, false, printService, null, false);
        } catch (java.awt.print.PrinterException ex) {
            // do nothing
        }
    }

    /**
     * Writes the specified String to a user chosen txt file.
     *
     * @param content of the file
     */
    public static void writeTextToFile(String content) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("", "txt"));
        try {
            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {

                FileWriter fileWriter;
                if (!fileChooser.getSelectedFile().getName().contains(".txt")) {
                    fileWriter = new FileWriter(fileChooser.getSelectedFile() + ".txt");
                } else {
                    fileWriter = new FileWriter(fileChooser.getSelectedFile());
                }

                fileWriter.write(content);
                fileWriter.close();
            }
        } catch (IOException exception) {
            // do nothing
        }
    }

    /**
     * Utility class; no construction!
     */
    private PrintUtility() {
    }

    public static void printUnformatted(String content, String printerName) {
        try {
            PrintService mPrinter = findPrintService(printerName);

            // Open the image file
            String testData = content + "\f";
            InputStream is = new ByteArrayInputStream(testData.getBytes());
            DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;

            // Find the default service
            PrintService service = mPrinter;

            // Create the print job
            DocPrintJob job = service.createPrintJob();
            Doc doc = new SimpleDoc(is, flavor, null);

            // Monitor print job events; for the implementation of PrintJobWatcher,
            PrintJobWatcher pjDone = new PrintJobWatcher(job);

            // Print it
            job.print(doc, null);

            // Wait for the print job to be done
            pjDone.waitForDone();

            // It is now safe to close the input stream
            is.close();
        } catch (PrintException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class PrintJobWatcher {
        // true iff it is safe to close the print job's input stream
        boolean done = false;

        PrintJobWatcher(DocPrintJob job) {
            // Add a listener to the print job
            job.addPrintJobListener(new PrintJobAdapter() {
                public void printJobCanceled(PrintJobEvent pje) {
                    allDone();
                }

                public void printJobCompleted(PrintJobEvent pje) {
                    allDone();
                }

                public void printJobFailed(PrintJobEvent pje) {
                    allDone();
                }

                public void printJobNoMoreEvents(PrintJobEvent pje) {
                    allDone();
                }

                void allDone() {
                    synchronized (PrintJobWatcher.this) {
                        done = true;
                        PrintJobWatcher.this.notify();
                    }
                }
            });
        }

        public synchronized void waitForDone() {
            try {
                while (!done) {
                    wait();
                }
            } catch (InterruptedException e) {
            }
        }
    }
}