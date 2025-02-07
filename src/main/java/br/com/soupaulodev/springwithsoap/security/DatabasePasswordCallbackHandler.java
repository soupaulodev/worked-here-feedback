package br.com.soupaulodev.springwithsoap.security;

import br.com.soupaulodev.springwithsoap.entity.UserEntity;
import br.com.soupaulodev.springwithsoap.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.springframework.stereotype.Component;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DatabasePasswordCallbackHandler implements CallbackHandler {

    private final UserRepository userRepository;

    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        for (Callback callback : callbacks) {
            if (callback instanceof WSPasswordCallback wsPasswordCallback) {
                Optional<UserEntity> user = userRepository.findByUsername(wsPasswordCallback.getIdentifier());

                if (user.isPresent() && user.get().getPassword().equals(wsPasswordCallback.getPassword())) {
                    return;
                } else {
                    throw new UnsupportedCallbackException(callback, "Username or password invalid");
                }
            }
        }
    }
}
