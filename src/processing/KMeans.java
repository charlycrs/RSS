/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processing;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Charly
 */
public class KMeans {

    int nCentroids;
    HashMap dicReps;
    int nWords;
    HashMap centroids;

    public KMeans(int centroids, HashMap dicReps, int nWords) {
        this.nCentroids = centroids;
        this.dicReps = dicReps;
        this.centroids = new HashMap();
        this.nWords = nWords;
    }

    public void runKMeans() {
        createCentroids();
        ArrayList<Double> entry;
//        for (Object key : dicReps.keySet()) {
//            entry = (ArrayList<Double>) dicReps.get(Integer.parseInt(key.toString()));
//        }
        for (Object key : centroids.keySet()) {
            System.out.println(centroids.get(Integer.parseInt(key.toString())));
        }
    }

    public void createCentroids() {
        ArrayList<Double> centroid;
        for (int i = 0; i < nCentroids; i++) {
            centroid = new ArrayList<>();
            for (int j = 0; j < nWords; j++) {
                centroid.add(Math.random());
            }
            centroids.put(i, centroid);
        }
    }
}
