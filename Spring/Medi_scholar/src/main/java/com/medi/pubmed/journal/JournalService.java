package com.medi.pubmed.journal;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface JournalService {
	
	List<HashMap<String, Object>> getJournalList(HashMap<String, Object> param);

}
