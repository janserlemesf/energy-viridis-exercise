package energy.viridis.exercise.controller;

import energy.viridis.exercise.exception.BadRequestException;
import energy.viridis.exercise.model.Equipment;
import energy.viridis.exercise.security.AccountCredentials;
import energy.viridis.exercise.util.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @PostMapping
    public ResponseEntity login(@RequestBody AccountCredentials accountCredentials) {
        return ResponseEntity.ok().build();
    }
}
