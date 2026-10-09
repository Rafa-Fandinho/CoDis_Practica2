import java.rmi.*;
import java.rmi.server.*;

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
         //Xérase unha parella aleatoria de números x e y
         double x = Math.random();
         double y = Math.random();
         //Compróbase se están debaixo da curva e, nese caso, incrementamos o contador
         if (y <= Math.pow(Math.E,-(Math.pow(x,2)))){
            i++;
         }
      }
      return i;
   }
} // end class
