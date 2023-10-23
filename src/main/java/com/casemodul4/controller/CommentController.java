package com.casemodul4.controller;

import com.casemodul4.model.Comment;
import com.casemodul4.model.Post;
import com.casemodul4.model.dto.CommentDto;
import com.casemodul4.service.ICommentService;
import com.casemodul4.service.impl.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    ICommentService commentService;
    @Autowired
    PostService postService;

    @GetMapping("/{userId}/{postId}")
    public ResponseEntity<List<CommentDto>> getCommentsByUserAndPost(@PathVariable int userId, @PathVariable int postId) {
        List<CommentDto> comments = commentService.findByUserAccIdAndPostId(userId, postId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{idP}")
    public ResponseEntity<List<CommentDto>> getAllByPostId(@PathVariable int idP) {
        List<CommentDto> commentDtos = commentService.getAllByPostId(idP);
        return new ResponseEntity<>(commentDtos, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveComment(@RequestBody Comment comment) {

        commentService.save(comment);
        Post post = postService.findById(comment.getPost().getId()).get();
        post.setCommentCount(post.getCommentCount() + 1);
        postService.save(post);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
