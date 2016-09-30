package de.dentrassi.kura.drools.example1;

import org.eclipse.kura.configuration.ConfigurableComponent;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;

public class DroolsService implements ConfigurableComponent {

    private static final String URL = "http://localhost:8180/kie-server-6.4.0.Final-ee7/services/rest/server";
    private static final String USER = "kieserver";
    private static final String PASSWORD = "kieserver";

    private static final MarshallingFormat FORMAT = MarshallingFormat.JSON;

    public void start() {
        try {
            KieServicesConfiguration conf;
            conf = KieServicesFactory.newRestConfiguration(URL, USER, PASSWORD);
            conf.setMarshallingFormat(FORMAT);
            KieServicesClient client = KieServicesFactory.newKieServicesClient(conf);

            Drools drools = new Drools(client);

            String containerId = System.currentTimeMillis() + "";
            drools.createContainer(containerId, "com.example", "myrules", "0.0.0-SNAPSHOT");
            drools.insert(containerId, "foo");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
