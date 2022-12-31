package tk.mwacha.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class JavaApplicationProperties {

    public static Properties readProperties() {

        final var props = new Properties();
        final var myPath = Paths.get("src/main/resources/application.yml");

        try {
            final var bf = Files.newBufferedReader(myPath,
                    StandardCharsets.UTF_8);

            props.load(bf);
        } catch (IOException ex) {
            Logger.getLogger(JavaApplicationProperties.class.getName()).log(
                    Level.SEVERE, null, ex);
        }

        return props;
    }
}
