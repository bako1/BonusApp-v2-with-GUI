import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;


/**
 * The class inherits Bonus member Class
 * SILVER_LIMIT lower limit(which 25000) to be qualified to silver member
 * GOLD_LIMIT lower limit/minimum earned points to be qualified to gold member
 * Used secure Random generator which is more secure than Random generator
 * */
public class MemberArchive{
    static final int SILVER_LIMIT = 25000;
    static final int GOLD_LIMIT = 75000;
    private SecureRandom randomGenerator;
    private HashMap<Integer, BonusMember> bonusMemberMap;
    private BasicMember basicMember;
    private SilverMember silverMember;
    
     

    public MemberArchive(){
        randomGenerator=new SecureRandom();
        bonusMemberMap =new HashMap<>();
        basicMember=new BasicMember(22,new Personals("Abdi","Bako",
                "" + "ab@gmail.com","8976"),LocalDate.now());

    }
/**
 *Generates random number between 10000000 and 99999999 until new number is unique
 * @return returns a unique random number that can be used later as a member number
 * */
    public int findAvailableNumber(){

        int randomNo=10000000 + randomGenerator.nextInt(89999999);
        while (randomNo==basicMember.getMemberNo()) {
            randomNo= 10000000 + randomGenerator.nextInt(99999999);
        }
        return randomNo;}
/**
 * @param personals personal object
 * @param dateEnrolled enrolled date
 * @return returns member number of a user                    
 * 
 * */
    public int addMember(Personals personals, LocalDate dateEnrolled){
       basicMember=new BasicMember(findAvailableNumber(),personals,dateEnrolled);
        bonusMemberMap.put(basicMember.getMemberNo(),basicMember);
        return basicMember.getMemberNo();
    }
/**
 * @return returns collection bonusMemberMap for lateral use
 * */
    public HashMap<Integer,BonusMember>displayMembers() {
        return bonusMemberMap;
    }
/** checks whether memberNo exists and register points if it exists
 * @return returns true if memberNo exists false otherwise 
 *
 * */

    public boolean registerPoints(int memberNo, int points){

        BonusMember bm = bonusMemberMap.get(memberNo);
        if (bm != null) bm.registerPoints(points);
        else return false;


        return true;
        /*
        boolean  foundMember= bonusMemberMap
                .entrySet()
              .stream()
              .filter(memberNum->memberNum.getKey()==memberNo)
              .count()>0; //count >0 if member number exits.
        if(foundMember){
            bonusMemberMap.get(memberNo).registerPoints(points);
            System.out.printf("  successfully added to a member " +memberNo );

            return true;
            }else
        {
            System.out.println(memberNo+"  invalid member number");
return false;
        }*/


    }

    /**
    @param memberNo member number of a user
     @param password password of a user
     Loops through all members and check whether memberNo exists
     and correct password is given
     @return returns bonus member if member found and correct password
     is provided -2 otherwise
    * 
    **/
    public int findPoints(int memberNo,String password){
  int earnedPoints =-2;
    for(BonusMember bonus: bonusMemberMap.values()){
        if(bonus.getMemberNo()==memberNo
        && bonus.okPassword(password))
            if(bonus instanceof SilverMember)
            { bonusMemberMap.values()
                    .forEach(a->a.getBonusPoints());
            earnedPoints=bonus.getBonusPoints();
            }else if (bonus instanceof GoldMember){
                bonusMemberMap.entrySet().stream()
                        .forEach(a->a.getValue().getBonusPoints());
                earnedPoints=bonus.getBonusPoints();
            }else if(bonus instanceof BasicMember)
            {
                bonusMemberMap.entrySet()
                        .forEach(a->a.getValue().getBonusPoints());
                earnedPoints=bonus.getBonusPoints();
            }
    }
     return earnedPoints; }
    
/**
 * @param dateToday today's date
 *It loops through all members and upgrades the members
 * according to their bonus point and enrolled date, i.e with in a year
 *for example
 *   members with less than  25000 bonus points are Basic
 *   members with between 25000 and 75000 bonus points
 *           and are less than a year since enrolled date  are Silver
 *   members with at least 75000 bonus points
 *  *           and are less than a year since enrolled date
 *                  are gold members
 *
 *
 * 
 * **/
    public void checkMembers(LocalDate dateToday) {
        Iterator<BonusMember> it=bonusMemberMap.values().iterator();
        while (it.hasNext()){

            BonusMember b=it.next();
            if(b instanceof BasicMember){
                if(b.findQualificationPoints(LocalDate.now())>=SILVER_LIMIT){
                    bonusMemberMap.put(b.getMemberNo(),
                             new SilverMember(b.getMemberNo(),b.getPersonals(),
                                    b.getEnrolledDate(), b.getBonusPoints()));
                    if(b.findQualificationPoints(LocalDate.now())>=GOLD_LIMIT){
                        bonusMemberMap.put(b.getMemberNo(),
                                new GoldMember(b.getMemberNo(),b.getPersonals(),
                                        b.getEnrolledDate(),b.getBonusPoints()));
                    }

                }
            }else if(b instanceof SilverMember && b.findQualificationPoints(LocalDate.now())>=GOLD_LIMIT){

                bonusMemberMap.put(b.getMemberNo(),
                        new GoldMember(b.getMemberNo(),b.getPersonals(),
                                b.getEnrolledDate(),b.getBonusPoints()));
            }
        }
       /* bonusMemberMap.values().forEach(member->{
            if(member instanceof BasicMember){
                if(member.findQualificationPoints(LocalDate.now())>=0
                        && member.findQualificationPoints(LocalDate.now())<SILVER_LIMIT)
                       // && member.getBonusPoints()<GOLD_LIMIT)
                {

                    bonusMemberMap.put(member.getMemberNo(),
                            new SilverMember(member.getMemberNo(),member.getPersonals(),
                                    member.getEnrolledDate(),member.getBonusPoints()));
                }
                else if(member.getBonusPoints()>=GOLD_LIMIT
                        && member.findQualificationPoints(LocalDate.now())>0){
                    new GoldMember(member.getMemberNo(),member.getPersonals(),
                                   member.getEnrolledDate(),member.getBonusPoints());

                }

            }else if(member instanceof SilverMember) {

                if (member.getBonusPoints() >= GOLD_LIMIT
                        && member.findQualificationPoints(LocalDate.now()) > 0) {
                    bonusMemberMap.put(member.getMemberNo(),
                            new GoldMember(member.getMemberNo(), member.getPersonals(),
                                    member.getEnrolledDate(), member.getBonusPoints()));
                }

            }


        });*/

    }

}


