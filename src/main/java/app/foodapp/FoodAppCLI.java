package app.foodapp;

import app.foodapp.model.Favourites;
import app.foodapp.model.Receipt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FoodAppCLI {
    public static void main(String[] args) throws IOException {
        System.out.println("\t\t\t\t\t\t\t\t\tWelcome to the food app\n");

        Scanner scanner = new Scanner(System.in);

        documentation();

        loop: while(true){
            switch (Integer.parseInt(scanner.next())) {
                case 1:
                    Favourites favorites = new Favourites();
                    favoritesDocumentation();
                    Scanner favoritesScanner = new Scanner(System.in);
                    switch (Integer.parseInt(favoritesScanner.next())) {
                        case 1:
                            //TODO
                            break;
                        case 2:
                            //TODO
                            break;
                        default:
                            System.out.println("please enter a valid number\n");
                            favoritesDocumentation();
                    }

                    documentation();
                    break;

                case 2:
                    Scanner requestScanner = new Scanner(System.in);
                    receiptsDocumentation();
                    switch (Integer.parseInt(requestScanner.next())) {
                        case 1:
                            Scanner receiptId = new Scanner(System.in);
                            System.out.println("please enter the receipt id: ");
                            Receipt receipt = new Receipt(Integer.parseInt(receiptId.next()));
                            addToFavorites(receipt);
                            break;

                        case 2:
                            System.out.println("please enter the ingredients separated with spaces:");
                            Scanner input = new Scanner(System.in);
                            String ingredientsString = input.nextLine();

                            String[] t = ingredientsString.split(" ");
                            List<String> ingredients = Arrays.asList(t);


                            System.out.println("please enter the number of receipts you want to generate:");
                            Scanner numberOfReceipts = new Scanner(System.in);
                            ArrayList<Receipt> receipts = Receipt.searchByIngredients(ingredients, Integer.parseInt(numberOfReceipts.next()),0);

                            System.out.println(receipts);

                            assert receipts != null;
                            for (Receipt value : receipts) {
                                addToFavorites(value);
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
        scanner.close();
    }

    public static void documentation() {
        System.out.println("1-favorites");
        System.out.println("2-requests");
        System.out.println("100-exit\n");
        System.out.println("choose a number:\n");
    }

    public static void favoritesDocumentation() {
        System.out.println("1-show favorites");
        System.out.println("2-delete receipt from favorites\n");
        System.out.println("Chose a number:\n");
    }


    public static void receiptsDocumentation() {
        System.out.println("1-send request using ID");
        System.out.println("2-search for receipts using ingredients\n");
        System.out.println("Chose a number:\n");
    }

    public static void addToFavorites(Receipt receipt){
        System.out.println("Do you want to add to favorites? (y/n): ");
        Scanner yOrN = new Scanner(System.in);
        if ("y".equals(yOrN.nextLine())) {
            Favourites.addToFavourites(receipt);
        }
    }
}
