package com.liyang.service;

import com.liyang.domain.base.AbstractAuditorAct;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.customer.Customer;
import com.liyang.domain.customer.CustomerRepository;
import com.liyang.domain.perbusinesscard.*;
import com.liyang.util.FailReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PerBusinessCardService
        extends AbstractAuditorService<PerBusinessCard, PerBusinessCardState, PerBusinessCardAct, PerBusinessCardLog> {
    @Autowired
    PerBusinessCardRepository perBusinessCardRepository;
    @Autowired
    PerBusinessCardLogRepository perBusinessCardLogRepository;
    @Autowired
    PerBusinessCardActRepository perBusinessCardActRepository;
    @Autowired
    PerBusinessCardStateRepository perBusinessCardStateRepository;
    @Autowired
    PerDetailsRepository perDetailsRepository;
    @Autowired
    CustomerRepository customerRepository;

    public Map<String, Object> showPer(Integer customerId){
//        System.out.println(customerId);
        PerBusinessCard per = perBusinessCardRepository.findByCustomer_Id(customerId);

        if (per!=null){
            Map<String, Object> jsonMap = new HashMap<String, Object>();
            jsonMap.put("id", per.getId());
            jsonMap.put("realName", per.getCustomer().getNickname());
            jsonMap.put("phone", per.getCustomer().getPhone());
            jsonMap.put("headImg", per.getCustomer().getHeadimgurl());
            jsonMap.put("industry", per.getIndustry());
            jsonMap.put("companyDesc", per.getCompanyDesc());
            jsonMap.put("perDetails",per.getDetails() );
            return  jsonMap;
        }
        else{
            throw  new FailReturnObject(200,"未找到该用户");
        }

    }
    public PerBusinessCard findOne(Integer id){
        PerBusinessCard one = perBusinessCardRepository.findOne(id);
        return  one;
    }
    // 移动端相应接口

    // 根据客户编号查出相应的信息
    public Map<String, Object> mobileSearch(String token) {
        PerBusinessCard perBusinessCard = perBusinessCardRepository.findByCustomerToken(token);
        if (perBusinessCard != null) {
            Map<String, Object> jsonMap = new HashMap<String, Object>();
            jsonMap.put("id", perBusinessCard.getId());
            jsonMap.put("realName", perBusinessCard.getRealName());
            jsonMap.put("phone", perBusinessCard.getPhone());
            jsonMap.put("headImg", perBusinessCard.getHeadImg());
            jsonMap.put("industry", perBusinessCard.getIndustry());
            jsonMap.put("companyDesc", perBusinessCard.getCompanyDesc());
            jsonMap.put("perDetails",perBusinessCard.getDetails() );
            return  jsonMap;

        }else {
            Map<String, Object> map = new HashMap<>();
            PerBusinessCard p = new PerBusinessCard();
            PerBusinessCardState createdState = perBusinessCardStateRepository.findByStateCode("ENABLED");
            p.setState(createdState);
            Customer customer = customerRepository.findByToken(token);
            p.setCustomer(customer);
            PerDetails perDetails = new PerDetails();
            p.setDetails(perDetails);
            perBusinessCardRepository.save(p);
            map.put("perDetails", p.getDetails());

            return map;
        }
    }

    //判断个人详情是否为空
    public Boolean isEmpty(Integer perDetailsId) {
        PerDetails one = perDetailsRepository.findOne(perDetailsId);
        if (one == null) {
            return true;
        }
        return false;
    }
    // 修改个人名片
    public PerBusinessCard mobileUpdatePer(PerBusinessCardBean perBusinessCardBean, String token) {
        PerBusinessCard perBusinessCard = perBusinessCardRepository.findByCustomerToken(token);
        if (perBusinessCard != null && perBusinessCard.getDetails() != null) {
            if (!perBusinessCard.getDetails().getPerInfoDesc().equals(perBusinessCardBean.getPerDetails().getPerInfoDesc())
                    || !perBusinessCard.getDetails().getImageUrl().equals(perBusinessCardBean.getPerDetails().getImageUrl())
                    || !perBusinessCard.getDetails().getPerHonor().equals(perBusinessCardBean.getPerDetails().getPerHonor())) {
                PerDetails p = perDetailsRepository.findOne(perBusinessCardBean.getPerDetails().getId());
                p.setPerInfoDesc(perBusinessCardBean.getPerDetails().getPerInfoDesc());
                p.setPerHonor(perBusinessCardBean.getPerDetails().getPerHonor());
                p.setImageUrl(perBusinessCardBean.getPerDetails().getImageUrl());
                perDetailsRepository.save(p);
                perBusinessCard.setDetails(p);
            }
        }
            perBusinessCardRepository.save(perBusinessCard);
            return perBusinessCard;
        }


    public Boolean isExist(PerBusinessCardBean perBusinessCardBean) {
        PerBusinessCard byPhone = perBusinessCardRepository.findByPhone(perBusinessCardBean.getPhone());
        if (byPhone == null) {
            return false;
        }
        return true;
    }

    public PerBusinessCard findByCustomerToken(String token) {
        return perBusinessCardRepository.findByCustomerToken(token);
    }

    @Override
    @PostConstruct
    public void sqlInit() {
        // TODO Auto-generated method stub
        if (perBusinessCardActRepository.count() <= 0) {
            PerBusinessCardAct save1 = perBusinessCardActRepository
                    .save(new PerBusinessCardAct("创建", "create", 10, AbstractAuditorAct.ActGroup.UPDATE));
            PerBusinessCardAct save2 = perBusinessCardActRepository
                    .save(new PerBusinessCardAct("读取", "read", 20, AbstractAuditorAct.ActGroup.READ));
            PerBusinessCardAct save3 = perBusinessCardActRepository
                    .save(new PerBusinessCardAct("更新", "update", 30, AbstractAuditorAct.ActGroup.UPDATE));
            PerBusinessCardAct save4 = perBusinessCardActRepository
                    .save(new PerBusinessCardAct("删除", "delete", 40, AbstractAuditorAct.ActGroup.UPDATE));
            PerBusinessCardAct save5 = perBusinessCardActRepository
                    .save(new PerBusinessCardAct("自己的列表", "listOwn", 50, AbstractAuditorAct.ActGroup.READ));
            PerBusinessCardAct save6 = perBusinessCardActRepository
                    .save(new PerBusinessCardAct("部门的列表", "listOwnDepartment", 60, AbstractAuditorAct.ActGroup.READ));
            PerBusinessCardAct save7 = perBusinessCardActRepository.save(new PerBusinessCardAct("部门和下属部门的列表",
                    "listOwnDepartmentAndChildren", 70, AbstractAuditorAct.ActGroup.READ));
            PerBusinessCardAct save8 = perBusinessCardActRepository
                    .save(new PerBusinessCardAct("通知其他人", "noticeOther", 80, AbstractAuditorAct.ActGroup.NOTICE));
            PerBusinessCardAct save9 = perBusinessCardActRepository
                    .save(new PerBusinessCardAct("通知操作者", "noticeActUser", 90, AbstractAuditorAct.ActGroup.NOTICE));
            PerBusinessCardAct save10 = perBusinessCardActRepository
                    .save(new PerBusinessCardAct("通知展示人", "noticeShowUser", 100, AbstractAuditorAct.ActGroup.NOTICE));
            PerBusinessCardState perBusinessCardState = new PerBusinessCardState("有效", 100, "ENABLED");
            perBusinessCardState.setActs(Arrays.asList(save1, save2, save3, save4, save5, save6, save7).stream()
                    .collect(Collectors.toSet()));
            perBusinessCardStateRepository.save(perBusinessCardState);
            perBusinessCardStateRepository.save(new PerBusinessCardState("无效", 200, "DISABLED"));
            perBusinessCardStateRepository.save(new PerBusinessCardState("已删除", 300, "DELETED"));
        }
    }

    @Override
    public LogRepository<PerBusinessCardLog> getLogRepository() {

        return perBusinessCardLogRepository;
    }

    @Override
    public PerBusinessCardLog getLogInstance() {
        // TODO Auto-generated method stub
        return new PerBusinessCardLog();
    }

    @Override
    public ActRepository<PerBusinessCardAct> getActRepository() {
        // TODO Auto-generated method stub
        return perBusinessCardActRepository;
    }

    @Override
    @PostConstruct
    public void injectEntityService() {
        // TODO Auto-generated method stub
        new PerBusinessCard().setService(this);
    }

    @Override
    public AuditorEntityRepository<PerBusinessCard> getAuditorEntityRepository() {
        // TODO Auto-generated method stub
        return (AuditorEntityRepository<PerBusinessCard>) perBusinessCardRepository;
    }

    @Override
    @PostConstruct
    public void injectLogRepository() {
        // TODO Auto-generated method stub
        new PerBusinessCard().setLogRepository(perBusinessCardLogRepository);
    }

}
