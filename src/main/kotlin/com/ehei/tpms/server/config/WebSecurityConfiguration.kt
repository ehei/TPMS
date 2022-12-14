package com.ehei.tpms.server.config

import com.ehei.tpms.server.config.WebSecurityConstants.UNAUTHENTICATED_ENDPOINTS
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

/**
 * Open endpoint security configuration
 *
 */
@Configuration
@EnableWebSecurity
class OpenEndpointSecurityConfiguration {

    /**
     * Configure
     *
     * @param http
     * @return
     */
    @Bean
    fun configure(http: HttpSecurity): SecurityFilterChain =
        http
            .cors()
            .and()
            .csrf()
            .disable()
            .headers()
            .frameOptions()
            .disable()
            .and()
            .authorizeRequests()
            .requestMatchers(EndpointRequest.to(*UNAUTHENTICATED_ENDPOINTS))
            .permitAll()
            .antMatchers("/**").permitAll()
            .antMatchers("/api/**").permitAll()
            .antMatchers("/authentication/**").permitAll()
            .and()
            .build()

}
