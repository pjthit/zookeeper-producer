package ejiopeg.example.zookeeper.server.core;

import java.io.IOException;

import org.apache.zookeeper.*;
import org.springframework.stereotype.Component;

@Component
public class ServiceRegistryImpl implements ServiceRegistry, Watcher {
	
	private ZooKeeper zk;	
	private static final int TIMEOUT = 5000;
	private static final String REGISTRY_PATH = "/registry";

	public ServiceRegistryImpl() {
	}
	
	public ServiceRegistryImpl(String zkServers) {
		try {
			zk = new ZooKeeper(zkServers, TIMEOUT, this);
			System.out.println("connected to zookeeper");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void register(String serviceName, String serviceAddress) {
		// TODO Auto-generated method stub
		String registryPath = REGISTRY_PATH;
		try {
			if (zk.exists(registryPath, false) == null){
				zk.create(registryPath, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
				System.out.println("create registry node: " + registryPath);
			}
			
			String servicePath = registryPath + "/" + serviceName;
			if (zk.exists(servicePath, false) == null) {
				zk.create(servicePath, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
				System.out.println("create service node: " + servicePath);
			}
			
			String instancePath = servicePath + "/instance-";
			String instanceNode = zk.create(instancePath, serviceAddress.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
			System.out.println("create instance node: " + instanceNode + ", address: " + serviceAddress);
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void process(WatchedEvent watchedEvent) {
		// TODO Auto-generated method stub
		if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
			System.out.println("......");
		}
	}

}
