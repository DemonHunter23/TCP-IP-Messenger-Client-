package network;

import java.io.IOException;

public interface Commands {

    void start() throws IOException, InterruptedException;
    void receiveMessage() throws IOException;
    void sendMessage() throws IOException;
    void disconnect();
}
