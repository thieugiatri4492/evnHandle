package tripqm.evn.java.post.mapper;

import org.mapstruct.Mapper;
import tripqm.evn.java.post.dto.response.PostResponse;
import tripqm.evn.java.post.entity.Post;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostResponse toPostResponse(Post post);
}
