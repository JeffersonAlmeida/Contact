package rga.contact.test;

import rga.contact.application.list.ContactsListActivity;
import com.robotium.solo.*;
import android.test.ActivityInstrumentationTestCase2;


public class CreateContactTest extends ActivityInstrumentationTestCase2<ContactsListActivity> {
  	private Solo solo;
  	
  	public CreateContactTest() {
		super(ContactsListActivity.class);
  	}

  	public void setUp() throws Exception {
        super.setUp();
		solo = new Solo(getInstrumentation());
		getActivity();
  	}
  
   	@Override
   	public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
  	}
  
	public void testRun() {
        //Wait for activity: 'rga.contact.application.list.ContactsListActivity'
		solo.waitForActivity(rga.contact.application.list.ContactsListActivity.class, 2000);
        //Click on (584.18866, 110.561775)
		solo.clickOnScreen(584.18866F, 110.561775F);
        //Wait for activity: 'rga.contact.application.create.CreateActivity'
		assertTrue("rga.contact.application.create.CreateActivity is not found!", solo.waitForActivity(rga.contact.application.create.CreateActivity.class));
        //Rotate the screen
		solo.setActivityOrientation(Solo.LANDSCAPE);
        //Click on (688.46216, 535.2566)
		solo.clickOnScreen(688.46216F, 535.2566F);
        //Click on Empty Text View
		solo.clickOnView(solo.getView(rga.contact.R.id.name));
        //Enter the text: 'Jefferson Almeida'
		solo.clearEditText((android.widget.EditText) solo.getView(rga.contact.R.id.name));
		solo.enterText((android.widget.EditText) solo.getView(rga.contact.R.id.name), "Jefferson Almeida");
        //Rotate the screen
		solo.setActivityOrientation(Solo.PORTRAIT);
        //Click on (303.57837, 686.4637)
		solo.clickOnScreen(303.57837F, 686.4637F);
        //Click on Empty Text View
		solo.clickOnView(solo.getView(rga.contact.R.id.email));
        //Rotate the screen
		solo.setActivityOrientation(Solo.LANDSCAPE);
        //Drag from (898.2982, 613.14844) to (915.2849, 341.86368)
		solo.drag(898.2982F, 915.2849F, 613.14844F, 341.86368F, 40);
        //Click on (845.8392, 439.88907)
		solo.clickOnScreen(845.8392F, 439.88907F);
        //Click on (239.81265, 417.42026)
		solo.clickOnScreen(239.81265F, 417.42026F);
        //Click on Empty Text View
		solo.clickOnView(solo.getView(rga.contact.R.id.email));
        //Set default small timeout to 16314 milliseconds
		Timeout.setSmallTimeout(16314);
        //Enter the text: 'jefferson.almeida.comp@gmail.com'
		solo.clearEditText((android.widget.EditText) solo.getView(rga.contact.R.id.email));
		solo.enterText((android.widget.EditText) solo.getView(rga.contact.R.id.email), "jefferson.almeida.comp@gmail.com");
        //Rotate the screen
		solo.setActivityOrientation(Solo.PORTRAIT);
        //Click on (516.28296, 772.39655)
		solo.clickOnScreen(516.28296F, 772.39655F);
        //Wait for dialog
		solo.waitForDialogToOpen(5000);
        //Click on (386.46326, 247.8064)
		solo.clickOnScreen(386.46326F, 247.8064F);
        //Drag from (434.39667, 574.55115) to (393.45355, 1017.2053)
		solo.drag(434.39667F, 393.45355F, 574.55115F, 1017.2053F, 40);
        //Drag from (482.3301, 585.54254) to (401.44244, 953.25525)
		solo.drag(482.3301F, 401.44244F, 585.54254F, 953.25525F, 40);
        //Drag from (478.33566, 578.54803) to (451.3731, 565.27765)
		solo.drag(478.33566F, 451.3731F, 578.54803F, 565.27765F, 40);
        //Click on (226.68517, 807.3692)
		solo.clickOnScreen(226.68517F, 807.3692F);
        //Click on (554.2303, 1010.7103)
		solo.clickOnScreen(554.2303F, 1010.7103F);
        //Click on (296.8776, 993.22406)
		solo.clickOnScreen(296.8776F, 993.22406F);
        //Click on Empty Text View
		solo.clickOnView(solo.getView(rga.contact.R.id.bio));
        //Rotate the screen
		solo.setActivityOrientation(Solo.LANDSCAPE);
        //Drag from (928.2748, 564.2164) to (982.56506, 349.84872)
		solo.drag(928.2748F, 982.56506F, 564.2164F, 349.84872F, 40);
        //Click on (863.3146, 654.0952)
		solo.clickOnScreen(863.3146F, 654.0952F);
        //Click on Empty Text View
		solo.clickOnView(solo.getView(rga.contact.R.id.bio));
        //Set default small timeout to 18479 milliseconds
		Timeout.setSmallTimeout(18479);
        //Enter the text: 'Best Soccer Player Ever'
		solo.clearEditText((android.widget.EditText) solo.getView(rga.contact.R.id.bio));
		solo.enterText((android.widget.EditText) solo.getView(rga.contact.R.id.bio), "Best Soccer Player Ever");
        //Press next button
		solo.pressSoftKeyboardNextButton();
        //Rotate the screen
		solo.setActivityOrientation(Solo.PORTRAIT);
        //Click on (684.0499, 108.4153)
		solo.clickOnScreen(684.0499F, 108.4153F);
        //Drag from (406.43552, 981.2334) to (499.75827, 510.58322)
		solo.drag(406.43552F, 499.75827F, 981.2334F, 510.58322F, 40);
        //Click on (361.99725, 1014.7072)
		solo.clickOnScreen(361.99725F, 1014.7072F);
        //Wait for activity: 'rga.contact.application.show.ShowContactActivity'

	}
}
