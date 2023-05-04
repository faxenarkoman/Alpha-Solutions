package kea.dk.alpha_solutions.AlphaRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
@Repository
public class AlphaRepository
{
        @Value("${spring.datasource.url}")
        private String DB_URL;
        @Value("${spring.datasource.username}")
        private String UID;
        @Value("${spring.datasource.password}")
        private String PWD;
}
