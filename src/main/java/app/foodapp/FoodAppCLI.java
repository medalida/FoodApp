package app.foodapp;

import app.foodapp.model.Favourites;
import app.foodapp.model.Receipt;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FoodAppCLI {
    public static void main(String[] args){
        System.out.println("\t\t\t\t\t\t\t\t\tWelcome to the food app\n");

        int userInput;

        documentation();




        loop: while(true){
            while (true) {
                Scanner scanner = new Scanner(System.in);
                try {
                    userInput = (scanner.nextInt());
                    break;
                } catch (Exception e) {
                    System.out.println("please enter a valid number:");
                }
            }
            switch (userInput) {
                case 1:
                    favoritesDocumentation();
                    int userInput1;
                    while (true) {
                        Scanner favoritesScanner = new Scanner(System.in);
                        try {
                            userInput1 = (favoritesScanner.nextInt());
                            break;
                        } catch (Exception e) {
                            System.out.println("please enter a valid number:");
                        }
                    }
                    switch (userInput1) {
                        case 1:
                            ArrayList<Object> listOfFavorites = Favourites.getFavourites();
                            for (Object listOfFavorite : listOfFavorites) {
                                JSONObject favorite = (JSONObject) listOfFavorite;
                                System.out.println(favorite.get("id") + " - " + favorite.get("title"));

                            }
                            System.out.println();
                            break;
                        case 2:
                            System.out.println("enter the IDs of receipts you want to delete separated with spaces");
                            Scanner input = new Scanner(System.in);
                            String numbers = input.nextLine();

                            String[] t = numbers.split(" ");
                            List<Integer> listOfNumbers = new ArrayList<>();
                            for (String listOfNumber : t) {
                                listOfNumbers.add(Integer.parseInt(listOfNumber));
                            }

                            for (int number : listOfNumbers) {
                                Favourites.removeFromFavourites(new Receipt(number));
                            }

                            break;
                        default:
                            System.out.println("please enter a valid number\n");
                            favoritesDocumentation();
                    }

                    documentation();
                    break;

                case 2:
                    receiptsDocumentation();
                    int userInput2;
                    while (true) {
                        Scanner requestScanner = new Scanner(System.in);
                        try {
                            userInput2 = (requestScanner.nextInt());
                            break;
                        } catch (Exception e) {
                            System.out.println("please enter a valid number:");
                        }
                    }
                    switch (userInput2) {
                        case 1:
                            Scanner receiptId = new Scanner(System.in);
                            System.out.println("enter the receipt id: ");
                            Receipt receipt = new Receipt(Integer.parseInt(receiptId.next()));
                            JSONObject myReceipt1 = (JSONObject) receipt.getSummary();
                            System.out.println(myReceipt1.get("title"));
                            System.out.println();
                            System.out.println("Do you want to add to favorites? (y/n): ");
                            Scanner yOrN = new Scanner(System.in);
                            if ("y".equals(yOrN.nextLine())) {
                                Favourites.addToFavourites(receipt);
                            }
                            break;

                        case 2:
                            System.out.println("enter the ingredients separated with spaces:");
                            Scanner input = new Scanner(System.in);
                            String ingredientsString = input.nextLine();

                            String[] t = ingredientsString.split(" ");
                            List<String> ingredients = Arrays.asList(t);


                            System.out.println("enter the number of receipts you want to generate:");
                            Scanner numberOfReceipts = new Scanner(System.in);
                            ArrayList<Receipt> receipts = Receipt.searchByIngredients(ingredients, Integer.parseInt(numberOfReceipts.next()),0);
                            assert receipts != null;
                            JSONObject myReceipt2;
                            for (Receipt value : receipts) {
                                myReceipt2 = (JSONObject) value.getSummary();
                                System.out.println(myReceipt2.get("id") + " - " + myReceipt2.get("title"));
                            }
                            System.out.println();

                            System.out.println("enter the IDs of receipts you want to add to favourites separated with spaces");
                            Scanner input666 = new Scanner(System.in);
                            String Snumbers = input666.nextLine();

                            String[] tt = Snumbers.split(" ");
                            List<Integer> listOfNumberss = new ArrayList<>();
                            for (String listOfNumber : tt) {
                                listOfNumberss.add(Integer.parseInt(listOfNumber));
                            }

                            for (int number : listOfNumberss) {
                                Favourites.addToFavourites(new Receipt(number));
                            }
                            break;

                        default:
                            System.out.println("please enter a valid number: ");
                            receiptsDocumentation();
                    }
                    documentation();
                    break;


                case 100:
                    System.out.println("exiting..");
                    break loop;
                default:
                    System.out.println("please enter a valid number\n");

                    documentation();
                    break;
            }
        }
    }

    public static void documentation() {
        System.out.println("1-favorites");
        System.out.println("2-requests");
        System.out.println("100-exit\n");
        System.out.println("choose a number:\n");
    }

    public static void favoritesDocumentation() {
        System.out.println("1-display favorites");
        System.out.println("2-delete receipts from favorites\n");
        System.out.println("Chose a number:\n");
    }


    public static void receiptsDocumentation() {
        System.out.println("1-send request using ID");
        System.out.println("2-search for receipts using ingredients\n");
        System.out.println("Chose a number:\n");
    }
}