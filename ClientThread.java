import java.rmi.RemoteException;

public class ClientThread implements Runnable {
    private final MonteCarloInterface servidor;
    private final int nPuntos;
    private int resultado;

    public ClientThread(MonteCarloInterface servidor, int nPuntos) {
        this.servidor = servidor;
        this.nPuntos = nPuntos;
    }


    @Override
    public void run() {
        try{    //Chamamos ao servidor correspondente
            resultado = servidor.MonteCarlo(nPuntos);
        } catch (RemoteException e) {
            System.out.println("Error in RMI call: " + e + "\n This might affect calculation values, so don't trust this execution."); //Poño o print en inglés para ser coerente co resto
        }
    }
    //Getter simple para obter o resultado
    public int getResultado() {
        return resultado;
    }
}
