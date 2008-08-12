/*
 * Copyright 2008 Ayman Al-Sairafi ayman.alsairafi@gmail.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License 
 *       at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License.  
 */

package jsyntaxpane.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author subwiz
 */
public class JarServiceProvider {
    
    private static final Logger LOG = Logger.getLogger(JarServiceProvider.class.getName());

    public static List<Object> getServiceProviders(Class cls) throws IOException{
        ArrayList<Object> l = new ArrayList<Object>();
        ClassLoader cl = JarServiceProvider.class.getClassLoader();
        cl = cl == null? ClassLoader.getSystemClassLoader(): cl;
        if(cl != null){
            String serviceFile = "META-INF/services/" + cls.getName();
            Enumeration<URL> e = cl.getResources(serviceFile);
            while(e.hasMoreElements()){
                URL u = e.nextElement();
                InputStream is = u.openStream();
                BufferedReader br = null;
                try{
                    br = new BufferedReader(
                            new InputStreamReader(is, Charset.forName("UTF-8")));
                    String str = null;
                    while((str = br.readLine())!=null){
                        int commentStartIdx = str.indexOf("#");
                        if(commentStartIdx != -1){
                            str = str.substring(0, commentStartIdx);
                        }
                        str = str.trim();
                        if(str.length() == 0){
                            continue;
                        }
                        try{
                            Object obj = cl.loadClass(str).newInstance();
                            l.add(obj);
                        }
                        catch(Exception ex){
                            LOG.warning("Could not load: " + str);
                            LOG.warning(ex.getMessage());
                        }
                    }
                }
                finally{
                    if(br != null){
                        br.close();
                    }
                }
            }
        }
        return l;
    }
}
