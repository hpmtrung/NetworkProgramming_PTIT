package example.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Cacl extends UnicastRemoteObject implements ICalc {
// Important
    public Cacl() throws RemoteException {
    }
    
    
    @Override
    public int sum(int a, int b) throws RemoteException {
        return a + b;
    }

}
