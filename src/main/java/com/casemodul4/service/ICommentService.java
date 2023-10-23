package com.casemodul4.service;

import com.casemodul4.model.Comment;
import com.casemodul4.model.dto.CommentDto;

import java.util.List;

public interface ICommentService {
    List<CommentDto> findByUserAccIdAndPostId(int idUser, int idPost);
    void save(Comment comment);
    List<CommentDto> getAllByPostId(int idPost);
}
