package id.my.agungdh.rakawikchat.seed;

import id.my.agungdh.rakawikchat.entity.User;
import id.my.agungdh.rakawikchat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) {
            return;
        }

        userRepository.save(User.builder()
                .username("agung")
                .password(passwordEncoder.encode("password123"))
                .fullName("Agung Dwi Haryanto")
                .isActive(true)
                .build());

        userRepository.save(User.builder()
                .username("budi")
                .password(passwordEncoder.encode("password123"))
                .fullName("Budi Santoso")
                .isActive(true)
                .build());

        userRepository.save(User.builder()
                .username("citra")
                .password(passwordEncoder.encode("password123"))
                .fullName("Citra Dewi")
                .isActive(true)
                .build());

        log.info("Default users seeded successfully");
    }
}
