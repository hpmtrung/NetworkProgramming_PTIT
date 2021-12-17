package example.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICalc extends Remote {

    int sum(int a, int b) throws RemoteException;
    
}
