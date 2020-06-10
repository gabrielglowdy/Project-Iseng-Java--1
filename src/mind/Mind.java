/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mind;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author g4732
 */
public class Mind {

    /**
     * @param args the command line arguments
     */
    static Scanner input;
    static String filenya = "filename.txt", dir = "D:/";

    public static void main(String[] args) {

        

        // TODO code application logic here
        input = new Scanner(System.in);

        Brain brain = new Brain();
        String inputX = "";
        while (!inputX.equals("exit")) {
            System.out.print("Ask me : ");
            inputX = input.nextLine();
            if (inputX.equals("exit")) {
                break;
            }
            brain.find(inputX);
        }

    }

    
}
