package com.medi.pubmed.board;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommunityServiceImpl implements CommunityService{

	@Autowired
	private CommunityDAO communityDao;
	@Override
	public List<HashMap<String, Object>> getCommunityList(HashMap<String, Object> param) {
		return communityDao.getCommunityList(param);
	}
	@Override
	public void insertCommunity(HashMap<String, Object> param) {
		communityDao.insertCommunity(param);
		
	}
	@Override
	public HashMap<String, Object> detailCommunity(HashMap<String, Object> param) {
		
		return communityDao.detailCommunity(param);
	}
	@Override
	public int updateCommunity(HashMap<String, Object> param) {
		
		return communityDao.updateCommunity(param);
	}
	@Override
	public void insertReply(HashMap<String, Object> param) {
		
		 communityDao.insertReply(param);
	}
	@Override
	public void deleteReply(HashMap<String, Object> param) {
		communityDao.deleteReply(param);
		
	}
	@Override
	public void insertSubreply(HashMap<String, Object> param) {
		communityDao.insertSubreply(param);
		
	}

	
}
