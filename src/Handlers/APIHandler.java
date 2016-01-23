package Handlers;

import Users.User;
import com.ECS.client.jax.*;

import javax.xml.ws.handler.HandlerResolver;
import java.util.List;

public class APIHandler {
    String secretKey = "+pF21JN9zLiD1yxJn3Uoizrk9pASgLvdOxEp+fDC";
    //String awsKey = "AKIAITXMG6UKJZQG4YTA";
    String awsKey = "AKIAJACBVOUFHYMNODOA";
    public List<Items> getSearchResults()
    {
        // Set the service:
        AWSECommerceService service = new AWSECommerceService();

        //Set the service port:
        AWSECommerceServicePortType port = service.getAWSECommerceServicePort();

        //Get the operation object:
        ItemSearchRequest itemRequest = new ItemSearchRequest();
        //Fill in the request object:
        itemRequest.setSearchIndex("Books");
        itemRequest.setKeywords("dog");

        //Not sure what this should be
        //Originally: itemRequest.setVersion("2013-08-01");
        //setVersion function does not exist
        //itemRequest.setReleaseDate("2013-08-01");

        ItemSearch ItemElement= new ItemSearch();
        //ItemElement.setAssociateTag("reco047-20");
        ItemElement.setAWSAccessKeyId(awsKey);
        ItemElement.getRequest().add(itemRequest);
        ItemSearchResponse itemSearchResponse = port.itemSearch(ItemElement);

        return itemSearchResponse.getItems();

    }
    void signUpFacebook (User user)
    {}
    void signUpGoogle(User user)
    {}
}
