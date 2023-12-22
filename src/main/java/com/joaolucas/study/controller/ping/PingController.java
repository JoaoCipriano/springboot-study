package com.joaolucas.study.controller.ping;

import com.joaolucas.api.PingApi;
import com.joaolucas.model.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController implements PingApi {

    @Override
    public ResponseEntity<MessageResponse> ping() {
        return ResponseEntity.ok(new MessageResponse().text("Pong"));
    }
}
