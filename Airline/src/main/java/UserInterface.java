import java.util.Scanner;

public class UserInterface extends ClientApp {
    MemberArchive memberArchive;
    Scanner scanner;
    private static final int ADD_NEW_MEMBER=1;
    private static final int REGISTER_POINTS=2;
    private static final int CHANGE_PASSWORD=3;
    private static final int DISPLAY_MEMBERS=4;
    private static  final int SHOW_EARNED_POINTS=5;
    private static  final int SHOW_QUALIFICATION_POINTS=6;

    private static  final int QUIT=7;


    public UserInterface(){
        scanner=new Scanner(System.in);
        memberArchive = new MemberArchive();
        init();

    }
    public int choices(){

        int choice=0;
        System.out.println("--------------------------------------------");
choice=0;
        while (choice<=0 || choice>6){
            System.out.println(" please enter a number between (1 and 7)");

            choice=readValidInteger();

        }

        return choice;

    }
    private void menu(){
        System.out.println("\n***********AirLine-Bonus App**********");
        System.out.println(" Menu Choice:  ");
        System.out.println("----------------------------------------");
        System.out.println(" Add new member:***********************1");
        System.out.println(" Register points:********************  2");
        System.out.println(" Change Password :*******************  3");
        System.out.println(" Display Members:********************* 4");
        System.out.println(" Show earned points:*******************5");
        System.out.println(" Show qualification points:************6");

        System.out.println("Quit: **********************************7");
    }
    public void menuChoice(){

boolean quit=false;

while (!quit){
    menu();
        switch (choices()) {
            case ADD_NEW_MEMBER:
                addNewMember();
                break;
            case REGISTER_POINTS:
                registerPoint();
                break;
            case CHANGE_PASSWORD:
                changePassword();
                break;
            case DISPLAY_MEMBERS:
                displayAllMembers();
                break;
            case SHOW_EARNED_POINTS:
                findPoints(); break;
            case SHOW_QUALIFICATION_POINTS:
                foundQualification(); break;

                case QUIT:
                System.out.println(" Closing the App...");
                quit=true; break;

        }


        }

    }


}
