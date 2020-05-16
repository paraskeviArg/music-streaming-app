/*import java.io.*;
import java.net.Socket;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.Scanner;

public class Consumer implements Serializable {

    int ID;

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }


    public static void main(String[] args) throws ClassNotFoundException, IOException {
        String artistChoice;
        Socket connection;
        Consumer consumer = new Consumer();
        File file = new File("Song Downloads");
        file.mkdir();

        while (true) {
            try {
                //Connect - Ask for type of session
                connection = new Socket("localhost", 9600);
                System.out.println("Connected.");
                ObjectInputStream input = new ObjectInputStream(connection.getInputStream());
                ObjectOutputStream output = new ObjectOutputStream(connection.getOutputStream());
                String sessionQ = (String) input.readObject();
                System.out.println(sessionQ);
                Scanner scanner = new Scanner(System.in);
                String session = scanner.nextLine();
                output.writeObject(session);
                output.writeObject(consumer);
                String artistQ;

                while (true) {
                    //Consumer chooses artist, gets back all the song that a publisher has from this artists and from those songs chooses the one he wants.
                    //If the consumer types "exit" the program ends there.
                    //If the consumer types an artist that links to no publisher, he gets the option to chose again.
                    System.out.println("Choose an artist or type exit to close the program.");
                    artistQ = (String) input.readObject();
                    System.out.println(artistQ);
                    scanner = new Scanner(System.in);
                    artistChoice = scanner.nextLine();
                    if (artistChoice.equals("exit")) break;
                    output.writeObject(artistChoice);
                    String newPort = (String) input.readObject();
                    if (newPort.equals("")) {
                        System.out.println("This artist does not exist in our database. Please search for an other artist.");
                    } else {
                        if (!newPort.equals("noRedirect")) {
                            connection = new Socket("localhost", Integer.parseInt(newPort));
                        }
                        System.out.println(input.readObject());
                        System.out.println(input.readObject());
                        String songChoice = scanner.nextLine();
                        output.writeObject(songChoice);
                        MusicFile chunk = (MusicFile) input.readObject();
                        OutputStream outStream;
                        ByteArrayOutputStream byteOutStream;
                        //Create folder "Song downloads" if does not exist, and download the chunks there one-by-one.
                        outStream = new FileOutputStream( "Song Downloads\\" + songChoice + ".mp3", true);
                        byteOutStream = new ByteArrayOutputStream();
                        if (chunk.getSong().getTrackName().equals("")) {
                            System.out.println((String) input.readObject());
                        } else {
                            while (chunk.getId() != 0) {
                                byteOutStream.write(chunk.getMusicFileExtract());
                                chunk = (MusicFile) input.readObject();
                            }
                            byteOutStream.writeTo(outStream);
                            System.out.println("Song " + songChoice + " downloaded.");
                        }
                    }

                }
                break;
            } catch (EOFException e) {
                //If its an offline session, the consumer gets disconnected and an exception is thrown. Here he gets the option to connect again.
                System.out.println("Reconnect if you want to continue listening. yes/no");
                Scanner scanner = new Scanner(System.in);
                String re = scanner.nextLine();
                if (re.equals("no")) {

                    break;
                }
            }

        }
    }
}
*/