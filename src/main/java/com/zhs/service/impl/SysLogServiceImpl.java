package com.zhs.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhs.dao.SysLogMapper;
import com.zhs.entity.SysLog;
import com.zhs.entity.SysUser;
import com.zhs.service.ISysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @project: zhs-blog
 * @author: zhs
 * @date: 2019/1/10 10:02
 * @package: com.zhs.service.impl
 * @description:
 */
@Service
public class SysLogServiceImpl implements ISysLogService {


    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public void insertOperlog(SysLog operLog) {
        sysLogMapper.insert(operLog);
    }

    @Override
    public PageInfo<SysLog> searchLog(SysLog operLog, Integer currentPage, Integer pageSize) {
        PageHelper.startPage( currentPage,  pageSize);
        List<SysLog> list= sysLogMapper.select(operLog);
        PageInfo<SysLog> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
