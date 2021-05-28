package sjjc.backend.domain;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
public class Response {
    Object content;
    int status;
}
