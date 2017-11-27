package com.example.demo;

import java.util.List;

import javax.annotation.PostConstruct;

import org.openstack4j.api.OSClient.OSClientV3;
import org.openstack4j.model.common.Identifier;
import org.openstack4j.model.compute.Flavor;
import org.openstack4j.model.identity.v3.User;
import org.openstack4j.model.image.Image;
import org.openstack4j.model.network.Network;
import org.openstack4j.openstack.OSFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class OpenStackAuthenticate {
	
	@Value("${openstack.user-name}")
	private String userName;

	@Value("${openstack.password}")
	private String password;
	
	@Value("${openstack.host-url}")
	private String openStackHost;
	
	@Bean
	public OSClientV3 authenticate() {
		//use Identifier.byId("domainId") or Identifier.byName("example-domain")
		Identifier domainIdentifier = Identifier.byId("default");

		//unscoped authentication
		//as the username is not unique across domains you need to provide the domainIdentifier
		OSClientV3 os = OSFactory.builderV3()
								.endpoint(openStackHost+":5000/v3")
		                        .credentials(userName,password, domainIdentifier)
		                       .authenticate();
		System.out.println("Open Stack Authentication Successful!!");
		/** Please uncomment if you want to see all images, flavors and networks in the instance
		// List all Images (Glance)
		List<? extends Image> images = os.images().list();
		for (Image image : images) {
			System.out.println(Utility.getResourceIdbyName(image, "centos-6"));
			System.out.println(image);
		}
		
		// Find all Compute Flavors
		List<? extends Flavor> flavors = os.compute().flavors().list();
		for (Flavor flavor : flavors) {
			System.out.println(Utility.getFlavorIdbyName(flavor, "lab.medium"));
			System.out.println(flavor);
		}
		
		// List all Networks
		List<? extends Network> networks = os.networking().network().list();
		for (Network network : networks) {
			System.out.println(Utility.getResourceIdbyName(network, "net-82826"));
			if (network.getName().equals("net-82826")){
				System.out.println(network.getId());
			}
			System.out.println(network);
		}
		
		*/
		return os;
	}

}
