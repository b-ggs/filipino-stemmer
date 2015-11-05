import java.util.*;

/**
 * Created by boggs on 11/4/15.
 */
public class Searcher {
    public HashMap<String, ArrayList<Integer>> wordsMap;
    public HashMap<Integer, Integer> resultsMap;

    public Searcher (HashMap<String, ArrayList<Integer>> wordsMap) {
        this.wordsMap = wordsMap;
    }

    public String search(ArrayList<String> queries){
        resultsMap = new HashMap<>();
        HashMap<Integer, Integer> andResultsMap = new HashMap<>();
        for(String query : queries) {
            if(wordsMap.containsKey(query)) {
                ArrayList<Integer> indices = wordsMap.get(query);
                for (Integer index : indices) {
                    if (resultsMap.containsKey(index)) {
                        Integer newVal = resultsMap.get(index) + 1;
                        resultsMap.put(index, newVal);
                        if (newVal == queries.size()) {
                            andResultsMap.put(index, newVal);
                        }
                    } else {
                        resultsMap.put(index, 1);
                    }
                }
            } else {
                return queries.toString();
            }
        }

        HashMap<Integer, Integer> sortedMap = new HashMap<>();

        List keys = new ArrayList(resultsMap.keySet());
        Collections.sort(keys);

        StringBuilder sb = new StringBuilder();
        for (Object keyObj : keys) {
            Integer key = (Integer) keyObj;
            Integer value = resultsMap.get(key);
            sb.append(String.format("%s ", key, value));
        }


        return String.format("%s\n - AND %s \n - OR %s", queries.toString(), andResultsMap.toString(), sb.toString());
    }

}
