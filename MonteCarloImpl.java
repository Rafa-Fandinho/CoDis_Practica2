import java.rmi.*;
import java.rmi.server.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class implements the remote interface
 *    HelloInterface.
 * @author M. L. Liu
 */

public class MonteCarloImpl extends UnicastRemoteObject
        implements MonteCarloInterface {

   public MonteCarloImpl() throws RemoteException {
      super( );
   }

   public int MonteCarlo(int nPuntos)
           throws RemoteException {
      int i = 0;
      for(int j = 0; j < nPuntos; j++){
         //Xérase unha parella aleatoria de números x e y. Úsase thread local random para evitar posibles problemas de concurrencia con Math.Random.
         double x = ThreadLocalRandom.current().nextDouble();;
         double y = ThreadLocalRandom.current().nextDouble();;
         //Compróbase se están debaixo da curva e, nese caso, incrementamos o contador
         if (y <= Math.exp(-x * x)){
            i++;
         }
      }
      return i;
   }
} // end class
