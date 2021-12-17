package example.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerRMI extends Thread {

    public static final int PORT = 3456;
    
    @Override
    public void run() {
        System.out.println("Server dang chay...");
        try {
            Registry r = LocateRegistry.createRegistry(PORT);
            ICalc c = new Cacl();
            r.bind("rmiCacl", c);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        ServerRMI server = new ServerRMI();
        server.start();
    }
    
}
