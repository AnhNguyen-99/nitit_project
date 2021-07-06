package com.niit.nitit_project.service.manage.Impl;

import com.niit.nitit_project.entity.Image;
import com.niit.nitit_project.model.dto.ImageDTO;
import com.niit.nitit_project.model.dto.WatchDTO;
import com.niit.nitit_project.model.mapper.ImageMapper;
import com.niit.nitit_project.model.mapper.WatchMapper;
import com.niit.nitit_project.model.response.ResponseNormal;
import com.niit.nitit_project.repository.ImageRepository;
import com.niit.nitit_project.repository.WatchRepository;
import com.niit.nitit_project.service.manage.ManageImageService;
import com.niit.nitit_project.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManageImageServiceImpl implements ManageImageService {
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private WatchRepository watchRepository;

    private static String UPLOAD_DIR = FileUtils.getResourceBasePath()+ "\\src\\main\\resources\\static\\images\\product";

    @Override
    public Model loadPage(Model model, Integer idWatch) {
        //Lấy danh sách ảnh theo id sản phẩm
        List<ImageDTO> imageDTOS = imageRepository.getListByIdWatch(idWatch)
                .stream()
                .map(o-> ImageMapper.toImageDTO(o))
                .collect(Collectors.toList());
        model.addAttribute("listImage", imageDTOS);
        WatchDTO watchDTO = WatchMapper.toWatchDTO(watchRepository.findById(idWatch).get());
        model.addAttribute("watchDTO", watchDTO);
        return model;
    }

    @Override
    public ResponseNormal<?> addImage(ImageDTO imageDTO) {
        Image image = ImageMapper.toImage(imageDTO);
        image.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
        imageRepository.save(image);
        return new ResponseNormal<>(HttpStatus.OK, "Thêm ảnh mới thành công!", null);
    }

    @Override
    public ResponseNormal<?> deleteImage(Integer idImage) {
        Image image = imageRepository.findById(idImage).get();
        List<Image> images = imageRepository.findByIdWatch(image.getIdWatch());
        if(images.size() <= 1){
            return new ResponseNormal<>(HttpStatus.BAD_REQUEST, "Phải có ít nhất 1 ảnh trong sản phẩm, không được xóa!", null);
        }
        //Xóa trên ổ đĩa và trên db
        FileUtils.deleteFile(UPLOAD_DIR, image.getLink().substring(16));
        imageRepository.delete(image);
        return new ResponseNormal<>(HttpStatus.OK, "Xóa thành công!", null);
    }
}
