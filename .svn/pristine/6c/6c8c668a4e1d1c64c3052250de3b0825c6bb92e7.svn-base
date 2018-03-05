package com.liyang.domain.base;


import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.liyang.annotation.Info;

/**
 * Created by win7 on 2017-07-25.
 * @author Administrator
 */
@MappedSuperclass
public abstract class AbstractTag extends BaseEntity {
    
	@Column(name = "tag_word")
    @Info(label="Tag",placeholder="",tip="",help="",secret="")			
    private String tagWord;
    @Info(label="权重",placeholder="",tip="",help="",secret="")			
    private Integer priority;
    public void setTagWord(String tagWord) {
        this.tagWord = tagWord;
    }

    public String getTagWord() {
        return tagWord;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getPriority() {
        return priority;
    }
}
