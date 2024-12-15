package ma.emsi.chemlal;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.request.ResponseFormat;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;

import java.time.Duration;
import java.util.Map;



public class Test2 {

    private static final String GEMINI_KEY = System.getenv("GEMINI_KEY");

    public static void main(String[] args) {

        ChatLanguageModel model = GoogleAiGeminiChatModel.builder()
                .apiKey(GEMINI_KEY)
                .modelName("gemini-1.5-flash") // name of the model
                .temperature(0.7)
                .timeout(Duration.ofSeconds(60))
                .responseFormat(ResponseFormat.JSON) // format of the response to return to the user (JSON )
                .build();

        // create a prompt to transtlate a text to italian
        Prompt prompt = PromptTemplate.from("translate this text to {{language}} : {{text}}").apply(Map.of("text", "Hello world!","language","italian"));
        String response = model.generate(prompt.text());

        // test the response
        System.out.println(response);




    }
}
