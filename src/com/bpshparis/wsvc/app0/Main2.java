package com.bpshparis.wsvc.app0;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;

public class Main2 {
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		
		String vcap_services = System.getenv("VCAP_SERVICES");
		
		try {
			Map<String, Resource> json = (Map<String, Resource>) Tools.fromJSON(vcap_services, new TypeReference<Map<String, Resource>>(){});
			
			List<Resource> resources = Arrays.asList(json.values().toArray(new Resource[0]));
			
			for(Resource resource: resources){
//				System.out.println(resources.getKey());
				if(resource.getService().equalsIgnoreCase("tone-analyzer")) {
					System.out.println(resource.getCredentials().get(0).getApikey());
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}
	
}
