package com.shure.common.mysql.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Shure
 * @date 2022/1/26 14:51
 */
@Component
@ConfigurationProperties(prefix = "kenplugin.sql")
@Data
public class KenConfigInfo {

    private boolean enable;

}
