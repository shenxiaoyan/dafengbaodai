package com.liyang.domain.perbusinesscard;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.liyang.annotation.Info;
import com.liyang.domain.image.Images;

import javafx.scene.image.Image;

@Entity
@Table(name="per_details")
public class PerDetails  implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
    //个人介绍、
    String perInfoDesc="";
    // 个人荣誉、
    @Info(label="个人荣誉 ")
   String perHonor="";

    // 个人风采（图片，4张）；
    @Info(label="个人风采（图片，4张）")
     @Lob
    String imageUrl="";
    @Lob
    public String getImageUrl() {
        return imageUrl;
    }
    @Lob
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPerInfoDesc() {
        return perInfoDesc;
    }

    public void setPerInfoDesc(String perInfoDesc) {
        this.perInfoDesc = perInfoDesc;
    }

    public String getPerHonor() {
        return perHonor;
    }

    public void setPerHonor(String perHonor) {
        this.perHonor = perHonor;
    }


}
