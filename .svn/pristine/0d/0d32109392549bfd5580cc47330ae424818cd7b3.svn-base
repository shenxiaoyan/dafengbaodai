package com.liyang.controller;

import com.liyang.domain.poster.*;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.enums.ThemeType;
import com.liyang.service.PosterService;
import com.liyang.util.FailReturnObject;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.liyang.helper.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author duyiting
 * @date 2018/01/19
 */
@RestController
@RequestMapping("/dafeng/poster")
public class PosterController {

    @Autowired
    private PosterService posterService;

    @Autowired
    private PosterStateRepository posterStateRepository;

    @Autowired
    PosterRepository posterRepository;

    @RequestMapping(value="/save",method= RequestMethod.POST)
    public ResponseBody save(@RequestBody @Valid PosterForSave posterForSave, BindingResult result){

        if(result.hasErrors()){
            System.out.println(result);
            throw new FailReturnObject(ExceptionResultEnum.POSTER_DATA_IS_NULL);
        }

        posterService.save(posterForSave);
        return new ResponseBody(" ");
    }

    //获取海报状态数组
    @RequestMapping(value = "/getStateList",method = RequestMethod.GET)
    public ResponseBody getStates(){
        List<PosterState> stateList = posterService.getStates();
        return new ResponseBody(stateList);
    }

    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public ResponseBody deletePicture(Integer id){
        posterService.deletePicture(id);
        return new ResponseBody(" ");
    }

    @RequestMapping(value="/search",method = RequestMethod.POST)
    public ResponseBody searchPoster(@RequestBody(required = false) PosterForSearch posterForSearch, @PageableDefault(sort = {"lastModifiedAt"},direction = Sort.Direction.DESC ) Pageable pageable){
        Page<Poster> posterPage = posterService.searchPoster(posterForSearch,pageable);
        List<Map<String,Object>> returnList = new ArrayList<>();
        Map<String,Object> returnMap = new HashMap<>();
        for (Poster poster:posterPage.getContent()) {
            Map<String,Object> posterMap = new HashMap<>();
            posterMap.put("id", poster.getId());
            posterMap.put("name",poster.getName());
            posterMap.put("type",poster.getType());
            posterMap.put("state",poster.getState().getLabel());
            posterMap.put("image",poster.getImages());
            posterMap.put("createdAt",poster.getCreatedAt());
            posterMap.put("lastModifiedAt",poster.getLastModifiedAt());
            returnList.add(posterMap);
        }
        returnMap.put("posterList",returnList);
        returnMap.put("size",posterPage.getSize());
        returnMap.put("number",posterPage.getNumber());
        returnMap.put("totalPages",posterPage.getTotalPages());
        returnMap.put("totalElements",posterPage.getTotalElements());
        return new ResponseBody(returnMap);
    }

    @RequestMapping(value = "/posterListForMobile",method = RequestMethod.POST)
    public ResponseBody mobilePosterList(@RequestBody(required = false) PosterForSearch posterForSearch, @PageableDefault(sort = {"lastModifiedAt"},direction = Sort.Direction.DESC)Pageable pageable ){
        if(posterForSearch == null){
            posterForSearch = new PosterForSearch();
            posterForSearch.setStateCode("ENABLED");
        }else{
            posterForSearch.setStateCode("ENABLED");
        }
        return searchPoster(posterForSearch,pageable);
    }


    @RequestMapping(value = "/changeState",method = RequestMethod.GET)
    public ResponseBody changeState(Integer id){
        posterService.chanageState(id);
        return new ResponseBody(" ");
    }
}
