package com.violentayang.blog.service;

import com.violentayang.blog.vo.TagVo;

import java.util.List;

public interface TagService {
    List<TagVo> findTagsByArticleId(Long articleId);
}
