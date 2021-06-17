package com.example.demo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

@RestController
public class ProductRequestedServiceDaoController {
	
	
	@GetMapping("/ProductsApplicationsDAO/{owner}")
	public List getProductApplications(@PathVariable String owner) {
		
		
		HashMap<String,List<ProductRequestedDao>> map = new HashMap<String,List<ProductRequestedDao>>();
		
		
		
		ArrayList response=new ArrayList();
		
		CassandraConnector client = new CassandraConnector();
		  client.connect(TypeConstants.CASSANDRA_SERVER, TypeConstants.CASSANDRA_PORT);
		  Session session = client.getSession();
		  
		  PreparedStatement prepared = session.prepare(
  			  "select owner,requester,namerequester,"+
		  " product,price,objectid,daterequested,"+
  		  " nameowner,idconversation from "+TypeConstants.NAMESPACE+"product_requested "+
		  " where owner=?");

  			//BoundStatement bound = prepared.bind(airport);
		        
		  BoundStatement bound = prepared.bind(owner);
  			ResultSet rset = session.execute(bound);
  			if(rset!=null) {
  				
  				Iterator<Row> itr =rset.iterator();
  				
  				List<ProductRequestedDao> list; 
  				
  				while(itr.hasNext()) {
  					
  					ProductRequestedDao dao = new ProductRequestedDao();
  					Row row = itr.next();
  					dao.setOwner(row.getString(0));
  					dao.setRequester(row.getString(1));
  					dao.setNamerequester(row.getString(2));
  					dao.setProduct(row.getString(3));
  					dao.setPrice(row.getDouble(4));
  					dao.setObjectid(row.getLong(5));
  					dao.setDaterequested(row.getString(6));
  					dao.setNameowner(row.getString(7));
  					dao.setIdConversation(row.getString(8));

  					if(map.containsKey(dao.getProduct()))
  						list = map.get(dao.getProduct());
  					else
  						list = new ArrayList<ProductRequestedDao>();
  					
  					list.add(new ProductRequestedDao(dao));
  					map.put(dao.getProduct(), list);
  					
  				
  					
  					
  				}
  			}
  			
  			Iterator<Entry<String,List<ProductRequestedDao>>>itr= map.entrySet().iterator();
  			
  			while(itr.hasNext()) {
  				Map.Entry<String,List<ProductRequestedDao>> entry =itr.next();
  				String product = entry.getKey();
  				List<ProductRequestedDao> list = entry.getValue();
  				//ProductRequestsDao dao = new ProductRequestsDao();
  				//dao.setProduct(product);
  				//dao.setList(list);
  				//response.add(new ProductRequestsDao(dao));
  				Group group = new Group();
  				group.setGroup(product);
  				
  				Iterator<ProductRequestedDao> itr2 = list.iterator();
  				
  				int cont=0;
  				while(itr2.hasNext()) {
  					ProductRequestedDao request = itr2.next();
  					
  					
  					if(cont==0) {
  						cont++;
  						group.setObjectid(request.getObjectid());
  						group.setOwner(request.getOwner());
  						//group.setPrice(request.getPrice());
  						//group.setRequester(request.getRequester());
  						response.add(group);
  						
  					}
  					
  					response.add(request);
  					
  				}
  				
  				
  				
  			}
  			
  			
  			
  			session.close();
		      client.close();
		      
		      System.err.println("Map Size:"+map);
		      
		      
	
		return response;
	}
	
	
	
