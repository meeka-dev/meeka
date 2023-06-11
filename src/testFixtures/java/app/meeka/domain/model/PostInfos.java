package app.meeka.domain.model;

import app.meeka.domain.model.post.PostInfo;

import static app.meeka.common.Randoms.aRandomId;
import static app.meeka.common.Texts.aPostContent;
import static app.meeka.common.Texts.aPostTitle;
import static app.meeka.common.URLs.aRandomURL;

public class PostInfos {

    private Long authorId;
    private String cover;
    private String title;
    private String content;

    private PostInfos() {
    }

    public static PostInfo aPostInfo() {
        return newPostInfo().build();
    }

    public static PostInfos newPostInfo() {
        return new PostInfos()
                .withAuthorId(aRandomId())
                .withTitle(aPostTitle())
                .withCover(aRandomURL())
                .withContent(aPostContent())
                ;
    }

    public PostInfos withAuthorId(Long authorId) {
        this.authorId = authorId;
        return this;
    }

    public PostInfos withCover(String cover) {
        this.cover = cover;
        return this;
    }

    public PostInfos withTitle(String title) {
        this.title = title;
        return this;
    }

    public PostInfos withContent(String content) {
        this.content = content;
        return this;
    }

    public PostInfo build() {
        return new PostInfo(this.authorId, this.cover, this.title, this.content);
    }
}
