package com.medi.pubmed.journal;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JournalServiceImpl implements JournalService{

	@Autowired
	private JournalDAO journalDao;

	public List<HashMap<String, Object>> getJournalList(HashMap<String, Object> param) {
		return journalDao.getJournalList(param);
	}
}
