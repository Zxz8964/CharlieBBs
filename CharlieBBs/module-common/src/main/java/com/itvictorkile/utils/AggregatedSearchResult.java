package com.itvictorkile.utils;

import com.itvictorkile.entity.Board;
import com.itvictorkile.entity.Post;
import com.itvictorkile.entity.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AggregatedSearchResult {

    private List<Post> posts = new ArrayList<>();
    private long totalPosts; // 总帖子数量

    private List<Board> boards = new ArrayList<>();
    private long totalBoards; // 总板块数量

    private List<User> users = new ArrayList<>();
    private long totalUsers; // 总用户数量

    // 添加帖子列表
    public void addPosts(List<Post> posts) {
        this.posts.addAll(posts);
    }

    // 添加板块列表
    public void addBoards(List<Board> boards) {
        this.boards.addAll(boards);
    }

    // 添加用户列表
    public void addUsers(List<User> users) {
        this.users.addAll(users);
    }

    // 设置总帖子数量
    public void setTotalPosts(long totalPosts) {
        this.totalPosts = totalPosts;
    }

    // 设置总板块数量
    public void setTotalBoards(long totalBoards) {
        this.totalBoards = totalBoards;
    }

    // 设置总用户数量
    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    // 转换为Map
    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();
        result.put("posts", posts);
        result.put("totalPosts", totalPosts); // 添加总帖子数量
        result.put("boards", boards);
        result.put("totalBoards", totalBoards); // 添加总板块数量
        result.put("users", users);
        result.put("totalUsers", totalUsers); // 添加总用户数量
        return result;
    }
}
