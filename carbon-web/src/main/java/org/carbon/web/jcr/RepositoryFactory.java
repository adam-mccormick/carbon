package org.carbon.web.jcr;

import org.modeshape.jcr.ModeShapeEngine;
import org.modeshape.jcr.RepositoryConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.io.Resource;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.jcr.Repository;
import java.util.concurrent.TimeUnit;

/**
 * {@link FactoryBean} which uses ModeShape to provide {@link Repository} instances
 *
 * @author Adam McCormick
 */
public class RepositoryFactory implements FactoryBean<Repository> {

    private static final Logger LOG = LoggerFactory.getLogger(RepositoryFactory.class);
    private static final ModeShapeEngine ENGINE = new ModeShapeEngine();

    private Resource configuration;
    private Repository repository;

    @PostConstruct
    public void start() throws Exception {
        RepositoryConfiguration repositoryConfiguration;
        if (configuration == null) {
            repositoryConfiguration = new RepositoryConfiguration("default");
        }
        else{
            repositoryConfiguration = RepositoryConfiguration.read(configuration.getURL());
        }
        ENGINE.start();
        repository = ENGINE.deploy(repositoryConfiguration);
        ENGINE.startRepository(repositoryConfiguration.getName());
    }

    @PreDestroy
    public void stop() throws Exception {
        try {
            ENGINE.shutdown().get(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            LOG.error("Error while waiting for the ModeShape engine to shutdown", e);
        }
    }

    public Repository getObject() throws Exception {
        return repository;
    }

    public Class<?> getObjectType() {
        return javax.jcr.Repository.class;
    }

    public boolean isSingleton() {
        return true;
    }

    public void setConfiguration( Resource configuration ) {
        this.configuration = configuration;
    }
}
