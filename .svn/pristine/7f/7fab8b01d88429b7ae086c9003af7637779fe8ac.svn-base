//package com.liyang;
//
//import java.beans.BeanInfo;
//import java.beans.Introspector;
//import java.beans.PropertyDescriptor;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.apache.commons.beanutils.BeanUtils;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.Resource;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.liyang.domain.department.Department;
//import com.liyang.domain.department.DepartmentRepository;
//import com.liyang.domain.menu.Menu;
//import com.liyang.domain.menu.MenuRepository;
//import com.liyang.domain.menu.MenuStateRepository;
//import com.liyang.domain.role.RoleRepository;
//import com.liyang.domain.user.UserRepository;
//import com.liyang.service.MenuService;
//import com.liyang.service.UserService;
//import com.liyang.util.CommonUtil;
//import com.liyang.util.FailReturnObject;
//
///**
// * @author Administrator
// *
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@ContextConfiguration(classes = Application.class)
//public class Test2 {
//	@Autowired
//	RoleRepository roleRepository;
//	@Autowired
//	MenuRepository menuRepository;
//	@Autowired
//	DepartmentRepository departmentRepository;
//
//	@Autowired
//	MenuStateRepository menuStateRepository;
//
//	@Autowired
//	MenuService menuService;
//	@Autowired
//	UserRepository userRepository;
//	
//	
//	@Test
//	public void test2() throws IllegalAccessException, InvocationTargetException{
//		Menu findOne = menuRepository.findOne(1);
//		System.out.println(transBean2Map(findOne));
//		
//	}
//	@Test
//	public void testDepartment() {
//		Department departmen=	userRepository.getOne(1).getDepartment();
//		System.out.println(departmen.getType());
//	}
//	
//    public static Map<String, Object> transBean2Map(Object obj) {  
//    	  
//        if(obj == null){  
//            return null;  
//        }          
//        Map<String, Object> map = new HashMap<String, Object>();  
//        try {  
//            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
//            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
//            for (PropertyDescriptor property : propertyDescriptors) {  
//                String key = property.getName();  
//  
//                // 过滤class属性  
//                if (!"class".equals(key)) {  
//                    // 得到property对应的getter方法  
//                    Method getter = property.getReadMethod();  
//                    Object value = getter.invoke(obj);  
//  
//                    map.put(key, value);  
//                }  
//  
//            }  
//        } catch (Exception e) {  
//            System.out.println("transBean2Map Error " + e);  
//        }  
//  
//        return map;  
//  
//    } 
//	
//	class User{
//		private String name;
//
//		public String getName() {
//			return name;
//		}
//
//		public void setName(String name) {
//			this.name = name;
//		}
//		
//	}
//	
//	@Test
//	public void test1() {
//		
//		Resource resource = new ClassPathResource("template/user");
//		if (!resource.exists()) {
//			throw new FailReturnObject(800, "实体模板不存在");
//		}
//		System.out.println(traceTaget("user","card"));
//	}
//	
//	public String traceTaget(String entityName, String viewType){
//		String defaultStr = "default";
//		String prefix = "template";
//		String split = "/";
//		String departmentType = CommonUtil.getCurrentUserDepartment()!=null? CommonUtil.getCurrentUserDepartment().getType().toString().toLowerCase():defaultStr ;
//		String departmentId = CommonUtil.getCurrentUserDepartment()!=null? CommonUtil.getCurrentUserDepartment().getId().toString().toLowerCase():defaultStr;
//		String roleCode = CommonUtil.getCurrentUserRoleCode().toString().toLowerCase();
//		String targetFile = entityName + split +  departmentType + split + departmentId + split + roleCode + split + viewType;
//		ClassPathResource resource = new ClassPathResource(prefix + split + targetFile + ".html");
//		if (!resource.exists()) {
//			targetFile = entityName + split +  departmentType + split + departmentId + split + viewType;
//			resource = new ClassPathResource(prefix + split + targetFile + ".html");
//			if (!resource.exists()) {
//				targetFile = entityName + split +  departmentType + split + viewType;
//				resource = new ClassPathResource(prefix + split + targetFile + ".html");
//				if (!resource.exists()) {
//					targetFile = entityName + split  + viewType;
//					resource = new ClassPathResource(prefix + split + targetFile + ".html");
//					if (!resource.exists()) {
//						throw new FailReturnObject(800, "实体模板不存在");
//					}
//				}
//			}
//		}
//		return targetFile;
//	}
//	
//
//}
