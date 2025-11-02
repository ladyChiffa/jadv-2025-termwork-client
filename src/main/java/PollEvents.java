import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class PollEvents {
    String host;
    int port;
    public PollEvents(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void poll() {
            try (Socket clientSocket = new Socket(host, port);
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
            ) {
                String response;
                String message = "/poll";
                response = in.readLine(); // hellomessage skip printing

                while (true) {
                    out.println(message);
                    out.flush();
                    response = in.readLine();
                    if (!response.equals("")) {
                        response = response.replace('~', '\n');
                        System.out.println(response);
                    }
                    Thread.sleep(300);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
            }
    }
}
