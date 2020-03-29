package sample;

import java.time.LocalDate;

/**
 * The class inherits BonusMember class
 *
 * */
public class SilverMember extends BonusMember {
  //  int bonusPoints;

    public SilverMember(int memberNo, Personals personals, LocalDate enrolledDate,int bonusPoints) {
        super(memberNo, personals, enrolledDate);
       super.registerPoints(bonusPoints);

    }



    /**
 * Overrides the method registerPoints
 * @param bonusPoints bonusPoints to be registered
 * Since its Silver member the point is calculated by multiplying a given bonusPoints
 *                    by FACTOR_SILVER which is 1.2
 */

    @Override
    public void registerPoints(int bonusPoints) {
        bonusPoints*=FACTOR_SILVER;

        super.registerPoints(bonusPoints);

    }
    /*public int getBonusPoints() {
        return super.getBonusPoints();
    }*/
}
