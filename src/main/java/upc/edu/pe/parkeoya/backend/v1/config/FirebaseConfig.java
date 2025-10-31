package upc.edu.pe.parkeoya.backend.v1.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.util.Base64;

@Configuration
@ConditionalOnProperty(prefix = "firebase", name = "enabled", havingValue = "true", matchIfMissing = false)//Quitar en produccion
public class FirebaseConfig {

    //Quitar en produccion
    private final Log logger = LogFactory.getLog(this.getClass());

    @Value("${FIREBASE_ADMIN_CONFIG:}")
    private String firebaseAdminConfig;
    //Bloque a retirar

    @PostConstruct
    public void initializeFirebase() {
        try {
            String firebaseConfigBase64 = System.getenv("FIREBASE_ADMIN_CONFIG");

            if (firebaseConfigBase64 == null || firebaseConfigBase64.isEmpty()) {
                throw new IllegalStateException("Variable de entorno FIREBASE_ADMIN_CONFIG no está definida.");
            }

            byte[] decodedBytes = Base64.getDecoder().decode(firebaseConfigBase64);
            ByteArrayInputStream serviceAccount = new ByteArrayInputStream(decodedBytes);

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                System.out.println("Firebase inicializado correctamente desde variable de entorno.");
            }

        } catch (Exception e) {
            System.err.println("Error al inicializar Firebase desde variable de entorno:");
            e.printStackTrace();
        }
    }
}
