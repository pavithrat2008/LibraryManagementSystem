package service;

import java.io.*;

public class FileManager {

    public static void saveData(String fileName, String data) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(data);
            System.out.println("Data saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    public static String loadData(String fileName) {
        StringBuilder data = new StringBuilder();

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(fileName))) {

            String line;

            while ((line = reader.readLine()) != null) {
                data.append(line).append("\n");
            }

        } catch (FileNotFoundException e) {
            return "";
        } catch (IOException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }

        return data.toString();
    }
}

