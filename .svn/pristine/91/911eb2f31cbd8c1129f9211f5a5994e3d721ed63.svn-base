package com.liyang.domain.base;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import com.liyang.domain.user.User;
/**
 * @author Administrator
 *
 * @param <T>
 */
@NoRepositoryBean
public interface FileRepository<T extends AbstractWorkflowFile> extends JpaRepository<T,Integer> {

	public List<T> findByEntityIdAndTopcategoryAndSubcategory(@Param("id") Integer id,@Param("topcategory") String topCategory, @Param("subcategory") String subCategory);

}
