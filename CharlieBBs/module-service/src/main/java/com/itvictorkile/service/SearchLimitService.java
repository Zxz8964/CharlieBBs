package com.itvictorkile.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SearchLimitService {

    @Value("${search.limit.logged-in.user}")
    private int loggedInUserLimit;

    @Value("${search.time.logged-in.user}")
    private int loggedInUserTime;

    @Value("${search.limit.guest.user}")
    private int guestUserLimit;

    @Value("${search.time.guest.user}")
    private int guestUserTime;

    private Map<Long, UserSearchInfo> userSearchInfoMap = new HashMap<>();

    public boolean canUserSearch(Long userId, boolean isLoggedIn) {
        UserSearchInfo userInfo = userSearchInfoMap.getOrDefault(userId, new UserSearchInfo());

        if (isLoggedIn) {
            return canSearch(userInfo, loggedInUserLimit, loggedInUserTime);
        } else {
            return canSearch(userInfo, guestUserLimit, guestUserTime);
        }
    }

    private boolean canSearch(UserSearchInfo userInfo, int limit, int timeLimit) {
        long currentTime = System.currentTimeMillis();

        // 如果用户的最后搜索时间超过了时间限制，则重置搜索计数
        if (currentTime - userInfo.lastSearchTime > timeLimit * 1000) {
            userInfo.searchCount = 0;  // 重置搜索计数
        }

        if (userInfo.searchCount < limit) {
            userInfo.searchCount++;
            userInfo.lastSearchTime = currentTime;
            userSearchInfoMap.put(userInfo.userId, userInfo);
            return true;
        }

        return false;  // 超过搜索限制
    }

    private static class UserSearchInfo {
        long userId;
        int searchCount;
        long lastSearchTime;

        UserSearchInfo() {
            this.searchCount = 0;
            this.lastSearchTime = 0;
        }
    }
}