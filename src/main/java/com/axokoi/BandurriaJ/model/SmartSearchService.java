package com.axokoi.BandurriaJ.model;

import java.util.List;

public interface SmartSearchService<T> {
	List<T> smartSearch(String inputSearch);
}
