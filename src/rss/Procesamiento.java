package rss;

import java.util.ArrayList;

/**
 *
 * @author Carlos Romero
 */
public class Procesamiento {

    int nEntries;
    ArrayList<String> words;
    ArrayList<String> dicReps;
    ArrayList<String> entries;

    public Procesamiento(ArrayList<String> entries) {
        this.nEntries = entries.size();
        this.words = new ArrayList<>();
        this.dicReps = new ArrayList<>();
        this.entries = entries;
    }

    public double getTFIDF(String entry, String word) {
        double tf = getFreq(entry, word);
        double tif = getInvFreq(word);
        return tf * tif;
    }

    private double getFreq(String entry, String word) {
        String[] arr = entry.split(" ");
        float partial = 0;
        for (String w : arr) {
            if (word.equals(w)) {
                partial++;
            }
        }
        return partial / arr.length;
    }

    private double getInvFreq(String word) {
        String file;
        float count = 0;
        for (String entry : entries) {
            file = entry;
            if (file.contains(word)) {
                count++;
            }
        }
        return Math.log(nEntries / count);
    }
}
