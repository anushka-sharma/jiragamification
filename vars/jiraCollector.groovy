import groovy.json.* 

def inprogress(jsondata){
def jsonString = jsondata
//def workspace = build.getEnvVars()["jiraCollector"]
//println(jsonString)
def jsonObj = readJSON text: jsonString
println(jsonObj.alm)

String a=jsonObj.alm.projects.project.project_name
String projectName=a.replaceAll("\\[", "").replaceAll("\\]","");
  
env.name = projectName

  withCredentials([usernamePassword(credentialsId: 'jira_password', passwordVariable: 'password', usernameVariable:'username')])
{
sh """
     curl -X GET \
    -H -d -u $username:$password \
     'http://ec2-18-191-16-16.us-east-2.compute.amazonaws.com:8080/rest/api/2/search?jql=project%3D${projectName}%20AND%20(status%3D'\'"In%20Progress"\'')%20order%20by%20duedate&fields=id%2Ckey%2Cpriority' \
  -H 'cache-control: no-cache' -o output.json
  """
 }   
def jsonSlurper = new JsonSlurper()
def resultJson = jsonSlurper.parse(new File("/var/lib/jenkins/workspace/${JOB_NAME}/output.json"))
def total = resultJson.total
echo "Total no.of issues in $projectName with statuts in-progress are $total"

}
	/*

def jsonSlurper = new JsonSlurper()
def resultJson = jsonSlurper.parse(new File("/var/lib/jenkins/workspace/${JOB_NAME}/Output.json"))
//def resultJson = jsonSlurper.parse(readFile("/var/lib/jenkins/workspace/${JOB_NAME}/Output.json"))	
def total = resultJson.size
 echo "Total no.of tasks with status "In-Progeress" are ${projectName} $total"
//def commiter=1
List<String> JSON = new ArrayList<String>();
List<String> JCOPY = new ArrayList<String>();

//Map<ImmutableList<String>, List<String>> map = new HashMap<ImmutableList<String>, List<String>>();
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
 } 
  

	 def count=JSON.size()
	 //println(jsonObj.config.emails.email[i])
	 JCOPY[i]=(JsonOutput.toJson(JSON))
	 //map.put(ImmutableList.of(jsonObj.config.emails.email[i],count),JCOPY[i])
	
	
	 JSON.clear()
	 

	  
//}
println(JCOPY)
*/


def done(jsondata){
def jsonString = jsondata
//println(jsonString)
def jsonObj = readJSON text: jsonString
println(jsonObj.alm)

String a=jsonObj.alm.projects.project.project_name
String projectName=a.replaceAll("\\[", "").replaceAll("\\]","");
  
env.name = projectName

  withCredentials([usernamePassword(credentialsId: 'jira_password', passwordVariable: 'password', usernameVariable:'username')]){
sh """
     curl -X GET \
    -H -d -u $username:$password \
     'http://ec2-18-191-16-16.us-east-2.compute.amazonaws.com:8080/rest/api/2/search?jql=project%3D${projectName}%20AND%20(status%3DDONE)%20order%20by%20duedate&fields=id%2Ckey%2Cpriority' \
  -H 'cache-control: no-cache' -o outputdone.json
  """
   
  }


def jsonSlurper = new JsonSlurper()
def resultJson = jsonSlurper.parse(new File("/var/lib/jenkins/workspace/${JOB_NAME}/outputdone.json"))
def total = resultJson.total
echo "Total no.of issues in $projectName with statuts done are $total"
}


def todo(jsondata){
def jsonString = jsondata
//println(jsonString)
def jsonObj = readJSON text: jsonString
println(jsonObj.alm)

String a=jsonObj.alm.projects.project.project_name
String projectName=a.replaceAll("\\[", "").replaceAll("\\]","");
  
env.name = projectName

  withCredentials([usernamePassword(credentialsId: 'jira_password', passwordVariable: 'password', usernameVariable:'username')]){
sh """
     curl -X GET \
    -H -d -u $username:$password \
     'http://ec2-18-191-16-16.us-east-2.compute.amazonaws.com:8080/rest/api/2/search?jql=project%3D${projectName}%20AND%20(status%3D'\'"To%20Do"\'')%20order%20by%20duedate&fields=id%2Ckey%2Cpriority' \
  -H 'cache-control: no-cache' -o outputToDo.json
  """
   
  }

def jsonSlurper = new JsonSlurper()
def resultJson = jsonSlurper.parse(new File("/var/lib/jenkins/workspace/${JOB_NAME}/outputToDo.json"))
def total = resultJson.total
echo "Total no.of issues in $projectName with statuts to-do are $total"	
	
}




*/
//def call(json){
//def jsonString = json
//def jsonObj = readJSON text: jsonString

//String b=jsonObj.alm.projects.project.project_name
//String Key=b.replaceAll("\\[", "").replaceAll("\\]","");
//String c=jsonObj.alm.projects.project.project_typeKey 
//String Key=c.replaceAll("\\[", "").replaceAll("\\]","");
//String d=jsonObj.alm.projects.project.project_typeKey
//String Key=d.replaceAll("\\[", "").replaceAll("\\]","");*/

//println(Key)

//collecting all issues with status Done in a project
 //sh "curl -X GET -i -H  -d  -u rig:digitalrig@123 'http://ec2-18-191-16-16.us-east-2.compute.amazonaws.com:8080/rest/api/2/search?jql=project%3D${Key}%20AND%20(status%3DDONE)'"
//collecting all issues with status To-do in a project
// sh "curl -X GET -i -H  -d  -u rig:digitalrig@123 'http://ec2-18-191-16-16.us-east-2.compute.amazonaws.com:8080/rest/api/2/search?jql=project%3D${Key}%20AND%20(status%3D'\'"To%20Do"\'')'"
//collecting all issues with status In-Progress in a project
 // sh "curl -X GET -i -H  -d  -u rig:digitalrig@123 'http://ec2-18-191-16-16.us-east-2.compute.amazonaws.com:8080/rest/api/2/search?jql=project%3D${Key}%20AND%20(status%3D'\'"In%20Progress"\'')'\
//println(Key)
 //collecting all issues assigned to a user with username which are in done state
//  sh "curl -X GET -i -H  -d  -u rig:digitalrig@123 http://ec2-18-191-16-16.us-east-2.compute.amazonaws.com:8080/rest/api/2/search?jql=assignee='${emailid/username}'%20AND%20(status%3DDONE) "
//collecting all issues assigned to a user with username with status "To-do"
//  sh "curl -X GET -i -H  -d  -u rig:digitalrig@123 http://ec2-18-191-16-16.us-east-2.compute.amazonaws.com:8080/rest/api/2/search?jql=assignee='${emailid/username}'%20AND%20(status%3D"To%20Do") "
//collecting all issues assigned to a user with username with status "in-progress"
//  sh "curl -X GET -i -H  -d  -u rig:digitalrig@123 http://ec2-18-191-16-16.us-east-2.compute.amazonaws.com:8080/rest/api/2/search?jql=assignee='${emailid/username}'%20AND%20(status%3D"In%20Progress") "

//}
