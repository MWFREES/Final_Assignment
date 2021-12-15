import java.util.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class BMI_USER{
    
    // get the data streams
    static DataInputStream inUser;
    static DataOutputStream outServer;
    
    //code for connecting to the server
    public static void connect(){
        try {
            //in
            outServer.writeDouble(weight.getText()));
            outServer.writeDouble(height.getText());
            outServer.flush();

            //out
            String BMI = inUser.readUTF();
            out(BMI);
        } catch (IOException e) {
            out("IO Exception connect");
        }
    }
    
    //setting up the javaFX and running the code
    public TextFeild weight = new TextFeild();
    public TextFeild height = new TextFeild();
    public Text response = new Text();
    public Button send = new Button("alculate BMI");

    @Override 
    public void start(Stage primaryStage){
        Scene scene = new Scene(350,350);
        Label label = new Label("Enter Weight in KG and Height in M");
        scene.add(label);
        scene.add(weight);
        scene.add(height);
        scene.add(response);
        scene.add(send);

        primaryStage.setScene(scene);
        primaryStage.show();
        send.setOnAction(e -> connect());
        
        try {
            //opening the socket for the server
            Socket socketForServer = new Socket("localhost", 8000);
            inUser = new DataInputStream(socketForServer.getInputStream());
            outServer = new DataOutputStream(socketForServer.getOutputStream());


        } catch (IOException e) {
            out("IO Exception Main");
        }
    }

    public static void out(String output){
        System.out.println(output);
    }
    public static void main(String[] args) {
       launch(args);
    }
}