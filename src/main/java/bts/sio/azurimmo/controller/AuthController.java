package bts.sio.azurimmo.controller;

import bts.sio.azurimmo.dto.RequestLoginDto;
import bts.sio.azurimmo.model.Utilisateur;
import bts.sio.azurimmo.repository.UtilisateurRepository;
import bts.sio.azurimmo.service.AuthService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @PostMapping("/register")
    public ResponseEntity<Utilisateur> register(@RequestBody Utilisateur utilisateur) {
        if (utilisateurRepository.findByEmail(utilisateur.getEmail()) != null) {
            return ResponseEntity.badRequest().build();
        }

        String hashed = authService.hashPassword(utilisateur.getPassword());
        utilisateur.setPassword(hashed);
        utilisateurRepository.save(utilisateur);

        return ResponseEntity.ok(utilisateur);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody RequestLoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = authService.generateToken(authentication);
        return ResponseEntity.ok(Map.of("token", token));
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        Utilisateur user = authService.getActiveUser(token);
        return ResponseEntity.ok(user);
    }
}



