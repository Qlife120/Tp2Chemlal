package ma.emsi.chemlal;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;

/**
 * Le RAG facile !
 * @author grin
 */
public class TestRAG {

    // Assistant conversationnel
    interface Assistant {
        // Prend un message de l'utilisateur et retourne une réponse du LLM.
        String chat(String userMessage);
    }

    public static void main(String[] args) {
        String llmKey = System.getenv("GEMINI_KEY");
        ChatLanguageModel model = GoogleAiGeminiChatModel.builder()
                .apiKey(llmKey)
                .modelName("gemini-1.5-flash")
                .temperature(0.7)
                .build();

        // Chargement du document, sous la forme d'embeddings, dans une base vectorielle en mémoire
        //String nomDocument = "infos.txt";
        String pdfPath = "ml.pdf";
        Document pdfDocument = FileSystemDocumentLoader.loadDocument(pdfPath);

        //Document document = FileSystemDocumentLoader.loadDocument(nomDocument);
        InMemoryEmbeddingStore embeddingStore = new InMemoryEmbeddingStore<>();
        // Calcule les embeddings et les enregistre dans la base vectorielle

        //EmbeddingStoreIngestor.ingest(document, embeddingStore);
        EmbeddingStoreIngestor.ingest(pdfDocument, embeddingStore);

        // Création de l'assistant conversationnel, avec une mémoire.
        // L'implémentation de Assistant est faite par LangChain4j.
        // La base vectorielle en mémoire est utilisée pour retrouver les embeddings.
        Assistant assistant =
                AiServices.builder(Assistant.class)
                        .chatLanguageModel(model)
                        .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
                        .contentRetriever(EmbeddingStoreContentRetriever.from(embeddingStore))
                        .build();

        // Le LLM va utiliser l'information du fichier infos.txt pour répondre à la question.
        //String question = "Comment s'appelle le chat de Pierre ?";
        String question = "En s'inspirant du cours 'Machine Learning' de Richard Grin, donne moi un QCM de 5 questions?";
        String question2 = "Quel est l'objectif du cours 'Machine Learning' de Richard Grin ?";
        // L'assistant analyse la question et recherche les informations pertinentes
        // pour la question dans la base vectorielle.
        // Ces informations pertinentes sont ajoutées à la question et le tout est envoyé au LLM.
        String reponse = assistant.chat(question);
        String reponse2 = assistant.chat(question2);

        // Affiche la réponse du LLM.
        System.out.println(reponse);
        System.out.println(reponse2);

    }

}