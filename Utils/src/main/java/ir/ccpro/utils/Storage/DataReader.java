package ir.ccpro.utils.Storage;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DataReader {
    public static final String FILE_NAME = "myFile";

    public static void SaveData(Context context, String data) {
        // Define the File Path and its Name
        try {
            File file = new File(context.getFilesDir(), FILE_NAME);
            FileWriter fileWriter = null;
            fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(data);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String ReadData(Context context) {
        try {
            File file = new File(context.getFilesDir(), FILE_NAME);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            // This responce will have Json Format String
            String responce = stringBuilder.toString();
            return responce;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
