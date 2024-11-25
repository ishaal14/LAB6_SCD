class PrinterJob {
    private int totalPages = 10; 

    public synchronized void calculatePages(int pagesToAdd) {
        totalPages += pagesToAdd;
        System.out.println("Added " + pagesToAdd + " pages to the tray. Total pages now: " + totalPages);
        notify(); 
    }

    
    public synchronized void printPages(int pagesToPrint) {
        while (totalPages < pagesToPrint) {
            System.out.println("Not enough pages to print " + pagesToPrint + ". Waiting for pages to be added...");
            try {
                wait(); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Thread interrupted.");
            }
        }
        totalPages -= pagesToPrint;
        System.out.println("Printed " + pagesToPrint + " pages. Remaining pages: " + totalPages);
    }
}

public class PrinterJob_Demo {
    public static void main(String[] args) {
        PrinterJob printerJob = new PrinterJob();

        
        Thread calculateThread = new Thread(() -> {
            try {
                Thread.sleep(2000); 
                printerJob.calculatePages(10); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        
        Thread printThread = new Thread(() -> printerJob.printPages(15)); 

        printThread.start();
        calculateThread.start();
    }
}




