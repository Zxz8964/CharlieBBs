package com.itvictorkile.api;

import com.itvictorkile.service.SearchLimitService;
import com.itvictorkile.service.SearchService;
import com.itvictorkile.utils.AggregatedSearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/search")
public class SearchController {
    private final SearchLimitService searchLimitService;
    private final SearchService searchService;

    @Autowired
    public SearchController(SearchLimitService searchLimitService, SearchService searchService) {
        this.searchLimitService = searchLimitService;
        this.searchService = searchService;
    }
    @GetMapping
    public ResponseEntity<Map<String, Object>> search(
            @RequestParam String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "created_at") String sortBy,
            @RequestParam(defaultValue = "desc") String direction,
            @RequestParam(required = false) Long userId,
            @RequestParam boolean isLoggedIn) {
        {

            // 检查搜索次数和时间限制
            if (!searchLimitService.canUserSearch(userId, isLoggedIn)) {
                return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                        .body(null); // 返回 429 状态码
            }

            // 获取聚合搜索结果
            AggregatedSearchResult searchResult = searchService.search(keyword, category, page, size, sortBy, direction);
            return ResponseEntity.ok(searchResult.toMap());
        }
    }
    }
