import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args)  throws IOException {
        String host = "localhost";
        int port = 8000;

        Scanner scanner = new Scanner(System.in);
        String message;

        Thread pollProcess = new Thread(() -> new PollEvents(host, port).poll());
        pollProcess.start();

        try (Socket clientSocket = new Socket(host, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            String hellomessage = in.readLine();
            System.out.println(hellomessage.replace('~', '\n'));

            String response;
            while (true) {
                System.out.print("[Я] ");
                // send message
                message = scanner.nextLine();
                out.println(message);
                out.flush();
                if (message.equals("/exit")) {
                    break;
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        pollProcess.interrupt();
    }
}
