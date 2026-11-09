
package testcases;

import org.testng.annotations.Test;
import org.testng.Assert;

import dataproviders.LoginDataProvider;

public class DataProviderTest {

    @Test(dataProvider = "loginData", dataProviderClass = LoginDataProvider.class)
    public void verifyLoginData(String username, String password, String expectedResult) {

        System.out.println("--------------------------------");
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        System.out.println("Expected Result: " + expectedResult);
        System.out.println("--------------------------------");

        Assert.assertNotNull(username);
        Assert.assertNotNull(password);
        Assert.assertNotNull(expectedResult);
    }
}
