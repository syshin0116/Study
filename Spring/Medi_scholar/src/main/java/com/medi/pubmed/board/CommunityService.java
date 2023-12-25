package com.medi.pubmed.board;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface CommunityService {
	
	List<HashMap<String, Object>> getCommunityList(HashMap<String, Object> param);

	void insertCommunity(HashMap<String, Object> param);

	HashMap<String, Object> detailCommunity(HashMap<String, Object> param);

	int updateCommunity(HashMap<String, Object> param);

	void insertReply(HashMap<String, Object> param);

	void deleteReply(HashMap<String, Object> param);

	void insertSubreply(HashMap<String, Object> param);

	

}
