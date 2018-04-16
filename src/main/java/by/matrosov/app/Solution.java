package by.matrosov.app;

import net.lingala.zip4j.core.ZipFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Solution {

    private static final String sourceZipFilePath = "d://myAppl/docs.zip";
    private static final String extractedZipFilePath = "d://myAppl/";
    private static final int passwordSize = 10;

    public static void main(String[] args) throws IOException{
        BufferedReader reader = Files.newBufferedReader(Paths.get("d://myAppl/pass_combinations.txt"));
        List<String> passwordsList = reader.lines().collect(Collectors.toList());
        reader.close();

        for (String pass : passwordsList) {

            List<String> combList = new ArrayList<>();
            calculateAllCombinations(combList, pass);
            //System.out.println(combList);

            boolean flag = true;
            for (String password : combList) {
                try{
                    ZipFile zipFile = new ZipFile(sourceZipFilePath);
                    zipFile.setPassword(password);
                    zipFile.extractAll(extractedZipFilePath);
                }catch (Exception e){
                    flag = false;
                }

                if (!flag){
                    flag = true;
                }else {
                    BufferedWriter writer = new BufferedWriter(new FileWriter("d://myAppl/right_password.txt"));
                    writer.write(password);
                    writer.close();
                    return;
                }
            }
        }


    }

    private static void calculateAllCombinations(List<String> list, String s){
        int count = 0;
        int size = passwordSize;
        int arr[] = new int[size];

        while(true){

            StringBuilder sb = new StringBuilder();
            for(int i : arr){
                sb.append(s.charAt(i));
            }
            list.add(String.valueOf(sb));
            count++;
            System.out.println(count);

            int i = size - 1;
            while(arr[i] == s.length() - 1){
                arr[i] = 0;
                i--;
                if(i < 0) return;
            }
            arr[i]++;
        }
    }


}
