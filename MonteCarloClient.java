import java.io.*;
import java.rmi.*;

/**
 * This class represents the object client for a distributed
 * object of class MonteCarlo, which implements the remote interface
 * MonteCarloInterface.
 * @author M. L. Liu e eu tamén!import java.lang.reflect.Array;
 */

public class MonteCarloClient {

   public static void main(String args[]) {
      try {
         int RMIPort;         
         String hostName;
         InputStreamReader is = new InputStreamReader(System.in);
         BufferedReader br = new BufferedReader(is);
         System.out.println("Enter the RMIRegistry host namer:");
         hostName = br.readLine();
         System.out.println("Enter the RMIregistry port number:");
         String portNum = br.readLine();
         RMIPort = Integer.parseInt(portNum);
         //Now, we ask for the desired number of processes. I made a switch just so nobody would ask for 1000 processes
         int pnum = -1;
         while (pnum == -1){
            System.out.println("How many processes must the workload be divided between?\n a) 1 \n b) 2 \n c) 4 \n d) 8 " );
            String option = br.readLine();
            switch (option){
               case "a", "A", "1":
                  pnum = 1;
                  break;
               case "b", "B", "2":
                  pnum = 2;
                  break;
               case "c", "C", "4":
                  pnum = 4;
                  break;
               case "d", "D", "8":
                  pnum = 8;
                  break;
               default:
                  System.out.println("Incorrect option. Try again. Enter only the letter, without the parenthesis, or the corresponding number." );
            }
         }
         //We move the registry to after knowing the number of processes so we can lookup the right amount of servers
         MonteCarloInterface[] h = new MonteCarloInterface[pnum];
         for (int j = 0; j < pnum; j++){
            String registryURL = "rmi://" + hostName+ ":" + portNum + "/MonteCarlo" + j;
            // find the remote object and cast it to an interface object
            h[j] = (MonteCarloInterface)Naming.lookup(registryURL);
         }
         System.out.println("Lookup completed " );
         int n = 0;
         while (n <= 0) {
            //Then, we ask for the number of points to be tested
            try {
               System.out.println("Enter the number of points to be generated for the Monte Carlo calculation:");
               n = Integer.parseInt(br.readLine());
            } catch (NumberFormatException e) {
               n = 0;
            }
            if(n <= 0) {
               System.out.println("The input must be a positive integer. Try again.");
            }
            System.out.println("Great! Starting call for calculation.");
         }
         //And calculate the distribution according to the number of processes, already creating the threads
         ClientThread[] client = new ClientThread[pnum];
         Thread[] threads = new Thread[pnum];
         int base = n / pnum;
         for (int j = 0; j < n % pnum; j++){
            client[j] = new ClientThread(h[j], base + 1);
            threads[j] = new Thread(client[j]);
         }
         for (int j = n % pnum; j < pnum; j++){
            client[j] = new ClientThread(h[j], base);
            threads[j] = new Thread(client[j]);
         }
         // invoke the remote method
         //Here, we cast a thread for every server process. We measure the time spent in this task too.
         long inicio = System.currentTimeMillis();
         for (int j = 0; j < pnum; j++){
            threads[j].start();
         }
         //Then, join the threads and calculate the final result
         for (int j = 0; j < pnum; j++) {
            threads[j].join();
         }
         int total = 0;
         for (int j = 0; j < pnum; j++) {
            total += client[j].getResultado();
         }
         double resultado =  (double) total / n;
         long fin = System.currentTimeMillis();
         //Finally, we print the result
         System.out.println("The result of the calculation for the integral value is: " + resultado);
         System.out.println("The task was completed in: " + (fin - inicio) + " milliseconds.");
      } // end try 
      catch (Exception e) {
         System.out.println("Exception in MonteCarloClient: " + e);
      } 
   } //end main
}//end class
