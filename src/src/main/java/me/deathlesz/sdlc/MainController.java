package me.deathlesz.sdlc;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import me.deathlesz.sdlc.morse.MorseModel;

public final class MainController implements MorseModel.Listener {

    private final MorseModel model = new MorseModel();

    @FXML
    private Label sourceLabel;

    @FXML
    private Label resultLabel;

    @FXML
    void initialize() {
        model.addListener(this);
    }

    @FXML
    private void onInputButtonClick() {
        var owner = sourceLabel.getScene().getWindow();
        var input = InputDialog.show(owner, model.getLastInput());
        if (input.isPresent()) {
            model.setInput(input.get());
        }
    }

    @Override
    public void modelChanged(MorseModel model) {
        sourceLabel.setText("Исходные данные: " + (model.getLastInput().isEmpty() ? "—" : model.getLastInput()));
        var error = model.getError();
        if (error == null) {
            resultLabel.setText("Результат: " + model.getDecodedText());
            resultLabel.getStyleClass().remove("bad");
        } else {
            resultLabel.setText("Результат: —");
            resultLabel.getStyleClass().add("bad");
            showError(error);
        }
    }

    private void showError(String message) {
        var alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Некорректные данные");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
