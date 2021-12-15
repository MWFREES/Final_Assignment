import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.Scene;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javafx.application.*;

public class BMI_SERVER extends Application{
    public static int UserCount = 0;

    //setting up the code for connecting to the User
   public static void connect(){
       try {
        //set up socket
        ServerSocket serverSocket = new ServerSocket(8000);
        Socket connectionToUser = serverSocket.accept();
        UserCount++;
        out("User count: " + UserCount);

        //getting the data sreams
        DataInputStream inUser = new DataInputStream(connectionToUser.getInputStream());
        DataOutputStream outServer = new DataOutputStream(connectionToUser.getOutputStream());
       
        while(true){
            //getting the data values
            double weightKG = inUser.readDouble();
            double heightM = inUser.readDouble();

            //Calculating BMI
            double BMI = weightKG / (heightM * heightM) ;
            String BMI_data = "BMI:  " + BMI;

            //returning the BMI to user
            outServer.writeUTF(BMI_data);

        }
    
       } catch (IOException e) {
           out("IO Exception Server");
       }
   }

    //seting up the primary stage for the project
    @Override
    public void start(Stage primaryStage){
        //this will set up the window for use
        Scene scene = new Scene(350,350);

        primaryStage.setScene(scene);
        primaryStage.setTitle("BMI SERVER");

        primaryStage.show();
		
		//threading will be used to impliment multiple users
		new Thread(()->connect()).start();
		
    }
    
    public static void main(String args[]){
    	launch(args);
    }
    
    public static void out(String output){
        System.out.println(output);
    }
}