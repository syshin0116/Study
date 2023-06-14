package kr.co.login.dao;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.login.vo.LoginVo;

@Repository
@Mapper
public interface LoginDao {
    
    //로그인 정보 조회
    LoginVo selectLogin(LoginVo vo);
}
