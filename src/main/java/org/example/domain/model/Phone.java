
package org.example.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Phone {
    private String number;
    private String citycode;
    private String countrycode;
}
