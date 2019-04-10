# Mailbox Analyzer

MailBox Analyzer is an application using [Watson Developer Cloud Java SDK](https://github.com/watson-developer-cloud/java-sdk) to demonstrate how to use the [Watson Developer Cloud services](https://www.ibm.com/watson/products-services/), a collection of REST APIs and SDKs that use cognitive computing to solve complex problems.

<br>

## Table of Contents

- [Application Flow](#application-flow)

- [Setup environment in IBM Cloud](#setup-environment-in-ibm-cloud)
  * [Setup Tone Analyzer service](#setup-tone-analyzer-service)
  * [Setup Natural Language Understanding service](#setup-natural-language-understanding-service)
  * [Setup Visual Recognition service](#setup-visual-recognition-service)  

- [Setup application](#setup-application)
  * [Install needed softwares](#install-needed-softwares)
  * [Check everything is installed properly](#check-everything-is-installed-properly)
  * [Login to IBM Cloud](#login-to-ibm-cloud)  
  * [Install WAS Liberty Kernel](#Install-was-liberty-kernel)
  * [Add application to defaultServer](#add-application-to-defaultserver)
  * [Set environment to access Watson service instances in IBM Cloud](#set-environment-to-access-watson-service-instances-in-ibm-cloud)
  * [Run application](#run-application)
- [Send your own datas for analysis](#send-your-own-datas-for-analysis)
- [Clean your room](#clean-your-room)
- [About Watson Developer Cloud services being used in the application](#about-watson-developer-cloud-services-being-used-in-the-application)
- [About other Watson Developer Cloud services](#about-other-watson-developer-cloud-services)

<br>

<!--
### Overview of the application

A sample demo of the application with a mailbox analysis *may be* available [here](http://ma.bpshparis.eu-de.mybluemix.net).

<br>
-->

### Application Flow
![Flow](images/appFlow.jpg)

<br>

### Setup environment in IBM Cloud

#### Create a credential file

:information_source: Let's store credentials for our 3 services in a json file which our application will use to be granted access.

Choose your favorite text editor, create a new file, paste the following content in it:

```
{
    "0": {
        "credentials": [
          {
        "id": "0",
        "name": "Auto-generated service credentials",
        "apikey": "${TA_APIKEY}",
        "url": "${TA_URL}",
        "role": "Manager"
          }
        ],
        "service": "tone-analyzer",
        "region": "us-south",
        "instance": "Tone Analyzer-fs"
      },
      "1": {
        "credentials": [
          {
        "id": "1",
        "name": "Auto-generated service credentials",
        "apikey": "${NLU_APIKEY}",
        "url": "${NLU_URL}",
        "role": "Manager"
          }
        ],
        "service": "natural-language-understanding",
        "region": "us-south",
        "instance": "Natural Language Understanding-qf"
      },
      "2": {
        "credentials": [
          {
        "id": "2",
        "name": "Auto-generated service credentials",
        "apikey": "${WVC_APIKEY}",
        "url": "${WVC_URL}",
        "role": "Manager"
          }
        ],
        "service": "watson-vision-combined",
        "region": "us-south",
        "instance": "Visual Recognition-gd"
      }
}
```

 and save it as **vcap.json**


![](res/web.png)

Ctrl + Click on [IBM Cloud Catalog](https://console.bluemix.net/catalog/?category=ai)

#### Setup Tone Analyzer service

![](res/ta50x.png) **Tone Analyzer** uses linguistic analysis to detect three types of tones from communications: emotion, social, and language.  This insight can then be used to drive high impact communications.

To instanciate **Tone Analyzer** service click

![](guiScreenShots/ta0.jpg)

Wait for followings panels to be available:

![](guiScreenShots/ta1.jpg)

![](guiScreenShots/ta2.jpg)

Then hit 

![](guiScreenShots/ta3.jpg)

:zzz: When you land on,

![](guiScreenShots/ta4.jpg)

:thumbsup: this mean that the **Tone Analyzer** service as been successfully instantiate.

<!--
To be ready to use  **Tone Analyzer** instance need a new credential to be created. So click on **Service credentials** available on top left under the menu:

![](guiScreenShots/ta5.jpg)

Then click

![](guiScreenShots/ta6.jpg)

Keep default setting

![](guiScreenShots/ta7.jpg)

and hit 

![](guiScreenShots/ta8.jpg)
-->

#### Setup Natural Language Understanding service

![](res/nlu50x.png) **Natural Language Understanding** analyze text to extract meta-data from content such as concepts, entities, emotion, relations, sentiment and more.

To instanciate **Natural Language Understanding** service, go back to [IBM Cloud Catalog](https://console.bluemix.net/catalog/?category=ai) and click

![](guiScreenShots/nlu0.jpg)

Wait for followings panels to be available:

![](guiScreenShots/nlu1.jpg)

![](guiScreenShots/nlu2.jpg)

Then hit 

![](guiScreenShots/nlu3.jpg)

:zzz: When you land on,

![](guiScreenShots/ta4.jpg)

:thumbsup: this mean that the **Natural Language Understanding** service as been successfully instantiate.

#### Setup Visual Recognition service

![](res/wvc50x.png) **Visual Recognition** find meaning in visual content! Analyze images for scenes, objects, faces, and other content. Choose a default model off the shelf, or create your own custom classifier. Develop smart applications that analyze the visual content of images or video frames to understand what is happening in a scene.

To instanciate **Visual Recognition** service, go back to [IBM Cloud Catalog](https://console.bluemix.net/catalog/?category=ai) and click

![](guiScreenShots/wvc0.jpg)

Wait for followings panels to be available:

![](guiScreenShots/wvc1.jpg)

![](guiScreenShots/wvc2.jpg)

Then hit 

![](guiScreenShots/wvc3.jpg)

:zzz: When you land on,

![](guiScreenShots/wvc4.jpg)

:thumbsup: this mean that the **Visual Recognition** service as been successfully instantiate.

> :checkered_flag: You are done with environment setup. Now at least three Watson services should be created.
You can check it in your [IBM Cloud Dashboard](https://console.bluemix.net/dashboard/apps).

<br>



<!--
	ibmcloud catalog search tone
	ibmcloud catalog service tone-analyzer
	ibmcloud resource service-instance-create ta tone-analyzer lite eu-de
	ibmcloud resource service-key-create taKey Manager --instance-name ta
	export TA_APIKEY=$(ibmcloud resource service-key taKey | awk '/^\s*apikey:/ {print $2}') && echo $TA_APIKEY
	export TA_URL=$(ibmcloud resource service-key taKey | awk '/^\s*url:/ {print $2}') && echo $TA_URL
	export TA_METHOD=/v3/tone?version=2017-09-21 && echo $TA_METHOD
	export TA_TEXT="On en a gros !" && echo $TA_TEXT
	jq -n --arg value "$TA_TEXT" '{"text": $value}' | tee ta.req.json | jq .
	curl -X POST -u 'apikey:'$TA_APIKEY -H 'Content-Type: application/json' -H 'Content-Language: fr' -H 'Accept-Language: fr' -d @ta0.req.json $TA_URL$TA_METHOD | tee ta.resp.json | jq .
-->

<!--
	ibmcloud catalog search understanding
	ibmcloud catalog service natural-language-understanding
	ibmcloud resource service-instance-create nlu natural-language-understanding free eu-de	
	ibmcloud resource service-key-create nluKey Manager --instance-name nlu
	export NLU_APIKEY=$(ibmcloud resource service-key nluKey | awk '/^\s*apikey:/ {print $2}') && echo $NLU_APIKEY
	export NLU_URL=$(ibmcloud resource service-key nluKey | awk '/^\s*url:/ {print $2}') && echo $NLU_URL
	export NLU_METHOD=/v1/analyze?version=2018-11-16 && echo $NLU_METHOD
	export NLU_FEATURES='{"sentiment": {}, "keywords": {}, "entities": {}}' && echo "$NLU_FEATURES" | jq .
	export NLU_TEXT="J'aimerai avoir des nouvelles de ma commande passée il y a déjà 15 jours et que je n'ai toujours pas reçu." && echo $NLU_TEXT
	jq -n --argjson features "$NLU_FEATURES" --arg text "$NLU_TEXT" '{"text": $text, "features": $features}' | tee nlu.req.json | jq .
	curl -X POST -u 'apikey:'$NLU_APIKEY -H 'Content-Type: application/json' -d @nlu.req.json $NLU_URL$NLU_METHOD | tee nlu.resp.json | jq .
-->

<!--
	ibmcloud catalog search vision
	ibmcloud catalog service watson-vision-combined
	ibmcloud resource service-instance-create wvc watson-vision-combined lite us-south	
	ibmcloud resource service-key-create wvcKey Manager --instance-name wvc
	export WVC_APIKEY=$(ibmcloud resource service-key wvcKey | awk '/^\s*apikey:/ {print $2}') && echo $WVC_APIKEY
	export WVC_URL=$(ibmcloud resource service-key wvcKey | awk '/^\s*url:/ {print $2}') && echo $WVC_URL
	export WVC_METHOD=/v3/classify?version=2018-03-19 && echo $WVC_METHOD
	export IMG=$(readlink -f image1.jpg) && echo $IMG
	curl -X POST -u 'apikey:'$WVC_APIKEY -H 'Accept-Language: fr' -F 'images_file=@'$IMG $WVC_URL$WVC_METHOD | tee wvc.resp.json | jq .
-->

	
### Setup application

#### Install needed softwares

> :bulb: Ctrl + Click on links below to open them in new tab and keep the tutorial tab opened.

![](res/win.png)

* Download and install [IBM Cloud CLI](https://console.bluemix.net/docs/cli/reference/ibmcloud/download_cli.html)  
* Download and install [curl](https://curl.haxx.se/windows/)
* Download [jq](https://github.com/stedolan/jq/releases/download/jq-1.5/jq-win64.exe), rename it to **jq** and copy it in your %PATH%.
* Download a [JDK](https://www.oracle.com/technetwork/java/javase/downloads/index.html) and install it.
* Download [WAS Liberty Kernel](https://developer.ibm.com/wasdev/downloads/#asset/runtimes-wlp-kernel).

![](res/mac.png)

* Download and install [IBM Cloud CLI](https://console.bluemix.net/docs/cli/reference/ibmcloud/download_cli.html)  
* **curl** should already be installed. If not, get it from [here](https://curl.haxx.se/dlwiz/?type=bin&os=Mac+OS+X&flav=-&ver=-&cpu=i386)
* Download [jq](https://github.com/stedolan/jq/releases/download/jq-1.5/jq-osx-amd64), rename it to **jq**, :warning: set its attribute to executable (e.g. **chmod +x**) and copy it in your $PATH.
* Download a [JDK](https://www.oracle.com/technetwork/java/javase/downloads/index.html) and install it.
* Download [WAS Liberty Kernel](https://developer.ibm.com/wasdev/downloads/#asset/runtimes-wlp-kernel).
<!--
* Download [sponge](https://github.com/bpshparis/CP2019/blob/master/osxtools/sponge) :warning: set its attribute to executable (e.g. **chmod +x**) and copy it in your $PATH.
-->

![](res/tux.png)

* Download and install [IBM Cloud CLI](https://console.bluemix.net/docs/cli/reference/ibmcloud/download_cli.html)  
* Get **curl** from your distribution repository or download and install it from [here](https://curl.haxx.se/dlwiz/?type=bin&os=Linux).
* Get **jq** from your distribution repository or download it from [here](https://github.com/stedolan/jq/releases/download/jq-1.5/jq-linux64), rename it to **jq**, :warning: set its attribute to executable (e.g. **chmod +x**) and copy it in your $PATH.
<!--
* Install **moreutils** package
-->
* Download a [JDK](https://www.oracle.com/technetwork/java/javase/downloads/index.html) and install it.
* Download [WAS Liberty Kernel](https://developer.ibm.com/wasdev/downloads/#asset/runtimes-wlp-kernel).
<br>

#### Check everything is installed properly

![](res/win.png) ![](res/cmd.png) 

![](res/mac.png) ![](res/tux.png) ![](res/term.png) 

Check ibmcloud command is available:

	ibmcloud -v

Check curl command is available:

	curl -V

Check jq command is available:

	jq

Check javac command is available:

	javac

<br>

#### Login to IBM Cloud

:bulb: To avoid being prompt when using ibmcloud command set the following config parameters

	ibmcloud config --check-version false
	ibmcloud config --usage-stats-collect false

Let's connect:
:warning: Substitute ${IC_ID} with your IBM Cloud id

	ibmcloud login -u ${IC_ID} --skip-ssl-validation --no-region

> :no_entry: If **login failed** because of logging in with a federated ID, then browse one of the following url:

> :bulb: Ctrl + Click on links below to open them in new tab and keep the tutorial tab opened.

 * https://login.ng.bluemix.net/UAALoginServerWAR/passcode
 * https://login.eu-gb.bluemix.net/UAALoginServerWAR/passcode
 * https://login.eu-de.bluemix.net/UAALoginServerWAR/passcode
 
> and get a one-time passcode.

> Then login with **--sso**,

:warning: Substitute ${IC_ID} with your IBM Cloud id

	ibmcloud login -u ${IC_ID} --sso --no-region
		
> paste the one-time passcode when prompt

	One Time Code (Get one at https://login.eu-gb.bluemix.net/UAALoginServerWAR/passcode)>
	
> and hit enter.

:bulb: If prompt to select a region, press enter to skip.

:checkered_flag: Now you should be logged to IBM Cloud.

<br>

#### Get application code

Download code

	curl -LO  https://github.com/bpshparis/cp2019/archive/master.zip

and unzip it:

	unzip master.zip

#### Install WAS Liberty Kernel

	unzip wlp-kernel-19.0.0.2.zip
	
Create defaultServer

	wlp/bin/server create
	
Replace **wlp/usr/servers/defaultServer/server.xml** with this section
```
<?xml version="1.0" encoding="UTF-8"?>
<server description="new server">

    <!-- Enable features -->
        <featureManager>
            <feature>servlet-3.0</feature>
        </featureManager>

        <httpEndpoint host="*" httpPort="9080" httpsPort="9443" id="defaultHttpEndpoint"/>
        <application id="app" location="app.war" name="app"/>

</server>
```
then run

	wlp/bin/installUtility install defaultServer

to configure defaultServer.
	
#### Add application to defaultServer

Create **wlp/usr/servers/defaultServer/apps/app.war.xml** with the following content:
```
<?xml version="1.0" encoding="UTF-8"?>
<archive>
    <dir sourceOnDisk="${APP_CODE_PATH}/WebContent" targetInArchive="/"/>
</archive>
```
:warning: Substitute ${APP_CODE_PATH} with the full path where you unzip application code earlier. 

#### Set environment to access Watson service instances in IBM Cloud

Change to code directory

	cd CP2019-master

> Now if you stand in the correct directory, you should be able to list files such as **resourcesAG.sh**. We're going to run **resourcesAG.sh** to get credentials for our 3 service instances from IBM Cloud and store them in a **json formatted system environment variable**.

	./resourcesAG.sh
	
	
```
{
	"b75d0d61-360f-42f3-baa5-953c936110ac": {
	    "credentials": [
	      {
		"id": "2f8a81c0-8eaf-4913-9983-58de02398f2a",
		"name": "Auto-generated service credentials",
		"apikey": "4Pe0RUDXt_1EBsrWk60PxbrxY7u-Zc1dRcbD_8n6jQet",
		"url": "https://gateway.watsonplatform.net/tone-analyzer/api",
		"role": "Manager"
	      }
	    ],
	    "service": "tone-analyzer",
	    "region": "us-south",
	    "instance": "Tone Analyzer-fs"
	  },
	  "771408bb-d479-409d-90cf-762bac34bd47": {
	    "credentials": [
	      {
		"id": "3a194377-2ec8-4a5e-b6a8-a57c551af48b",
		"name": "Auto-generated service credentials",
		"apikey": "210l8T-InTbjpyFG7BUoonNTGjVRAgdFUsyPXPfITNnk",
		"url": "https://gateway.watsonplatform.net/natural-language-understanding/api",
		"role": "Manager"
	      }
	    ],
	    "service": "natural-language-understanding",
	    "region": "us-south",
	    "instance": "Natural Language Understanding-qf"
	  },
	  "40d26948-5fc4-4a59-828e-e44d211cce3e": {
	    "credentials": [
	      {
		"id": "e8357b44-ca94-403f-9e6d-bc0b5b44af26",
		"name": "Auto-generated service credentials",
		"apikey": "M6WC_MW-SRs_sppmIk0yIkiYLwCPF-JwardYBjzpn5Oh",
		"url": "https://gateway.watsonplatform.net/visual-recognition/api",
		"role": "Manager"
	      }
	    ],
	    "service": "watson-vision-combined",
	    "region": "us-south",
	    "instance": "Visual Recognition-gd"
	  }
  }
```

This command should generate a file called **resourcesAG.json** that we're going to store in a environment variable called **VCAP_SERVICES** with the following command:

	export VCAP_SERVICES=$(cat resourcesAG.json)
	
:bulb: Check the variable with:	

	echo $VCAP_SERVICES	
	
It should display something like:

```	
{ "d85669d6-1e12-468c-b64e-2cc429e68142": { "credentials": [ { "id": "582d5c6f......
```	
> :checkered_flag: You should now be ready to run the application.

#### Run application

Come back in your home directory

	cd..

and start WAS Liberty Kernel defaultServer

	wlp/bin/server start defaultServer

![](res/web.png)
Then browse [app](http://localhost:9080/app)

When app is loaded, click on ![](res/envelope.png) to upload sample attached documents in Discovery Collection and get sample mails.

Once mails are displayed, click ![](res/cogwheels.png) to send sample mails for analysis.

When Watson returned, **3 new tabs** (one per service) should appear and are ready to browse. 

![](res/watsontabs.png)

<br>

### Send your own datas for analysis

![](res/notepad.png)

Edit a json file of this form :

```
[
  {
    "subject": "paste some text between double quotation marks or set to null",
    "content": "paste some text between double quotation marks or set to null",
    "attached": "paste a doc|docx|pdf file name between double quotation marks or set to null",
    "picture": "paste a picture file name between double quotation marks or set to null",
    "face": "paste a picture file name between double quotation marks or set to null",
    "tip": "paste a picture file name between double quotation marks or set to null"
  }
]
```

An example for 2 mails with documents and pictures attached :

```
[
  {
      "subject": "At UEFA, Mounting Concern About A.C. Milan’s Murky Finances",
      "content": null,
      "attached": "3.pdf",
      "picture": "pic3.jpg",
      "face": null,
      "tip": null
  },
  {
      "subject": null,
      "content": "At a flea market six years ago, a North Carolina lawyer named Frank Abrams unknowingly bought...",
      "attached": "4.doc",
      "picture": null,
      "face": "face4.jpg",
      "tip": "tip4.jpg"
  }
]
```

Save this file as **mails.json** 

:bulb: Test it with jq

	jq . mails.json

The command should display pretty json without error.

Now **zip mails.json with all files set in attached, picture, face and tip fields from mails.json**.

:bulb: your archive should be this form

```
Archive:  mails0.zip
    testing: 3.doc                    OK
    testing: 3.pdf                    OK
    testing: 4.doc                    OK
    testing: face4.jpg                OK
    testing: mails.json               OK
    testing: pic3.jpg                 OK
    testing: tip4.jpg                 OK
No errors detected in compressed data of mails0.zip.
```
Now go back to your application and click ![](res/compressed.png) to upload your datas.

Once your datas have been upload, click on ![](res/envelope.png) to upload your attached documents in Discovery Collection and get your mails.

Once your mails are displayed, click ![](res/cogwheels.png) to send your mails for analysis.

<br>

### Clean your room

![](res/mac.png) ![](res/tux.png) ![](res/term.png)

	export APP_NAME=app0 && for svc in ta0 nlu0 dsc0 wvc0; do ibmcloud service unbind ${APP_NAME} $svc; ibmcloud service key-delete -f $svc user0; ibmcloud service delete -f $svc; done && ic app delete ${APP_NAME} -f

![](res/win.png) ![](res/web.png)

Browse your [IBM Cloud dashboard](https://console.bluemix.net/dashboard/apps)

Open each services **More Actions** popup menu and choose **Delete Service**

![](res/delsvc.png)

Do the same for application. Open application **More Actions** popup menu and choose **Delete App**

![](res/delapp.png)

<br>

### About Watson Developer Cloud services being used in the application

![](res/ta50x.png) **Tone Analyzer** uses linguistic analysis to detect three types of tones from communications: emotion, social, and language.  This insight can then be used to drive high impact communications.

[Documentation](https://console.bluemix.net/docs/services/tone-analyzer/getting-started.html) 
[Dashboard](https://www.ibm.com/watson/developercloud/dashboard/en/tone-analyzer-dashboard.html) 
[Github](https://github.com/watson-developer-cloud)

![](res/nlu50x.png) **Natural Language Understanding** analyze text to extract meta-data from content such as concepts, entities, emotion, relations, sentiment and more.

[Documentation](https://console.bluemix.net/docs/services/natural-language-understanding/getting-started.html)
[Dashboard](https://www.ibm.com/watson/developercloud/dashboard/en/natural-language-understanding-dashboard.html)
[Github](https://github.com/watson-developer-cloud)

![](res/dsc50x.png) **Discovery** add a cognitive search and content analytics engine to applications.

[Documentation](https://console.bluemix.net/docs/services/discovery/getting-started.html)
[Dashboard](https://www.ibm.com/watson/developercloud/dashboard/en/discovery-dashboard.html)
[Github](https://github.com/watson-developer-cloud)
[Tool](https://watson-discovery.bluemix.net)

![](res/wvc50x.png) **Visual Recognition** find meaning in visual content! Analyze images for scenes, objects, faces, and other content. Choose a default model off the shelf, or create your own custom classifier. Develop smart applications that analyze the visual content of images or video frames to understand what is happening in a scene.

[Documentation](https://console.bluemix.net/docs/services/visual-recognition/getting-started.html)
[Dashboard](https://www.ibm.com/smarterplanet/us/en/ibmwatson/developercloud/dashboard/en/visual-recognition-dashboard.html)
[Github](https://github.com/watson-developer-cloud)
[Tool](https://watson-visual-recognition.ng.bluemix.net/)

<br>

### About other Watson Developer Cloud services

![](res/s2t50x.png) **Speech to Text** Low-latency, streaming transcription.

[Documentation](https://console.bluemix.net/docs/services/speech-to-text/getting-started.html)
[Dashboard](https://www.ibm.com/watson/developercloud/dashboard/en/speech-to-text-dashboard.html)
[Github](https://github.com/watson-developer-cloud)

![](res/t2s50x.png) **Text to Speech** Synthesizes natural-sounding speech from text.

[Documentation](https://console.bluemix.net/docs/services/text-to-speech/getting-started.html)
[Dashboard](https://www.ibm.com/watson/developercloud/dashboard/en/text-to-speech-dashboard.html)
[Github](https://github.com/watson-developer-cloud)

![](res/lt50x.png) **Language Translator** Translate text from one language to another for specific domains.

[Documentation](https://console.bluemix.net/docs/services/language-translator/getting-started.html)
[Dashboard](https://www.ibm.com/watson/developercloud/dashboard/en/language-translator-dashboard.html)
[Github](https://github.com/watson-developer-cloud)

![](res/pi50x.png) **Personality Insights** The Watson Personality Insights derives insights from transactional and social media data to identify psychological traits

[Documentation](https://console.bluemix.net/docs/services/personality-insights/getting-started.html)
[Dashboard](https://www.ibm.com/watson/developercloud/dashboard/en/personality-insights-dashboard.html)
[Github](https://github.com/watson-developer-cloud)

![](res/cvt50x.png) **Conversation** Add a natural language interface to your application to automate interactions with your end users. Common applications include virtual agents and chat bots that can integrate and communicate on any channel or device. 

[Documentation](https://console.bluemix.net/docs/services/conversation/getting-started.html)
[Dashboard](https://www.ibm.com/watson/developercloud/dashboard/en/conversation-dashboard.html)
[Github](https://github.com/watson-developer-cloud)
[Tool](https://watson-conversation.ng.bluemix.net)

![](res/nlc50x.png) **Natural Language Classifier** performs natural language classification on question texts. A user would be able to train their data and the predict the appropriate class for a input question.

[Documentation](https://console.bluemix.net/docs/services/natural-language-classifier/getting-started.html)
[Dashboard](https://www.ibm.com/watson/developercloud/dashboard/en/natural-language-classifier-dashboard.html)
[Github](https://github.com/watson-developer-cloud)
[Tool](https://natural-language-classifier-toolkit.eu-gb.bluemix.net)

### Annexes

#### Run application elsewhere from IBM Cloud

##### Using Resource Group (Manual-generated service credentials)

```
#!/bin/sh

output=resources.json

echo '{}' | tee $output

services=$(ibmcloud service list | awk 'NR>6 {print "{\""$2"\":[{\"credentials\":null,\"name\":\""$1"\"}]}"}')

count=$(ibmcloud resource service-instances | awk -F'   ' 'NR>3 {count++} END {print count}') && echo $count " service-instances found."

instances=$(ibmcloud resource service-instances | awk -F'   ' 'NR>3 {print $1 ";;"}')

for i in $(seq 1 $count)
	do
		instance=$(echo $instances | awk -F ';;' '{print $'$i'  }');
		# service=$(echo $service | tr -d '[:space:]');
		instance=$(echo $instance | sed -e 's/^[[:space:]]*//');
		echo "Getting setting for service-instance "$instance;
		obj=$(ibmcloud resource service-instance "$instance" | awk -F ':' '/^ID:/ {print $6 ":" $7 ":" $9}');
		# echo $obj;

		service=$(echo $obj | cut -d':' -f1);
		region=$(echo $obj | cut -d':' -f2);
		id=$(echo $obj | cut -d':' -f3);

		region=$(ibmcloud resource service-instance "$instance" | awk -F ':' '/^ID:/ {print $7}');
		jq --argjson svc "{\"$id\":{\"credentials\":null,\"service\":\"$service\",\"region\":\"$region\",\"instance\":\"$instance\"}}" '. += $svc' $output | sponge $output;


	done

count=$(ibmcloud resource service-keys | awk -F'   ' 'NR>3 {count++} END {print count}') && echo $count " service-keys found."
keys=$(ibmcloud resource service-keys | awk -F'   ' 'NR>3 {print $1 ";;"}')

for i in $(seq 1 $count)
	do
		keyName=$(echo $keys | awk -F ';;' '{print $'$i'  }');
		# service=$(echo $service | tr -d '[:space:]');
		keyName=$(echo $keyName | sed -e 's/^[[:space:]]*//');
		echo "Getting setting for service-key "$keyName;
		obj=$(ibmcloud resource service-key "$keyName" | awk -F ':' '/^ID:/ {print $9 ":" $11}');
		instanceId=$(echo $obj | cut -d':' -f1);
		keyId=$(echo $obj | cut -d':' -f2);
		apikey=$(ibmcloud resource service-key "$keyName" | awk -F ':' '/^\s*apikey:/ {print $2}');
		apikey=$(echo $apikey | tr -d '[:space:]');
		url=$(ibmcloud resource service-key "$keyName" | awk '/^\s*url:/ {print $2}');
		role=$(ibmcloud resource service-key "$keyName" | awk -F ':' '/^\s*iam_role_crn:/ {print $11}');
		role=$(echo $role | tr -d '[:space:]');

		cred='{"id": "'$keyId'", "name": "'$keyName'", "apikey": "'$apikey'", "url": "'$url'", "role": "'$role'"}';

		jq --argjson cred "$cred" 'if (.["'$instanceId'"]) then .["'$instanceId'"].credentials[.["'$instanceId'"].credentials| length] |= . + $cred else . end' $output | sponge $output;

	done

jq . $output

echo ""
echo "!!!! Resources available in " $(readlink -f $output) " !!!!"

# Sample usage:
# jq -r '.[] | select(.instance=="Visual Recognition-cv" and .credentials[1].role=="Writer") | .credentials[1].apikey + ":" + .credentials[1].role' $output


exit 0;
``` 

##### Using Resource Group (Auto-generated service credentials)

```
#!/bin/bash

input=credentials
output=resourcesAG.json

echo '{}' | tee $output

services=$(ibmcloud service list | awk 'NR>6 {print "{\""$2"\":[{\"credentials\":null,\"name\":\""$1"\"}]}"}')

count=$(ibmcloud resource service-instances | awk -F'   ' 'NR>3 {count++} END {print count}') && echo $count " service-instances found."

instances=$(ibmcloud resource service-instances | awk -F'   ' 'NR>3 {print $1 ";;"}')

for i in $(seq 1 $count)
	do
		instance=$(echo $instances | awk -F ';;' '{print $'$i'  }');
		# service=$(echo $service | tr -d '[:space:]');
		instance=$(echo $instance | sed -e 's/^[[:space:]]*//');
		echo "Getting setting for service-instance "$instance;
		obj=$(ibmcloud resource service-instance "$instance" | awk -F ':' '/^ID:/ {print $6 ":" $7 ":" $9}');
		# echo $obj;

		service=$(echo $obj | cut -d':' -f1);
		region=$(echo $obj | cut -d':' -f2);
		id=$(echo $obj | cut -d':' -f3);

		region=$(ibmcloud resource service-instance "$instance" | awk -F ':' '/^ID:/ {print $7}');
		jq --argjson svc "{\"$id\":{\"credentials\":null,\"service\":\"$service\",\"region\":\"$region\",\"instance\":\"$instance\"}}" '. += $svc' $output | sponge $output;


	done

ibmcloud resource service-key "Auto-generated service credentials" > ./input;

instanceIds=();
keyIds=();
keyNames=();
apikeys=();
urls=();
roles=();

while read LINE
  do
    id=$(echo $LINE | awk -F ':' '/^ID:/ {print $9 ":" $11}');
    instanceId=$(echo $id | cut -d':' -f1);
    if [ ! -z "$instanceId" ]; then
      instanceIds+=($instanceId);
    fi

    keyId=$(echo $id | cut -d':' -f2);
    if [ ! -z "$keyId" ]; then
      keyIds+=($keyId);
    fi

    keyName=$(echo $LINE | awk -F ':' '/^Name:/ {print $2}');
    keyName=$(echo $keyName | sed -e 's/^[[:space:]]*//');
    if [ ! -z "$keyName" ]; then
      keyNames+=("$keyName")
    fi

    apikey=$(echo $LINE | awk -F ':' '/^\s*apikey:/ {print $2}');
		apikey=$(echo $apikey | tr -d '[:space:]');
    if [ ! -z "$apikey" ]; then
      apikeys+=($apikey)
    fi

    url=$(echo $LINE | awk '/^\s*url:/ {print $2}');
    if [ ! -z "$url" ]; then
      urls+=($url)
    fi

    role=$(echo $LINE | awk -F ':' '/^\s*iam_role_crn:/ {print $11}');
    role=$(echo $role | tr -d '[:space:]');
    if [ ! -z "$role" ]; then
      roles+=($role)
    fi

  done < ./input

rm -f ./input;

echo "instanceIds="${instanceIds[@]};
echo "keyNames="${keyNames[@]};
echo "keyIds="${keyIds[@]};
echo "apikeys="${apikeys[@]};
echo "urls="${urls[@]};
echo "roles="${roles[@]};

count=${#apikeys[@]};
echo $count "service-keys found";

for i in $(seq 0 $(($count -1)))
  do
    echo "Getting setting for service-key "${keyNames[$i]};
    cred='{"id": "'${keyIds[$i]}'", "name": "'${keyNames[$i]}'", "apikey": "'${apikeys[$i]}'", "url": "'${urls[$i]}'", "role": "'${roles[$i]}'"}';
    echo $cred;
    jq --argjson cred "$cred" 'if (.["'${instanceIds[$i]}'"]) then .["'${instanceIds[$i]}'"].credentials[.["'${instanceIds[$i]}'"].credentials| length] |= . + $cred else . end' $output | sponge $output;
  done

jq . $output

echo ""
echo "!!!! Resources available in " $(readlink -f $output) " !!!!"

# Sample usage:
# jq -r '.[] | select(.instance=="Visual Recognition-cv" and .credentials[1].role=="Writer") | .credentials[1].apikey + ":" + .credentials[1].role' $output


exit 0;
```

##### Using Cloud Foundry

```
#!/bin/sh

output=vcap.json

echo '{}' | tee $output

services=$(ibmcloud service list | awk 'NR>6 {print "{\""$2"\":[{\"credentials\":null,\"name\":\""$1"\"}]}"}')

for service in $(echo $services)
	do 
		jq --argjson value $service '. += $value' $output | sponge $output; 
	done

for svc in $(ibmcloud service list | awk 'NR>6 {print $1 ":" $2}')
	do
		echo $svc;
		label=$(echo $svc | cut -d':' -f2);
		name=$(echo $svc | cut -d':' -f1);
		echo $label;
		echo $name;
		
		key=$(ibmcloud service key-show $name user0 | awk 'NR>4');
		
		echo $key
		
		jq \
		--arg name $name \
		--arg label $label \
		--argjson clef "$key" \
		'if (.["'$label'"][].name==$name) then .["'$label'"][].credentials = $clef else . end' $output | sponge $output;
	done
	
jq . $output
	
exit 0;
```
		
	
	
