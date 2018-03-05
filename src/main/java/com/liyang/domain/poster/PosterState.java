package com.liyang.domain.poster;


import com.liyang.domain.base.AbstractAuditorState;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="poster_state")
@Cacheable
public class PosterState extends AbstractAuditorState<Poster,PosterAct>{

    public PosterState(String label,Integer sort,String stateCode){
        super(label,sort,stateCode);
    }

    public PosterState(){ super(); }

}
