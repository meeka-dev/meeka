package app.meeka.presentation.rest;

import app.meeka.presentation.rest.request.CreatePostRequest;

import static app.meeka.common.Randoms.aRandomId;
import static app.meeka.common.Texts.aPostContent;
import static app.meeka.common.Texts.aPostTitle;
import static app.meeka.common.URLs.aRandomURL;

public class Requests {

    public static CreatePostRequest aCreatePostRequest() {
        return new CreatePostRequest(
                aRandomId(),
                aRandomURL(),
                aPostTitle(),
                aPostContent()
        );
    }
}
