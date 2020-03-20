package sample;

import java.time.LocalDate;

/**
 * The class inherits the Class BonusMember
 * All members are BasicMembers initially, Based on the earned points they could be
 * upgraded to either silver or Gold member
 * */
public class BasicMember extends BonusMember{
    public BasicMember(int memberNo, Personals personals, LocalDate enrolledDate) {
        super(memberNo, personals, enrolledDate);
    }
}
