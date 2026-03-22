package com.example.backend.config;

import com.example.backend.dataHandler.entity.Stats;
import com.example.backend.dataHandler.entity.Student;
import com.example.backend.repository.StudentRepository;
import com.example.backend.utils.HumorType;
import com.example.backend.utils.StudentType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(StudentRepository repository) {
        return args -> {
            if (repository.count() > 0) return;

            repository.save(student("Lucas", "Martin", "LucasBot", "Cybersécurité",
                    StudentType.FISE, HumorType.GRIND_MASTER,
                    95, 88, 70,
                    "Trouver des failles dans n'importe quel système en 10 minutes",
                    "Je dors pas, je compile.", null));

            repository.save(student("Camille", "Dubois", "CamDev", "Génie Logiciel",
                    StudentType.FISA, HumorType.CAFE_ADDICT,
                    78, 65, 90,
                    "Refactoriser du code legacy sans rien casser",
                    "Un café de plus et je résous P=NP.", null));

            repository.save(student("Nathan", "Lefèvre", "NathanLaFantôme", "Réseaux",
                    StudentType.FISE, HumorType.FANTOME,
                    60, 55, 85,
                    "Apparaître uniquement lors des soutenances",
                    "Je suis là, mais suis-je vraiment là ?", null));

            repository.save(student("Léa", "Bernard", "LeaSharp", "Intelligence Artificielle",
                    StudentType.FAT, HumorType.NOCTAMBULE,
                    85, 92, 60,
                    "Entraîner un modèle en une nuit blanche",
                    "Le meilleur commit est celui fait à 3h du matin.", null));

            repository.save(student("Tom", "Garnier", "TomCrash", "Systèmes Embarqués",
                    StudentType.FISE, HumorType.CAFE_ADDICT,
                    70, 80, 75,
                    "Faire tourner Linux sur n'importe quel appareil",
                    "Ça marche sur ma machine.", null));

            repository.save(student("Sofia", "Petit", "SofiaStack", "Cloud & DevOps",
                    StudentType.FISA, HumorType.GRIND_MASTER,
                    88, 75, 82,
                    "Déployer une infra Kubernetes en moins de 5 minutes",
                    "Si c'est pas dans le pipeline, ça n'existe pas.", null));

            repository.save(student("Hugo", "Moreau", "HugoZZZ", "Cybersécurité",
                    StudentType.FAT, HumorType.NOCTAMBULE,
                    65, 70, 95,
                    "Survivre à n'importe quelle réunion sans dormir (presque)",
                    "Je suis pas en retard, je suis asynchrone.", null));

            repository.save(student("Inès", "Simon", "InesML", "Data Science",
                    StudentType.FISE, HumorType.GRIND_MASTER,
                    90, 85, 68,
                    "Visualiser un dataset de 10 Go en temps réel",
                    "Les données ne mentent pas. Moi non plus.", null));

            repository.save(student("Maxime", "Laurent", "MaxDev404", "Génie Logiciel",
                    StudentType.FISA, HumorType.FANTOME,
                    55, 60, 80,
                    "Disparaître avant la fin du sprint planning",
                    "Le ticket dit 'done', c'est suffisant.", null));

            repository.save(student("Chloé", "Rousseau", "ChloeOps", "Cloud & DevOps",
                    StudentType.FAT, HumorType.CAFE_ADDICT,
                    80, 78, 88,
                    "Automatiser tout ce qui peut l'être",
                    "Pourquoi faire à la main ce qu'un script peut rater automatiquement ?", null));
        };
    }

    private Student student(String firstName, String lastName, String nickname,
                            String speciality, StudentType type, HumorType humorType,
                            int hp, int attack, int defense,
                            String superPower, String catchPhrase, String imageUrl) {
        Stats stats = new Stats();
        stats.setHp(hp);
        stats.setAttack(attack);
        stats.setDefense(defense);

        Student s = new Student();
        s.setFirstName(firstName);
        s.setLastName(lastName);
        s.setNickname(nickname);
        s.setSpeciality(speciality);
        s.setType(type);
        s.setHumorType(humorType);
        s.setStats(stats);
        s.setSuperPower(superPower);
        s.setCatchPhrase(catchPhrase);
        s.setImageUrl(imageUrl);
        return s;
    }
}
