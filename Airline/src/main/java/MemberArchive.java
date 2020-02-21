import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.HashMap;


/**
 * The class inherits Bonus member Class
 * SILVER_LIMIT lower limit(which 25000) to be qualified to silver member
 * GOLD_LIMIT lower limit/minimum earned points to be qualified to gold member
 * */
public class MemberArchive{
    static final int SILVER_LIMIT = 25000;
    static final int GOLD_LIMIT = 75000;
    private SecureRandom randomGenerator;
    private HashMap<Integer, BonusMember> bonusMemberMap;
    private BasicMember basicMember;

    public MemberArchive(){
        randomGenerator=new SecureRandom();
        bonusMemberMap =new HashMap<>();
        basicMember=new BasicMember(22,new Personals("Abdi","Bako",
                "" + "ab@gmail.com","8976"),LocalDate.now());

    }
/**
 *
 * */
    public int findAvailableNumber(){

        int randomNo=10000000 + randomGenerator.nextInt(99999999);
        while (randomNo==basicMember.getMemberNo()) {
            randomNo= 10000000 + randomGenerator.nextInt(99999999);
        }
        return randomNo;}

    public int addMember(Personals personals, LocalDate dateEnrolled){
       basicMember=new BasicMember(findAvailableNumber(),personals,dateEnrolled);
        bonusMemberMap.put(basicMember.getMemberNo(),basicMember);
        return basicMember.getMemberNo();
    }

    public HashMap<Integer,BonusMember>displayMembers() {
        return bonusMemberMap;
    }
/** checks whether memberNo exists and register points if it exists
 * @return returns true if memberNo exists otherwise returns false
 *
 * */

    public boolean registerPoints(int memberNo, int points){

        boolean  foundMember= bonusMemberMap
                .entrySet()
              .stream()
              .filter(a->a.getKey()==memberNo)
              .count()>0; //count >0 if member number exits.
        if(foundMember){
            bonusMemberMap.get(memberNo).registerPoints(points);
            System.out.printf("  successfully added to a member "+memberNo);

            return true;
            }else
        {
            System.out.println(memberNo+"  invalid member number enter 2 to try again.");
return false;
        }
    }

    public int findPoints(int memberNo,String password){
  int earnedPoints =-2;
    for(BonusMember bonus: bonusMemberMap.values()){
        if(bonus.getMemberNo()==memberNo
        && bonus.okPassword(password))

        {
            earnedPoints=bonus.getBonusPoints();
        }
    }
     return earnedPoints;

    }




    public void checkMembers(LocalDate today) {
        bonusMemberMap.values().forEach(member -> {

                if (member instanceof BasicMember) {
                    if (member.getBonusPoints() >= SILVER_LIMIT && member.getBonusPoints() < GOLD_LIMIT) {
                        bonusMemberMap.put(member.getMemberNo(), new SilverMember(member.getMemberNo(), member.getPersonals(),
                                member.getEnrolledDate(), member.getBonusPoints()));
                    }
                    else if(member.getBonusPoints() >= GOLD_LIMIT) {
                        bonusMemberMap.put(member.getMemberNo(), new GoldMember(member.getMemberNo(), member.getPersonals(),
                                member.getEnrolledDate(), member.getBonusPoints()));
                    }
                }
                else if(member instanceof SilverMember) {
                    if(member.getBonusPoints() >= GOLD_LIMIT) {
                        bonusMemberMap.put(member.getMemberNo(), new GoldMember(member.getMemberNo(), member.getPersonals(),
                                member.getEnrolledDate(), member.getBonusPoints()));
                    }
                }

        });
    }

}


