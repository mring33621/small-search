package com.mattring.smallsearch;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.index.memory.MemoryIndex;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.standard.StandardQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Search {

    public static List<Object> find(List<Object> userQueries, Object targetDocument) {
        final boolean storeOffsets = true;
        final MemoryIndex memoryIndex = new MemoryIndex(storeOffsets);
        final Analyzer analyzer = new EnglishAnalyzer();
        memoryIndex.addField("mainTxt", targetDocument.toString(), analyzer);
        memoryIndex.freeze();
        final StandardQueryParser parser = new StandardQueryParser();
        parser.setAnalyzer(analyzer);
        final IndexSearcher searcher = memoryIndex.createSearcher();
        final List<Object> userHits = new LinkedList<>();
        for (Object userQuery : userQueries) {
            try {
                final Query luceneQuery = parser.parse(userQuery.toString(), "mainTxt");
                final TopDocs hits = searcher.search(luceneQuery, 1);
                userHits.add(hits.totalHits.value);
            } catch (QueryNodeException qnex) {
                qnex.printStackTrace();
            } catch (IOException ioex) {
                ioex.printStackTrace();
            }
        }
        return userHits;
    }
}
