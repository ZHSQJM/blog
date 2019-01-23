package com.zhs.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhs.dao.AttachmentMapper;
import com.zhs.entity.Attachment;
import com.zhs.service.IAttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @project: zhs-blog
 * @author: zhs
 * @date: 2019/1/21 10:47
 * @package: com.zhs.service.impl
 * @description:
 */

@Service
public class AttachmentServiceImpl implements IAttachmentService {

    @Autowired
    private AttachmentMapper attachmentMapper;


    @Override
    public void save(Attachment attachment) throws Exception {
        attachmentMapper.insert(attachment);
    }

    @Override
    public PageInfo<Attachment> getAttachment(int page, int limit) {
        PageHelper.startPage(page, limit);
        List<Attachment> lists = attachmentMapper.selectAll();
        return new PageInfo<>(lists);
    }

    @Override
    public Attachment findById(int id)  {
        return attachmentMapper.selectByPrimaryKey(id);
    }

    @Override
    public void deleteAttachment(int id) throws Exception {
        attachmentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Attachment> countAttachment() {
        return null;
    }
}
