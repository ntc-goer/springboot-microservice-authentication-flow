package ntcgoer.sharingmodule.configuration;

import feign.Response;
import feign.codec.ErrorDecoder;
import ntcgoer.sharingmodule.exception.*;

public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        return switch (response.status()) {
            case 400 -> new BadRequestException(s);
            case 401 -> new UnauthorizedException(s);
            case 403 -> new ForbiddenException(s);
            case 404 -> new ResourceNotFoundException(s);
            case 409 -> new ConflictException(s);
            default -> new InternalServerErrorException(s);
        };
    }
}
