// A simple RMI interface file - M. Liu
import java.rmi.*;

/**
 * This is a remote interface.
 * @author M. L. Liu
 */

public interface MonteCarloInterface extends Remote {
/**
 * This remote method returns a message.
 * @param  nPuntos - número de puntos a ser calculados.
 * @return cantidade de puntos que se atopan debaixo da curva.
 */
   public int MonteCarlo(int nPuntos)
      throws java.rmi.RemoteException;

} //end interface
