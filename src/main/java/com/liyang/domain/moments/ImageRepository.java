package com.liyang.domain.moments;

import org.springframework.data.jpa.repository.JpaRepository;

import com.liyang.domain.image.Images;

import java.util.List;

public interface ImageRepository extends JpaRepository<Images,Integer> {

}
