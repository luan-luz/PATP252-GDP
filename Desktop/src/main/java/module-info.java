module Desktop {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    exports ideau.ControlePatrimonioDesktop;
    opens ideau.ControlePatrimonioDesktop.controller;
}