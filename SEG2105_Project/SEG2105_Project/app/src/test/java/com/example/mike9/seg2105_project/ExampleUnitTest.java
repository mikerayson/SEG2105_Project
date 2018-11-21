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

    private ServiceInformation service;
    private SignUp signUpTest;

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
    }
}
