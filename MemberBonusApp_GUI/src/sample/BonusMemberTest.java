package sample;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.logging.log4j.core.LoggerContext;

import java.time.LocalDate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import org.junit.jupiter.api.Test;


class BonusMemberTest{

  private static Logger logger = LogManager.getLogger(BonusMemberTest.class);



  private LocalDate testDate;
  private Personals ole;
  private Personals tove;

  @org.junit.jupiter.api.BeforeEach
  void setUp() {
    LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
    //File file = new File("app.log");
    //System.out.println("Name\t"+file);
    //context.setName("properties/log4j2.properties");


    this.testDate = LocalDate.of(2008, 2, 10);
    this.ole = new Personals("Olsen", "Ole",
        "ole.olsen@dot.com", "ole");
    this.tove = new Personals("Hansen", "Tove",
        "tove.hansen@dot.com", "tove");
  }

  @Test
  void testBasicMemberOle() {








    BasicMember b1 = new BasicMember(100, ole,
        LocalDate.of(2006, 2, 15));
    b1.registerPoints(30000);
    logger.info("Test nr 1: No qualification points");
    assertEquals(0, b1.findQualificationPoints(testDate));
    assertEquals(30000, b1.getBonusPoints());

    logger.info("Test nr 2: Adding 15 000 points, still no qualification points");
    b1.registerPoints(15000);
    assertEquals(0, b1.findQualificationPoints(testDate));
    assertEquals(45000, b1.getBonusPoints());
  }

  /**
   * constructor test

   * **/
  @Test
  public void testInvalidParameterTest(){
    try {
      BonusMember bm = new BasicMember(12,null,null);
      logger.info("fail parameter(s)");
    }catch (IllegalArgumentException e){

    };
  }

  /**
   * Tests the accuracy of the calculation of points for the absic member Tove,
   * who was registered with basic membership less than 365 days before 10/2-2008,
   * and hence does qualify for an upgrade, first to Silver, then to Gold.
   */
  @Test
  void testBasicMemberTove() {


    BasicMember b2 = new BasicMember(110, tove,
        LocalDate.of(2007, 3, 5));
    b2.registerPoints(30000);

    logger.info("Test nr 3: Tove should qualify");
    assertEquals(30000, b2.findQualificationPoints(testDate));
    assertEquals(30000, b2.getBonusPoints());

    logger.info("Test nr 4: Tove as silver member");
    SilverMember b3 = new SilverMember(b2.getMemberNo(), b2.getPersonals(),
        b2.getEnrolledDate(), b2.getBonusPoints());
    b3.registerPoints(50000);
    assertEquals( 90000, b3.findQualificationPoints(testDate));
    assertEquals( 90000, b3.getBonusPoints());

    logger.info("Test nr 5: Tove as gold member");
    GoldMember b4 = new GoldMember(b3.getMemberNo(), b3.getPersonals(),
        b3.getEnrolledDate(), b3.getBonusPoints());
    b4.registerPoints(30000);
    assertEquals( 135000, b4.findQualificationPoints(testDate));
    assertEquals( 135000, b4.getBonusPoints());

    logger.info("Test nr 6: Changed test date on Tove");
    testDate = LocalDate.of(2008, 12, 10);
    assertEquals( 0, b4.findQualificationPoints(testDate));
    assertEquals( 135000, b4.getBonusPoints());

  }


  @Test
  void testPasswords() {
    logger.error("Test nr 7: Trying wrong password on Ole");
    assertFalse(ole.okPassword("000"));
    logger.info("Test nr 8: Trying correct password on Tove.");
    assertTrue(tove.okPassword("tove"));
  }


}