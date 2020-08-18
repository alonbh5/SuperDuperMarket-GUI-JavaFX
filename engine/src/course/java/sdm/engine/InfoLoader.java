package course.java.sdm.engine;

import course.java.sdm.generatedClasses.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;

public class InfoLoader {

    private final static String JAXB_XML_SDM_PACKAGE_NAME = "course.java.sdm.generatedClasses";

    public static SuperDuperMarketDescriptor TryingFIle (String str)  {

        InputStream inputStream = InfoLoader.class.getResourceAsStream("files1/ex1-small.xml");
        SuperDuperMarketDescriptor superDuperMarketDescriptor = null;
        try {
            superDuperMarketDescriptor = deserializeFrom(inputStream);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return superDuperMarketDescriptor;
    }

    private static SuperDuperMarketDescriptor deserializeFrom(InputStream inputStream) throws JAXBException {

        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_SDM_PACKAGE_NAME);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        return (SuperDuperMarketDescriptor) unmarshaller.unmarshal(inputStream);
    }

}
