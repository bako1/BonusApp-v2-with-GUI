package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Main extends Application  {
  private Controller controller = new Controller();




    @Override
    public void start(Stage primaryStage) throws Exception {



        /**
         * Table*/

//table column for first name
        TableColumn<TableClass, String> firstName = new TableColumn<>("firstName");
        firstName.setMaxWidth(200);
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        //table column for surname

        TableColumn<TableClass, String> surname = new TableColumn<>("Surname");
        surname.setMaxWidth(100);
        surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        //table column for Member number
        TableColumn<TableClass, Integer> memberNo = new TableColumn<>("Member.No");
        memberNo.setMaxWidth(100);
        memberNo.setCellValueFactory(new PropertyValueFactory<>("memberNo"));


        /**
         *
         *
         *
         * **/


        Button addNew = new Button("Add");

        Image image = new Image(getClass().getResourceAsStream("addNewMember.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(25);
        imageView.setFitWidth(25);
        addNew.setGraphic(imageView);
        addNew.setBackground(new Background(new BackgroundFill(Color.GRAY,CornerRadii.EMPTY,Insets.EMPTY)));
        /***
         * Add set onAction
         */
       addNew.setOnAction(add->
       controller.onAddClicked());





        Button deleteM = new Button("Remove");
        Button registerPoints = new Button("Register \nPoints");
        Button showDetails  = new Button("Details");

        Image image2 = new Image(getClass().getResourceAsStream("removeMember.png"));
        ImageView image2View = new ImageView(image2);
        image2View.setFitHeight(25);
        image2View.setFitWidth(25);
        deleteM.setGraphic(image2View);


        deleteM.setBackground(new Background(new BackgroundFill(Color.GRAY,CornerRadii.EMPTY,Insets.EMPTY)));

        deleteM.setOnAction(del->controller.onDeleteClicked());

        Image image3 = new Image(getClass().getResourceAsStream("viewDetails.png"));
        ImageView image3View = new ImageView(image3);
        image3View.setFitHeight(25);
        image3View.setFitWidth(25);
        showDetails.setGraphic(image3View);


        showDetails.setBackground(new Background(new BackgroundFill(Color.GRAY,CornerRadii.EMPTY,Insets.EMPTY)));

        registerPoints.setBackground(new Background(new BackgroundFill(Color.GRAY,CornerRadii.EMPTY,Insets.EMPTY)));

        showDetails.setOnAction(show->controller.onSeeDetail());
        String borderColor = "-fx-border-color: red";

        addNew.setPrefSize(100,70);
        addNew.setStyle(borderColor);
        deleteM.setPrefSize(100,70);
        deleteM.setStyle(borderColor);

        showDetails.setPrefSize(100,70);
        showDetails.setStyle(borderColor);

        registerPoints.setPrefSize(100,70);
        registerPoints.setStyle(borderColor);


        Image image4 = new Image(getClass().getResourceAsStream("registerP.png"));
        ImageView image4View = new ImageView(image4);
        image4View.setFitHeight(25);
        image4View.setFitWidth(25);
        registerPoints.setGraphic(image4View);

        HBox adddshdel = new HBox(10);


        registerPoints.setOnAction(rP->controller.onRegisterClicked());

        adddshdel.setOpaqueInsets(new Insets(10,10,10,10));
        adddshdel.setBackground( new Background(new BackgroundFill(Color.PALEGOLDENROD,CornerRadii.EMPTY,Insets.EMPTY)));
        adddshdel.getChildren().addAll(addNew,deleteM,showDetails,registerPoints);

        /**
         * Table view
         *
         *
         */

        controller.tableView.getOnScroll();
        controller.tableView.getColumns().addAll(surname, firstName,memberNo);
        controller.tableView.setItems(controller.initiate());
        controller.tableView.setTableMenuButtonVisible(true);


        String styles =

              "-fx-border-color:  yellow" ;

        controller.tableView.setStyle(styles);



        Label label = new Label("Member List");
        label.setFont(Font.font("verdana", FontWeight.BOLD, 30));


        label.setMinWidth(300);
        HBox labelBox = new HBox(10);
        labelBox.getChildren().add(label);
        labelBox.setOpaqueInsets(new Insets(10, 10, 10, 10));
        labelBox.setFillHeight(true);


        VBox root = new VBox();
        root.setStyle(styles);
        root.setBackground(new Background(new BackgroundFill(Color.BISQUE, CornerRadii.EMPTY, Insets.EMPTY)));
        controller.tableView.setBackground(new Background(new BackgroundFill[100]));

VBox tableBox=new VBox(10);

tableBox.setPadding(new Insets(50,10,10,10));

tableBox.getChildren().addAll(adddshdel);

/**
 * Text area where Detail info about selected members display
 * */
        Label basicLabel = new Label("Basic Members");
      controller.basicMember.setStyle("-fx-border-color: white");


        Label silverLabel = new Label("Silver Members");
        controller.silverMember.setStyle("-fx-background-color: silver");
        Label goldLabel = new Label("Gold Members");
        controller.goldMember.setStyle("-fx-background-color: gold");


        root.getChildren().addAll(tableBox,controller.tableView, basicLabel,controller.basicMember,silverLabel,
              controller.silverMember,goldLabel,controller.goldMember); //Add tableView to VBox

        /**
         *
         * Delete Button
         * */

        //Delete Button
        Button delete = new Button("Delete");
        //set on action
        delete.setOnAction(d ->

           controller.onDeleteClicked());

        HBox hBox = new HBox();
/***
 * MENU
 * */


        MenuItem seeDetail;
        MenuItem deleteMember;
        MenuItem registerPoint;
        ContextMenu contextMenu;


        seeDetail = new MenuItem("See Detail");

        deleteMember = new MenuItem(" Remove");
        registerPoint = new MenuItem("Register Point");
        contextMenu = new ContextMenu();

        contextMenu.getItems().addAll(seeDetail, deleteMember, registerPoint);


        surname.setContextMenu(contextMenu);

        controller.tableView.setOnMouseClicked(click -> {
            if (click.getButton() == MouseButton.SECONDARY) {
                TableClass selectedItem = controller.tableView.getSelectionModel().getSelectedItem();
                try {

                    if (!selectedItem.surname.equals("")) {

                        contextMenu.show(controller.tableView, click.getSceneX(), click.getSceneY());
                        deleteMember.setOnAction(remove ->controller.onDeleteClicked());
                        registerPoint.setOnAction(reg ->controller.onRegisterClicked());
                    seeDetail.setOnAction(see->controller.onSeeDetail());


                    }

                } catch (NullPointerException e) {
                  System.out.println(e.getMessage());
                }
            }
        });



        /**
         * set Up stage and scene
         *
         * */

        root.setSpacing(5);
        root.setPadding(new Insets(10, 10, 10, 10));
        root.getChildren().addAll(hBox);

        primaryStage.setTitle("AirLine Bonus App");
        primaryStage.setScene(new Scene(root, 700, 700));
        primaryStage.show();


        /*********
         *
         * Login page
         *
         *
         */


        Label usernameL = new Label("Username:  ");
        Label passL = new Label("Password:  ");
        Button log =new Button("   Login ");
        log.setMaxSize(700,50);


        TextField username = new TextField();

        username.setPromptText("Username");

        PasswordField pass = new PasswordField();



        pass.setPromptText("********");
        GridPane gridPane = new GridPane();

        gridPane.add(usernameL,0,0);
        gridPane.add(username,1,0);


        gridPane.add(passL,0,4);

        gridPane.add(pass,1,4);
        gridPane.add(log,1,5);



        gridPane.setVgap(10);
        HBox rootLog = new HBox(10);
        rootLog.getChildren().addAll(gridPane);
       rootLog.setBackground(new Background(new BackgroundFill(Color.PALEGOLDENROD,CornerRadii.EMPTY,Insets.EMPTY)));
    rootLog.setPadding(new Insets(100,10,10,10));

    rootLog.setAlignment(Pos.CENTER);

        Scene scene = new Scene(rootLog,500,500);
        Stage stageLog = new Stage();
        stageLog.setTitle("Login page");

        stageLog.setScene(scene);
        rootLog.setBackground(new Background(new BackgroundFill(Color.AZURE,CornerRadii.EMPTY,Insets.EMPTY
        )));


        primaryStage.close();
log.setOnAction(e->{

    if(username.getText().equals("bako") && pass.getText().equals("45548979")){
    primaryStage.show();
    stageLog.close();

    usernameL.setFont(Font.font("Arial",20));
        passL.setFont(Font.font("Times New Roman",25));

    }else {

        pass.setText("password");

        Text message = new Text();
        username.setStyle("-fx-border-color: #ff2c1a");


        pass.setStyle("-fx-border-color: red");
        message.setText(" Username and/or password is incorrect");
        message.setStroke(Color.RED);
        message.setFont(Font.font("Times New Roman",20));
        gridPane.add(message,1,6);
        message.setLineSpacing(10);

    }
});
        stageLog.show();











    }


    public static void main(String[] args) {
        launch(args);
    }










}
