package com.challenge.cities.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileConverter {

    public static List<String[]> convertCSVToList() throws FileNotFoundException, IOException {
        String filePath = "/home/yuribergamo/Documentos/Yuri/challenge/cidades.csv";
        List<String[]> retorno = new ArrayList<>();
        BufferedReader br = null;
        String line = "";
        try {
            br = new BufferedReader(new FileReader(filePath));
            while ((line = br.readLine()) != null) {
                // use comma as separator
                retorno.add(line.split(","));
            }
        } finally {
            if (br != null) {
                br.close();
            }
        }
        return retorno;
    }
}
