package Handlers;

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

    private List<String> urls = new ArrayList<String>();
    private List<String> titles = new ArrayList<String>();
    private List<String> creators = new ArrayList<String>();
    private List<String> asins = new ArrayList<String>();
    private List<String> genres = new ArrayList<String>();
    private List<String> prices = new ArrayList<String>();
    private List<String> rankings = new ArrayList<String>();
    private List<String> images = new ArrayList<String>();

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

    public List<String> getUrls()
    {
        return urls;
    }
    public List<String> getTitles()
    {
        return titles;
    }
    public List<String> getCreators() { return creators;}
    public List<String> getAsins()
    {
        return asins;
    }
    public List<String> getGenres()
    {
        return genres;
    }
    public List<String> getPrices()
    {
        return prices;
    }
    public List<String> getRankings()
    {
        return rankings;
    }
    public List<String> getImages()
    {
        return images;
    }
    //public Map<String, String> getSearchResults(String input, String category) {
    public void getSearchResults(String input, String category) {
        initializeHelper();

        String requestUrl = null;
        urls = new ArrayList<String>();
        titles = new ArrayList<String>();
        creators = new ArrayList<String>();
        asins = new ArrayList<String>();
        genres = new ArrayList<String>();
        prices = new ArrayList<String>();
        rankings = new ArrayList<String>();
        images = new ArrayList<String>();

        Map<String, String> params = new HashMap<String, String>();
        params.put("Service", AWS_SERVICE);
        params.put("Version", AWS_VERSION);
        params.put("Operation", "ItemSearch");
        params.put("Keywords", input);
        params.put("SearchIndex", category);
        params.put("AssociateTag", AWS_ASSOCIATE_TAG);

        requestUrl = helper.sign(params);

        String asin = getItemASIN(requestUrl);

        params = new HashMap<String, String>();
        params.put("Service", AWS_SERVICE);
        params.put("Version", AWS_VERSION);
        params.put("Operation", "SimilarityLookup");
        params.put("ItemId", asin);
        params.put("SearchIndex", category);
        params.put("AssociateTag", AWS_ASSOCIATE_TAG);
        params.put("ResponseGroup", "ItemAttributes,OfferSummary,SalesRank,Images");

        requestUrl = helper.sign(params);
        asins.addAll(getASINsFromRequest(requestUrl));
        titles.addAll(getTitlesFromRequest(requestUrl));
        creators.addAll(getCreatorsFromRequest(requestUrl));
        urls.addAll(getURLsFromRequest(requestUrl));
        genres.addAll(getGenresFromRequest(requestUrl));
        prices.addAll(getPricesFromRequest(requestUrl));
        rankings.addAll(getRankingsFromRequest(requestUrl));
        images.addAll(getImagesFromRequest(requestUrl));
/*
        for(int i = 0; i < 1; ++i)
        {
            params = new HashMap<String, String>();
            params.put("Service", AWS_SERVICE);
            params.put("Version", AWS_VERSION);
            params.put("Operation", "SimilarityLookup");
            params.put("ItemId", asins.get(asins.size() - 1));
            params.put("SearchIndex", category);
            params.put("AssociateTag", AWS_ASSOCIATE_TAG);
            params.put("ResponseGroup", "ItemAttributes,OfferSummary,SalesRank,Images");

            requestUrl = helper.sign(params);

            asins.addAll(getASINsFromRequest(requestUrl));
            titles.addAll(getTitlesFromRequest(requestUrl));
            urls.addAll(getURLsFromRequest(requestUrl));
            genres.addAll(getGenresFromRequest(requestUrl));
            prices.addAll(getPricesFromRequest(requestUrl));
            rankings.addAll(getRankingsFromRequest(requestUrl));
            images.addAll(getImagesFromRequest(requestUrl));
        }

        Map<String, String> results = new HashMap<String, String>(100);

        for (int j = 0; j < titles.size(); j++) {
            //NOTE!!!!!! DO NOT DELETE!!!!!
            //Could possibly implement this like this: results.put(titles.get(j), urls.get(j));
            //this would work but only results with unique titles would be added, this would be
            //clearer, but there is the chance that when finding the ASINs and URLs afterwards
            //they wouldn't be the appropriate ASIN or URL. Worth looking into.
            results.put(urls.get(j), titles.get(j));
        }
        return results;
        */
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


    private List<String> getASINsFromRequest(String requestUrl) {
        List<String> ASINs = new ArrayList<String>();
        try {
            Document doc = getDocumentFromUrl(requestUrl);
            NodeList asinNodes = doc.getElementsByTagName("ASIN");

            for(int i = 0; i < asinNodes.getLength(); ++i)
            {
                if(!ASINs.contains(asinNodes.item(i).getTextContent()))
                    ASINs.add(asinNodes.item(i).getTextContent());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ASINs;
    }


    private static List<String> getTitlesFromRequest(String requestUrl) {
        List<String> titles = new ArrayList<String>();
        try {
                Document doc = getDocumentFromUrl(requestUrl);
                NodeList titleNodes = doc.getElementsByTagName("Title");
                for (int i = 0; i < titleNodes.getLength() && i < 10; ++i) {
                    titles.add(titleNodes.item(i).getTextContent());
                }
            return titles;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> getURLsFromRequest(String requestUrl) {
        List<String> urls = new ArrayList<String>();
        try {
            Document doc = getDocumentFromUrl(requestUrl);
            NodeList urlNodes = doc.getElementsByTagName("DetailPageURL");
            for (int i = 0; i < urlNodes.getLength() && i < 10; ++i)
            {
                if(!urls.contains(urlNodes.item(i).getTextContent()))
                    urls.add(urlNodes.item(i).getTextContent());
            }
            return urls;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private static List<String> getCreatorsFromRequest(String requestUrl) {
        List<String> creators = new ArrayList<String>();
        try {
            Document doc = getDocumentFromUrl(requestUrl);
            NodeList itemAttributes = doc.getElementsByTagName("ItemAttributes");

            for (int i = 0; i < itemAttributes.getLength(); ++i)
            {
                Node attribute = itemAttributes.item(i);
                NodeList attributes = attribute.getChildNodes();

                for (int j = 0; j < attributes.getLength(); ++j)
                {
                    Node attributeNode = attributes.item(j);
                    String attributeName = attributeNode.getNodeName();
                    if (attributeName.equals("Director")) {
                        creators.add(attributeNode.getTextContent());
                        break;
                    } else if (attributeName.equals("Author")) {
                        creators.add(attributeNode.getTextContent());
                        break;
                    } else if (attributeName.equals("Creator")) {
                        creators.add(attributeNode.getTextContent());
                        break;
                    } else if (attributeName.equals("Artist")) {
                        creators.add(attributeNode.getTextContent());
                        break;
                    }
                }
            }
            return creators;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> getGenresFromRequest(String requestUrl) {
        List<String> genres = new ArrayList<String>();
        try {
            Document doc = getDocumentFromUrl(requestUrl);
            NodeList genreNodes = doc.getElementsByTagName("Genre");

            for (int i = 0; i < genreNodes.getLength() && i < 10; ++i) {
                    if (genreNodes.item(i).getTextContent() != null)
                        genres.add(genreNodes.item(i).getTextContent());
            }

            return genres;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> getPricesFromRequest(String requestUrl) {
        List<String> prices = new ArrayList<String>();
        try {
            Document doc = getDocumentFromUrl(requestUrl);
            NodeList priceNodes = doc.getElementsByTagName("FormattedPrice");

            for (int i = 0; i < priceNodes.getLength() && i < 10; ++i)
            {
                if (priceNodes.item(i).getTextContent() != null)
                    prices.add(priceNodes.item(i).getTextContent());
            }

            return prices;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> getRankingsFromRequest(String requestUrl) {
        List<String> rankings = new ArrayList<String>();
        try {
            Document doc = getDocumentFromUrl(requestUrl);
            NodeList rankingNodes = doc.getElementsByTagName("SalesRank");

            for (int i = 0; i < rankingNodes.getLength() && i < 10; ++i)
            {
                if(rankingNodes.item(i).getTextContent() != null)
                    rankings.add(rankingNodes.item(i).getTextContent());
            }

            return rankings;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> getImagesFromRequest(String requestUrl) {
        List<String> images = new ArrayList<String>();
        try {
            Document doc = getDocumentFromUrl(requestUrl);
            NodeList imageSetNodes = doc.getElementsByTagName("ImageSets");
            for (int j = 0; j < imageSetNodes.getLength(); ++j) {
                Node imagesNode = imageSetNodes.item(j).getLastChild();
                String category = imagesNode.getAttributes().getNamedItem("Category").getNodeValue();
                if (category.equals("primary")) {
                    NodeList imageTypeNodes = imagesNode.getChildNodes();
                    for (int k = 0; k < imageTypeNodes.getLength(); ++k) {
                        Node imageType = imageTypeNodes.item(k);
                        String type = imageType.getNodeName();
                        if (type.equals("ThumbnailImage")) {
                            String image = imageType.getFirstChild().getTextContent();
                            images.add(image);
                        }
                    }
                }
            }

            return images;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
