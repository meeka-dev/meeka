package app.meeka.domain.model;

import java.util.regex.Pattern;

import static java.util.Objects.nonNull;

public record PostInfo(
        Long authorId,
        String cover,
        String title,
        String content
) {

    private static final String COVER_PATTERN = "(https?)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]";

    public boolean isValid() {
        return isAuthorIdValid()
                && isCoverValid()
                && isTitleValid()
                && isContentValid()
                ;
    }

    private boolean isAuthorIdValid() {
        return nonNull(this.authorId);
    }

    private boolean isCoverValid() {
        return nonNull(this.cover) && Pattern.matches(COVER_PATTERN, this.cover);
    }

    private boolean isTitleValid() {
        return nonNull(this.title) && title.length() > 0 && title.length() < 255;
    }

    private boolean isContentValid() {
        return nonNull(this.content) && title.length() > 0 && title.length() < 65535;
    }
}
