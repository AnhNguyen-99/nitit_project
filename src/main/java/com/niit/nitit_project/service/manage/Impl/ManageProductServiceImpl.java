package com.niit.nitit_project.service.manage.Impl;

import com.niit.nitit_project.entity.*;
import com.niit.nitit_project.model.dto.BrandDTO;
import com.niit.nitit_project.model.dto.WatchDTO;
import com.niit.nitit_project.model.mapper.BrandMapper;
import com.niit.nitit_project.model.mapper.ImageMapper;
import com.niit.nitit_project.model.mapper.WatchMapper;
import com.niit.nitit_project.model.response.ResponseNormal;
import com.niit.nitit_project.repository.*;
import com.niit.nitit_project.sercurity.CustomUserDetails;
import com.niit.nitit_project.service.manage.ManageProductService;
import com.niit.nitit_project.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ManageProductServiceImpl implements ManageProductService {
    @Autowired
    private WatchRepository watchRepository;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private CartWatchRepository cartWatchRepository;
    @Autowired
    private OrderWatchRepository orderWatchRepository;

    private static String UPLOAD_DIR = FileUtils.getResourceBasePath()+ "\\src\\main\\resources\\static\\images\\product";
    @Value("${config.link.image}")
    String LINK_IMG;

    @Override
    public Model loadPage(Model model, WatchDTO watchDTO, Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by("id").descending());
        if(watchDTO.getName() == null){
            //Lấy danh sách sản phẩm
            Page<WatchDTO> pageWatch = watchRepository.findAll(pageable).map(o-> {
                WatchDTO w = WatchMapper.toWatchDTO(o);
                w.setBrandName(brandRepository.findById(o.getIdBrand()).get().getName());
                return w;
            });
            model.addAttribute("listWatch", pageWatch);
        }else{
            Page<WatchDTO> pageWatch = watchRepository.findByKeyWord(watchDTO.getName(), pageable)
                    .map(o->{
                        WatchDTO w = WatchMapper.toWatchDTO(o);
                        w.setBrandName(brandRepository.findById(o.getIdBrand()).get().getName());
                        return w;
                    });
            model.addAttribute("listWatch", pageWatch);
        }
        //Lấy danh sách thương hiệu
        List<BrandDTO> brandDTOList = brandRepository.findAll()
                .stream()
                .map(o-> BrandMapper.toBrandDTO(o))
                .collect(Collectors.toList());
        model.addAttribute("listBrand", brandDTOList);
        return model;
    }

    @Override
    public ResponseNormal<?> findById(Integer id) {
        WatchDTO watchDTO = WatchMapper.toWatchDTO(watchRepository.findById(id).get());
        watchDTO.setBrandName(brandRepository.findById(watchDTO.getIdBrand()).get().getName());
        watchDTO.setImageDTOList(imageRepository.findByIdWatch(id).stream().map(o-> ImageMapper.toImageDTO(o)).collect(Collectors.toList()));
        return new ResponseNormal<>(HttpStatus.OK, "Success!", watchDTO);
    }

    @Override
    public ResponseNormal<?> uploadFile(MultipartFile multipartFile) {
        //Tạo thư mục chứa ảnh nếu không tồn tại
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        //Lấy tên file và đuôi mở rộng của file
        String originalFilename = multipartFile.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        if (originalFilename.length() > 0) {

            //Kiểm tra xem file có đúng định dạng không
            if (!extension.equals("png") && !extension.equals("jpg") && !extension.equals("gif") && !extension.equals("svg") && !extension.equals("jpeg")) {
                return new ResponseNormal( HttpStatus.BAD_REQUEST, "Không hỗ trợ định dạng file này!",null);
            }
            try {
                String nameFile = UUID.randomUUID().toString() + "." +extension;
                String linkFile = UPLOAD_DIR + "\\" + nameFile;
                //Tạo file
                File file = new File(linkFile);
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                bos.write(multipartFile.getBytes());
                bos.close();
                //Tạo link trên server gửi về fe
                String linkImg = LINK_IMG + nameFile;
                return new ResponseNormal(HttpStatus.OK, "Upload ảnh thành công!", linkImg);

            } catch (Exception e) {
                System.out.println("Có lỗi trong quá trình upload file!");
            }
        }
        return new ResponseNormal(HttpStatus.BAD_REQUEST, "Không upload được file này!", null);
    }

    @Override
    public ResponseNormal<?> addProduct(WatchDTO watchDTO) {
        //Kiểm tra mã code sản phẩm
        if(this.checkDuplicateCode(watchDTO.getCode())){
            return new ResponseNormal<>(HttpStatus.BAD_REQUEST, "Đã tồn tại mã sản phẩm này!", null);
        }
        //Lưu sản phẩm
        User user = ((CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal())
                .getUser();
        Watch watch = WatchMapper.toWatch(watchDTO);
        String code = watch.getCode().toUpperCase();
        watch.setCode(code);
        watch.setStatus(1);
        watch.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
        watch.setCreatedBy(user.getId());
        Watch newWatch = watchRepository.save(watch);
        //Lưu ảnh sản phẩm
        watchDTO.getImageDTOList().forEach(o -> {
            Image image = ImageMapper.toImage(o);
            image.setIdWatch(newWatch.getId());
            image.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
            imageRepository.save(image);
        });
        return new ResponseNormal<>(HttpStatus.OK, "Thêm sản phẩm thành công!", null);
    }

    @Override
    public ResponseNormal<?> deleteProduct(Integer id) {
        //Kiểm tra sản phẩm trong cart
        List<CartWatch> cartWatches = cartWatchRepository.findByIdWatch(id);
        if(cartWatches.size() > 0){
            return new ResponseNormal<>(HttpStatus.BAD_REQUEST, "Vẫn còn sản phẩm trong giỏ hàng, không được xóa!", null);
        }
        //Kiểm tra sản phẩm trong order
        List<OrderWatch> orderWatchList = orderWatchRepository.findByIdWatch(id);
        if(orderWatchList.size() > 0){
            return new ResponseNormal<>(HttpStatus.BAD_REQUEST, "Vẫn còn sản phẩm trong đơn đặt hàng, không được xóa!", null);
        }
        //Xóa các ảnh liên quan
        List<Image> images = imageRepository.findByIdWatch(id);
        images.forEach(image -> {
            //Xóa ảnh trên ổ đĩa
            FileUtils.deleteFile(UPLOAD_DIR, image.getLink().substring(16));
            imageRepository.delete(image);
        });
        //Xóa sản phẩm
        watchRepository.deleteById(id);
        return new ResponseNormal<>(HttpStatus.OK, "Xóa sản phẩm thành công!", null);
    }

    @Override
    public ResponseNormal<?> updateProduct(WatchDTO watchDTO) {
        Optional<Watch> watchOptional = watchRepository.findByCode(watchDTO.getCode().toUpperCase());
        if(watchOptional.isPresent() && !watchOptional.get().getId().equals(watchDTO.getId())){
            return new ResponseNormal<>(HttpStatus.BAD_REQUEST, "Mã sản phẩm đã tồn tại, vui lòng nhập lại!", null);
        }

        if(watchDTO.getImageDTOList().size() > 0){
            //Xóa ảnh cũ lưu lại ảnh mới
            List<Image> images = imageRepository.findByIdWatch(watchDTO.getId());
            images.forEach(image -> {
                //Xóa ảnh trên ổ đĩa
                FileUtils.deleteFile(UPLOAD_DIR, image.getLink().substring(16));
                imageRepository.delete(image);
            });
            watchDTO.getImageDTOList().forEach(o -> {
                Image image = ImageMapper.toImage(o);
                image.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
                image.setIdWatch(watchDTO.getId());
                imageRepository.save(image);
            });
        }
        User user = ((CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal())
                .getUser();
        Watch watch = WatchMapper.toWatch(watchDTO);
        watch.setModifiedDate(Timestamp.valueOf(LocalDateTime.now()));
        watch.setModifiedBy(user.getId());
        watchRepository.save(watch);
        return new ResponseNormal<>(HttpStatus.OK, "Sửa sản phẩm thành công!", null);
    }

    public Boolean checkDuplicateCode(String code){
        Optional<Watch> watch = watchRepository.findByCode(code.toUpperCase());
        if(watch.isPresent()){
            return true;
        }
        return false;
    }
}
