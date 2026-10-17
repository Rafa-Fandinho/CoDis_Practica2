import java.rmi.RemoteException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class DynamicThread implements Runnable {
    private final MonteCarloInterface servidor;
    private final int nPuntos;
    private final int bloque;
    private final AtomicInteger procesado;
    private final AtomicInteger total;
    private final AtomicBoolean erro;

    public DynamicThread(MonteCarloInterface servidor, int nPuntos, int bloque, AtomicInteger procesado, AtomicInteger total, AtomicBoolean erro) {
        this.servidor = servidor;
        this.nPuntos = nPuntos;
        this.bloque = bloque;
        this.total = total;
        this.procesado = procesado;
        this.erro = erro;
    }


    @Override
    public void run() {
        while(!erro.get()){ //Mantemos un traballo constante ata rematar ou detectar un erro
            int init = procesado.getAndAdd(bloque);
            if(init >= nPuntos){ //Outro fío xa rematou de procesar os últimos puntos
                break;
            }
            int puntos = (int) Math.min(bloque, nPuntos - init); //No caso de que sexa o último bloque e a división non fose exacta
            try{    //Chamamos ao servidor correspondente
                int resultado = servidor.MonteCarlo(puntos);
                total.addAndGet(resultado);
            } catch (RemoteException e) {
                erro.set(true);
                System.out.println("Error in RMI call: " + e + "\n This might affect calculation values, so don't trust this execution."); //Poño o print en inglés para ser coerente co resto
                break;
            }
        }
    }
}
