package de.dentrassi.kura.drools.example1;

import org.kie.api.KieServices;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.api.runtime.ExecutionResults;
import org.kie.server.api.model.KieContainerResource;
import org.kie.server.api.model.ReleaseId;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.RuleServicesClient;

import static org.kie.server.api.model.ServiceResponse.ResponseType.FAILURE;

public class Drools {

    private final KieServicesClient kieServicesClient;

    public Drools(KieServicesClient kieServicesClient) {
        this.kieServicesClient = kieServicesClient;
    }

    public void createContainer(String id, String groupId, String artifactId, String version) {
        KieContainerResource container = new KieContainerResource();
        container.setReleaseId(new ReleaseId(groupId, artifactId, version));
        ServiceResponse<KieContainerResource> createResponse = kieServicesClient.createContainer(id, container);
        if(createResponse.getType() == FAILURE) {
            throw new RuntimeException(createResponse.getMsg());
        }
    }

    public void insert(String container, Object fact) {
        RuleServicesClient rulesClient = kieServicesClient.getServicesClient(RuleServicesClient.class);
        KieCommands commandsFactory = KieServices.Factory.get().getCommands();
        Command<?> insert = commandsFactory.newInsert(fact);
        ServiceResponse<ExecutionResults> executeResponse = rulesClient.executeCommandsWithResults(container, insert);
        if(executeResponse.getType() == FAILURE) {
            throw new RuntimeException(executeResponse.getMsg());
        }
    }

}
