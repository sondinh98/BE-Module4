package com.casemodul4.service.impl;

import com.casemodul4.config.filter.FileUtils;
import com.casemodul4.model.Post;
import com.casemodul4.model.UserAcc;
import com.casemodul4.repository.IPostRepo;
import com.casemodul4.service.ICrudService;
import com.casemodul4.service.IUserAccService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PostService implements ICrudService<Post> {
    @Autowired
    IPostRepo iPostRepo;
    @Autowired
    FileUtils fileUtils;
    @Autowired
    IUserAccService userAccService;

    @Override
    public List<Post> getAll() {
        return iPostRepo.findByOrderByIdDesc();
    }


    @Override
    public Post save(Post post) {
        return iPostRepo.save(post);
    }

    public List<Post> getAllPostByUserAcc(int id) {
        return iPostRepo.findAllPostByUserAccId(id);
    }


    @Override
    public Optional<Post> findById(int id) {
        return iPostRepo.findById(id);
    }

    // upload
    @Override
    public void delete(int id) {
        iPostRepo.deleteById(id);
    }

    public Post uploadImage(MultipartFile file, String content , int idUser) {
        Post post = new Post();
        UserAcc userAcc = userAccService.findByIdUserAcc(idUser);
        if (file!=null){
            fileUtils.validateFile(file);
            try {
                if (userAcc != null){
                    byte[] fileBytes = file.getBytes();
                    post.setImg(fileBytes);
                    post.setContent(content);
                    post.setUserAcc(userAcc);
                    return iPostRepo.save(post);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Upload post error");
            }
        }else{
            post.setContent(content);
            post.setUserAcc(userAcc);
            return iPostRepo.save(post);
        }

        return null;
    }

    public void delete(Post post) {
         iPostRepo.delete(post);
    }
}
