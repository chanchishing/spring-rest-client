
package guru.springframework.api.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;


@Builder
@Getter
@Setter
public class Company implements Serializable
{
    private String name;
    private String catchPhrase;
    private String bs;
    @Builder.Default
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 4664733244264983062L;

}
