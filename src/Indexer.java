import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by boggs on 11/3/15.
 */
public class Indexer {
    private Writer writer;
    private ArrayList<String> articles = new ArrayList<>();
    private HashMap<String, ArrayList<Integer>> wordsMap = new HashMap<>();

    public Indexer(Writer writer) {
        this.writer = writer;
    }

    public HashMap<String, ArrayList<Integer>> getWordsMap() {
        return wordsMap;
    }

    public void addArticle(String article) {
        articles.add(article);
    }

    public void addWord(String word, int articleNumber) {
        if(wordsMap.containsKey(word)) {
            ArrayList<Integer> indices = wordsMap.get(word);
            if(!indices.contains(articleNumber))
                indices.add(articleNumber);
            wordsMap.put(word, indices);
        } else {
            ArrayList<Integer> indices = new ArrayList<>();
            if(!indices.contains(articleNumber))
                indices.add(articleNumber);
            wordsMap.put(word, indices);
        }
    }

    public void write() {
        writer.writeLine("");
        writeArticles();
        writer.writeLine("");
        writeMap();
    }

    public void writeArticles() {
        writer.writeLine("Articles");
        int index = 0;
        for(String article : articles) {
            writer.writeLine(String.format("%s: %s", index, article));
            index++;
        }
    }

    public void writeMap() {
        writer.writeLine("Words");
        Object[] keys = wordsMap.keySet().toArray();
        for(Object keyObject : keys) {
            String key = keyObject.toString();
            ArrayList<Integer> indices = wordsMap.get(key);
            writer.writeLine(String.format("%s - (%s occurences): %s", key, indices.size(), indices.toString()));
        }
    }
 }
