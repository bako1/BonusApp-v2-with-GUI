package sample;

import java.time.LocalDate;

public class TableClass extends BonusMember {


    String firstName;
    String surname;
    int memberNo;



    public TableClass(int memberNo, Personals personals, LocalDate enrolledDate, int bonusPoints) {
        super(memberNo, personals, enrolledDate, bonusPoints);
        this.firstName = personals.getFirstName();
        this.surname = personals.getSurname();
        this.memberNo=memberNo;

    }




    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }
//public int getMemberNo(){
  //      return memberNo;
//}


}
