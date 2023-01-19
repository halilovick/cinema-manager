package ba.unsa.etf.rpr.Controllers;

import ba.unsa.etf.rpr.App;
import ba.unsa.etf.rpr.business.filmoviManager;
import ba.unsa.etf.rpr.business.karteManager;
import ba.unsa.etf.rpr.business.usersManager;
import ba.unsa.etf.rpr.domain.Film;
import ba.unsa.etf.rpr.domain.Karta;
import ba.unsa.etf.rpr.domain.User;
import ba.unsa.etf.rpr.exceptions.FilmoviException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static ba.unsa.etf.rpr.Controllers.LoginController.user;
import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class AdminPageController {
    public TableColumn colID1;
    public TableColumn colUser1;
    public TableColumn colPassword1;
    public TableColumn colIme1;
    public TableColumn colEmail1;
    public TableColumn colAdresa1;
    public TableColumn colGrad1;
    public TableColumn colDatumRodj1;
    public TableColumn colAdmin1;
    public TextField userField1;
    public TextField passwordField1;
    public TextField imeField1;
    public TextField emailField1;
    public TextField adminField1;
    public Button dodajButton1;
    public Button azurirajButton1;
    public Button obrisiButton1;
    public Button nazadButton21;
    public TableView tabelaUsera;
    public Label cijenaLabelFiksna;
    public Label trajanjeLabelFiksna;
    public Label zanrLabelFiksna;
    public Tab prodajaKarataAdminButton;
    private int brojKarata = 0;
    private final filmoviManager fmanager = new filmoviManager();
    private final karteManager kmanager = new karteManager();
    private final List<String> listaFilmova = fmanager.getAllNames();
    private final ObservableList<String> filmovi = FXCollections.observableArrayList(listaFilmova);
    public ChoiceBox filmChoiceBox;
    public Label zanrLabel;
    public Label trajanjeLabel;
    public Label cijenaLabel;
    public TextField brojKarataTextField;
    public DatePicker odabirDatuma;
    private LocalDate datum;
    String imeOdabranogFilma = "";
    private Integer id;
    public TableColumn colID;
    public TableColumn colIme;
    public TableColumn colZanr;
    public TableColumn colTrajanje;
    public TableColumn colCijena;
    public TableColumn colBrojSale;
    public TableView<Film> tabelaFilmova;
    private final usersManager umanager = new usersManager();
    public TextField imeField;
    public TextField zanrField;
    public TextField trajanjeField;
    public TextField cijenaField;
    public TextField brojsaleField;

    public AdminPageController() throws FilmoviException {
    }

    public void dodajFilmButtonClick(ActionEvent actionEvent) throws FilmoviException {
        if (imeField.getText().isEmpty() || zanrField.getText().isEmpty() || trajanjeField.getText().isEmpty() || cijenaField.getText().isEmpty() || brojsaleField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid details");
            alert.setContentText("Please enter valid details!");
            alert.showAndWait();
            return;
        }
        try {
            Film f = new Film();
            f.setIme(imeField.getText());
            f.setZanr(zanrField.getText());
            f.setTrajanje(Integer.parseInt(trajanjeField.getText()));
            f.setCijena(Integer.parseInt(cijenaField.getText()));
            f.setBroj_sale(Integer.parseInt(brojsaleField.getText()));
            fmanager.add(f);
            UpdateFilmoviTable();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success!");
            alert.setHeaderText(null);
            alert.setContentText("Film has been successfully added");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid details");
            alert.setContentText("Please enter valid details!");
            alert.showAndWait();
        } catch (FilmoviException e) {
            throw new FilmoviException(e.getMessage(), e);
        }
    }

    public void azurirajFilmButtonClick(ActionEvent actionEvent) throws FilmoviException {
        if (imeField.getText().isEmpty() || zanrField.getText().isEmpty() || trajanjeField.getText().isEmpty() || cijenaField.getText().isEmpty() || brojsaleField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Choose a film!");
            alert.setContentText("A film has not been chosen");
            alert.showAndWait();
            return;
        }
        Film f = new Film();
        f.setId(id);
        f.setIme(imeField.getText());
        f.setZanr(zanrField.getText());
        f.setTrajanje(Integer.parseInt(trajanjeField.getText()));
        f.setCijena(Integer.parseInt(cijenaField.getText()));
        f.setBroj_sale(Integer.parseInt(brojsaleField.getText()));
        fmanager.update(f);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success!");
        alert.setHeaderText(null);
        alert.setContentText("Film has been successfully updated");
        UpdateFilmoviTable();
        alert.showAndWait();
    }

    public void obrisiFilmButtonClick(ActionEvent actionEvent) throws FilmoviException {
        if (imeField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Choose a film!");
            alert.setContentText("A film has not been chosen");
            alert.showAndWait();
            return;
        }
        kmanager.deleteWithFilmId(fmanager.getByIme(imeField.getText()).getId());
        fmanager.delete(fmanager.getByIme(imeField.getText()).getId());
        UpdateFilmoviTable();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success!");
        alert.setHeaderText(null);
        alert.setContentText("Film has been successfully deleted");
        alert.showAndWait();
    }

    public void getSelectedFilm(MouseEvent mouseEvent) {
        int index = tabelaFilmova.getSelectionModel().getSelectedIndex();
        if (index <= -1) return;
        id = Integer.valueOf(colID.getCellData(index).toString());
        imeField.setText(colIme.getCellData(index).toString());
        zanrField.setText(colZanr.getCellData(index).toString());
        trajanjeField.setText(colTrajanje.getCellData(index).toString());
        cijenaField.setText(colCijena.getCellData(index).toString());
        brojsaleField.setText(colBrojSale.getCellData(index).toString());
    }

    public void UpdateFilmoviTable() throws FilmoviException {
        colID.setCellValueFactory(new PropertyValueFactory<Film, Integer>("id"));
        colIme.setCellValueFactory(new PropertyValueFactory<Film, String>("ime"));
        colZanr.setCellValueFactory(new PropertyValueFactory<Film, String>("zanr"));
        colTrajanje.setCellValueFactory(new PropertyValueFactory<Film, Integer>("trajanje"));
        colCijena.setCellValueFactory(new PropertyValueFactory<Film, Integer>("cijena"));
        colBrojSale.setCellValueFactory(new PropertyValueFactory<Film, Integer>("broj_sale"));
        List<Film> filmovi = fmanager.getAll();
        ObservableList<Film> f = FXCollections.observableArrayList(filmovi);
        tabelaFilmova.setItems(f);
        tabelaFilmova.refresh();
    }

    public void UpdateProdajaTable() {
        filmChoiceBox.setItems(filmovi);
        filmChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            try {
                imeOdabranogFilma = listaFilmova.get(newValue.intValue());
                Film f = fmanager.getByIme(imeOdabranogFilma);
                trajanjeLabelFiksna.setText("DURATION:");
                cijenaLabelFiksna.setText("PRICE:");
                zanrLabelFiksna.setText("GENRE:");
                trajanjeLabel.setText(f.getTrajanje() + " MIN");
                zanrLabel.setText(f.getZanr());
                cijenaLabel.setText(f.getCijena() + " KM");
            } catch (FilmoviException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @FXML
    public void initialize() throws FilmoviException {
        UpdateProdajaTable();
        UpdateFilmoviTable();
        UpdateUseriTable();
    }

    public void nazadButtonClick(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/loginProzor.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
        LoginController lc = fxmlLoader.getController();
        stage.setResizable(false);
        stage.getIcons().add(new Image("https://cdn-icons-png.flaticon.com/512/3418/3418886.png"));
        stage.setTitle("Login page");
        stage.setScene(scene);
        stage.show();
        Node n = (Node) actionEvent.getSource();
        Stage stage2 = (Stage) n.getScene().getWindow();
        stage2.close();
    }

    public void dodajUserButtonClick(ActionEvent actionEvent) throws FilmoviException {
        if (userField1.getText().isEmpty() || passwordField1.getText().isEmpty() || imeField1.getText().isEmpty() || emailField1.getText().isEmpty() || adminField1.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid details");
            alert.setContentText("Please enter valid details.");
            alert.showAndWait();
            return;
        }
        try {
            User u = new User();
            u.setUser(userField1.getText());
            u.setPassword(passwordField1.getText());
            u.setIme(imeField1.getText());
            u.setEmail(emailField1.getText());
            u.setAdmin(Boolean.parseBoolean(adminField1.getText()));
            umanager.add(u);
            UpdateUseriTable();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success!");
            alert.setHeaderText(null);
            alert.setContentText("User successfully added");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid details");
            alert.setContentText("Please enter valid details.");
            alert.showAndWait();
        } catch (FilmoviException e) {
            throw new FilmoviException(e.getMessage(), e);
        }
    }

    public void azurirajUserButtonClick(ActionEvent actionEvent) throws FilmoviException {
        if (userField1.getText().isEmpty() || passwordField1.getText().isEmpty() || imeField1.getText().isEmpty() || emailField1.getText().isEmpty() || adminField1.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Choose a user!");
            alert.setContentText("A user has not been chosen!");
            alert.showAndWait();
            return;
        }
        User u = new User();
        u.setId(id);
        u.setUser(userField1.getText());
        u.setPassword(passwordField1.getText());
        u.setIme(imeField1.getText());
        u.setEmail(emailField1.getText());
        u.setAdresa(umanager.getById(id).getAdresa());
        u.setGrad(umanager.getById(id).getGrad());
        u.setDatum_rodjenja(umanager.getById(id).getDatum_rodjenja());
        u.setAdmin(Boolean.parseBoolean(adminField1.getText()));
        umanager.update(u);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success!");
        alert.setHeaderText(null);
        alert.setContentText("User successfully updated!");
        UpdateUseriTable();
        alert.showAndWait();
    }

    public void obrisiUserButtonClick(ActionEvent actionEvent) throws FilmoviException {
        if (userField1.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Choose a film");
            alert.setContentText("A film has not been chosen!");
            alert.showAndWait();
            return;
        }
        umanager.delete(umanager.getLoggedInId(userField1.getText(), passwordField1.getText()));
        UpdateUseriTable();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success!");
        alert.setHeaderText(null);
        alert.setContentText("User has been successfully deleted.");
        alert.showAndWait();
    }

    public void getSelectedUser(MouseEvent mouseEvent) {
        int index = tabelaUsera.getSelectionModel().getSelectedIndex();
        if (index <= -1) return;
        id = Integer.valueOf(colID1.getCellData(index).toString());
        userField1.setText(colUser1.getCellData(index).toString());
        passwordField1.setText(colPassword1.getCellData(index).toString());
        imeField1.setText(colIme1.getCellData(index).toString());
        emailField1.setText(colEmail1.getCellData(index).toString());
        adminField1.setText(colAdmin1.getCellData(index).toString());
    }

    public void UpdateUseriTable() throws FilmoviException {
        colID1.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
        colUser1.setCellValueFactory(new PropertyValueFactory<User, String>("user"));
        colPassword1.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
        colIme1.setCellValueFactory(new PropertyValueFactory<User, String>("ime"));
        colEmail1.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        colAdresa1.setCellValueFactory(new PropertyValueFactory<User, String>("adresa"));
        colGrad1.setCellValueFactory(new PropertyValueFactory<User, String>("grad"));
        colDatumRodj1.setCellValueFactory(new PropertyValueFactory<User, Date>("datum_rodjenja"));
        colAdmin1.setCellValueFactory(new PropertyValueFactory<User, Boolean>("admin"));
        List<User> useri = umanager.getAll();
        ObservableList<User> u = FXCollections.observableArrayList(useri);
        tabelaUsera.setItems(u);
        tabelaUsera.refresh();
    }

    public void odabirDatumaClick(ActionEvent actionEvent) {
        datum = odabirDatuma.getValue();
    }

    public void kupiButtonClick(ActionEvent actionEvent) throws FilmoviException, IOException {
        try {
            brojKarata = Integer.parseInt(brojKarataTextField.getText());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Choose number of tickets!");
            alert.setContentText("A number of tickets has not been chosen");
            alert.showAndWait();
            return;
        }
        if (imeOdabranogFilma.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Choose a film!");
            alert.setContentText("A film has not been chosen");
            alert.showAndWait();
            return;
        } else if (brojKarata <= 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Choose number of tickets!");
            alert.setContentText("A number of tickets has not been chosen");
            alert.showAndWait();
            return;
        } else if (datum == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Choose a date!");
            alert.setContentText("A date has not been chosen");
            alert.showAndWait();
            return;
        }
        int ukupnaCijena = brojKarata * fmanager.getByIme(imeOdabranogFilma).getCijena();
        while (brojKarata != 0) {
            Karta k = new Karta();
            k.setFilm(fmanager.getByIme(imeOdabranogFilma));
            k.setUser(user);
            kmanager.add(k);
            brojKarata--;
        }
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/KupljenaKarta.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
        KupljenaKartaController kkc = fxmlLoader.getController();
        kkc.imeFilma.setText("Title: " + imeOdabranogFilma);
        kkc.datumFilma.setText("Date: " + datum);
        kkc.cijenaKarte.setText("Price: " + ukupnaCijena + "KM");
        stage.setResizable(false);
        stage.getIcons().add(new Image("https://cdn-icons-png.flaticon.com/512/3418/3418886.png"));
        stage.setTitle("Ticket successfully purchased!");
        stage.setScene(scene);
        stage.show();
    }
}
