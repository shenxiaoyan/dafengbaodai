package com.liyang.config;

import com.liyang.domain.user.User;
import com.liyang.domain.user.UserAct;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.SingleTableEntityPersister;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.nio.channels.Channel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * @author Administrator
 * Created by win7 on 2017-08-07.
 *
 */
@Configuration
public class SpringRestConfig {
    @Autowired
    private EntityManager entityManager;

    @Bean
    public RepositoryRestConfigurer repositoryRestConfigurer() {
        return new RepositoryRestConfigurerAdapter() {
            @Override
            public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
                List<Class> classList = getAllEntityClass();
                for (Class clazz : classList) {
                    config.exposeIdsFor(clazz);
                }
//                config.exposeIdsFor(UserAct.class);
//                config.setDefaultMediaType(org.springframework.http.MediaType.APPLICATION_JSON);

            }
        };
    }

    /**
     * 获取所有实体类的class
     * @return
     */
    @Bean
    public List<Class> getAllEntityClass() {
        List<Class> classList = new ArrayList<>();
        try {
            EntityManagerFactory factory = entityManager.getEntityManagerFactory();
            SessionFactory sessionFactory = factory.unwrap(SessionFactory.class);
            if (sessionFactory != null) {

                Map<String, ClassMetadata> map = sessionFactory.getAllClassMetadata();
                Set<Map.Entry<String, ClassMetadata>> set = map.entrySet();
                for (Map.Entry<String, ClassMetadata> entry : set) {
                    Class clazz = Class.forName(entry.getKey());
                    classList.add(clazz);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return classList;
    }

}
