package com.liyang.domain.moments;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonInfoRepository extends JpaRepository<PersonInfo, Integer> {
    @Query("select p from PersonInfo p where p.isShow=:isShow")
    public Page<PersonInfo> findAllPersonInfo(@Param("isShow") Integer isShow, Pageable pageable);

    @Query("select p from PersonInfo p ")
    public Page<PersonInfo> findAllPer( Pageable pageable);

    @Query("select p.id from PersonInfo p  where  p.nickName=?1")
    public List<PersonInfo> getNickName(String nickName);

    public PersonInfo findById(Integer id);

    public PersonInfo findByIsShow(Integer isShow);

}
