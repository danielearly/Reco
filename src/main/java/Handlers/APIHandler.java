package Handlers;

import Users.User;
import com.amazon.advertising.api.sample.SignedRequestsHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.*;


public class APIHandler {
    /*
     * AWS Access Key ID, as taken from the AWS Your Account page.
     */
    private static final String AWS_ACCESS_KEY_ID = "AKIAJACBVOUFHYMNODOA";

    /*
     * AWS Secret Key corresponding to the above ID, as taken from the AWS
     * Your Account page.
     */
    private static final String AWS_SECRET_KEY = "g9wCzjssgZnJ5DvdXL6YkRunBlqbGzH+M0AoH6e+";

    /*
     * AWS Associate Tag corresponding to the above ID, as taken from the AWS
     * Your Account page.
     */
    private static final String AWS_ASSOCIATE_TAG = "reco047-20";

    private static final String AWS_SERVICE = "AWSECommerceService";

    private static final String AWS_VERSION = "2009-03-31";

    /*
     * Use one of the following end-points, according to the region you are
     * interested in:
     *
     *      US: ecs.amazonaws.com
     *      CA: ecs.amazonaws.ca
     *      UK: ecs.amazonaws.co.uk
     *      DE: ecs.amazonaws.de
     *      FR: ecs.amazonaws.fr
     *      JP: ecs.amazonaws.jp
     *
     */
    private static final String ENDPOINT = "ecs.amazonaws.com";

    SignedRequestsHelper helper = null;

    void initializeHelper() {
    /*
         * Set up the signed requests helper
         */
        try {
            helper = SignedRequestsHelper.getInstance(ENDPOINT, AWS_ACCESS_KEY_ID, AWS_SECRET_KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, String> getSearchResults(String input, String category) {
        initializeHelper();
        String requestUrl;
        String title;
        String ASIN;
        HashMap<String, String> products = new HashMap<String, String>();

        Map<String, String> params = new HashMap();
        params.put("Service", AWS_SERVICE);
        params.put("Version", AWS_VERSION);
        params.put("Operation", "ItemSearch");
        params.put("Keywords", input);
        params.put("SearchIndex", category);
        params.put("AssociateTag", AWS_ASSOCIATE_TAG);

        requestUrl = helper.sign(params);
        System.out.println("Signed Request is \"" + requestUrl + "\"");

        ASIN = getItemASIN(requestUrl);

        params = new HashMap();
        params.put("Service", AWS_SERVICE);
        params.put("Version", AWS_VERSION);
        params.put("Operation", "SimilarityLookup");
        params.put("ItemId", ASIN);
        params.put("SearchIndex", category);
        params.put("AssociateTag", AWS_ASSOCIATE_TAG);

        requestUrl = helper.sign(params);

        List<String> titles = getRecommendedTitles(requestUrl);
        List<String> urls = getItemURLs(requestUrl);
        Map<String, String> results = new LinkedHashMap<String, String>();

        for (int i = 0; i < titles.size(); i++) {
            results.put(titles.get(i), urls.get(i));
        }
        return results;
    }

    public List<String> getSearchResultsTitle(String input, String category)
    {
        initializeHelper();
        String requestUrl;
        String title;
        String ASIN;
        List<String> products = new ArrayList();

        Map<String, String> params = new HashMap();
        params.put("Service", AWS_SERVICE);
        params.put("Version", AWS_VERSION);
        params.put("Operation", "ItemSearch");
        params.put("Keywords", input);
        params.put("SearchIndex", category);
        params.put("AssociateTag", AWS_ASSOCIATE_TAG);

        requestUrl = helper.sign(params);
        System.out.println("Signed Request is \"" + requestUrl + "\"");

        title = fetchTitle(requestUrl);
        products.add(title);
        ASIN = getItemASIN(requestUrl);

        params = new HashMap();
        params.put("Service", AWS_SERVICE);
        params.put("Version", AWS_VERSION);
        params.put("Operation", "SimilarityLookup");
        params.put("ItemId", ASIN);
        params.put("SearchIndex", category);
        params.put("AssociateTag", AWS_ASSOCIATE_TAG);

        requestUrl = helper.sign(params);
        products.addAll(getRecommendedTitles(requestUrl));
        return products;
    }

    public List<String> getSearchResultsURLs(String input, String category) {
        initializeHelper();
        List<String> urls = new ArrayList();

        Map<String, String> params = new HashMap();
        params.put("Service", AWS_SERVICE);
        params.put("Version", AWS_VERSION);
        params.put("Operation", "ItemSearch");
        params.put("Keywords", input);
        params.put("SearchIndex", category);
        params.put("AssociateTag", AWS_ASSOCIATE_TAG);

        String requestUrl = helper.sign(params);
        urls.addAll(getItemURLs(requestUrl));

        return urls;
    }

    private static Document getDocumentFromUrl(String requestUrl)
    {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            return db.parse(requestUrl);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String getItemASIN(String requestUrl) {
        String ASIN;
        try {
            Document doc = getDocumentFromUrl(requestUrl);
            Node asinNode = doc.getElementsByTagName("ASIN").item(0);
            ASIN = asinNode.getTextContent();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ASIN;
    }

    private static List<String> getRecommendedTitles(String requestUrl) {
        List<String> titles = new ArrayList();
        try {
            Document doc = getDocumentFromUrl(requestUrl);
            NodeList titleNodes = doc.getElementsByTagName("Title");
            for (int i = 0; i < titleNodes.getLength() && i < 11; ++i)
            {
                titles.add(titleNodes.item(i).getTextContent());
            }
            return titles;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> getItemURLs(String requestUrl) {
        List<String> urls = new ArrayList();
        try {
            Document doc = getDocumentFromUrl(requestUrl);
            NodeList urlNodes = doc.getElementsByTagName("DetailPageURL");
            for (int i = 0; i < urlNodes.getLength() && i < 11; ++i)
            {
                urls.add(urlNodes.item(i).getTextContent());
            }
            return urls;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /*
 * Utility function to fetch the response from the service and extract the
 * title from the XML.
 */
    private static String fetchTitle(String requestUrl) {
        String title;
        try {
            Document doc = getDocumentFromUrl(requestUrl);
            Node titleNode = doc.getElementsByTagName("Title").item(0);
            title = titleNode.getTextContent();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return title;
    }

    void signUpFacebook (User user)
    {}
    void signUpGoogle(User user)
    {}


}
