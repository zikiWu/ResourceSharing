package com.zk.service;



import com.zk.po.model.Category;
import com.zk.po.query.CategoryQuery;

import java.util.List;

public interface CategoryService {
	
	public List<Category> findCategoryList(CategoryQuery categoryQuery);
	
	public List<Category> findCategory4TopicCount(CategoryQuery categoryQuery);
	
	public Category findCategoryBypCategoryId(Integer pCategoryId);
	
	public Category findCategoryByCategoryId(Integer categoryId);
	
	public Category findSingleCategoryByCategoryId(Integer category);
}
