package billsservice.utils;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class Utils {
    public String createUuid() {
        return String.valueOf(UUID.randomUUID());
    }
}
