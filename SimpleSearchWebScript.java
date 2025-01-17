package net.madon.tutwebscripts;

import java.io.IOException;

import org.springframework.extensions.webscripts.AbstractWebScript;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.StoreRef;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.LimitBy;
import org.alfresco.service.cmr.search.SearchParameters;

public class SimpleSearchWebScript extends AbstractWebScript
{

    private static final Log LOGGER = LogFactory.getLog(SimpleSearchWebScript.class);

    private final int PAGE_SIZE = 100;
    private static final String USER_SEARCH_QUERY = "TYPE:\"cm:person\"";
    private SearchService searchService;
   
    public void setSearchService(SearchService searchService)
    {
	this.searchService = searchService;
    }

    public void execute(WebScriptRequest req, WebScriptResponse res)
        throws IOException
    {
	try
	    {

		
		int pageNumber = 0;

		List<NodeRef> nodesList = null;
		nodesList = findNodes(pageNumber, USER_SEARCH_QUERY);
		LOGGER.debug(nodesList);

		// see https://www.baeldung.com/java-converting-list-to-json-array
		JSONArray jsonArray = new JSONArray(nodesList);
		
		// build a json object
		// JSONObject obj = new JSONObject();
      
		// put some data on it
		// obj.put("field2", "data2");
      
		// build a JSON string and send it back
		// String jsonString = obj.toString();
		String jsonString = jsonArray.toString();
		res.setContentType("application/json"); // see BulkMetadataGet.java
		res.getWriter().write(jsonString);		
	    }
	catch(JSONException e)
	    {
		throw new WebScriptException("Unable to serialize JSON");
	    }
    }


        /**
     * Executing a paginated query
     *
     * @return the node list
     * @param pageNumber : page number
     * @param query : request
     */
    private List<NodeRef> findNodes(int pageNumber, String query) {
        List<NodeRef> nodesList = null;
        int skipCount = pageNumber * PAGE_SIZE;
        SearchParameters sp = new SearchParameters();
        sp.addStore(StoreRef.STORE_REF_WORKSPACE_SPACESSTORE);
        sp.setLanguage(SearchService.LANGUAGE_FTS_ALFRESCO);
        sp.setQuery(query);
        sp.setLimit(PAGE_SIZE);
        sp.setLimitBy(LimitBy.FINAL_SIZE);
        sp.setSkipCount(skipCount);
        ResultSet results = null;
        try {
          results = searchService.query(sp);
          if (null != results && results.length() > 0) {
            nodesList = results.getNodeRefs();
          }
        } catch (Exception e) {
            LOGGER.error("Fail to find nodes ", e);
        } finally {
          try {
            results.close();
            results = null;
          } catch (Exception e) {
          }
        }
        return nodesList;
    }

}
