package com.axokoi.BandurriaJ.services.dataaccess;

import java.util.List;

public interface SmartSearchService<T> {
	List<T> smartSearch(String inputSearch);
}
