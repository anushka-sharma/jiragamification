import groovy.json.* 

def inprogress(jsondata){
//defining function and parameters
def jsonString = jsondata
//Storing parameters into an variable
def jsonObj = readJSON text: jsonString
//readJSON - how to read key elements as a list
//text:  differentiate between plain text file and json file upon file read

	
int ecount = jsonObj.config.emails.email.size()	
//Groovy - size() Obtains the number of elements in this List.
println("No of users "+ ecount)	
//println(jsonObj.config)

String a=jsonObj.config.emails.email[i]
String eMail=a.replaceAll("\\[", "").replaceAll("\\]","");
  
env.name = eMail
withCredentials([usernamePassword(credentialsId: 'jira_password', passwordVariable: 'password', usernameVariable:'username')])
  {
sh """
     curl -X GET \
    -H -d -u $username:$password \
     'http://ec2-18-191-16-16.us-east-2.compute.amazonaws.com:8080/rest/api/2/search?jql=assignee='${eMail[i]}'%20AND%20(status%3D'\'"In%20Progress"\'')%20order%20by%20duedate&fields=id%2Ckey%2Cpriority' \
   -H 'cache-control: no-cache' -o outputInProgressUser.json
  """
  }   

	
	
/*	

//File file = new File(output.json)
//file.write(total)
//def commiter=1
List<String> JSON = new ArrayList<String>();
List<String> JCOPY = new ArrayList<String>();
//List<String> jsonStringa = new ArrayList<String>();

for(i=0;i<ecount;i++)
{	 
  for(j=0;j<total;j++)
  {
 if(jsonObj.config.emails.email[i]==resultJson.values.author[j].emailAddress)
	     {
	JSON.add(resultJson.values[j])
	 }
 }
   count=JSON.size()
    JCOPY.add(["email":jsonObj.config.emails.email[i],"Individual_commit":JsonOutput.toJson(JSON),"Commit_count":count])
	
	 JSON.clear()
	
	  
}
//for(i=0;i<jsonStringa.size();i++)
//  { 
//    int score=0
//if(jsonStringa[i].contains("jsonObj.config.emails.email[i]"))
    {
//def jsonObja = readJSON text: jsonStringa[i]
//int total=jsonObja.bitbucket.Commit_count 
//  if(total>5)
//  {
//    score=score+10
//  }
//  }

 def list = JCOPY.sort()

def jsonBuilder = new groovy.json.JsonBuilder()

jsonBuilder.bitbucket(
  "total_commit": resultJson,
 "commit_count": resultJson.size,
	"individual":JCOPY
)

File file = new File("/var/lib/jenkins/workspace/${JOB_NAME}/commits.json")
file.write(jsonBuilder.toPrettyString())
	//println(list)
//def copyAndReplaceText(source, dest, targetText, replaceText){
   // file.write(jsonBuilder.toPrettyString())
}
	
	

*/


}


def done(jsondata){
def jsonString = jsondata
//println(jsonString)
def jsonObj = readJSON text: jsonString
println(jsonObj.config)
println(jsonObj.config.emails.email.size())
for(i=0;i<jsonObj.config.emails.email.size();i++)
	{
String a=jsonObj.config.emails.email[i]
String eMail=a.replaceAll("\\[", "").replaceAll("\\]","");
  
env.name = eMail

  withCredentials([usernamePassword(credentialsId: 'jira_password', passwordVariable: 'password', usernameVariable:'username')]){
sh """
     curl -X GET \
    -H -d -u $username:$password \
     'http://ec2-18-191-16-16.us-east-2.compute.amazonaws.com:8080/rest/api/2/search?jql=assignee='${eMail[i]}'%20AND%20(status%3DDONE)%20order%20by%20duedate&fields=id%2Ckey%2Cpriority' \
  -H 'cache-control: no-cache' -o outputDoneUser.json
  """
   
  }

	
def jsonSlurper = new JsonSlurper()
def resultJson = jsonSlurper.parse(new File("/var/lib/jenkins/workspace/${JOB_NAME}/outputDoneUser.json"))
def total = resultJson.total
echo "Total no.of issues of user $eMail with statuts done are $total"
}

}

def todo(jsondata){
def jsonString = jsondata
//println(jsonString)
def jsonObj = readJSON text: jsonString
println(jsonObj.config)

String a=jsonObj.config.emails.email
String eMail=a.replaceAll("\\[", "").replaceAll("\\]","");
  
env.name = eMail

  withCredentials([usernamePassword(credentialsId: 'jira_password', passwordVariable: 'password', usernameVariable:'username')]){
sh """
     curl -X GET \
    -H -d -u $username:$password \
     'http://ec2-18-191-16-16.us-east-2.compute.amazonaws.com:8080/rest/api/2/search?jql=assignee='${eMail[i]}'%20AND%20(status%3D'\'"To%20Do"\'')%20order%20by%20duedate&fields=id%2Ckey%2Cpriority' \
  -H 'cache-control: no-cache' -o outputToDoUser.json
  """
   
  }

def jsonSlurper = new JsonSlurper()
def resultJson = jsonSlurper.parse(new File("/var/lib/jenkins/workspace/${JOB_NAME}/outputToDoUser.json"))
def total = resultJson.total
echo "Total no.of issues of user $eMail with statuts to-do are $total"	
	
}


