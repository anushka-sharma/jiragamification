def call(json){
def jsonString = json
def jsonObj = readJSON text: jsonString

String b=jsonObj.alm.projects.project.project_name
String Key=b.replaceAll("\\[", "").replaceAll("\\]","");
/* String c=jsonObj.alm.projects.project.project_typeKey 
String Key=c.replaceAll("\\[", "").replaceAll("\\]","");
String d=jsonObj.alm.projects.project.project_typeKey
String Key=d.replaceAll("\\[", "").replaceAll("\\]","");*/
println(Key)

//collecting all issues with status Done in a project
 sh '''curl -X GET -i -H  -d  -u rig:digitalrig@123 http://ec2-18-191-16-16.us-east-2.compute.amazonaws.com:8080/rest/api/2/search?jql=project%3D${Key}%20AND%20(status%3DDONE)'''
//collecting all issues with status To-do in a project
  sh '''curl -X GET -i -H  -d  -u rig:digitalrig@123 http://ec2-18-191-16-16.us-east-2.compute.amazonaws.com:8080/rest/api/2/search?jql=project%3D'${Key}'%20AND%20(status%3D"To%20Do")'''
//collecting all issues with status In-Progress in a project
  sh '''curl -X GET -i -H  -d  -u rig:digitalrig@123 http://ec2-18-191-16-16.us-east-2.compute.amazonaws.com:8080/rest/api/2/search?jql=project%3D'${Key}'%20AND%20(status%3D"In%20Progress") '''
//collecting all issues assigned to a user with username which are in done state
//  sh "curl -X GET -i -H  -d  -u rig:digitalrig@123 http://ec2-18-191-16-16.us-east-2.compute.amazonaws.com:8080/rest/api/2/search?jql=assignee='${emailid/username}'%20AND%20(status%3DDONE) "
//collecting all issues assigned to a user with username with status "To-do"
//  sh "curl -X GET -i -H  -d  -u rig:digitalrig@123 http://ec2-18-191-16-16.us-east-2.compute.amazonaws.com:8080/rest/api/2/search?jql=assignee='${emailid/username}'%20AND%20(status%3D"To%20Do") "
//collecting all issues assigned to a user with username with status "in-progress"
//  sh "curl -X GET -i -H  -d  -u rig:digitalrig@123 http://ec2-18-191-16-16.us-east-2.compute.amazonaws.com:8080/rest/api/2/search?jql=assignee='${emailid/username}'%20AND%20(status%3D"In%20Progress") "

}
