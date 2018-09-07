package com.github.theque5t.RundeckWebhookNotificationsPlugin;

import com.dtolabs.rundeck.core.plugins.Plugin;
import com.dtolabs.rundeck.plugins.notification.NotificationPlugin;
import com.dtolabs.rundeck.plugins.descriptions.PluginDescription;
import com.dtolabs.rundeck.plugins.descriptions.PluginProperty;
import java.util.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Plugin(service="Notification",name="RundeckWebhookNotificationsPlugin")
@PluginDescription(title="Rundeck Webhook Notifications Plugin", description="A plugin for sending Rundeck job status notifications via a Webhook.")
public class RundeckWebhookNotificationsPlugin implements NotificationPlugin{

    @PluginProperty(name = "webhookUrl",title = "Webhook URL",description = "The webhook url. Example: https://hostname/services/TXXXXXXXX/XXXXXXXXX/XXXXXXXXXXXXXXXXXXXXXXXX")
    private String webhookUrl;

    @PluginProperty(name = "contentType",title = "Content Type",description = "The content type header. Example: application/json")
    private String contentType;
    
    @PluginProperty(name = "messageBody",title = "Message Body",description = "The message body. Example: {\\\"text\": \\\"Hello, world.\\\"}")
    private String messageBody;
    
    public RundeckWebhookNotificationsPlugin(){

    }

	private String sendMessage(String webhookUrl, String contentType, String messageBody) throws IOException {		
		URL url = new URL(webhookUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setConnectTimeout(5000);
		connection.setReadTimeout(5000);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-type", contentType);
		connection.setDoOutput(true);
		DataOutputStream body = new DataOutputStream(connection.getOutputStream());
		body.write(messageBody.getBytes("UTF-8"));
		body.flush();
		body.close();
		int responseCode = connection.getResponseCode();
		connection.disconnect();
		String result = "The response code is: "+responseCode;
		return result;
	}
    
    public boolean postNotification(String trigger, Map executionData, Map config){
    	try(FileWriter fw = new FileWriter("/tmp/RundeckWebhookNotificationsPlugin.txt", true); 
    		BufferedWriter bw = new BufferedWriter(fw);
    		PrintWriter out = new PrintWriter(bw))
		{
			out.printf("Trigger: %s \n",trigger);
			out.printf("Execution data: %s \n",executionData);
			out.printf("Config: %s \n",config);
	            out.printf("Text string: %s \n",test);
	            String result = sendMessage("https://hostname/services/TXXXXXXXX/XXXXXXXXX/XXXXXXXXXXXXXXXXXXXXXXXX","application/json","{\"text\": \"Hello, world.\"}");
			out.println(result);
		} 
		catch (IOException e) 
		{
			System.out.printf("The exception: %s", e);
		}
        return true;
    }
}