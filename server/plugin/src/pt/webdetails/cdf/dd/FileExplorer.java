/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/. */

package pt.webdetails.cdf.dd;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.pentaho.platform.api.engine.IPentahoSession;
import org.pentaho.platform.api.engine.ISolutionFile;

import pt.webdetails.cpf.repository.RepositoryAccess;
import pt.webdetails.cpf.repository.RepositoryAccess.FileAccess;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class FileExplorer {
	
	private static FileExplorer fileExplorer = null;
	//private static Log logger = LogFactory.getLog(FileExplorer.class);
	
	static FileExplorer getInstance() {
		if(fileExplorer == null) {
			fileExplorer = new FileExplorer();
		}
		return fileExplorer;
	}

	
	public String toJQueryFileTree(String baseDir, ISolutionFile[] files) {
	  StringBuilder out = new StringBuilder();
      out.append("<ul class=\"jqueryFileTree\" style=\"display: none;\">");
      
      for (ISolutionFile file : files) {
          if (file.isDirectory()) {
              out.append("<li class=\"directory collapsed\"><a href=\"#\" rel=\"" + baseDir + file.getFileName() + "/\">"+ file.getFileName() + "</a></li>");
          }
      }
      
      for (ISolutionFile file : files) {
          if (!file.isDirectory()) {
              int dotIndex = file.getFileName().lastIndexOf('.');
              String ext = dotIndex > 0 ? file.getFileName().substring(dotIndex + 1) : "";
              out.append("<li class=\"file ext_" + ext + "\"><a href=\"#\" rel=\"" + baseDir + file.getFileName() + "\">"+ file.getFileName() + "</a></li>");
          }
      }
      out.append("</ul>");
      return out.toString();
	}
	
	public String getJqueryFileTree(final String dir, final String fileExtensions, final String access,  IPentahoSession userSession){
	  ISolutionFile[] files = RepositoryAccess.getRepository(userSession).getFileList(dir, fileExtensions, access, userSession);
	  return toJQueryFileTree(dir, files);
	}
  
  public String toJSON(String baseDir, ISolutionFile[] files) {
    
      JSONArray arr = new JSONArray();
    
      for (ISolutionFile file : files) {
          JSONObject json = new JSONObject();
          json.put("path" , baseDir);
          json.put("name" , file.getFileName() );
          json.put("label", file.getFileName() );
          
          if (file.isDirectory()) {
              json.put("type", "dir");
          } else{
              int dotIndex = file.getFileName().lastIndexOf('.');
              String ext = dotIndex > 0 ? file.getFileName().substring(dotIndex + 1) : "";
              json.put("ext" , ext);
              json.put("type" , "file");
          }
          arr.add(json);
      }

      return arr.toString();
	}
	
	public String getJSON(final String dir, final String fileExtensions, final String access,  IPentahoSession userSession){
	  ISolutionFile[] files = RepositoryAccess.getRepository(userSession).getFileList(dir, fileExtensions, access, userSession);
    
	  return toJSON(dir, files);
	}


}
