package com.casemodul4.controller;

import com.casemodul4.model.Post;
import com.casemodul4.service.impl.PostService;
import com.casemodul4.service.impl.UserAccService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/posts")
public class PostController {
    @Autowired
    PostService postService;
    @Autowired
    UserAccService userAccService;

    @GetMapping
    public List<Post> getAll() {
        return postService.getAll();
    }

    @GetMapping("/user/{idUserAcc}")
    public List<Post> getAllOfUserAcc(@PathVariable int idUserAcc) {
        return postService.getAllPostByUserAcc(idUserAcc);
    }

    @PostMapping("/createPost")
    public List<Post> create(@RequestParam("file") MultipartFile file, @RequestParam("content") String content, @RequestParam("idUser") int idUser) {
        if (file.isEmpty()){
            postService.uploadImage(null,content,idUser);
        }else
            postService.uploadImage(file, content, idUser);
        return postService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> findUserAccById(@PathVariable int id) {
        Optional<Post> postOptional = postService.findById(id);
        if (!postOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(postOptional.get(), HttpStatus.OK);
    }

    @PostMapping("/edit/{idPost}")
    public ResponseEntity<Post> updatePost(@PathVariable int idPost, @RequestBody Post post) {
        Optional<Post> postOptional = postService.findById(idPost);
        if (!postOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        post.setId(postOptional.get().getId());
        return new ResponseEntity<>(postService.save(post), HttpStatus.OK);
    }

    @GetMapping("/deletePost/{idPost}")
    public ResponseEntity<Post> remove(@PathVariable int idPost) {
        Optional<Post> postOptional = postService.findById(idPost);
        if (!postOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        postService.delete(idPost);
        return ResponseEntity.ok().build();
    }





//    @PostMapping("/editPost")
//    public void edit(@RequestBody Post post){
//        postService.save(post);
//    }

    @PostMapping("/deletePost")
    public void delete(@RequestBody Post post) {
        postService.delete(post.getId());
    }



}

