import org.apache.log4j.BasicConfigurator;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) {
        BasicConfigurator.configure();
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
//            telegramBotsApi.registerBot(new botClassTA());
//            telegramBotsApi.registerBot(new BotChat());
//            telegramBotsApi.registerBot(new formChat());
//            telegramBotsApi.registerBot(new DirectionsHandlers());
//            telegramBotsApi.registerBot(new RaeHandlers());
//            telegramBotsApi.registerBot(new WeatherHandlers());
//            telegramBotsApi.registerBot(new TransifexHandlers());
//            telegramBotsApi.registerBot(new FilesHandlers());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}