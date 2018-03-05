package com.liyang.service;

import com.jpa.query.expression.QueryExpSpecificationBuilder;
import com.jpa.query.expression.generic.GenericQueryExpSpecification;
import com.liyang.domain.base.AbstractAuditorAct.ActGroup;
import com.liyang.domain.base.AbstractWorkflowAct.ActType;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.department.Department;
import com.liyang.domain.department.DepartmentRepository;
import com.liyang.domain.departmenttype.DepartmenttypeRepository;
import com.liyang.domain.role.Role;
import com.liyang.domain.role.RoleRepository;
import com.liyang.domain.user.*;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.helper.ResponseBody;
import com.liyang.util.FailReturnObject;
import com.liyang.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.cache.NullUserCache;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Administrator
 */
@Service
@Order(100)
public class UserService extends AbstractWorkflowService<User, UserWorkflow, UserAct, UserState, UserLog, UserFile>
        implements UserDetailsService {

    @Autowired
    UserActRepository userActRepository;

    @Autowired
    UserStateRepository userStateRepository;

    @Autowired
    UserLogRepository userLogRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    UserWorkflowRepository userWorkflowRepository;

    @Autowired
    DepartmenttypeRepository departmenttypeRepository;

    // 所有的登录用户都保存在SessionRegistry
    @Autowired
    SessionRegistry sessionRegistry;

    private UserCache userCache = new NullUserCache();

    private static String ACCESS_DENIED_ERROR_MSG = "Can't change password as no Authentication object found in context "
            + "for current user. ";
    private static String RESET_PSWD_SUCCESS_MSG = " Reset Password Success";

    private static String CHANGE_PSWD_SUCCESS_MSG = "Change Password Success ";

    private static String ADD_ENDUSER_SUCCESS_MSG = "Add User Success ";

    private static String CHANGE_PSWD_FAIL_MSG = "Change Password Fail ";

    @Override
    @PostConstruct
    public void sqlInit() {
        System.out.println("userService初始化");
        // List<UserAct> findAll = userActRepository.findAll();
        // if (findAll == null || findAll.isEmpty()) {
        if (userActRepository.count() <= 0) {

            UserWorkflow userApplyWorkflow = new UserWorkflow();
            userApplyWorkflow.setLabel("入职流程");
            userApplyWorkflow = userWorkflowRepository.save(userApplyWorkflow);

            UserAct save1 = userActRepository.save(new UserAct("创建", "create", 10, ActGroup.UPDATE));
            UserAct save2 = userActRepository.save(new UserAct("读取", "read", 20, ActGroup.READ));
            UserAct save3 = userActRepository.save(new UserAct("更新", "update", 30, ActGroup.UPDATE));
            UserAct save4 = userActRepository.save(new UserAct("删除", "delete", 40, ActGroup.UPDATE));
            UserAct applyAct = userActRepository.save(new UserAct("入门申请", "apply", 45, ActGroup.OPERATE));
            UserAct save5 = userActRepository.save(new UserAct("自己的列表", "listOwn", 50, ActGroup.READ));
            UserAct save6 = userActRepository.save(new UserAct("部门的列表", "listOwnDepartment", 60, ActGroup.READ));
            UserAct save7 = userActRepository
                    .save(new UserAct("部门和下属部门的列表", "listOwnDepartmentAndChildren", 70, ActGroup.READ));
            UserAct save8 = userActRepository.save(new UserAct("通知其他人", "noticeOther", 80, ActGroup.NOTICE));
            UserAct save9 = userActRepository.save(new UserAct("通知操作者", "noticeActUser", 90, ActGroup.NOTICE));
            UserAct save10 = userActRepository.save(new UserAct("通知展示人", "noticeShowUser", 100, ActGroup.NOTICE));

            UserState newState = new UserState("待创建", 0, "UNBORN");
            newState.setActs(Arrays.asList(applyAct).stream().collect(Collectors.toSet()));
            newState = userStateRepository.save(newState);

            UserState applyState = userStateRepository.save(new UserState("待审核", 100, "APPLIED"));
            UserState createState = new UserState("已创建", 200, "CREATED");
            UserState enableState = new UserState("有效", 300, "ENABLED");

            enableState.setActs(Arrays.asList(save1, save2, save3, save4, save5, save6, save7).stream()
                    .collect(Collectors.toSet()));
            userStateRepository.save(enableState);
            userStateRepository.save(new UserState("无效", 400, "DISABLED"));
            userStateRepository.save(new UserState("已删除", 500, "DELETED"));

            userApplyWorkflow.setStates(new HashSet<UserState>(Arrays.asList(newState, applyState)));
            userApplyWorkflow = userWorkflowRepository.save(userApplyWorkflow);

            applyAct.setActType(ActType.START);
            applyAct.setStartWorkflow(userApplyWorkflow);
            applyAct.setTargetState(applyState);

            // Role role =
            // roleRepository.findByRoleCode(Role.RoleCode.valueOf("USER"));
            Role role = roleRepository.findByRoleCode("USER");
            if (role != null) {
                applyAct.setRoles(new HashSet<Role>(Arrays.asList(role)));
            }
            userActRepository.save(applyAct);

        }
        // 删除已审核状态及相关数据
        UserAct findByActCode = userActRepository.findByActCode("apply");
        if (findByActCode != null) {
            findByActCode.setTargetState(null);
            userActRepository.save(findByActCode);
        }
        UserState byStateCode = userStateRepository.getByStateCode("APPLIED");
        if (byStateCode != null) {
            byStateCode.getActs().clear();
            userStateRepository.save(byStateCode);
            UserWorkflow findOne = userWorkflowRepository.findOne(1);
            Set<UserState> states = findOne.getStates();
            Iterator<UserState> iterator = states.iterator();
            while (iterator.hasNext()) {
                UserState next = iterator.next();
                if (next.getId().equals(2) || next.getId().equals(3) || next.getId().equals(1)) {
                    iterator.remove();
                }
            }
            userWorkflowRepository.save(findOne);
            userStateRepository.delete(byStateCode);
        }
        // 删除待创建状态及相关数据
        UserState stateCode = userStateRepository.getByStateCode("CREATED");
        if (stateCode != null) {
            stateCode.getActs().clear();
            userStateRepository.save(stateCode);
            userStateRepository.delete(stateCode);
        }
        // 删除待创建状态及相关数据
        UserState state = userStateRepository.getByStateCode("UNBORN");
        if (state != null) {
            state.getActs().clear();
            userStateRepository.save(state);
            userStateRepository.delete(state);
        }

    }

    @Override
    public LogRepository<UserLog> getLogRepository() {
        // TODO Auto-generated method stub
        return userLogRepository;
    }

    @Override
    public AuditorEntityRepository<User> getAuditorEntityRepository() {

        return userRepository;
    }

    @Override
    @PostConstruct
    public void injectLogRepository() {
        new User().setLogRepository(userLogRepository);
    }

    @Override
    public UserLog getLogInstance() {
        // TODO Auto-generated method stub
        return new UserLog();
    }

    @Override
    public ActRepository<UserAct> getActRepository() {
        // TODO Auto-generated method stub
        return userActRepository;
    }

    @Override
    @PostConstruct
    public void injectEntityService() {
        new User().setService(this);

    }

    @Override
    public UserFile getFileLogInstance() {
        // TODO Auto-generated method stub
        return new UserFile();
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        User user = userRepository.findByUsername(s);
        List<User> users = userRepository.findByUsername(s);
        User user = null;
        for (User tempUser : users) {
			if ("ENABLED".equals(tempUser.getState().getStateCode()) || 
					"DISABLED".equals(tempUser.getState().getStateCode())) {
				user = tempUser;
				break;
			}
		}
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        System.out.println("username:" + user.getUsername() + ";password:" + user.getPassword());
        return user;
    }

    @Transactional
    public ResponseBody changePassword(String username, String oldPassword, String newPassword) {

        // Authentication
        // currentuser=SecurityContextHolder.getContext().getAuthentication();
        //
        //// if(currentuser==null ||
        // currentuser.getPrincipal()=="anonymousUser")
        //// {
        //// throw new AccessDeniedException(ACCESS_DENIED_ERROR_MSG);
        //// }
        //
        // Object principal =
        // SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // System.out.println("principal..." + principal);

        // String username2=((UserDetails)principal).getUsername();
        String username2 = username;
        System.out.println("username2:" + username2);
//        User user = userRepository.findByUsername(username2);
        List<User> users = userRepository.findByUsernameAndStateStateCode(username2, "ENABLED");
        User user = users.get(0);
        if (user.getPassword().equals(MD5Util.encode((String) oldPassword))) {
            user.setPassword(MD5Util.encode((String) newPassword));

            userRepository.save(user);

            SecurityContextHolder.clearContext();

            // SecurityContextHolder.getContext().setAuthentication(createNewAuthentication(currentuser,
            // newPassword));

            userCache.removeUserFromCache(username);
            return new ResponseBody(0, CHANGE_PSWD_SUCCESS_MSG);
        }

        return new ResponseBody(100, CHANGE_PSWD_FAIL_MSG);

    }

    public Authentication createNewAuthentication(Authentication currentAuth, String newPassword) {
        UserDetails user = loadUserByUsername(currentAuth.getName());

        UsernamePasswordAuthenticationToken newAuthentication = new UsernamePasswordAuthenticationToken(user,
                user.getPassword(), user.getAuthorities());
        newAuthentication.setDetails(currentAuth.getDetails());

        return newAuthentication;
    }

    @Transactional
    public ResponseBody resetPassword(String username) throws Exception {
        String defautlPswd;

//        User user = userRepository.findByUsername(username);
        List<User> users = userRepository.findByUsernameAndStateStateCode(username, "ENABLED");
        User user = users.get(0);
        if (user == null) {
            throw new FailReturnObject(ExceptionResultEnum.USER_LOAD_USERNAME_ERROE);
            // throw new UsernameNotFoundException("用户名不存在");
        }
        int size = username.length();
        if (username.length() < 6) {
            throw new FailReturnObject(ExceptionResultEnum.USER_LENGTH_USERNAME_ERROR);
            // throw new Exception("用户名长度错误 ");
        }

        defautlPswd = username.substring(size - 6, size);
        // System.out.println("defautlPswd...." + defautlPswd);
        System.out.println(defautlPswd);
        user.setPassword(MD5Util.encode((String) defautlPswd));
        // System.out.println("user.getPassword():"+user.getPassword());
        userRepository.save(user);
        return new ResponseBody(0, RESET_PSWD_SUCCESS_MSG);

    }


    /**
     * 重复性判断
     *
     * @return
     */
    public Boolean existOne(String username) {
        String[] stateCodes = new String[]{"ENABLED", "DISABLED"};
        User existOne = userRepository.findByUsernameAndStateStateCodeIn(username, stateCodes);
        if (existOne != null) {
            throw new FailReturnObject(668, "用户已存在");
        }
        return false;
    }

    /**
     * 更新重复性判断
     *
     * @param
     * @param
     * @return
     */
    public Boolean updateExistOne(String username, Integer id) {
        String[] stateCodes = new String[]{"ENABLED", "DISABLED"};
        User one = userRepository.findByUsernameAndIdNotAndStateStateCodeIn(username, id, stateCodes);
        if (one != null) {
            throw new FailReturnObject(669, "用户已存在不能重复");
        }
        return true;
    }

    /**
     * 添加后台用户
     *
     * @param roleCode     角色代码
     * @param username     用户名
     * @param nickname     昵称
     * @param departmentId 部门编号
     * @return 用户
     */
    public User addEndUser(String roleCode, String username, String nickname, Integer departmentId) {
        User applyUser = new User();
        Department department = departmentRepository.findOne(departmentId);
        if (department == null) {
            throw new FailReturnObject(ExceptionResultEnum.USER_NOTFOUND_DEPARTMENT_ERROR);
        }
        Role role = roleRepository.findByRoleCode(roleCode);
        if (role == null) {
            throw new FailReturnObject(ExceptionResultEnum.USER_NOTFOUND_ROLE_ERROR);
        }
        UserState state = userStateRepository.getByStateCode("ENABLED");
        existOne(username);
        applyUser.setUsername(username);
        applyUser.setDepartment(department);
        applyUser.setState(state);
        applyUser.setRole(role);
        applyUser.setNickname(nickname);
        int size = username.length();
        applyUser.setPassword(MD5Util.encode((String) username.substring(size - 6, size)));
        System.out.println("username:"+username+"\tpwd:"+applyUser.getPassword());
        return userRepository.save(applyUser);
    }

    //修改后台用户信息
    public User update(User user, UserForUpdate userForUpdate, Integer id) {
        updateExistOne(userForUpdate.getUsername(), id);
        user=userRepository.getOne(id);
        user.setUsername(userForUpdate.getUsername());
        user.setNickname(userForUpdate.getNickname());
        Role role=roleRepository.findByRoleCode(userForUpdate.getRoleCode());
        user.setRole(role);
        Department department = departmentRepository.findOne(userForUpdate.getDepartmentId());
        user.setDepartment(department);
        return userRepository.save(user);
    }



    //修改用户状态
    public User updateUserState(UserForUpdate userForUpdate) {
        User user = userRepository.getOne(userForUpdate.getId());
        if (null != user) {
            if ("DELETED".equals(userForUpdate.getStateCode())) {
                UserState state = userStateRepository.getByStateCode("DELETED");
                user.setState(state);
                userRepository.save(user);
            }
            if ("DISABLED".equals(userForUpdate.getStateCode())) {
                // 如果禁用的话，则将对应用户的session直接过期掉
                invalidateSession(user.getUsername());
                UserState state = userStateRepository.getByStateCode("DISABLED");
                user.setState(state);
                userRepository.save(user);

            }
            if ("ENABLED".equals(userForUpdate.getStateCode())) {
                UserState state = userStateRepository.getByStateCode("ENABLED");
                user.setState(state);
                userRepository.save(user);
            }

        }
        return user;
    }


//    public void deleteUser(User user) {
//        UserState userState = userStateRepository.getByStateCode("DELETED");
//        user.setState(userState);
//        userRepository.delete(user.getId());
//    }


//    public User findByUsername(String username) {
//        return userRepository.findByUsername(username);
//    }

    public void invalidateSession(String username) {
        List<Object> users = sessionRegistry.getAllPrincipals();
        for (Object object : users) {
            // 取到禁用用户的session
            if (username.equals(((User) object).getUsername())) {
                List<SessionInformation> sessions = sessionRegistry.getAllSessions(object, false);
                for (SessionInformation session : sessions) {
                    session.expireNow();// 直接设置超时
                }
            }
        }
    }

//    @Transactional
//    public ResponseBody updateUsernameAndNickname(User user, String username, String nickname) {
//        user.setUsername(username);
//        user.setNickname(nickname);
//        userRepository.save(user);
//        try {
//        } catch (Exception e) {
//            throw new FailReturnObject(255, "修改失败");
//        }
//        return new ResponseBody("修改成功");
//    }

    public User findOne(Integer id) {

        return userRepository.findById(id);
    }

    public Map<String, Object> mulConditionSearch(UserForSearch userForSearch, Pageable pageable) {
        GenericQueryExpSpecification<User> queryExpression = new GenericQueryExpSpecification<>();
        queryExpression.add(QueryExpSpecificationBuilder.eq("username", userForSearch.getUsername(), true))
                .add(QueryExpSpecificationBuilder.eq("nickname", userForSearch.getNickname(), true))
                .add(QueryExpSpecificationBuilder.eq("department.label", userForSearch.getDepartmentName(), true))
                .add(QueryExpSpecificationBuilder.eq("state.stateCode", userForSearch.getStateCode(), true))
                .add(QueryExpSpecificationBuilder.eq("department.id", userForSearch.getDepartmentId(), true));
        Page<User> userPage = userRepository.findAll(queryExpression, pageable);
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("content", getPageContentInfo(userPage));
        returnMap.put("page", getPageData(userPage));
        return returnMap;
    }

    private List<Map<String, Object>> getPageContentInfo(Page<User> page) {
        List<Map<String, Object>> infoList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (User user : page) {
            Map<String, Object> userInfoMap = new HashMap<>();
            userInfoMap.put("id", user.getId());
            userInfoMap.put("nickname", user.getNickname());
            userInfoMap.put("username", user.getUsername());
            userInfoMap.put("departmentId", user.getDepartment() == null ? null : user.getDepartment().getId());
            userInfoMap.put("departmentLabel", user.getDepartment() == null ? null : user.getDepartment().getLabel());
            userInfoMap.put("stateLabel", user.getState() == null ? null : user.getState().getLabel());
            userInfoMap.put("roleId", user.getRole() == null ? null : user.getRole().getId());
            userInfoMap.put("roleLabel", user.getRole() == null ? null : user.getRole().getLabel());
            userInfoMap.put("lastModifiedAt", user.getLastModifiedAt() == null ? null : sdf.format(user.getLastModifiedAt()));
            infoList.add(userInfoMap);
        }
        return infoList;
    }

    private Map<String, Integer> getPageData(Page<User> page) {
        Map<String, Integer> pageDataMap = new HashMap<>();
        pageDataMap.put("size", page.getSize());
        pageDataMap.put("totalElements", (int) page.getTotalElements());
        pageDataMap.put("totalPages", page.getTotalPages());
        pageDataMap.put("number", page.getNumber());
        return pageDataMap;
    }

}
