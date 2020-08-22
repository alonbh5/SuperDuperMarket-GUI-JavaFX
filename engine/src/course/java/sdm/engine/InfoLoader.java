package course.java.sdm.engine;

import course.java.sdm.exceptions.NoValidXMLException;
import course.java.sdm.generatedClasses.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;


 class InfoLoader {

    private final static String JAXB_XML_SDM_PACKAGE_NAME = "course.java.sdm.generatedClasses";

     static SuperDuperMarketDescriptor UploadFile (String str) throws JAXBException, NoValidXMLException {

        File XmlFile = new File(str);

        //InputStream inputStream = InfoLoader.class.getResourceAsStream(str);

        //if (inputStream == null)
           // throw new NoValidXMLException();

         if (XmlFile.isFile())
              throw new NoValidXMLException();

        SuperDuperMarketDescriptor superDuperMarketDescriptor = null;
       // superDuperMarketDescriptor = deserializeFrom(inputStream);
         superDuperMarketDescriptor = deserializeFrom(XmlFile);

        return superDuperMarketDescriptor;
    }

    private static SuperDuperMarketDescriptor deserializeFrom(InputStream inputStream) throws JAXBException {

        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_SDM_PACKAGE_NAME);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        return (SuperDuperMarketDescriptor) unmarshaller.unmarshal(inputStream);
    }

     private static SuperDuperMarketDescriptor deserializeFrom(File inputStream) throws JAXBException {

         JAXBContext jc = JAXBContext.newInstance(JAXB_XML_SDM_PACKAGE_NAME);
         Unmarshaller unmarshaller = jc.createUnmarshaller();
         return (SuperDuperMarketDescriptor) unmarshaller.unmarshal(inputStream);
     }

}
