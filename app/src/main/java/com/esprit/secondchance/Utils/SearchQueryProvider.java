package com.esprit.secondchance.Utils;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by sofien on 19/12/2017.
 */

public class SearchQueryProvider extends SearchRecentSuggestionsProvider {
    public final static  String authority ="com.labidi.wafa.secondchance.Utils.SearchQueryProvider";
    public final static int MODE = DATABASE_MODE_QUERIES | DATABASE_MODE_2LINES;

    public SearchQueryProvider(){
        setupSuggestions(authority ,MODE);
    }
}
