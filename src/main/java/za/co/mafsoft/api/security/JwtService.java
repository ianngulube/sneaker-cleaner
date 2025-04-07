package za.co.mafsoft.api.security;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashSet;
import java.util.List;

@ApplicationScoped
public class JwtService {

    public String generateToken(final String userName) {
        return Jwt.issuer("Sneaker Cleaner API Authenticator")
                .upn(userName)
                .groups(new HashSet<>(List.of("User", "Admin")))
                .sign();
    }


}
