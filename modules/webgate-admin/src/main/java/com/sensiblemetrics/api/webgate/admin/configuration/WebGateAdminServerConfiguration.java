package com.sensiblemetrics.api.webgate.admin.configuration;

import com.sensiblemetrics.api.webgate.admin.property.WebGateAdminProperty;
import de.codecentric.boot.admin.server.config.AdminServerProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Slf4j
@Configuration
@Import(WebGateAdminServerNotifierConfiguration.class)
@ConditionalOnProperty(prefix = WebGateAdminProperty.PROPERTY_PREFIX, value = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(WebGateAdminProperty.class)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics WebGate Admin Server configuration")
public abstract class WebGateAdminServerConfiguration {

    @Profile("insecure")
    @Configuration(proxyBeanMethods = false)
    @EnableConfigurationProperties(AdminServerProperties.class)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Description("Authority Admin Server Web Security configuration adapter")
    public static class AuthorityAdminSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
        private final String adminContextPath;

        public AuthorityAdminSecurityConfigurerAdapter(final AdminServerProperties adminServerProperties) {
            this.adminContextPath = adminServerProperties.getContextPath();
        }

        @Override
        protected void configure(final HttpSecurity http) throws Exception {
            http.authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().permitAll())
                .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                    .ignoringRequestMatchers(
                        new AntPathRequestMatcher(this.adminContextPath + "/instances",
                            HttpMethod.POST.toString()),
                        new AntPathRequestMatcher(this.adminContextPath + "/instances/*",
                            HttpMethod.DELETE.toString()),
                        new AntPathRequestMatcher(this.adminContextPath + "/actuator/**")
                    )
                );
        }
    }

    @Profile("secure")
    @Configuration(proxyBeanMethods = false)
    @EnableConfigurationProperties(AdminServerProperties.class)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Description("NoAuthority Admin Server Web Security configuration adapter")
    public static class NoAuthorityAdminSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
        private final String adminContextPath;

        public NoAuthorityAdminSecurityConfigurerAdapter(final AdminServerProperties adminServerProperties) {
            this.adminContextPath = adminServerProperties.getContextPath();
        }

        @Override
        protected void configure(final HttpSecurity http) throws Exception {
            http.authorizeRequests(authorizeRequests -> authorizeRequests
                .antMatchers(this.adminContextPath + "/assets/**").permitAll()
                .antMatchers(this.adminContextPath + "/login").permitAll().anyRequest().authenticated())
                .formLogin(formLogin -> formLogin.loginPage(this.adminContextPath + "/login").successHandler(this.authenticationSuccessHandler()))
                .logout(logout -> logout.logoutUrl(this.adminContextPath + "/logout"))
                .httpBasic(Customizer.withDefaults())
                .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                    .ignoringRequestMatchers(
                        new AntPathRequestMatcher(this.adminContextPath + "/instances",
                            HttpMethod.POST.toString()),
                        new AntPathRequestMatcher(this.adminContextPath + "/instances/*",
                            HttpMethod.DELETE.toString()),
                        new AntPathRequestMatcher(this.adminContextPath + "/actuator/**")
                    )
                );
        }

        private SavedRequestAwareAuthenticationSuccessHandler authenticationSuccessHandler() {
            final SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
            successHandler.setTargetUrlParameter("redirectTo");
            successHandler.setDefaultTargetUrl(this.adminContextPath + "/");
            return successHandler;
        }
    }
}
