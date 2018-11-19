package by.iba.mail.config.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.ArrayList;
import java.util.List;

@Component
public class AddressSplitterator {

    public static final Logger LOGGER = LogManager.getLogger(AddressSplitterator.class);

    public List<Address> getAdressesFromString(String to) {
        String[] springAddresses = to.split(",");
        List<Address> addresses = new ArrayList<>(springAddresses.length);
        for (String springAddress : springAddresses) {
            try {
                addresses.add(new InternetAddress(springAddress));
            } catch (AddressException e) {
                LOGGER.error(e.getMessage());
                e.printStackTrace();
            }
        }
        return addresses;

    }
}
