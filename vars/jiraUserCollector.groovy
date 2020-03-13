import groovy.json.* 

def inprogress(jsondata){
//defining function and parameters
def jsonString = jsondata
//Storing parameters into an variable
def jsonObj = readJSON text: jsonString
//readJSON - how to read key elements as a list
//text:  differentiate between plain text file and json file upon file read

println(jsonObj.config)
println(jsonObj.config.emails.email.size())
for(i=0;i<jsonObj.config.emails.email.size();i++)
{
	
//int ecount = jsonObj.config.emails.email.size()	
//Groovy - size() Obtains the number of elements in this List.
//println("No of users "+ ecount)	
//println(jsonObj.config)

String a=jsonObj.config.emails.email
String eMail=a.replaceAll("\\[", "").replaceAll("\\]","");
  
env.name = eMail
withCredentials([usernamePassword(credentialsId: 'jira_password', passwordVariable: 'password', usernameVariable:'username')])
  {
sh """
     curl -X GET \
    -H -d -u $username:$password \
     'http://ec2-18-191-16-16.us-east-2.compute.amazonaws.com:8080/rest/api/2/search?jql=assignee='${eMail}'%20AND%20(status%3D'\'"In%20Progress"\'')%20order%20by%20duedate&fields=id%2Ckey%2Cpriority' \
   -H 'cache-control: no-cache' -o outputInProgressUser.json
  """
  }
	
def jsonSlurper = new JsonSlurper()
def resultJson = jsonSlurper.parse(new File("/var/lib/jenkins/workspace/${JOB_NAME}/outputDoneUser.json"))
def total = resultJson.total
echo "Total no.of issues of user $eMail with statuts done are $total"
}	

	
	



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


