import groovy.json.* 

def inprogress(jsondata){
def jsonString = jsondata
//def workspace = build.getEnvVars()["jiraCollector"]
//println(jsonString)
def jsonObj = readJSON text: jsonString
println(jsonObj.alm)

String a=jsonObj.emails.email
String eMail=a.replaceAll("\\[", "").replaceAll("\\]","");
  
env.name = eMail
withCredentials([usernamePassword(credentialsId: 'jira_password', passwordVariable: 'password', usernameVariable:'username')])
  {
sh """
     curl -X GET \
    -H -d -u $username:$password \
     'http://ec2-18-191-16-16.us-east-2.compute.amazonaws.com:8080/rest/api/2/search?jql=assignee='${eMail}'%20AND%20(status%3D'\'"In%20Progress"\'')%20order%20by%20duedate&fields=id%2Ckey%2Cpriority' \
  -H 'cache-control: no-cache' -o outputInProgress.json
  """
  }   
def jsonSlurper = new JsonSlurper()
def resultJson = jsonSlurper.parse(new File("/var/lib/jenkins/workspace/${JOB_NAME}/outputInProgress.json"))
def total = resultJson.total
echo "Total no.of issues of user $eMail with statuts in-progress are $total"

}
	
	
/*	
List<String> JSON = new ArrayList<String>();
List<String> JCOPY = new ArrayList<String>();
List<String> JSON1= new ArrayList<String>();
for(i=0;i<ecount;i++)
{	 
  for(j=0;j<total;j++)
  {
 if(jsonObj.config.emails.email[i]==resultJson.values.author[j].emailAddress)
	     {
	JSON.add(resultJson.values[j])
	//println(JSON) 
     
		     
    }
	
      
  
      }
	 
	
	 count=JSON.size()
	 //  println(USER)
         	
 JSON1[i]=JSON.clone()
	   JCOPY.add(["Email":jsonObj.config.emails.email[i],"Individual_commit":JSON1[i],"Commit_count":count])
	
	 
	
	
	 JSON.clear()
	 
	  
}
def jsonBuilder = new groovy.json.JsonBuilder()
jsonBuilder.bitbucket(
  "Total_commits": resultJson.values,
 "Commit_count": resultJson.size(),
 "Individual_commits":JCOPY
)
File file = new File("/var/lib/jenkins/workspace/${JOB_NAME}/commits1.json")
file.write(jsonBuilder.toPrettyString())	
//def result = jsonSlurper.parse(new File("/var/lib/jenkins/workspace/${JOB_NAME}/commits1.json"))
//def commits = result.bitbucket.Commit_count
//println(commits)
}
*/







def done(jsondata){
def jsonString = jsondata
//println(jsonString)
def jsonObj = readJSON text: jsonString
println(jsonObj.alm)

String a=jsonObj.config.emails.email
String eMail=a.replaceAll("\\[", "").replaceAll("\\]","");
  
env.name = eMail

  withCredentials([usernamePassword(credentialsId: 'jira_password', passwordVariable: 'password', usernameVariable:'username')]){
sh """
     curl -X GET \
    -H -d -u $username:$password \
     'http://ec2-18-191-16-16.us-east-2.compute.amazonaws.com:8080/rest/api/2/search?jql=assignee='${eMail}'%20AND%20(status%3DDONE)%20order%20by%20duedate&fields=id%2Ckey%2Cpriority' \
  -H 'cache-control: no-cache' -o outputdone.json
  """
   
  }


def jsonSlurper = new JsonSlurper()
def resultJson = jsonSlurper.parse(new File("/var/lib/jenkins/workspace/${JOB_NAME}/outputdone.json"))
def total = resultJson.total
echo "Total no.of issues of user $eMail with statuts done are $total"
}



def todo(jsondata){
def jsonString = jsondata
//println(jsonString)
def jsonObj = readJSON text: jsonString
println(jsonObj.alm)

String a=jsonObj.config.emails.email
String eMail=a.replaceAll("\\[", "").replaceAll("\\]","");
  
env.name = eMail

  withCredentials([usernamePassword(credentialsId: 'jira_password', passwordVariable: 'password', usernameVariable:'username')]){
sh """
     curl -X GET \
    -H -d -u $username:$password \
     'http://ec2-18-191-16-16.us-east-2.compute.amazonaws.com:8080/rest/api/2/search?jql=assignee='${eMail}'%20AND%20(status%3D'\'"To%20Do"\'')%20order%20by%20duedate&fields=id%2Ckey%2Cpriority' \
  -H 'cache-control: no-cache' -o outputToDo.json
  """
   
  }

def jsonSlurper = new JsonSlurper()
def resultJson = jsonSlurper.parse(new File("/var/lib/jenkins/workspace/${JOB_NAME}/outputToDo.json"))
def total = resultJson.total
echo "Total no.of issues of user $eMail with statuts to-do are $total"	
	
}
