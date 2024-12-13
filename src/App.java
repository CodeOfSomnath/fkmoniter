
import java.util.Scanner;

import com.somnath.fkmon.Monitor;

public class App {

    /**
     * How to use this application, this function will print this message for user
     * so user can use this message for checking the options
     * there will be number from 0 to 5 till now.
     * Anynumber can be choosed the program will respond for that number
     * @author Somnath Nandi
     * @version 0.1
     */
    static void printUsage() {
        System.out.println("This is the options for moniter: ");
        System.out.println("\t1. Add Url");
        System.out.println("\t2. Show Urls");
        System.out.println("\t3. Remove Url");
        System.out.println("\t4. Re-activate Url");
        System.out.println("\t5. Print Usage");
        System.out.println("\t0. Exit");
    }

    public static void main(String[] args) throws Exception {
        Monitor mon = new Monitor();
        Scanner sc = new Scanner(System.in);
        int option, id, price;
        String url;
        printUsage();

        while (true) {
            mon.refresh();
            System.out.println("\nEnter your option(0-5): ");
            option = sc.nextInt();
            sc.nextLine();
            if (option == 0) {
                // exit the system
                break;
            } else if (option == 1) {
                // add url
                System.out.println("Enter url:");
                url = sc.nextLine();
                System.out.println("Enter expacted price:");
                price = sc.nextInt();
                mon.addUrl(url, price);
            } else if (option == 2) {
                // show urls
                System.out.println("\nPrice match Urls:");
                mon.matchPriceUrls();
                System.out.println("\nActive Urls:");
                mon.display();
                System.out.println("\nDead Urls:");
                mon.displayDeadUrls();

            } else if (option == 3) {
                // remove url
                System.out.println("Enter url id:");
                id = sc.nextInt();
                mon.removeUrl(id);
            } else if (option == 4) {
                // re-activate url
                System.out.println("Enter url id:");
                id = sc.nextInt();
                mon.enableUrl(id);
            } else if (option == 5) {
                // print usage
                printUsage();
            } 
            else {
                System.out.println("Enter a correct option !!");
            }
        }

        sc.close();

    }
}
