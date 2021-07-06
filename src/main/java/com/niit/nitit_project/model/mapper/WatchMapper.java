package com.niit.nitit_project.model.mapper;

import com.niit.nitit_project.entity.Watch;
import com.niit.nitit_project.model.dto.WatchDTO;

public class WatchMapper {

    public static Watch toWatch(WatchDTO watchDTO){
        Watch watch = new Watch();
        watch.setId(watchDTO.getId());
        watch.setCode(watchDTO.getCode());
        watch.setName(watchDTO.getName());
        watch.setDescription(watchDTO.getDescription());
        watch.setFeature(watchDTO.getFeature());
        watch.setPrice(watchDTO.getPrice());
        watch.setReducedPrice(watchDTO.getReducedPrice());
        watch.setSalePrice(watchDTO.getSalePrice());
        watch.setStatus(watchDTO.getStatus());
        watch.setCreatedBy(watchDTO.getCreatedBy());
        watch.setModifiedBy(watchDTO.getModifiedBy());
        watch.setCreatedDate(watchDTO.getCreatedDate());
        watch.setModifiedDate(watchDTO.getModifiedDate());
        watch.setIdBrand(watchDTO.getIdBrand());
        return watch;
    }

    public static WatchDTO toWatchDTO(Watch watch){
        WatchDTO watchDTO = new WatchDTO();
        watchDTO.setId(watch.getId());
        watchDTO.setCode(watch.getCode());
        watchDTO.setName(watch.getName());
        watchDTO.setDescription(watch.getDescription());
        watchDTO.setFeature(watch.getFeature());
        watchDTO.setPrice(watch.getPrice());
        watchDTO.setReducedPrice(watch.getReducedPrice());
        watchDTO.setSalePrice(watch.getSalePrice());
        watchDTO.setStatus(watch.getStatus());
        watchDTO.setCreatedBy(watch.getCreatedBy());
        watchDTO.setModifiedBy(watch.getModifiedBy());
        watchDTO.setCreatedDate(watch.getCreatedDate());
        watchDTO.setModifiedDate(watch.getModifiedDate());
        watchDTO.setIdBrand(watch.getIdBrand());
        return watchDTO;
    }
}
