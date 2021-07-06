package com.niit.nitit_project.model.mapper;

import com.niit.nitit_project.entity.Image;
import com.niit.nitit_project.model.dto.ImageDTO;

public class ImageMapper {
    public static Image toImage(ImageDTO imageDTO){
        return new Image(imageDTO.getId(), imageDTO.getLink(), imageDTO.getIdWatch(), imageDTO.getCreatedDate());
    }

    public static ImageDTO toImageDTO(Image image){
        return new ImageDTO(image.getId(), image.getLink(), image.getIdWatch(), image.getCreatedDate());
    }
}
