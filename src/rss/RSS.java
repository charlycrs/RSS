package rss;

import java.util.ArrayList;
import java.util.HashMap;
import processing.KMeans;

/**
 *
 * @author Carlos Romero
 */
public class RSS {

    public static String eliminarPuntuaciones(String cad) {
        cad = cad.replace("&quot;", "");
        cad = cad.replace("¿", "");
        cad = cad.replace("?", "");
        cad = cad.replace("!", "");
        cad = cad.replace("¡", "");
        cad = cad.replace("-", "");
        cad = cad.replace("_", "");
        cad = cad.replace("/", "");
        cad = cad.replace("+", "");
        cad = cad.replace("*", "");
        cad = cad.replace("[", "");
        cad = cad.replace("]", "");
        cad = cad.replace("{", "");
        cad = cad.replace("}", "");
        cad = cad.replace("(", "");
        cad = cad.replace(")", "");
        cad = cad.replace("$", "");
        cad = cad.replace("%", "");
        cad = cad.replace("&", "");
        cad = cad.replace("\"", "");
        cad = cad.replace(".", "");
        cad = cad.replace(":", "");
        cad = cad.replace(",", "");
        cad = cad.replace(";", "");
        cad = cad.replace("<", "");
        cad = cad.replace(">", "");
        cad = cad.replace("á", "a");
        cad = cad.replace("é", "e");
        cad = cad.replace("í", "i");
        cad = cad.replace("ó", "o");
        cad = cad.replace("ú", "u");
        return cad;
    }

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        RSSFeedParser parser = new RSSFeedParser("http://www.eluniversal.com.mx/rss/ciencia.xml");
        Feed feed = parser.readFeed();
        String msg;
        ArrayList<String> arrMsg = new ArrayList<>();
        ArrayList<String> exclusionList = new ArrayList<>();
        exclusionList.add("a");
        exclusionList.add("ante");
        exclusionList.add("bajo");
        exclusionList.add("con");
        exclusionList.add("contra");
        exclusionList.add("de");
        exclusionList.add("en");
        exclusionList.add("para");
        exclusionList.add("por");
        exclusionList.add("sin");
        exclusionList.add("so");
        exclusionList.add("sobre");
        exclusionList.add("tras");
        exclusionList.add("el");
        exclusionList.add("la");
        exclusionList.add("lo");
        exclusionList.add("las");
        exclusionList.add("los");
        exclusionList.add("el");
        exclusionList.add("ella");
        exclusionList.add("ellos");
        exclusionList.add("yo");
        exclusionList.add("tu");
        exclusionList.add("el");
        exclusionList.add("nosotros");
        exclusionList.add("ustedes");
        exclusionList.add("que");
        exclusionList.add("ti");
        exclusionList.add("mi");
        exclusionList.add("un");
        exclusionList.add("uno");
        exclusionList.add("unos");
        exclusionList.add("una");
        exclusionList.add("unas");
        exclusionList.add("y");
        for (FeedMessage message : feed.getMessages()) {
            msg = message.title + " " + message.description;
            msg = msg.toLowerCase();
            msg = eliminarPuntuaciones(msg);
            arrMsg.add(msg);
        }
        Procesamiento procesamiento = new Procesamiento(arrMsg);
        String[] arrWords;
        HashMap words = new HashMap();
        HashMap dicReps = new HashMap();
        int c = 0;
        for (String entry : arrMsg) {
            arrWords = entry.split(" ");
            for (String word : arrWords) {
                if (!words.containsValue(word) && !exclusionList.contains(word)) {
                    words.put(c, word);
                    c++;
                }
            }
        }
        String word;
        int i = 0;
        ArrayList<Double> arrRep;
        for (String entry : arrMsg) {
            arrRep = new ArrayList<>();
            for (Object w : words.values()) {
                word = w.toString();
                arrRep.add(procesamiento.getTFIDF(entry, word));
            }
            dicReps.put(i, arrRep);
            i++;
        }
        int rep;
        double max = 0.0;
        int index = 0;
        ArrayList<String> maxWords;
        for (Object r : dicReps.keySet()) {
            maxWords = new ArrayList<>();
            rep = Integer.parseInt(r.toString());
            for (double d : (ArrayList<Double>) dicReps.get(rep)) {
                if (d > max) {
                    max = d;
                }
                index++;
            }
            index = 0;
            for (double d : (ArrayList<Double>) dicReps.get(rep)) {
                if (d == max) {
                    maxWords.add(words.get(index).toString());
                }
                index++;
            }
            System.out.println(arrMsg.get(rep) + "-----" + maxWords + "-----" + max);
            index = 0;
            max = 0.0;
            maxWords.clear();
        }

        System.out.println("\n\n");

        KMeans kMeans = new KMeans(5, dicReps, words.size());
        kMeans.runKMeans();
    }
}
