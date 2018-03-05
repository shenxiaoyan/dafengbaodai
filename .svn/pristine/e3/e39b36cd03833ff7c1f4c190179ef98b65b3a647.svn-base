package com.liyang.service;

import com.liyang.domain.base.AbstractAuditorAct;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.image.Images;
import com.liyang.domain.moments.*;
import com.liyang.domain.perbusinesscard.PerBusinessCard;
import com.liyang.helper.ResponseBody;
import com.liyang.util.FailReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MomentsService extends AbstractAuditorService<Moments, MomentsState, MomentsAct, MomentsLog> {
    @Autowired
    MomentsRepository momentsRepository;
    @Autowired
    MomentsLogRepository momentsLogRepository;
    @Autowired
    MomentsActRepository momentsActRepository;
    @Autowired
    MomentsStateRepository momentsStateRepository;
    @Autowired
    PersonInfoRepository personInfoRepository;
    //新增

    public ResponseBody save(MomentsBean momentsBean) {
        Moments moments = new Moments();
        moments.setContent(momentsBean.getContent());
        PersonInfo one = personInfoRepository.getOne(momentsBean.getPersonInfoId());
        if (one != null) {
            Set<Images> images = momentsBean.getImages();

            for (Images img : images) {
                images.add(img);
            }
            moments.setPersonInfo(one);
            moments.setImages(images);
            MomentsState state = momentsStateRepository.findByStateCode("UNPUBLISHED");
            moments.setState(state);
            moments.setTitle(momentsBean.getTitle());
            moments.setLinkUrl(momentsBean.getLinkUrl());
            momentsRepository.save(moments);
            return new ResponseBody("新增成功");
        } else {
            throw new FailReturnObject(555, "personInfoID不能为空");
        }

    }

    //修改
    public ResponseBody update(Moments moments, MomentsBean momentsBean, Integer id) {
        moments = momentsRepository.getOne(id);
        moments.setLinkUrl(momentsBean.getLinkUrl());
        moments.setTitle(momentsBean.getTitle());
        Set<Images> images = momentsBean.getImages();
        for (Images img : images) {
            images.add(img);
        }
        moments.setImages(images);
        moments.setContent(momentsBean.getContent());
        momentsRepository.save(moments);
        if (moments == null) {
            return new ResponseBody("修改失败！");
        }
        return new ResponseBody("修改成功");
    }

    //显示列表
    public Page<Moments> query(MomentsBean momentsBean, Pageable pageable) {

        Page<Moments> momentsPage = null;
        if (null == momentsBean) {
            momentsPage = momentsRepository.findAll(pageable);
        } else {
            momentsPage = momentsRepository.findAll(new Specification<Moments>() {
                @Override
                public Predicate toPredicate(Root<Moments> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    List<Predicate> predicates = new ArrayList<Predicate>();
                    if (momentsBean != null) {
                        if (!StringUtils.isEmpty(momentsBean.getStartTime())) {
                            Predicate startTimeEqual = cb.greaterThanOrEqualTo(root.get("lastModifiedAt"), momentsBean.getStartTime());
                            predicates.add(startTimeEqual);

                        }
                        if (!StringUtils.isEmpty(momentsBean.getEndTime())) {
                            Predicate endTimeEqual = cb.lessThanOrEqualTo(root.get("lastModifiedAt"), momentsBean.getEndTime());
                            predicates.add(endTimeEqual);
                        }
                        if (!StringUtils.isEmpty(momentsBean.getStateCode())) {

                            Predicate stateCodeEqual = cb.equal(root.get("state").get("stateCode"), momentsBean.getStateCode());
                            predicates.add(stateCodeEqual);
                        }

                    }
                    query.where(predicates.toArray(new Predicate[predicates.size()]));
                    return null;
                }
            }, pageable);

        }
        return momentsPage;
    }


//移动端


    //显示列表
    public Page<Moments> mobileQuery(Pageable pageable) {

        Page<Moments> momentsPage = null;
        if (null == momentsPage) {
            momentsPage = momentsRepository.findAll(pageable);
        } else {
            momentsPage = momentsRepository.findAll(new Specification<Moments>() {
                @Override
                public Predicate toPredicate(Root<Moments> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    List<Predicate> predicates = new ArrayList<Predicate>();
                    query.where(predicates.toArray(new Predicate[predicates.size()]));
                    return null;
                }
            }, pageable);

        }
        return momentsPage;
    }

    //更改未发布已发布状态
    public Moments changeState(Integer id) {
        Moments one = momentsRepository.getOne(id);//根据id查出相应朋友圈数据
        if (one == null) {
            throw new FailReturnObject(0001, "未找到相应数据");
        }
        if ("UNPUBLISHED".equals(one.getState().getStateCode())) {
            MomentsState momentsState = momentsStateRepository.findByStateCode("PUBLISHED");
            one.setState(momentsState);
            momentsRepository.save(one);
        }
        return one;

    }

    //删除
    public Moments delete(Integer id) {
        Moments one = momentsRepository.getOne(id);
        if (one == null) {
            throw new FailReturnObject(0001, "未找到相应数据");
        }
        if (!one.getState().getStateCode().equals("DELETED")) ;
        MomentsState state = momentsStateRepository.findByStateCode("DELETED");
        one.setState(state);

        momentsRepository.save(one);
        return one;
    }

    //发布人的增删改查

    //查询所有发布人
    public Page<PersonInfo> personInfoList(Pageable pageable) {

        Page<PersonInfo> personInfos = personInfoRepository.findAllPer(pageable);
        return personInfos;
    }

    //增加
    public PersonInfo add(PersonInfoBean personInfoBean) {
        PersonInfo personInfo = new PersonInfo();
        personInfo.setHeadImg(personInfoBean.getHeadImg());
        personInfo.setNickName(personInfoBean.getNickName());
        personInfo.setIsShow(1);
        personInfo.setCreatedTime(new Date());
        personInfoRepository.save(personInfo);
        return personInfo;
    }

    public PersonInfo findOne(Integer id) {
        PersonInfo one = personInfoRepository.getOne(id);
        return one;
    }

    //修改
    public PersonInfo update(PersonInfo personInfo, PersonInfoBean personInfoBean) {
        personInfo.setNickName(personInfoBean.getNickName());
        personInfo.setHeadImg(personInfoBean.getHeadImg());
        personInfoRepository.save(personInfo);
        return personInfo;
    }

    //启用，禁用
    public PersonInfo changeIsShow(PersonInfoBean personInfoBean,PersonInfo personInfo) {
        personInfo = personInfoRepository.findById(personInfoBean.getId());
        if (personInfo != null) {
            if (personInfoBean.getIsShow() == 1) {
                personInfo.setIsShow(1);
            }
            if (personInfoBean.getIsShow() == 0) {
                personInfo.setIsShow(0);
            }
        }
       personInfoRepository.save(personInfo);
        return personInfo;
    }

    @Override
    @PostConstruct
    public void sqlInit() {
        // TODO Auto-generated method stub
        if (momentsActRepository.count() <= 0) {
            MomentsAct save1 = momentsActRepository.save(new MomentsAct("创建", "create", 10, AbstractAuditorAct.ActGroup.UPDATE));
            MomentsAct save2 = momentsActRepository.save(new MomentsAct("读取", "read", 20, AbstractAuditorAct.ActGroup.READ));
            MomentsAct save3 = momentsActRepository.save(new MomentsAct("更新", "update", 30, AbstractAuditorAct.ActGroup.UPDATE));
            MomentsAct save4 = momentsActRepository.save(new MomentsAct("删除", "delete", 40, AbstractAuditorAct.ActGroup.UPDATE));
            MomentsAct save5 = momentsActRepository.save(new MomentsAct("自己的列表", "listOwn", 50, AbstractAuditorAct.ActGroup.READ));
            MomentsAct save6 = momentsActRepository
                    .save(new MomentsAct("部门的列表", "listOwnDepartment", 60, AbstractAuditorAct.ActGroup.READ));
            MomentsAct save7 = momentsActRepository
                    .save(new MomentsAct("部门和下属部门的列表", "listOwnDepartmentAndChildren", 70, AbstractAuditorAct.ActGroup.READ));
            MomentsAct save8 = momentsActRepository
                    .save(new MomentsAct("通知其他人", "noticeOther", 80, AbstractAuditorAct.ActGroup.NOTICE));
            MomentsAct save9 = momentsActRepository
                    .save(new MomentsAct("通知操作者", "noticeActUser", 90, AbstractAuditorAct.ActGroup.NOTICE));
            MomentsAct save10 = momentsActRepository
                    .save(new MomentsAct("通知展示人", "noticeShowUser", 100, AbstractAuditorAct.ActGroup.NOTICE));
            MomentsState momentsState = new MomentsState("未发布", 100, "UNPUBLISHED");
            momentsState.setActs(Arrays.asList(save1, save2, save3, save4, save5, save6, save7).stream()
                    .collect(Collectors.toSet()));
            momentsStateRepository.save(momentsState);
            momentsStateRepository.save(new MomentsState("已发布", 200, "PUBLISHED"));
            momentsStateRepository.save(new MomentsState("已删除", 300, "DELETED"));
        }
    }

    @Override
    public LogRepository<MomentsLog> getLogRepository() {

        return momentsLogRepository;
    }

    @Override
    public MomentsLog getLogInstance() {
        // TODO Auto-generated method stub
        return new MomentsLog();
    }

    @Override
    public ActRepository<MomentsAct> getActRepository() {
        // TODO Auto-generated method stub
        return momentsActRepository;
    }

    @Override
    @PostConstruct
    public void injectEntityService() {
        // TODO Auto-generated method stub
        new PerBusinessCard().setService(this);
    }

    @Override
    public AuditorEntityRepository<Moments> getAuditorEntityRepository() {
        // TODO Auto-generated method stub
        return (AuditorEntityRepository<Moments>) momentsRepository;
    }


    @Override
    @PostConstruct
    public void injectLogRepository() {
        // TODO Auto-generated method stub
        new PerBusinessCard().setLogRepository(momentsLogRepository);
    }

}
