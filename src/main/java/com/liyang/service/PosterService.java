package com.liyang.service;

import com.liyang.domain.base.AbstractAuditorAct;
import com.liyang.domain.poster.*;
import com.liyang.enums.ThemeType;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.image.Images;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.util.FailReturnObject;

/**
 * @author duyiting
 * @date 2018/01/19
 */
@Service
public class PosterService extends AbstractAuditorService<Poster,PosterState,PosterAct,PosterLog>{

    @Autowired
    private PosterRepository posterRepository;
    @Autowired
    private PosterActRepository actRepository;
    @Autowired
    private PosterLogRepository logRepository;
    @Autowired
    private PosterStateRepository stateRepository;

    @Override
    @PostConstruct
    public void sqlInit() {
        List<PosterAct> list = actRepository.findAll();
        // 初始化
        if (list == null || list.size() == 0) {
            PosterAct save1 = actRepository.save(new PosterAct("创建", "create", 10, AbstractAuditorAct.ActGroup.UPDATE));
            PosterAct save2 = actRepository.save(new PosterAct("读取", "read", 20, AbstractAuditorAct.ActGroup.READ));
            PosterAct save3 = actRepository.save(new PosterAct("更新", "update", 30, AbstractAuditorAct.ActGroup.UPDATE));
            PosterAct save4 = actRepository.save(new PosterAct("删除", "delete", 40, AbstractAuditorAct.ActGroup.UPDATE));

            PosterState enabledState = new PosterState("有效", 0, "ENABLED");
            enabledState.setActs(Arrays.asList(save1, save2, save3).stream().collect(Collectors.toSet()));
            stateRepository.save(enabledState);
            PosterState disabledState = new PosterState("无效", 100, "DISABLED");
            disabledState.setActs(Arrays.asList(save1, save2, save3,save4).stream().collect(Collectors.toSet()));
            stateRepository.save(disabledState);
            PosterState deletedState = new PosterState("已删除",200,"DELETED");
            deletedState.setActs(Arrays.asList(save1,save2,save3,save4).stream().collect(Collectors.toSet()));
            stateRepository.save(deletedState);
        }


    }

    @Override
    public LogRepository<PosterLog> getLogRepository() {
        return logRepository;
    }

    @Override
    public PosterLog getLogInstance() {
        return new PosterLog();
    }

    @Override
    public AuditorEntityRepository<Poster> getAuditorEntityRepository() {
        return posterRepository;
    }

    @Override
    public ActRepository<PosterAct> getActRepository() {
        return actRepository;
    }

    @Override
    @PostConstruct
    public void injectEntityService() {
        new Poster().setService(this);
    }

    @Override
    @PostConstruct
    public void injectLogRepository() {
        new Poster().setLogRepository(logRepository);
    }

    public void save(PosterForSave posterForSave) {

        if(posterForSave == null){
            throw new FailReturnObject(ExceptionResultEnum.POSTER_DATA_ERROR);
        }

        List<Poster> posterList = posterRepository.findByNameAndType(posterForSave.getName(), ThemeType.valueOf(posterForSave.getPosterType()));
        for (Poster poster:posterList) {
            if(poster != null && !"DELETED".equals(poster.getState().getStateCode())){
                throw new FailReturnObject(ExceptionResultEnum.POSTER_REPEAT);
            }
        }

        Poster poster = new Poster();
        Images images = new Images();
        images.setHeight(posterForSave.getHeight());
        images.setImgUrl(posterForSave.getImgUrl());
        images.setSize(posterForSave.getSize());
        images.setWidth(posterForSave.getWidth());
        poster.setImages(images);
        poster.setType(ThemeType.valueOf(posterForSave.getPosterType()));
        poster.setName(posterForSave.getName());
        PosterState posterState = stateRepository.findByStateCode("DISABLED");
        poster.setState(posterState);
        posterRepository.save(poster);
    }


    public void deletePicture(Integer id) {
        Poster poster = posterRepository.findOne(id);
        if(poster == null){
            throw new FailReturnObject(ExceptionResultEnum.POSTER_ID_ERROR);
        }
        if(!"DISABLED".equals(poster.getState().getStateCode())){
            throw new FailReturnObject(ExceptionResultEnum.POSTER_STATE_ERROR);
        }
        PosterState deleteState = stateRepository.findByStateCode("DELETED");
        poster.setState(deleteState);
        posterRepository.save(poster);
    }

    public Page<Poster> searchPoster(PosterForSearch posterForSearch,Pageable pageable) {
        Page<Poster> posterPage = posterRepository.findAll(new Specification<Poster>() {

            @Override
            public Predicate toPredicate(Root<Poster> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                if (posterForSearch != null) {
                    if (!StringUtils.isEmpty(posterForSearch.getName())) {
                        Predicate nameEqual = cb.like(root.<String>get("name"),  posterForSearch.getName());
                        predicates.add(nameEqual);
                    }
                    if (!StringUtils.isEmpty(posterForSearch.getStateCode())) {
                        Predicate stateCodeEQ = cb.equal(root.get("state").get("stateCode"), posterForSearch.getStateCode());
                        predicates.add(stateCodeEQ);
                    }
//                    else {
//                        Predicate stateCodeEQ1 = cb.equal(root.get("state").get("stateCode"), "ENABLED");
//                        Predicate stateCodeEQ2 = cb.equal(root.get("state").get("stateCode"), "DISABLED");
//                        Predicate stateCodeOr = cb.or(stateCodeEQ1, stateCodeEQ2);
//                        predicates.add(stateCodeOr);
//                    }
                    if(!StringUtils.isEmpty(posterForSearch.getType())){
                        try {
                            ThemeType.valueOf(posterForSearch.getType());
                        }catch (Exception e){
                            e.printStackTrace();
                            throw new FailReturnObject(ExceptionResultEnum.POSTER_TYPE_ERROR);
                        }
                        Predicate typeEqual = cb.equal(root.<String>get("type"), ThemeType.valueOf(posterForSearch.getType()));
                        predicates.add(typeEqual);
                    }
                }
                query.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
        return posterPage;
    }

    public List<PosterState> getStates() {
        List<PosterState> stateList = stateRepository.findAll();
        return stateList;
    }

    public void chanageState(Integer id) {
        Poster poster = posterRepository.findOne(id);
        if(poster == null ){
            throw new FailReturnObject(ExceptionResultEnum.POSTER_ID_ERROR);
        }
        if("ENABLED".equals(poster.getState().getStateCode())){
            PosterState disabledState = stateRepository.findByStateCode("DISABLED");
            poster.setState(disabledState);
            posterRepository.save(poster);
        }else if("DISABLED".equals(poster.getState().getStateCode())){
            PosterState disabledState = stateRepository.findByStateCode("ENABLED");
            poster.setState(disabledState);
            posterRepository.save(poster);
        }
    }
}
