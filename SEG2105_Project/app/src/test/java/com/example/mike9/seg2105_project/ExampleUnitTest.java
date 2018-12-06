package com.example.mike9.seg2105_project;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {


    private Timeslot timeTest;
    private UserInformation userInfo;
    private MainActivity main;
    private SPAddService addTest;
    private SPDeleteService delTest;
    private WelcomeScreen userTest;

    @Before
        public void init(){
        timeTest = new Timeslot();
        userInfo = new UserInformation();
        main = new MainActivity();
        addTest = new SPAddService();
        delTest = new SPDeleteService();
        userTest = new WelcomeScreen();

        userInfo.setFirstname("Juliano");
        userInfo.setLastname("Falotico");

        timeTest.setDay("Monday");
        timeTest.setStartHour(8);
        timeTest.setFinishHour(17);
    }

    @Test
    public void testMain(){
        assertNotNull(main);
    }
    @Test
    public void userPage(){
        assertEquals("Juliano",userInfo.getFirstname());
        assertEquals("Falotico",userInfo.getLastname());
    }
    @Test
    public void dayTest(){
        assertEquals("Monday",timeTest.getDay());
    }
    @Test
    public void startTest(){
        assertEquals(8,timeTest.getStartHour());
    }
    @Test
    public void endTime(){
        assertEquals(17,timeTest.getFinishHour());
    }
    @Test
    public void userMain(){
        assertEquals(WelcomeScreen.class,userTest.getClass());
    }
    @Test
    public void addPageTest(){
        assertNotNull(addTest);
    }
    @Test
    public void delPageTest(){
        assertNotNull(delTest);
    }
    @Test
    public void timeToString(){
        assertNotNull(timeTest.toString());
    }
    @Test
    public void userToString(){
        assertNotNull(userInfo.toString());
    }



}


/* OLD LEGACY TESTS

@Before
    public void init(){
        adminScreen = new AdminWelcomeScreen();
        userInfo = new UserInformation();
        userInfo.setFirstname("John");
        userInfo.setLastname("Doe");
    }

    @Test
    public void testUserInfo(){
        assertEquals("John",userInfo.getFirstname());
        assertEquals("Doe",userInfo.getLastname());
    }

    @Test
    public void test2(){
        adminScreen.openAddService();
        assertNotNull(adminScreen);
    }

//////////////////////////////////////////////////////////////////////
 @Before
 public void init(){
 service = new ServiceInformation();
 signUpTest = new SignUp();
 service.setName("Plumbing");
 service.setRate("120");
 }

 @Test
 public void testName(){
 assertEquals("Plumbing",service.getName());

 }
 @Test
 public void testCost(){
 assertEquals("120",service.getRate());
 }

 @Test
 public void testNameEdit() {
 service.setName("Cleaning");
 assertEquals("Cleaning",service.getName());
 }

 @Test
 public void testPriceEdit(){
 service.setRate("80");
 assertEquals("80",service.getRate());
 }
 @Test
 public void testSignUp(){
 //signUpTest.openUserWelcomePage();
 assertNotNull(signUpTest);
 }*/