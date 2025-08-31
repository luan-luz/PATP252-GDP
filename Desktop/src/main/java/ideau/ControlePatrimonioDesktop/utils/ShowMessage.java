package ideau.ControlePatrimonioDesktop.utils;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ShowMessage {

    public static void showMessage(Alert.AlertType tpAlerta, String strMensagem) {
        Alert alert = new Alert(tpAlerta);
        alert.setHeaderText(null);

        if (tpAlerta == Alert.AlertType.ERROR) {
            alert.setTitle("Erro!");
        } else {
            alert.setTitle("Atenção!");
        }

        alert.setContentText(strMensagem);
        try {
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(ShowMessage.class.getResourceAsStream("/view/imagens/logo_ideau.png")));
        } catch (Exception e) {
            //adicionar uns logs melhores no futuro, por enquanto vamos de sout :P
            System.out.println("Logo da janela não encontrada! " + e.getMessage()); //se não achar executa normal, apenas avisa que nao achou
        }
        alert.showAndWait();
    }

    public static boolean showMessageComConfirmacao(String strMensagem) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Atenção!");
        alert.setContentText(strMensagem);
        try {
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(ShowMessage.class.getResourceAsStream("/view/imagens/logo_ideau.png")));
        } catch (Exception e) {
            //adicionar uns logs melhores no futuro, por enquanto vamos de sout :P
            System.out.println("Logo da janela não encontrada! " + e.getMessage()); //se não achar executa normal, apenas avisa que não achou
        }
        return alert.showAndWait().filter(resp -> resp.getText().equals("OK")).isPresent();
    }

}
