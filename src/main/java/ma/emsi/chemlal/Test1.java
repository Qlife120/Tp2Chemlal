package ma.emsi.chemlal;


import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.request.ResponseFormat;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import lombok.extern.slf4j.Slf4j;


import java.time.Duration;

@Slf4j
public class Test1 {


   private static final String GEMINI_KEY = System.getenv("GEMINI_KEY");

    public static void main(String[] args) {

        
        ChatLanguageModel model = GoogleAiGeminiChatModel.builder()
                .apiKey(GEMINI_KEY).modelName("gemini-1.5-flash")
                .temperature(0.7) // name of the model to use
                .timeout(Duration.ofSeconds(60))
                .responseFormat(ResponseFormat.JSON) // format of the response to return to the user (JSON )
                .build();

        String response = model.generate(("How are you doing?"));

        System.out.println(response);

    }
}
