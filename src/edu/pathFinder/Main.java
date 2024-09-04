package edu.pathFinder;

import java.io.File;
import java.util.Scanner;

/**
 * Student name : P. T. R. Rodrigo
 * Student id : 20220673 / w1953311
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println("-------------------------------");
            System.out.println("    Sliding Puzzles Game   ");
            System.out.println("-------------------------------");
            System.out.println(" 1. Load a file            ");
            System.out.println(" 2. Exit                   ");
            System.out.println("-------------------------------");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    loadFileMenu();
                    break;
                case 2:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid input. Please enter a number between 1 and 3 :");
                    break;
            }
        }
        System.out.println("See you later. Have a nice day!");
    }

    private static void loadFileMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean backToMenu = false;
        while (!backToMenu) {
            System.out.println("-------------------------------");
            System.out.println("      Load File Menu       ");
            System.out.println("-------------------------------");
            System.out.println("  1. Load from benchmark    ");
            System.out.println("  2. Load from examples     ");
            System.out.println("  3. Open created examples  ");
            System.out.println("  4. Back to main menu      ");
            System.out.println("-------------------------------");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    loadFileFromFolder("benchmark/");
                    break;
                case 2:
                    loadFileFromFolder("examples/");
                    break;
                case 3:
                    loadFileFromFolder("new_maze/");
                    break;
                case 4:
                    backToMenu = true;
                    break;
                default:
                    System.out.println("Invalid input. Please enter a number between 1 and  4 :");
                    break;
            }
        }
    }


    private static void loadFileFromFolder(String folderPath) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        if (files != null && files.length > 0) {
            System.out.println("Available files:");
            for (int i = 0; i < files.length; i++) {
                System.out.println((i + 1) + ". " + files[i].getName());
            }
            System.out.println("Enter the number of the file to start game: ");
            Scanner scanner = new Scanner(System.in);
            int fileChoice = scanner.nextInt();
            if (fileChoice >= 1 && fileChoice <= files.length) {
                String fileName = folderPath + files[fileChoice - 1].getName();
                new ShortestPath(fileName);
            } else {
                System.out.println("Invalid input");
            }
        } else {
            System.out.println("No files in the selected folder");
        }
    }
}

