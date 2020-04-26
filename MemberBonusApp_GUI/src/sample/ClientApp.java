
package sample;



import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Inherits class MemberArchive
 * */
public class ClientApp extends MemberArchive {
    private Scanner scanner;
    private BonusMember bonusMember;

    public ClientApp(){
        scanner=new Scanner(System.in);
        new Personals("Ole",
                "Olsen", "olsen@gmail.com", "765");
    }


    public  void init(){

        addMember(new Personals("Ole","Olsen",
                        "olsen@gmail.com","45"),
                LocalDate.of(2019,12,2));
        addMember(new Personals("Marion","Martin",
                        "marion@gmail.com","098765000"),
                LocalDate.of(2019,2,12));
        addMember(new Personals("Eyban","Bako",
                        "eyban@gmail.com","45549090"),
                LocalDate.of(2019,11,28));
        addMember(new Personals("Hisam","Hamid",
                        "hisan@gmail.com","56655665"),
                LocalDate.of(2019,1,1));
    }

    public int readValidInteger(){
        int number=0;

        try {
            number = scanner.nextInt();

        } catch (InputMismatchException e) {
            System.out.println("\tPlease enter only an integer");
            scanner.next();

        }
        return number;

    }

    public void addNewMember(){


        System.out.println(" First Name: ");
        String firstName=scanner.next();

        System.out.println(" Sur Name: ");
        String surName=scanner.next();

        System.out.println(" E-mail address: ");
        String emailAddress=scanner.next();
        while (!emailAddress.contains("@")){
            System.out.println("Invalid e-mail address");
            emailAddress=scanner.next();
        }

        System.out.println(" Password: ");
        String password=scanner.next();
        while (password.length()<8){
            System.out.println(" weak Password! minimum character length is 8:  ");
            password=scanner.next();
        }


        System.out.println(" Enrolled Dte (yy mm dd) : ");
        int year= readValidInteger();
        int month=readValidInteger();
        int day=readValidInteger();

        try {

            while (LocalDate.of(year,month,day).isAfter(LocalDate.now())) {
                System.out.println("Enrollment date cannot be after today: try om " +
                        ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.of(year, month, day)) + "  days");
                year = scanner.nextInt();
                month = scanner.nextInt();
                day = scanner.nextInt();
            }
        }catch (Exception e){
            System.out.println(e.getMessage() + " Invalid format");
        }
        try {
            addMember(new Personals(firstName,surName,emailAddress,password),LocalDate.of(year,month,day));

        }catch (Exception e){
            System.out.println(e+"   Invalid date format entered");
        }

    }
    public void registerPoint(){

        int memberNo=0;
        while (memberNo<=0){
            System.out.println("member Number( ' > 0'): ");
            memberNo = readValidInteger();
        }
        int points=0;
        while (points<=0 ){
            System.out.println(" points to register '( > 0 )': ");
            points =readValidInteger();
            checkMembers(LocalDate.now());

            registerPoints(memberNo,points);

        }
    }

    public void changePassword() {
        System.out.println(" Member No ");
        int memberNo = readValidInteger();
        System.out.println(" Old password: ");
        String oldPassword = scanner.next();
        System.out.println("New Password ");
        String newPassword=scanner.next();
        if(oldPassword.equalsIgnoreCase(newPassword) || newPassword.length()<8 ){
            System.out.println("Some thing wrong happened!!!");
        }else

        {

            displayMembers().entrySet().stream()
                    .filter(pass-> pass.getKey()==memberNo &&
                            pass.getValue().getPersonals()
                                    .changePassword(oldPassword,newPassword) )
                    .forEach(pass-> System.out.println("Password is successfully changed"));
        }


    }
    public void findPoints(){
        System.out.println("Member Number: ");
        int memberNo=0;
        while (memberNo<=0){
            memberNo=readValidInteger();
        }

        System.out.println("password : ");
        String password=scanner.next();

        System.out.println("\tEarned Points:  "+findPoints(memberNo,password));
    }
    /**
     * @param memberNo member number of the user
     * @param dayToday date to day
     * @return qualification returns qualification points  if any
     * or zero
     *
     * */
    private int qualification(int memberNo, LocalDate dayToday){
        int qualification=-1;
        for (BonusMember bm :displayMembers().values()){
            if(bm.getMemberNo()==memberNo){
                qualification= bm.findQualificationPoints(dayToday);
            }
        }
        return qualification;

    }
    public void foundQualification(){
        System.out.println("Member No: ");
        int memberNo=readValidInteger();
        if(qualification(memberNo,LocalDate.now())>=0){
            System.out.println("Qualification Points: " + qualification(memberNo,LocalDate.now()));
        }
        else
            System.out.println("No Member with "+memberNo+" exists");
    }

    public void displayAllMembers(){



        System.out.println("***************** The Members********************");
        System.out.println("\n+--------+-------------------+-------------------+--------" +
                "-----------+--------------+--------------+--------------+--------------+");
        System.out.printf("|%5s%20s%20s%20s%15s%15s%15s%15s","MemberNo|","FName|",
                "LName|","E-mailAddress|","Enrolled|","EarnedPts|","Type|","Qualfic.Points| ");
        System.out.println("\n+--------+-------------------+-------------------+--------" +
                "-----------+--------------+--------------+--------------+--------------+");

        String memberList = null;
        for (BonusMember bm :displayMembers().values())
        {

            System.out.printf("|%5s%20s%20s%20s%15s%15s%15s%15s",bm.getMemberNo()+"|",bm.getPersonals().getFirstName()+"|",
                    bm.getPersonals().getSurname()+'|',
                    bm.getPersonals().getEMailAddress()+'|',
                    bm.getEnrolledDate()+"|",
                    bm.getBonusPoints() +"|", bm.getClass().getName().substring(7)+'|',qualification(bm.getMemberNo(), LocalDate.now())+"|");
            System.out.println("\n+--------+-------------------+-------------------+--------" +

                    "-----------+--------------+--------------+--------------+--------------+");


            memberList = bm.getMemberNo()+"\t"+bm.getPersonals().getFirstName()+"\t"+
                    bm.getPersonals().getSurname()+'\t'+
                    bm.getPersonals().getEMailAddress()+'\t'+
                    bm.getEnrolledDate()+"\t"+
                    bm.getBonusPoints() +"\t"+ bm.getClass().getName()+'\t'+qualification(bm.getMemberNo(), LocalDate.now())+"\n";

        }


    }



}

