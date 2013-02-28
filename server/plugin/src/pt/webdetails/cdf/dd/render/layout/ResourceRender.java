package pt.webdetails.cdf.dd.render.layout;

import org.apache.commons.jxpath.JXPathContext;

public class ResourceRender extends Render {

    public ResourceRender(JXPathContext context) {
        super(context);
    }

    @Override
    public void processProperties() {
    }

    public String renderStart() {
    	
        String out = "";
        
        String resourceType = getPropertyString("resourceType");
        
        if (hasProperty("resourceFile")) {
        	if(resourceType.equals("Css"))
        		out += "<link rel=\"stylesheet\" type=\"text/css\" href=\"" + getPropertyString("resourceFile") +  "\" />\n";
        	else if(resourceType.equals("Javascript")){
        		out += "<script language=\"javascript\" type=\"text/javascript\" src=\"" + getPropertyString("resourceFile") +  "\" ></script>\n";
        	}
        }
        if (hasProperty("resourceCode")) {
        	if(resourceType.equals("Css"))
        		out += "<style>\n<!--\n" + getPropertyString("resourceCode") + "\n-->\n</style>\n";
        	else if(resourceType.equals("Javascript")){
        		out += "<script language=\"javascript\" type=\"text/javascript\" >\n" + getPropertyString("resourceCode") + "\n</script>\n";
        	}
        }

        return out;

    }

    public String renderClose() {

        return "";
    }
}
