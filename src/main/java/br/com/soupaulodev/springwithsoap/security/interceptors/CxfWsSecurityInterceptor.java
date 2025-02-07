package br.com.soupaulodev.springwithsoap.security.interceptors;

import lombok.RequiredArgsConstructor;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.EndpointInterceptor;

@RequiredArgsConstructor
public class CxfWsSecurityInterceptor implements EndpointInterceptor {

    private final WSS4JInInterceptor cxfInterceptor;

    @Override
    public boolean handleRequest(MessageContext messageContext, Object o) throws Exception {
        SoapMessage soapMessage = (SoapMessage) messageContext.getProperty("org.apache.cxf.message.Message");

        if (soapMessage != null) {
            String soapAction = (String) messageContext.getProperty("org.apache.cxf.message.Message");

            cxfInterceptor.handleMessage(soapMessage);

            if(soapAction != null && soapAction.contains("createUser")) {
                return true;
            }

            try {
                cxfInterceptor.handleMessage(soapMessage);
            } catch (Fault fault) {
                throw new RuntimeException("Invalid WS-Security", fault);
            }
        }
        return true;
    }

    @Override
    public boolean handleResponse(MessageContext messageContext, Object o) throws Exception {
        return false;
    }

    @Override
    public boolean handleFault(MessageContext messageContext, Object o) throws Exception {
        return false;
    }

    @Override
    public void afterCompletion(MessageContext messageContext, Object o, Exception e) throws Exception {

    }
}
