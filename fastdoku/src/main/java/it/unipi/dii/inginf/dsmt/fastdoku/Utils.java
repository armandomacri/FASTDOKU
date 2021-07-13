package it.unipi.dii.inginf.dsmt.fastdoku;

import java.util.*;
public class Utils {
    //funzione che sort un hashmap presa da gameon, da modificare
    public static HashMap<String, Integer> sortHashMap(HashMap<String, Integer> hashMap, int limit) {
        if (hashMap == null)
        {
            return null;
        }

        Comparator<Map.Entry<String, Integer>> valueComparator = (o1, o2) -> {
            Integer i1 = o1.getValue();
            Integer i2 = o2.getValue();
            return i2.compareTo(i1);
        };

        Set<Map.Entry<String, Integer>> entries = hashMap.entrySet();

        // Sort method needs a List, so let's first convert Set to List in Java
        List<Map.Entry<String, Integer>> listOfEntries = new ArrayList<>(entries);

        // sorting HashMap by values using comparator
        listOfEntries.sort(valueComparator);
        HashMap<String, Integer> sortedByValue = new LinkedHashMap<>(listOfEntries.size());

        // copying entries from List to Map with limited records
        int i = 0;
        for (Map.Entry<String, Integer> entry : listOfEntries) {
            if(i >= limit) break;
            sortedByValue.put(entry.getKey(), entry.getValue());
            i++;
        }

        return sortedByValue;
    }
}
