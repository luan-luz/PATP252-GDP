package ideau.ControlePatrimonioDesktop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ClientDesktop extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/telaLogin.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setResizable(false);
        stage.setTitle("Login");
        try {
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/view/icones-png/logo.png")));
        } catch (Exception e) {
            //adicionar uns logs """mais melhor""", por enquanto vamos de sout :P
            System.out.println("Logo da janela não encontrada! " + e.getMessage()); //se não achar executa normal, apenas avisa que nao achou
        }
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
