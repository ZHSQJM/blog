package com.zhs.dao;

import com.zhs.entity.Orgchart;
import com.zhs.service.MyMapper;

import java.util.List;

public interface OrgChartMapper extends MyMapper<Orgchart> {

    List<Orgchart> selectByPid(Integer id);

    int insertReturnId(Orgchart orgchart);
}