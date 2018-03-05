package com.liyang.domain.poster;

import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.enums.ThemeType;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author duyiting
 * @date 2018/01/19
 */
public interface PosterRepository extends AuditorEntityRepository<Poster>,JpaSpecificationExecutor<Poster>{


    List<Poster> findByNameAndType(String name, ThemeType themeType);
}
