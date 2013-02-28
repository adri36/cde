/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/. */

package pt.webdetails.cdf.dd.render.cda;

import java.util.Iterator;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.jxpath.JXPathContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author pdpi
 */
public class Output implements CdaElementRenderer
{

  private JSONObject definition;
  private JXPathContext context;

  public void renderInto(Element dataAccess)
  {
    JSONArray columns = JSONArray.fromObject(definition.getString("value"));
    String mode;
    try
    {
      mode = context.getValue("properties/.[name='outputMode']/value").toString();
    }
    catch (Exception e)
    {
      // If we fail to read the property, it defaults to Inclusive mode.
      mode = "include";
    }
    if (columns.size() == 0)
    {
      return;
    }
    Document doc = dataAccess.getOwnerDocument();
    Element output = doc.createElement("Output");
    output.setAttribute("mode", mode.toLowerCase());
    dataAccess.appendChild(output);
    @SuppressWarnings("unchecked")
    Iterator<String> paramIterator = columns.iterator();
    StringBuilder indexes = new StringBuilder();
    while (paramIterator.hasNext())
    {
      String col = paramIterator.next();
      indexes.append(col);
      if (paramIterator.hasNext())
      {
        indexes.append(",");
      }
    }
    output.setAttribute("indexes", indexes.toString());
  }

  public void setDefinition(JSONObject definition)
  {
    this.definition = definition;
  }

  public void setContext(JXPathContext context)
  {
    this.context = context;
  }
}
