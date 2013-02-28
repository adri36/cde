/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/. */

package pt.webdetails.cdf.dd.render.cda;

import java.util.HashMap;
import net.sf.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author pdpi
 */
public class Olap4jProperties implements CdaElementRenderer
{

  private JSONObject definition;
  private String paramName = "";

  private HashMap<String,String> names;
  public Olap4jProperties(String paramName)
  {
    this.paramName = paramName;
    names = new HashMap<String,String>();
    names.put("olap4juser","JdbcUser");
    names.put("olap4jpass","JdbcPassword");
    names.put("olap4jurl","Jdbc");
    names.put("olap4jcatalog","Catalog");
    names.put("olap4jdriver","JdbcDrivers");
  }

  public void renderInto(Element dataAccess)
  {
    Document doc = dataAccess.getOwnerDocument();

    Element prop = doc.createElement("Property");
    prop.setAttribute("name",names.get(paramName));
    prop.appendChild(doc.createTextNode((String) definition.get("value")));
    dataAccess.appendChild(prop);
  }

  public void setDefinition(JSONObject definition)
  {
    this.definition = definition;

  }

}
