package com.anon.DAO;
import java.util.List;
import java.util.Map;

import com.anon.test.ImTestPOJO;
public interface ImTestPOJOMapper{

public List<ImTestPOJO> getSimpleList();


public ImTestPOJO selectByPrimaryKey(Integer id);


public Integer selectCount();


public Integer insertSelective(ImTestPOJO imtestpojo);


public List<ImTestPOJO> selectByPageNo(Map<String, Object> map);


public Integer deleteByPrimaryKey(Integer id);


public Integer updateByPrimaryKeySelective(ImTestPOJO imtestpojo);
}