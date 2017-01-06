package com.asterisk.opensource.dao;

import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BlackWhiteDao {

    List<String> findWhiteList();

    List<String> findBlackList();



}
