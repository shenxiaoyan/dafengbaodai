package com.liyang.controller;

import com.liyang.domain.moments.*;
import com.liyang.helper.ResponseBody;
import com.liyang.service.MomentsService;
import com.liyang.util.PageUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/dafeng/moments")
public class MomentsController {
    @Autowired
    MomentsService momentsService;
    @Autowired
    PersonInfoRepository personInfoRepository;
    @Autowired
    MomentsStateRepository stateRepository;

    @InitBinder
    public void initData(WebDataBinder wdb) {
        wdb.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss"), true));
    }

    @RequestMapping("/getAllPer")
    public ResponseBody getPer(@RequestParam("isShow") Integer isShow, @PageableDefault(size = 15, direction = Sort.Direction.DESC) Pageable pageable) {
        return new ResponseBody(personInfoRepository.findAllPersonInfo(isShow,pageable));
    }

    //查询所有状态
    @RequestMapping("/getAllState")
    public ResponseBody allState() {
        return new ResponseBody(stateRepository.findAll());
    }

    //更改状态
    @RequestMapping("/updateState/{id}")
    public ResponseBody updateState(@PathVariable("id") Integer id) {
        momentsService.changeState(id);
        return new ResponseBody(0, "SUCCESS");
    }

    //删除
    @RequestMapping("/delete/{id}")
    public ResponseBody delete(@PathVariable("id") Integer id) {
        momentsService.delete(id);
        return new ResponseBody(0, "SUCCESS");
    }

    //保存
    @RequestMapping("/save")
    public ResponseBody save(@RequestBody MomentsBean momentsBean) {
        momentsService.save(momentsBean);
        return new ResponseBody("OK");
    }

    @RequestMapping("/queryAllMoments")
    public ResponseBody query(@RequestBody(required = false) MomentsBean momentsBean, @PageableDefault(size = 15, sort = ("lastModifiedAt"), direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Moments> page = momentsService.query(momentsBean, pageable);
//        BeanUtils.copyProperties(momentsBean, page);
        List<MomentsBean> momentsBeanList = new ArrayList<>();
        for (Moments moment : page) {
            MomentsBean momentsTO = new MomentsBean();
            BeanUtils.copyProperties(moment, momentsTO);

            PersonInfo personInfo = personInfoRepository.findOne(moment.getPersonInfo().getId());
            momentsTO.setPersonInfoId(personInfo.getId());
            momentsTO.setStateCode(moment.getState().getStateCode());
            momentsTO.setPersonInfo(moment.getPersonInfo());
            momentsTO.setLastModifiedAt(moment.getLastModifiedAt());
            momentsTO.getImages().addAll(moment.getImage());
            momentsBeanList.add(momentsTO);
        }
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("moments", momentsBeanList);
        returnMap.put("page", PageUtil.getPageData(page));
        return new ResponseBody(returnMap);
    }

    //移动端

    @RequestMapping("/mobileQueryAllMoments")
    public ResponseBody mobileQuery(@PageableDefault(size = 15, sort = "lastModifiedAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Moments> page = momentsService.mobileQuery(pageable);
        List<MomentsBean> momentsBeanList = new ArrayList<>();
        for (Moments moment : page) {
            MomentsState state = stateRepository.findByStateCode("PUBLISHED");
            if (moment.getState().equals(state)) {
                MomentsBean momentsTO = new MomentsBean();
                BeanUtils.copyProperties(moment, momentsTO);

                PersonInfo personInfo = personInfoRepository.findOne(moment.getPersonInfo().getId());
                momentsTO.setPersonInfoId(personInfo.getId());
                momentsTO.setStateCode(moment.getState().getStateCode());
                momentsTO.setPersonInfo(moment.getPersonInfo());
                momentsTO.setLastModifiedAt(moment.getLastModifiedAt());
                momentsTO.getImages().addAll(moment.getImage());
                momentsBeanList.add(momentsTO);
            }
        }
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("moments", momentsBeanList);
        returnMap.put("page", PageUtil.getPageData(page));
        return new ResponseBody(returnMap);
    }


    /**
     * TODO 确认可否复制集合
     * 属性复制
     *
     * @param
     * @return
     */
    public List<MomentsBean> copyProperties(List<Moments> moments) {
        List<MomentsBean> momentsList = new ArrayList<>();
        for (Moments moments1 : moments) {
            MomentsBean momentsBean = new MomentsBean();
            BeanUtils.copyProperties(moments1, momentsBean);
//            momentsBean.setStateCode(team.getState().getStateCode());
            momentsList.add(momentsBean);
        }
        return momentsList;
    }


    //发布人相关操作：发布人的增删改查
    //发布人列表
    @RequestMapping("/personInfoList")
    public ResponseBody personInfoList(@PageableDefault(size = 15, sort="createdTime",direction = Sort.Direction.DESC) Pageable pageable) {
        return new ResponseBody(momentsService.personInfoList(pageable));
    }

    //增加发布人
    @RequestMapping("/addPersonInfo")
    public ResponseBody addPersonInfo(@RequestBody PersonInfoBean personInfoBean) {
        momentsService.add(personInfoBean);
        return new ResponseBody("ok");
    }

    //修改发布人
    @RequestMapping("/updatePersonInfo")
    public ResponseBody updatePersonInfo(@RequestBody PersonInfoBean personInfoBean) {
        PersonInfo one = momentsService.findOne(personInfoBean.getId());
        if (one != null) {
            momentsService.update(one, personInfoBean);
            return new ResponseBody("ok");
        }
        return new ResponseBody("error");

    }

    //启用，禁用
    @RequestMapping("/update/isShow")
    public ResponseBody updateState(@RequestBody PersonInfoBean personInfoBean) {
        PersonInfo personInfo = momentsService.findOne(personInfoBean.getId());
        if (personInfo != null) {
            momentsService.changeIsShow(personInfoBean, personInfo);
            return new ResponseBody("ok");
        }
        return new ResponseBody("error");
    }
}