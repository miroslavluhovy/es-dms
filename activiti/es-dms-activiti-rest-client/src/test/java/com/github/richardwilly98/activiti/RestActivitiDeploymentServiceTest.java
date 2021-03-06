package com.github.richardwilly98.activiti;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.github.richardwilly98.activiti.rest.EsDmsServerWithRestActivitiServerBase;
import com.github.richardwilly98.activiti.rest.api.RestDeployment;
import com.github.richardwilly98.activiti.rest.api.RestSearchResult;
import com.github.richardwilly98.activiti.rest.client.RestDeploymentServiceClient;
import com.github.richardwilly98.esdms.services.ProcessServiceProvider;

public class RestActivitiDeploymentServiceTest extends EsDmsServerWithRestActivitiServerBase {

    public RestActivitiDeploymentServiceTest() throws Exception {
        super();
    }
    
    @Test
    @org.activiti.engine.test.Deployment(resources={"com/github/richardwilly98/activiti/rest/service/repository/oneTaskProcess.bpmn20.xml"})
    public void testGetDeployments() {
        log.debug("*** testGetDeployments ***");
        RestDeploymentServiceClient client = new RestDeploymentServiceClient(getBaseURI(), ProcessServiceProvider.DEFAULT_REST_TIMEOUT);
        client.setToken(adminToken);
        RestSearchResult<RestDeployment> deployments = client.getDeployments();
        log.debug(deployments);
        Assert.assertNotNull(deployments);
        Assert.assertEquals(deployments.getSize(), 1);
        RestDeployment deployment = deployments.getData().get(0);
        Assert.assertNotNull(deployment);
        log.debug(deployment.getId());
        deployment = client.getDeploment(deployment.getId());
        log.debug(deployment);
    }

    @Test
    @org.activiti.engine.test.Deployment(resources={"com/github/richardwilly98/activiti/rest/service/repository/twoTaskProcess.bpmn20.xml"})
    public void testGetDeployments2() {
        log.debug("*** testGetDeployments ***");
        RestDeploymentServiceClient client = new RestDeploymentServiceClient(getBaseURI(), ProcessServiceProvider.DEFAULT_REST_TIMEOUT);
        client.setToken(adminToken);
        RestSearchResult<RestDeployment> deployments = client.getDeployments();
        log.debug(deployments);
        Assert.assertNotNull(deployments);
        Assert.assertEquals(deployments.getSize(), 1);
        RestDeployment deployment = deployments.getData().get(0);
        Assert.assertNotNull(deployment);
        log.debug(deployment.getId());
        deployment = client.getDeploment(deployment.getId());
        log.debug(deployment);
    }
}
