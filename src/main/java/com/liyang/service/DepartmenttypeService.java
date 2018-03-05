package com.liyang.service;

import com.liyang.domain.base.AbstractAuditorAct.ActGroup;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.department.Department;
import com.liyang.domain.department.DepartmentRepository;
import com.liyang.domain.departmenttype.*;
import com.liyang.domain.role.Role;
import com.liyang.domain.role.RoleRepository;
import com.liyang.enums.ExceptionResultEnum;
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

/**
 * @author Administrator
 */
@Service
public class DepartmenttypeService
        extends AbstractAuditorService<Departmenttype, DepartmenttypeState, DepartmenttypeAct, DepartmenttypeLog> {

    @Autowired
    DepartmenttypeActRepository actRepository;

    @Autowired
    DepartmenttypeStateRepository stateRepository;

    @Autowired
    DepartmenttypeLogRepository logRepository;

    @Autowired
    DepartmenttypeRepository departmenttypeRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    //保存部门类型
    public void save(Departmenttype departmenttype) {
        String code = createRandomSrting(8);
        while (departmenttypeRepository.findByCode(code) != null) {
            code = createRandomSrting(8);
        }
        departmenttype.setCode(code);
        DepartmenttypeState disabledState = stateRepository.findByStateCode("DISABLED");
        departmenttype.setState(disabledState);
        departmenttypeRepository.save(departmenttype);
    }
    //修改部门类型
    public Departmenttype edit(DepartmenttypeForSearch departmenttypeForSearch) {
       Departmenttype departmenttype= departmenttypeRepository.findOne(departmenttypeForSearch.getId());
       if (departmenttype==null){

           throw  new FailReturnObject(200,"没有该部门");
       }else{
           departmenttype.setLabel(departmenttypeForSearch.getLabel());
           departmenttype.setContactName(departmenttypeForSearch.getContactName());
           departmenttype.setContactAddress(departmenttypeForSearch.getContactAddress());
           departmenttype.setContactPhone(departmenttypeForSearch.getContactPhone());
           departmenttypeRepository.save(departmenttype);
       }
       return  departmenttype;
    }

    //生成任意字符串
    private static String createRandomSrting(int length) {
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    //查询
    public Page<Departmenttype> search(final DepartmenttypeForSearch search, Pageable pageable) {
        Page<Departmenttype> page = departmenttypeRepository.findAll(new Specification<Departmenttype>() {

            @Override
            public Predicate toPredicate(Root<Departmenttype> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                if (search != null) {
                    if (!StringUtils.isEmpty(search.getLabel()) && !"undefined".equals(search.getLabel())) {
                        Predicate labelEq = cb.equal(root.<String>get("label"), search.getLabel());
                        predicates.add(labelEq);
                    }
                    if (!StringUtils.isEmpty(search.getContactName()) && !"undefined".equals(search.getContactName())) {
                        Predicate contactNameEq = cb.equal(root.<String>get("contactName"), search.getContactName());
                        predicates.add(contactNameEq);
                    }
                    if (!StringUtils.isEmpty(search.getContactPhone())
                            && !"undefined".equals(search.getContactPhone())) {
                        Predicate contactPhoneEq = cb.equal(root.<String>get("contactPhone"), search.getContactPhone());
                        predicates.add(contactPhoneEq);
                    }
                    if (!StringUtils.isEmpty(search.getStateCode()) && !"undefined".equals(search.getStateCode())) {
                        Predicate stateCodeEq = cb.equal(root.get("state").get("stateCode"), search.getStateCode());
                        predicates.add(stateCodeEq);
                    } else {
                        Predicate stateCodeNQ = cb.notEqual(root.get("state").get("stateCode"), "DELETED");
                        predicates.add(stateCodeNQ);
                    }
                }
                query.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
        return page;
    }

    public Departmenttype findOne(Integer id) {
        return departmenttypeRepository.findOne(id);
    }

    //修改状态
    public Departmenttype updateState(Departmenttype type) {
        if ("ENABLED".equals(type.getState().getStateCode())) {
            DepartmenttypeState disableState = stateRepository.findByStateCode("DISABLED");
            type.setState(disableState);
        } else if ("DISABLED".equals(type.getState().getStateCode())) {
            DepartmenttypeState enableState = stateRepository.findByStateCode("ENABLED");
            type.setState(enableState);
        } else {
            throw new FailReturnObject(ExceptionResultEnum.DEPARTMENTTYPE_STATE_ERROR);
        }
        return departmenttypeRepository.save(type);
    }

    public ResponseBody findListDept(Integer id, Pageable pageable) {
        Departmenttype type = departmenttypeRepository.findOne(id);
        Page<Department> departmentsPage = departmentRepository.findByTypeId(id, pageable);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        // 部门类型的封装
        Map<String, Object> typeMap = new HashMap<String, Object>();
        List<Map<String, Object>> departmentsList = new ArrayList<Map<String, Object>>();
        typeMap.put("label", type.getLabel());
        typeMap.put("contactPhone", type.getContactPhone());
        typeMap.put("contactName", type.getContactName());
        typeMap.put("contactAddress", type.getContactAddress());
        returnMap.put("depttype", typeMap);
        for (Department department : departmentsPage) {
            // 各下属部门的封装
            Map<String, Object> departmentMap = new HashMap<String, Object>();
            departmentMap.put("label", department.getLabel());
            departmentMap.put("departmenttype", department.getType() == null ? null : department.getType().getLabel());
            departmentMap.put("attactPerson", department.getAttactPerson());
            departmentMap.put("attactPhone", department.getAttactPhone());
            departmentMap.put("address", department.getAddress());
            departmentMap.put("state", department.getState() == null ? null : department.getState().getLabel());
            departmentMap.put("parent", department.getParent() == null ? null : department.getParent().getLabel());
            departmentsList.add(departmentMap);
        }
        returnMap.put("page", getPageData(departmentsPage));
        returnMap.put("deptList", departmentsList);
        return new ResponseBody(returnMap);
    }


    /**
     * 获取page分页信息
     *
     * @param page
     * @return
     */
    private Map<String, Integer> getPageData(Page<Department> page) {
        Map<String, Integer> pageDataMap = new HashMap<>();
        pageDataMap.put("size", page.getSize());
        pageDataMap.put("totalElements", (int) page.getTotalElements());
        pageDataMap.put("totalPages", page.getTotalPages());
        pageDataMap.put("number", page.getNumber());
        return pageDataMap;
    }

    @Override
    @PostConstruct
    public void sqlInit() {
        // List<DepartmenttypeAct> findAll = actRepository.findAll();
        // if (findAll == null || findAll.isEmpty()) {
        if (actRepository.count() <= 0) {
            DepartmenttypeAct save1 = actRepository.save(new DepartmenttypeAct("创建", "create", 10, ActGroup.UPDATE));
            DepartmenttypeAct save2 = actRepository.save(new DepartmenttypeAct("读取", "read", 20, ActGroup.READ));
            DepartmenttypeAct save3 = actRepository.save(new DepartmenttypeAct("更新", "update", 30, ActGroup.UPDATE));
            DepartmenttypeAct save4 = actRepository.save(new DepartmenttypeAct("删除", "delete", 40, ActGroup.UPDATE));

            DepartmenttypeState createState = new DepartmenttypeState("已创建", 0, "CREATED");
            createState.setActs(Arrays.asList(save1).stream().collect(Collectors.toSet()));
            stateRepository.save(createState);
            DepartmenttypeState enabledState = new DepartmenttypeState("正常", 100, "ENABLED");
            enabledState.setActs(Arrays.asList(save1, save2, save3, save4).stream().collect(Collectors.toSet()));
            stateRepository.save(enabledState);
            DepartmenttypeState disabledState = new DepartmenttypeState("禁用", 200, "DISABLED");
            disabledState.setActs(Arrays.asList(save1, save2, save3, save4).stream().collect(Collectors.toSet()));
            stateRepository.save(disabledState);
            stateRepository.save(new DepartmenttypeState("已删除", 300, "DELETED"));

            // 为管理员初始化赋权
            Role administrator = roleRepository.findByRoleCode("ADMINISTRATOR");
            save1.setRoles(new HashSet<>(Arrays.asList(administrator)));
            save2.setRoles(new HashSet<>(Arrays.asList(administrator)));
            save3.setRoles(new HashSet<>(Arrays.asList(administrator)));
            save4.setRoles(new HashSet<>(Arrays.asList(administrator)));
            actRepository.save(save1);
            actRepository.save(save2);
            actRepository.save(save3);
            actRepository.save(save4);
        }

        Departmenttype group = departmenttypeRepository.findByCode("Group");
        if (group != null && group.getState() == null) {
            DepartmenttypeState enabledState = stateRepository.findByStateCode("ENABLED");
            group.setState(enabledState);
            departmenttypeRepository.save(group);
        }
        Departmenttype salesdept = departmenttypeRepository.findByCode("SALESDEPT");
        if (salesdept != null && salesdept.getState() == null) {
            DepartmenttypeState enabledState = stateRepository.findByStateCode("ENABLED");
            salesdept.setState(enabledState);
            departmenttypeRepository.save(salesdept);
        }
    }

    @Override
    public LogRepository<DepartmenttypeLog> getLogRepository() {
        return logRepository;
    }

    @Override
    public DepartmenttypeLog getLogInstance() {
        return new DepartmenttypeLog();
    }

    @Override
    public AuditorEntityRepository<Departmenttype> getAuditorEntityRepository() {
        return departmenttypeRepository;
    }

    @Override
    public ActRepository<DepartmenttypeAct> getActRepository() {
        return actRepository;
    }

    @Override
    @PostConstruct
    public void injectEntityService() {
        new Departmenttype().setService(this);
    }

    @Override
    @PostConstruct
    public void injectLogRepository() {
        new Departmenttype().setLogRepository(logRepository);
    }
}
