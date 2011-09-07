package org.jahia.services.content.nodetypes.initializers;

import org.jahia.data.templates.JahiaTemplatesPackage;
import org.jahia.registries.ServicesRegistry;
import org.jahia.services.content.nodetypes.ExtendedPropertyDefinition;
import org.jahia.services.content.nodetypes.ValueImpl;
import org.jahia.utils.i18n.JahiaResourceBundle;

import javax.jcr.PropertyType;
import java.util.*;

public class XmlInitializer implements ModuleChoiceListInitializer {
    private String key;

    public List<ChoiceListValue> getChoiceListValues(ExtendedPropertyDefinition epd, String param, List<ChoiceListValue> values, Locale locale, Map<String, Object> context) {

        JahiaResourceBundle rb = new JahiaResourceBundle(null, locale, getTemplatePackageName(epd));

        //Create the list of ChoiceListValue to return
        List<ChoiceListValue> myChoiceList = new ArrayList<ChoiceListValue>();

        if (context == null) {
            return myChoiceList;
        }

        HashMap<String, Object> myPropertiesMap = null;

        //For test purpose we hardcode to value, but we should retrieve value from external, like a web service

        //Choice 1
        myPropertiesMap = new HashMap<String, Object>();
        //Add a special property in the properties map, the addMixin property. Jahia will add the specified mixin when the associated value is selected.
        myPropertiesMap.put("addMixin", "mymix:xmlUri");
        myChoiceList.add(new ChoiceListValue(rb.get("mynt_xmlxslt.xmlUri", "External URI"), myPropertiesMap, new ValueImpl("mymix:xmlUri", PropertyType.STRING, false)));

        //Choice 2
        myPropertiesMap = new HashMap<String, Object>();
        myPropertiesMap.put("addMixin", "mymix:xmlFile");
        myChoiceList.add(new ChoiceListValue(rb.get("mynt_xmlxslt.xmlFile", "File"), myPropertiesMap, new ValueImpl("mymix:xmlFile", PropertyType.STRING, false)));

        //Choice 3
        myPropertiesMap = new HashMap<String, Object>();
        myPropertiesMap.put("addMixin", "mymix:xmlText");
        myChoiceList.add(new ChoiceListValue(rb.get("mynt_xmlxslt.xmlText", "Direct input"), myPropertiesMap, new ValueImpl("mymix:xmlText", PropertyType.STRING, false)));

        //Return the list
        return myChoiceList;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    private String getTemplatePackageName(ExtendedPropertyDefinition definition) {
        String systemId = definition.getDeclaringNodeType().getSystemId();
        if (systemId.equals("system-jahia")) {
            systemId = "Default Jahia Templates";
        }
        final JahiaTemplatesPackage tpkg = ServicesRegistry.getInstance().getJahiaTemplateManagerService()
                .getTemplatePackage(systemId);

        return tpkg != null ? tpkg.getName() : null;
    }

}
