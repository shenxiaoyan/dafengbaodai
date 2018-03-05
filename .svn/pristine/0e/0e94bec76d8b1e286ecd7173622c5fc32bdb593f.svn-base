package com.liyang.domain.poster;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author duyiting
 * @date 2018/01/20
 */
public class PosterForSave {

    @NotBlank(message = "海报名称不能为空")
    private String name;
    @NotNull(message = "海报宽度不能为空")
    private Integer width;
    @NotNull(message = "海报高度不能为空")
    private Integer height;
    @NotBlank(message = "海报地址不能为空")
    private String imgUrl;
    @NotNull(message = "海报大小不能为空")
    private Integer size;
    @NotBlank(message = "海报类型不能为空")
    private String posterType;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getPosterType() {
        return posterType;
    }

    public void setType(String posterType) {
        this.posterType = posterType;
    }
}
