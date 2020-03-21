package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Controller Class
 * **/

public class Controller {
MemberArchive memberArchive=new MemberArchive();
TableView<TableClass> tableView = new TableView<>();

    TextArea basicMember = new TextArea();

    TextArea silverMember = new TextArea();
    TextArea goldMember = new TextArea();


    TextInputDialog inputDialog;


    ObservableList<TableClass> personalObservableList = FXCollections.observableArrayList();



/**
 * @return returns observableList av type TableClass class
 * The method initiate() -> instantiates The class Personal and adds
 * the object to the hash map by calling addMember() of memberArchive class.
 *
 *
 *
 * **/

    public ObservableList<TableClass> initiate() {
//creating p1 object
        Personals p1= new Personals("Martin",
                "Hansen","mar@gmail.com",
                "ghhh45");

// add to the hashMap and as well as get member number
        int memberNum1=  memberArchive.addMember(p1,LocalDate.of(2020,3,8));


        Personals p2= new Personals("Abdi",
                "Bako","bako@gmail.com",
                "1234");
        int memberNum2 =  memberArchive.addMember(p2, LocalDate.of(2020,1,8));


        Personals p3= new Personals("Ola",
                "Tomas","tom@gmail.com",
                "4333h45");
        int memberNum3 =   memberArchive.addMember(p3,LocalDate.of(2019,6,8));

        Personals p4= new Personals("Hanna",
                "Hansen","han@gmail.com",
                "ghhh45");
        int memberNum4 =   memberArchive.addMember(p4,LocalDate.of(2018,11,8));

        TableClass tableClass1=new TableClass(memberNum1,p1,LocalDate.of(2020,6,8),5050);
        TableClass tableClass2=new TableClass(memberNum2,p2,LocalDate.of(2020,4,2),3050);
        TableClass tableClass3=  new TableClass(memberNum3,p3,LocalDate.of(2019,6,8),50050);
        TableClass tableClass4=  new TableClass(memberNum4,p4,LocalDate.of(2018,11,8),75000);


        personalObservableList.addAll(tableClass1,tableClass2,tableClass3,tableClass4);


      //  System.out.println(memberArchive.displayMembers());
        return personalObservableList;
    }




/**
 * 
 * */


    public void onAddClicked()  {

        Stage stage = new Stage();
        stage.setTitle("Adding New Members");

        Label firstNameL = new Label("First Name: ");
        Label surnameL = new Label("Surname: ");
        Label e_mailL = new Label("E-mail:" );
        Label passL = new Label("Password: ");
        Label date  = new Label("Enrolled Date: ");
        DatePicker datePicker = new DatePicker();




        TextField firstName = new TextField();
        firstName.setPromptText("first Name");
        TextField surname = new TextField();
        surname.setPromptText("Surname");
        TextField e_mail = new TextField();
        e_mail.setPromptText("E-mail");
        PasswordField pass=new PasswordField();
        pass.setPromptText("*********");
        GridPane root =new GridPane();
        Button save = new Button("Save");
        Button cancel = new Button("Cancel");

        VBox vBox=new VBox();



        root.add(firstNameL,0,0);
        root.add(firstName,1,0);
        root.add(surnameL,0, 1);
        root.add(surname,1, 1);//col 1 row 1
        root.add(e_mailL,0,2);
        root.add(e_mail,1,2);
        root.add(passL,0,3);
        root.add(pass,1,3);
        root.add(date,0,4);
        root.add(datePicker,1,4);
        root.add(save,1,5);
        root.add(cancel, 2,5);
        root.setVgap(10);



        vBox.getChildren().addAll(root);
        vBox.setBackground(new Background(new BackgroundFill(Color.BISQUE, CornerRadii.EMPTY, Insets.EMPTY)));
        vBox.setAlignment(Pos.CENTER_RIGHT);
        root.setHgap(50);
        root.setPadding(new Insets(10,30,20,20));
        Scene scene = new Scene(vBox,500,500);



        save.setOnAction(toSave->{

try {


    Personals personals = new Personals(firstName.getText(), surname.getText(),
            e_mail.getText(), pass.getText());

    int memberNumb = memberArchive.addMember(personals, datePicker.getValue());
    TableClass tableClass = new TableClass(memberNumb, personals, datePicker.getValue(), 0);
    personalObservableList.removeAll(tableClass); //removes what already available in the table,helps to refresh the table
    personalObservableList.addAll(tableClass);  //Adds up to dated value to the table

    firstName.clear();
    surname.clear();
    pass.clear();
    e_mail.clear();

}catch (IllegalArgumentException e){
  Label eMessage = new Label();
    eMessage.setText(e.getMessage());
}



        });

      //  System.out.println(memberArchive.displayMembers());

        stage.initModality((Modality.APPLICATION_MODAL));
        stage.setScene(scene);

        stage.show();

    }





