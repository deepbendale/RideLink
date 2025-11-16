package com.rideLink.app.RideLink;

import com.rideLink.app.RideLink.services.EmailSenderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RideLinkApplicationTests {

    @Autowired
    private EmailSenderService emailSenderService;



	@Test
	void contextLoads() {
        emailSenderService.sendEmail(
                "riliro5883@canvect.com",
                "This is testing email",
                "Body of my email"
        );
	}

    @Test
    void sendEmailMultiple() {
       String emails[] = {
               "riliro5883@canvect.com",
               "deepbendale2112@gmail.com",
               "bendaledeep956@gmail.com",
       };
        emailSenderService.sendEmail(
                emails,
                "This is testing email",
                "Body of my email"
        );
    }

}
