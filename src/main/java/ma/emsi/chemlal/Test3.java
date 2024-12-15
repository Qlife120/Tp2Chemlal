package ma.emsi.chemlal;


import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.googleai.GoogleAiEmbeddingModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.store.embedding.CosineSimilarity;

import java.time.Duration;


public class Test3 {

    private final static String GEMINI_KEY = System.getenv("GEMINI_KEY");

    public static void main(String[] args) {
        EmbeddingModel embeddingModel = new GoogleAiEmbeddingModel("embedding-001", GEMINI_KEY,
                2,
                GoogleAiEmbeddingModel.TaskType.SEMANTIC_SIMILARITY,
                ""
                ,200 // dimension du resultat
                , Duration.ofSeconds(60), // timeout
                true); // for logging purposes

        String phrase1 = "I love programming";
        String phrase2 = "Coding is my passion";

        Response<Embedding> response1  = embeddingModel.embed(phrase1);
        Response<Embedding> response2  = embeddingModel.embed(phrase2);;
        Embedding embedding1 = response1.content();
        Embedding embedding2 = response2.content();

        double similarity = CosineSimilarity.between(embedding1, embedding2);

        System.out.println("Similarity between the two phrases is: " + similarity);


    }
}