package core.tcp;

import java.io.IOException;

/**
 * Server TCP instance
 * @author DELL
 */
public class ServerTCP extends AServerTCP {

    private static volatile ServerTCP instance;

    public static ServerTCP getInstance() {
        if (instance == null) {
            synchronized (ServerTCP.class) {
                if (instance == null) {
                    instance = new ServerTCP();
                }
            }
        }
        return instance;
    }

    @Override
    public void task() throws IOException {
        int flag = dis.readInt();
        System.out.println("Xu ly bai: " + flag);
        switch (flag) {
            case 1:
//                    dos.writeUTF(getResultProblem1());
            break;
            case 2:
//                    String[] res = getResultProblem2().split(",");
//                    dos.writeUTF(res[0]);
//                    dos.writeUTF(res[1]);
                break;
            case 3:
//                    dos.writeUTF(getResultProblem3());
                break;
            default:
                break;
        }
    }
    
    
}
