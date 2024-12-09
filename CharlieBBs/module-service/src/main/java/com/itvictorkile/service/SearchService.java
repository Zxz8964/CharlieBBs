package com.itvictorkile.service;

import com.itvictorkile.dao.BoardRepository;
import com.itvictorkile.dao.PostRepository;
import com.itvictorkile.dao.UserRepository;
import com.itvictorkile.entity.Board;
import com.itvictorkile.entity.Post;
import com.itvictorkile.entity.User;
import com.itvictorkile.utils.AggregatedSearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class SearchService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserRepository userRepository;

    public AggregatedSearchResult search(String keyword, String category, int page, int size, String sortBy, String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        AggregatedSearchResult result = new AggregatedSearchResult();

        // 分类查询并添加到结果中
        if (category == null || category.equals("post")) {
            Page<Post> postsPage = postRepository.searchByKeyword(keyword, pageable);
            result.addPosts(postsPage.getContent());
            result.setTotalPosts(postsPage.getTotalElements());
        }
        if (category == null || category.equals("board")) {
            Page<Board> boardsPage = boardRepository.searchByKeyword(keyword, pageable);
            result.addBoards(boardsPage.getContent());
            result.setTotalBoards(boardsPage.getTotalElements());
        }
        if (category == null || category.equals("user")) {
            Page<User> usersPage = userRepository.searchByKeyword(keyword, pageable);
            result.addUsers(usersPage.getContent());
            result.setTotalUsers(usersPage.getTotalElements());
        }

        return result;
    }
}

