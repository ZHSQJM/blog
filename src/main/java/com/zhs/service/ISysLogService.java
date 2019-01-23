package com.zhs.service;

import com.github.pagehelper.PageInfo;
import com.zhs.entity.SysLog;
import com.zhs.entity.SysUser;

/**
 * @project: zhs-blog
 * @author: zhs
 * @date: 2019/1/10 10:01
 * @package: com.zhs.service
 * @description:
 */
public interface ISysLogService {

    /**
     * 新增操作日志
     * @param operLog 操作日志对象
     */
     void insertOperlog(SysLog operLog);

    PageInfo<SysLog> searchLog(SysLog operLog, Integer currentPage, Integer pageSize);
}
