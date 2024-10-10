package tripqm.evn.java.post.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import tripqm.evn.java.post.dto.request.PostRequest;
import tripqm.evn.java.post.dto.response.ApiResponse;
import tripqm.evn.java.post.dto.response.PaginationResponse;
import tripqm.evn.java.post.dto.response.PostResponse;
import tripqm.evn.java.post.service.PostService;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostController {
    PostService postService;
    @PostMapping("/createPost")
    ApiResponse<PostResponse> createPost(@RequestBody PostRequest request){
        return ApiResponse.<PostResponse>builder()
                .result(postService.createPost(request))
                .build();
    }

    @GetMapping("/getPosts")
    ApiResponse<PaginationResponse<PostResponse>> myPosts(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ){
        return ApiResponse.<PaginationResponse<PostResponse>>builder()
                .result(postService.getMyPosts(page, size))
                .build();
    }
}