    public void onRegisterClicked() {

        //selected Item from a table


        TableClass selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem!=null) {
            for(BonusMember bm : memberArchive.displayMembers().values()) {
                if (selectedItem.memberNo == bm.getMemberNo()) {

                    inputDialog = new TextInputDialog();
                    inputDialog.setHeaderText("Register Point Dialog");
                    inputDialog.setTitle("Point registration");

                    Optional<String> response = inputDialog.showAndWait();


                    if (response.isPresent()) {


                        try {

                            int points = Integer.parseInt(response.get());
                            if (points > 0) {
                                memberArchive.checkMembers(LocalDate.now());
                                memberArchive.registerPoints(selectedItem.memberNo, points);
                              //  System.out.println(memberArchive.displayMembers());
                            }

                        } catch (NumberFormatException e) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("Invalid Input Only numbers Allowed");
                            alert.show();

                        }


                    }


                }
            }
        }
    }


    /**
     * Delete clicked
     * */
    public void onDeleteClicked(){
        TableClass selectedItem = tableView.getSelectionModel().getSelectedItem();



        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);

        if(  selectedItem!=null){

            alert.showAndWait();
            if (alert.getResult()== ButtonType.OK) {
                //Remove selected Item from the table
               tableView.getItems().remove(selectedItem);
                //Remove yhe selected item from the collection(HashMap)
                memberArchive.displayMembers().remove(selectedItem.memberNo);
                Alert alert1=new Alert(Alert.AlertType.INFORMATION,
                        "You have "+" Successfully removed  "+selectedItem.surname)  ;

                alert1.show();}}


    }


    public void onSeeDetail(){

        TableClass selectedItem=tableView.getSelectionModel().getSelectedItem();
        if((selectedItem!=null)) {

            for (BonusMember bm : memberArchive.displayMembers().values()) {

                if (selectedItem.memberNo == bm.getMemberNo()) {
                    String type = bm.getClass().getName().substring(7).replace("Member", " ");
                    String detailInfo = "Member Type: \t" + type + "\n" + "Full Name:  " +
                            "  \t" + bm.getPersonals().getSurname() + " , " + bm.getPersonals().getFirstName() + "\n"
                           +"E-mail:\t"+bm.getPersonals().getEMailAddress()+"\n"+
                            "Enrolled Date: \t"+bm.getEnrolledDate()+"\n"
                            + "MemberNo:    \t" + bm.getMemberNo() + "\n" +
                            "Earned Points: \t" + bm.getBonusPoints() + "\n";
                    memberArchive.checkMembers(LocalDate.now());

                    if (bm instanceof BasicMember) {

                        //memberArchive.checkMembers(LocalDate.now());
                        basicMember.setText(detailInfo);
                        basicMember.setEditable(false);

                    } else if (bm instanceof SilverMember) {





                        silverMember.setText(detailInfo);
                        silverMember.setEditable(false);
                    }else {
                        //memberArchive.checkMembers(LocalDate.now());

                        goldMember.setText(detailInfo);
                        goldMember.setEditable(false);
                    }



                }


            }


        }

    }

















}
