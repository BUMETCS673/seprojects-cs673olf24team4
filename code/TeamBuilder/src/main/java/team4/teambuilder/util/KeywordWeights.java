package team4.teambuilder.util;

import java.util.HashMap;
import java.util.Map;

public class KeywordWeights {
    public static final Map<String, Integer> WEIGHTS = new HashMap<>();

    static {
        // Leadership and soft skills
        WEIGHTS.put("leader", 5);
        WEIGHTS.put("leadership", 5);
        WEIGHTS.put("manager", 5);
        WEIGHTS.put("collaborator", 4);
        WEIGHTS.put("team player", 4);
        WEIGHTS.put("problem solver", 4);
        WEIGHTS.put("coordinator", 3);
        WEIGHTS.put("communicator", 3);
        WEIGHTS.put("detail-oriented", 3);
        WEIGHTS.put("creative", 3);
        WEIGHTS.put("innovative", 3);
        WEIGHTS.put("analytical", 3);

        // Programming languages
        WEIGHTS.put("java", 4);
        WEIGHTS.put("python", 4);
        WEIGHTS.put("javascript", 4);
        WEIGHTS.put("c++", 4);
        WEIGHTS.put("c#", 4);
        WEIGHTS.put("ruby", 3);
        WEIGHTS.put("go", 3);
        WEIGHTS.put("swift", 3);
        WEIGHTS.put("kotlin", 3);
        WEIGHTS.put("scala", 3);

        // Web technologies
        WEIGHTS.put("html", 3);
        WEIGHTS.put("css", 3);
        WEIGHTS.put("react", 4);
        WEIGHTS.put("angular", 4);
        WEIGHTS.put("vue", 4);
        WEIGHTS.put("node.js", 4);

        // Databases
        WEIGHTS.put("sql", 3);
        WEIGHTS.put("mysql", 3);
        WEIGHTS.put("postgresql", 3);
        WEIGHTS.put("mongodb", 3);
        WEIGHTS.put("oracle", 3);

        // DevOps and Cloud
        WEIGHTS.put("devops", 4);
        WEIGHTS.put("aws", 4);
        WEIGHTS.put("azure", 4);
        WEIGHTS.put("google cloud", 4);
        WEIGHTS.put("docker", 3);
        WEIGHTS.put("kubernetes", 3);
        WEIGHTS.put("jenkins", 3);

        // Design and UX
        WEIGHTS.put("ui/ux", 4);
        WEIGHTS.put("user experience", 4);
        WEIGHTS.put("user interface", 4);
        WEIGHTS.put("photoshop", 3);
        WEIGHTS.put("illustrator", 3);
        WEIGHTS.put("sketch", 3);
        WEIGHTS.put("figma", 3);

        // Project management and methodologies
        WEIGHTS.put("agile", 3);
        WEIGHTS.put("scrum", 3);
        WEIGHTS.put("kanban", 3);
        WEIGHTS.put("waterfall", 2);
        WEIGHTS.put("prince2", 2);

        // Testing and QA
        WEIGHTS.put("qa", 3);
        WEIGHTS.put("quality assurance", 3);
        WEIGHTS.put("testing", 3);
        WEIGHTS.put("automation", 3);
        WEIGHTS.put("selenium", 3);
        WEIGHTS.put("junit", 3);
        WEIGHTS.put("testng", 3);
    }
}