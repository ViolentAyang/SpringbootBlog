package com.violentayang.blog.service;

import com.violentayang.blog.vo.Result;
import com.violentayang.blog.vo.params.PageParams;

public interface ArticleService {
    /**
     * 分页查询文章列表
     * @param pageParams
     * @return
     */
    Result listArticle(PageParams pageParams);
}
