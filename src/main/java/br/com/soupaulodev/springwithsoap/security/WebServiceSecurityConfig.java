package br.com.soupaulodev.springwithsoap.security;

import br.com.soupaulodev.springwithsoap.security.interceptors.CxfWsSecurityInterceptor;
import lombok.RequiredArgsConstructor;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.server.endpoint.mapping.UriEndpointMapping;

import javax.security.auth.callback.CallbackHandler;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class WebServiceSecurityConfig {

    @Bean
    public WSS4JInInterceptor wsSecurityInterceptor(CallbackHandler passwordCallbackHandler) {
        Map<String, Object> inProps = new HashMap<>();

        inProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
        inProps.put(WSHandlerConstants.PW_CALLBACK_REF, passwordCallbackHandler);
        inProps.put(WSHandlerConstants.USER, "admin");

        return new WSS4JInInterceptor(inProps);
    }

    @Bean
    public CallbackHandler passwordCallbackHandler(DatabasePasswordCallbackHandler dbHandler) {
        return dbHandler;
    }

    @Bean
    public UriEndpointMapping uriEndpointMapping(WSS4JInInterceptor securityInterceptor) {
        UriEndpointMapping mapping = new UriEndpointMapping();
        mapping.setInterceptors(new EndpointInterceptor[]{new CxfWsSecurityInterceptor(securityInterceptor)});
        return mapping;
    }
}
