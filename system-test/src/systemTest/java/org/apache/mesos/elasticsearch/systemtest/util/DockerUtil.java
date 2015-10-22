package org.apache.mesos.elasticsearch.systemtest.util;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Docker helper utilities
 */
public class DockerUtil {
    private final DockerClient dockerClient;

    public DockerUtil(DockerClient dockerClient) {
        this.dockerClient = dockerClient;
    }

    public List<Container> getContainers() {
        return dockerClient.listContainersCmd().exec();
    }

    public List<Container> getExecutorContainers() {
        return getContainers().stream().filter(container -> container.getImage().contains("elasticsearch-executor")).collect(Collectors.toList());
    }

    public String getLastExecutorId() {
        return getExecutorContainers().get(0).getId();
    }

    public void killOneExecutor() throws IOException {
        String executorId = getLastExecutorId();
        dockerClient.killContainerCmd(executorId).exec();
    }
}
