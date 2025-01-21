package ntcgoer.sharingmodule.http;

import lombok.Builder;
import lombok.Getter;
import ntcgoer.sharingmodule.exception.ErrorCode;

@Builder
@Getter
public class InternalFeignCallResponse<T> {
    private String error;
    public T response;
    public String errorMessage;

    public boolean hasError() {
        return this.error != null && !this.error.trim().isEmpty();
    }

    public boolean hasForbiddenError() {
        return this.error.equalsIgnoreCase(ErrorCode.FORBIDDEN);
    }

    public boolean hasNotFoundError() {
        return this.error != null && this.error.equalsIgnoreCase(ErrorCode.NOT_FOUND);
    }

    public boolean hasBadRequestError() {
        return this.error != null && this.error.equalsIgnoreCase(ErrorCode.BAD_REQUEST);
    }

    public boolean hasUnauthorizedError() {
        return this.error != null && this.error.equalsIgnoreCase(ErrorCode.UNAUTHORIZED);
    }

    public boolean hasConflictError() {
        return this.error != null && this.error.equalsIgnoreCase(ErrorCode.CONFLICT);
    }

    public boolean hasInternalServerError() {
        return this.error != null && this.error.equalsIgnoreCase(ErrorCode.INTERNAL_SERVER_ERROR);
    }
}
