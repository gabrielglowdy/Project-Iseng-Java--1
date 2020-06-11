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
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author g4732z aka NDX
 */
public class Brain {

    ArrayList<ObjectInput> knowledges;
    String brainLocation, separator = "->";

    String prevQuery = "";

    boolean debugMode = false;

    String[] wh = new String[6];

    public void setWh() {
        wh[0] = "what";
        wh[1] = "who";
        wh[2] = "when";
        wh[3] = "where";
        wh[4] = "why";
        wh[5] = "how";

    }

    public Brain() {
        knowledges = new ArrayList<>();
        if (debugMode) {
            System.out.println("DEBUG MODE : " + debugMode);
        }
        setWh();
        this.brainLocation = "D:/brain.txt";
        makeFile();
        readFile();
//        commonKnowledge();
    }

    public Brain(String brainLocation) {
        knowledges = new ArrayList<>();
        setWh();
        this.brainLocation = brainLocation;
        makeFile();
        readFile();
//        commonKnowledge();

    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    public void toggleDebug() {
        this.debugMode = !this.debugMode;
        System.out.println("Debug Mode : " + this.debugMode);
    }

    public void makeFile() {
        try {
            File myObj = new File(brainLocation);
            if (myObj.createNewFile()) {
                if (debugMode) {
                    System.out.println("File created: " + myObj.getName());
                }
                commonKnowledge();
            } else {
                if (debugMode) {
                    System.out.println("File already exists.");
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");

            e.printStackTrace();
        }
    }

    public void writeFile() {
        try {
            FileWriter myWriter = new FileWriter(brainLocation);
            String temp = "";
            for (int i = 0; i < knowledges.size(); i++) {
                temp += knowledges.get(i).getQuery() + separator + knowledges.get(i).getResult();
                if (i + 1 < knowledges.size()) {
                    temp += "\n";
                }
            }
            myWriter.write(temp);
            myWriter.close();
            if (debugMode) {
                System.out.println("Successfully wrote to the file.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void readFile() {
        try {
            File myObj = new File(brainLocation);
            Scanner myReader = new Scanner(myObj);
            knowledges.clear();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String tmp[] = data.split(separator);

                addKnowledges(tmp[0], tmp[1]);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void commonKnowledge() {
        learn("Hello", "Hi");
        learn("Hi", "HI there");
        learn("Your Name", "Enzy");
    }

    public void showKnowledges() {
        for (int i = 0; i < knowledges.size(); i++) {
            System.out.println((i + 1) + ". " + knowledges.get(i).getQuery() + "\t: " + knowledges.get(i).getResult());
        }

    }

    public void refresh() {
        knowledges.clear();
        readFile();
        System.out.println("refresh success");
    }

    public void find(String input) {
        ArrayList<MetaData> results = new ArrayList<>();
        String[] inputs = input.toLowerCase().split(" ");

        switch (input.toLowerCase()) {
            case "show()":
                showKnowledges();
                this.prevQuery = "";
                return;

            case "about()":
                System.out.println("Created by NDX");
                this.prevQuery = "";
                return;

            case "toggledebug()":
                toggleDebug();
                this.prevQuery = "";
                return;

            case "refresh()":
                refresh();
                this.prevQuery = "";
                return;

            case "rearrange()":
                refresh();
                rearrange();
                this.prevQuery = "";
                return;
        }

      
        
//        boolean findExact = false;

        for (int i = 0; i < knowledges.size(); i++) {
            if (knowledges.get(i).getQuery().toLowerCase().contains(input.toLowerCase())) {
//                findExact = true;
                results.add(new MetaData(knowledges.get(i).getQuery(), knowledges.get(i).getResult()));
                break;
            }

        }

        if (inputs[0].equals("are") || inputs[0].equals("am") || inputs[0].equals("can") || inputs[0].equals("do")) {
            int angka = (int) Math.round(Math.random());
            if (angka == 0) {
                System.out.println("yes");
            } else {
                System.out.println("no");
            }
            return;
        }

        if (input.toLowerCase().equals("wrong") || input.toLowerCase().equals("no") || input.toLowerCase().equals("n")) {
            if (!prevQuery.equals("")) {
                dontKnow(prevQuery);
            } else {
                System.out.println("What do you mean " + input + "?");
            }
            return;
        }

        for (int i = 0; i < knowledges.size(); i++) {
            for (int j = 0; j < inputs.length; j++) {

                if (inputs[j].length() > 2) {
                    for (int x = 2; x < inputs[j].length(); x++) {
                        String inputnya = inputs[j].toLowerCase().substring(0, x);

                        boolean skip = false;

                        if (inputs[j].toLowerCase().equals("what")) {
                            skip = true;

                        }

                        if (!skip && !inputnya.equals("is") && !inputnya.equals("are") && knowledges.get(i).getQuery().contains(inputnya)) {
                            boolean sameAnswer = false;

                            if (debugMode) {
                                System.out.println("Found : " + inputnya);
                            }
                            for (int k = 0; k < results.size(); k++) {
                                if (results.get(k).getResult().equals(knowledges.get(i).getResult())) {
                                    results.get(k).upVote();
                                    sameAnswer = true;
                                    break;
                                }
                            }
                            if (!sameAnswer) {
//                            results.add(new MetaData(inputnya, knowledges.get(i).getResult()));

                                results.add(new MetaData(knowledges.get(i).getQuery(), knowledges.get(i).getResult()));;

                            }
                        }

                    }
                }

            }
        }

        String res = "";
        int maxVote = 0;

        for (int i = 0; i < results.size(); i++) {

            if (i > 0 && results.get(i).getVote() > results.get(maxVote).getVote()) {
                maxVote = i;
            }
            res = results.get(maxVote).getResult();
        }
        if (debugMode && !res.equals("")) {

            //show all
            res += "\n=================\n";
            for (int i = 0; i < results.size(); i++) {
                res += results.get(i).getQuery() + " : " + results.get(i).getResult() + "(" + results.get(i).getVote() + ")\n";
            }

        }
        if (res.equals("")) {
            dontKnow(input);

        } else {
            System.out.println(res);
            this.prevQuery = input;

        }

    }

    public void learn(String input, String output) {
        boolean success = addKnowledges(input, output);
        if (success) {
            writeFile();

        }
    }

    public boolean addKnowledges(String input, String output) {
        boolean ketemu = false;
        for (int i = 0; i < knowledges.size(); i++) {
            if (knowledges.get(i).getQuery().toLowerCase().equals(input.toLowerCase())) {
                ketemu = true;
                knowledges.get(i).setResult(output);
                break;
            }
        }
        if (!ketemu) {
            knowledges.add(new ObjectInput(input.toLowerCase(), output));
            return true;
        }

        return false;
    }

    public void dontKnow(String query) {
//        boolean mengandungUnsur = false;
//        for (int i = 0; i < wh.length; i++) {
//            if (query.toLowerCase().split(" ")[0].equals(wh[i])) {
//                
//
//                mengandungUnsur = true;
//                break;
//            }
//
//        }
//        if (!mengandungUnsur) {
//            System.err.println("I dont know, What is " + query + "?");
//            System.out.print(query + " is : ");
//
//        }
        System.err.println("I dont know how to answer " + query);
        System.out.print("I should answer : ");
        Scanner inputx = new Scanner(System.in);
        String result = inputx.nextLine();
//        inputx.close();

        learn(query, result);
        System.out.println("now, I know how to answer '" + query + "'");
//        return mengandungUnsur;
    }

    public void rearrange() {
        boolean sorted = false;

        while (!sorted) {
            sorted = true;
            for (int i = 0; i < knowledges.size() - 1; i++) {
                if (knowledges.get(i).getQuery().length() > knowledges.get(i + 1).getQuery().length()) {
                    if (debugMode) {
                        System.out.println("Switch " + knowledges.get(i).getQuery() + "->" + knowledges.get(i + 1).getQuery());
                    }
                    ObjectInput tmp = knowledges.get(i);
                    knowledges.set(i, knowledges.get(i + 1));
                    knowledges.set(i + 1, tmp);
                    sorted = false;
                }
            }
        }
        writeFile();
        System.out.println("Rearrange Completed");
    }
}
