package com.Infotrix.week1.Task1.Currency.Converter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class CurrencyConverter {
    private static final String API_URL = "http://api.exchangerate.host/live?access_key=c1038bcea094e22501a5dccbf256eb3a";
    private Map<String, Double> exchangeRates;
    private Set<String> favorites;

    public CurrencyConverter() {
        exchangeRates = new HashMap<>();
        favorites = new HashSet<>();
        fetchCurrencyData();
    }

    private void fetchCurrencyData() {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JSONObject json = new JSONObject(response.toString());
            JSONObject quotes = json.getJSONObject("quotes");
            for (String key : quotes.keySet()) {
            	System.out.println(key);
            	System.out.println(quotes.getDouble(key));
            	
                exchangeRates.put(key, quotes.getDouble(key));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showAllCurrencies() {
        System.out.println("All available currencies:");
        for (String currencyCode : exchangeRates.keySet()) {
            System.out.println(currencyCode);
        }
    }

    public void addFavoriteCurrency(String currencyCode) {
        if (exchangeRates.containsKey(currencyCode)) {
            favorites.add(currencyCode);
            System.out.println(currencyCode + " has been added to your favorite currencies.");
        } else {
            System.out.println("Invalid currency code. Please enter a valid currency code.");
        }
    }

    public void showFavoriteCurrencies() {
        System.out.println("Your favorite currencies:");
        for (String currencyCode : favorites) {
            System.out.println(currencyCode);
        }
    }

    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Menu:");
            System.out.println("1. Add favorite currencies");
            System.out.println("2. Show favorite currencies");
            System.out.println("3. Convert amount to favorite currencies");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    addFavoriteCurrency(scanner);
                    break;
                case 2:
                    showFavoriteCurrencies();
                    break;
                case 3:
                    convertAmountToCurrencies(scanner);
                    break;
                case 4:
                    System.out.println("Exiting. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);
    }

    private void addFavoriteCurrency(Scanner scanner) {
        String input;
        System.out.println("Enter currency code to add to favorites (or type 'done' to finish):");
        while (!(input = scanner.nextLine().trim()).equalsIgnoreCase("done")) {
            if (exchangeRates.containsKey(input.toUpperCase())) {
                addFavoriteCurrency(input.toUpperCase());
            } else {
                System.out.println("Invalid currency code. Please enter a valid currency code.");
            }
        }
    }

    private void convertAmountToCurrencies(Scanner scanner) {
        System.out.println("Choose a currency code to convert from:");
        for (String currencyCode : exchangeRates.keySet()) {
            System.out.println(currencyCode);
        }
        System.out.print("Enter source currency code: ");
        String sourceCurrencyCode = scanner.nextLine().toUpperCase();

        if (exchangeRates.containsKey(sourceCurrencyCode)) {
            System.out.print("Enter the amount to convert: ");
            double amount = scanner.nextDouble();
            scanner.nextLine(); // Consume newline character

            System.out.println("Converting " + amount + " " + sourceCurrencyCode + " to favorite currencies:");
            for (String targetCurrencyCode : favorites) {
                if (!targetCurrencyCode.equals(sourceCurrencyCode)) {
                    double convertedAmount = amount * exchangeRates.get(targetCurrencyCode) / exchangeRates.get(sourceCurrencyCode);
                    System.out.printf("%.2f %s is equal to %.2f %s\n", amount, sourceCurrencyCode, convertedAmount, targetCurrencyCode);
                }
            }
        } else {
            System.out.println("Invalid currency code. Please enter a valid currency code.");
        }
    }
    public static void main(String[] args) {
        CurrencyConverter converter = new CurrencyConverter();
        converter.showMenu();
    }
}
