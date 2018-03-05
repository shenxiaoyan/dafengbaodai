package com.liyang.controller;


import com.liyang.domain.user.User;
import com.liyang.domain.user.UserForSearch;
import com.liyang.domain.user.UserForUpdate;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.helper.ResponseBody;
import com.liyang.service.UserService;
import com.liyang.util.FailReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 类说明
 *
 * @author lcj
 * @date 2017年10月16日  新建
 */

@RestController
@RequestMapping("/dafeng/enduser")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/changePswd", method = RequestMethod.POST)
    public ResponseBody changePswd(@RequestParam(value = "username") String username,
                                   @RequestParam("oldpassword") String oldPassword,
                                   @RequestParam("newPassword") String newPassword) {
        ResponseBody responseBody =
                userService.changePassword(username, oldPassword, newPassword);
        return responseBody;
    }

    @RequestMapping(value = "/resetPswd", method = RequestMethod.POST)
    public ResponseBody resetPswd(@RequestParam(value = "username") String username) throws Exception {
        ResponseBody responseBody =
                userService.resetPassword(username);
        return responseBody;
    }

    //添加后台用户
    @RequestMapping(value = "/addEndUser2")
    public ResponseBody addEndUser2(@RequestBody UserForUpdate userForUpdate) {
        userService.addEndUser(userForUpdate.getRoleCode(), userForUpdate.getUsername(), userForUpdate.getNickname(), userForUpdate.getDepartmentId());
        return new ResponseBody("新增成功");
    }


//
//	@RequestMapping(value = "/addEndUser", method = RequestMethod.POST)
//	public  ResponseBody addEndUser(@RequestParam(value="username") String username,
//									@RequestParam(value="nickname") String nickname,
//									@RequestParam(value="depLabel") String depLabel,
//									@RequestParam(value="depTypeCode") String depTypeCode,
//									@RequestParam(value="roleCode") String roleCode) throws Exception  {
//		ResponseBody responseBody =
//				userService.addEndUserSer(username,nickname,depLabel,depTypeCode,roleCode);
//		return responseBody;
//	}

    //	@RequestMapping(value = "/changeUserState")
//	public ResponseBody changeUserState(@RequestParam(value = "username") String username,
//										@RequestParam(value = "stateCode") String stateCode, HttpServletRequest request) throws Exception {
//		User user = userService.findByUsername(username);
//		if (null == user) {
//			throw new FailReturnObject(ExceptionResultEnum.USER_NOUSER_ERROR);
//		}
//		if ("DELETED".equals(stateCode)) {
//			userService.deleteUser(user);
//		} else {
//			userService.updateUserStateSer(user, stateCode);
//		}
//		if ("DISABLED".equals(stateCode)) {// 如果禁用的话，则将对应用户的session直接过期掉
//			userService.invalidateSession(username);
//		}
//		return new ResponseBody(ExceptionResultEnum.SUCCESS);
//	}
    @RequestMapping(value = "/changeUserState")
    public ResponseBody changeUserState(@RequestBody UserForUpdate userForUpdate) {
        User user = userService.updateUserState(userForUpdate);
        if (user == null) {
            throw new FailReturnObject(200, "没有找到该用户");
        }
        return new ResponseBody("修改成功");
    }
//    @PostMapping("/update")
//    public ResponseBody updateUsernameAndNickname(@RequestParam(value = "id") Integer id,
//                                                  @RequestParam(value = "username") String username,
//                                                  @RequestParam(value = "nickname") String nickname) {
//        User user = userService.findOne(id);
//        if (null == user) {
//            throw new FailReturnObject(ExceptionResultEnum.USER_NOUSER_ERROR);
//        }
//        ResponseBody responseBody = userService.updateUsernameAndNickname(user, username, nickname);
//        return responseBody;
//    }
    @PostMapping("/update/{id}")
    public  ResponseBody update(@PathVariable Integer id,@RequestBody UserForUpdate userForUpdate){
        User user=userService.findOne(id);
        if (user == null) {
            throw new FailReturnObject(200,"该用户不存在");
        }
        userService.update(user,userForUpdate,id);
        return new ResponseBody("ok");

    }

    @PostMapping("/search")
    public ResponseBody mulConditionSearch(@RequestBody UserForSearch userForSearch, @PageableDefault(value = 15) Pageable pageable) {
        Map<String, Object> returnMap = userService.mulConditionSearch(userForSearch, pageable);
//        Map<String,Object> map=new HashMap<String, Object>();
//        map.put("",returnMap.get())

        return new ResponseBody(returnMap);
    }
}