	@GetMapping("/ProductsRequestedDAO/{requester}")
	public List getProductRequested(@PathVariable String requester) {
		
		
HashMap<String,List<ProductRequestedDao>> map = new HashMap<String,List<ProductRequestedDao>>();
		
		
		
		ArrayList response=new ArrayList();
		
		CassandraConnector client = new CassandraConnector();
		  client.connect(TypeConstants.CASSANDRA_SERVER, TypeConstants.CASSANDRA_PORT);
		  Session session = client.getSession();
		  
		  PreparedStatement prepared = session.prepare(
  			  "select owner,requester,namerequester,"+
		  " product,price,objectid,daterequested,"+
  		  " nameowner,idconversation from "+TypeConstants.NAMESPACE+"product_requested "+
		  " where requester=?");

  			//BoundStatement bound = prepared.bind(airport);
		        
		  BoundStatement bound = prepared.bind(requester);
  			ResultSet rset = session.execute(bound);
  			if(rset!=null) {
  				
  				Iterator<Row> itr =rset.iterator();
  				
  				List<ProductRequestedDao> list; 
  				
  				while(itr.hasNext()) {
  					
  					ProductRequestedDao dao = new ProductRequestedDao();
  					Row row = itr.next();
  					dao.setOwner(row.getString(0));
  					dao.setRequester(row.getString(1));
  					dao.setNamerequester(row.getString(2));
  					dao.setProduct(row.getString(3));
  					dao.setPrice(row.getDouble(4));
  					dao.setObjectid(row.getLong(5));
  					dao.setDaterequested(row.getString(6));
  					dao.setNameowner(row.getString(7));
  					dao.setIdConversation(row.getString(8));

  					if(map.containsKey(dao.getNameowner()))
  						list = map.get(dao.getNameowner());
  					else
  						list = new ArrayList<ProductRequestedDao>();
  					
  					list.add(new ProductRequestedDao(dao));
  					map.put(dao.getNameowner(), list);
  					
  				
  					
  					
  				}
  			}
  			
  			Iterator<Entry<String,List<ProductRequestedDao>>>itr= map.entrySet().iterator();
  			
  			while(itr.hasNext()) {
  				Map.Entry<String,List<ProductRequestedDao>> entry =itr.next();
  				String nameowner = entry.getKey();
  				List<ProductRequestedDao> list = entry.getValue();

  				
  				Iterator<ProductRequestedDao> itr2 = list.iterator();
  				
  				int cont=0;
  				
  				while(itr2.hasNext()) {
  					ProductRequestedDao request = itr2.next();
  					
  					if(cont==0) {
  						cont++;
  						Group group = new Group();
  						group.setObjectid(request.getObjectid());
  						group.setOwner(request.getOwner());
  						
  						group.setGroup(nameowner);
  						
  						response.add(group);
  						
  					}
  					
  					response.add(request);
  				}
  				
  			}
  			
  			
  			
  			session.close();
		      client.close();
		      
		      System.err.println("Map Size:"+map);
		      
		      
	
		return response;
	}
	
	
	/*@GetMapping("/setProductRequestEventDAO/{objectid}/{requester}/{typevent}")
	public void setContactRecordDao(@PathVariable String objectid, @PathVariable String requester,
			@PathVariable String typevent) {
		
		
		CassandraConnector client = new CassandraConnector();
		  client.connect(TypeConstants.CASSANDRA_SERVER, TypeConstants.CASSANDRA_PORT);
		  Session session = client.getSession();
		  
		  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
          LocalDateTime now = LocalDateTime.now();
          
          String query=" update "+TypeConstants.NAMESPACE+"product_requested ";
          

          if(typevent.equalsIgnoreCase(TypeConstants.PRODUCT_REQUESTED_RED)) {
        	  query += " set red=true, datered=?";
        	  query += " where objectid=? and requester=?";
          }else if(typevent.equalsIgnoreCase(TypeConstants.PRODUCT_REQUESTED_SOLD)) {
        	  query += " set sold=true, datesold=?";
        	  query += " where objectid=?";
          }
          
		  
		  PreparedStatement prepared = session.prepare(query);
  	  BoundStatement bound = prepared.bind(dtf.format(now),Long.parseLong(objectid),requester);
			session.execute(bound);
			
			
			
			session.close();
		      client.close();
		
	}*/
	

}
