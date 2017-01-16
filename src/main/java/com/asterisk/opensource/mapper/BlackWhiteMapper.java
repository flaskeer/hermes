package com.asterisk.opensource.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BlackWhiteMapper {

    List<String> findWhiteList();

    List<String> findBlackList();



}
