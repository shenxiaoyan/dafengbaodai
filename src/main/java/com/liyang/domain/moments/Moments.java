package com.liyang.domain.moments;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.annotation.Info;
import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.image.Images;
import com.liyang.service.AbstractAuditorService;
import org.hibernate.validator.constraints.Length;


import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="moments")
public class Moments  extends AbstractAuditorEntity<MomentsState,MomentsAct,MomentsLog>{
    @Transient
    private static final long serialVersionUID = 1L;
    @Transient
    private static LogRepository logRepository;
    @Transient
    private static AbstractAuditorService service;

    @ManyToOne(optional = true,cascade = CascadeType.ALL)
    @JoinColumn(name="personInfo_id")
    @Info(label="头像,昵称等相关信息")
    private PersonInfo personInfo;

    @Info(label="标题")
    private  String title;//标题
    @Lob
    @Length(max = 450)
    @Info(label="内容")
    private  String content;//内容

    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name="moments_id")
    @Info(label="图片")
    private Set<Images> images;//图片信息
    @Transient
    private List<Images> image;

    @Info(label="链接地址")
    private  String linkUrl;//链接地址


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<Images> getImages() {
        return images;
    }

    public void setImages(Set<Images> images) {
        this.images = images;
    }
    @Transient
    public List<Images> getImage() {
        image=new ArrayList<Images>(images);
        Collections.sort(image, new Comparator<Images>() {
            @Override
            public int compare(Images o1, Images o2) {
                return o1.getId()<o2.getId()?-1:1;
            }
        });
        return image;
    }

    public void setImage(List<Images> image) {
        this.image = image;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public PersonInfo getPersonInfo() {
        return personInfo;
    }

    public void setPersonInfo(PersonInfo personInfo) {
        this.personInfo = personInfo;
    }

    @Override
    @JsonIgnore
    @Transient
    public AbstractAuditorService getService() {
        return service;
    }


    @Override
    public void setService(AbstractAuditorService service) {
        Moments.service= service;
    }

    @Override
    @JsonIgnore
    @Transient
    public MomentsLog getLogInstance() {
        return new MomentsLog();
    }

    @Override
    @JsonIgnore
    @Transient
    public LogRepository getLogRepository() {
        // TODO Auto-generated method stub
        return logRepository;
    }
    @Override
    public void setLogRepository(LogRepository logRepository) {
        Moments.logRepository=logRepository;

    }
}
