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
  -H 'cache-control: no-cache' -o outputInProgress.json
  """
  }   
def jsonSlurper = new JsonSlurper()
def resultJson = jsonSlurper.parse(new File("/var/lib/jenkins/workspace/${JOB_NAME}/outputInProgress.json"))
def total = resultJson.total
echo "Total no.of issues in $projectName with statuts in-progress are $total"
}


/*
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
println(jsonObj.config.emails.email[i])
JCOPY[i]=(JsonOutput.toJson(JSON))
map.put(ImmutableList.of(jsonObj.config.emails.email[i],count),JCOPY[i])
	
	
JSON.clear()
}
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

