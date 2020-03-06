import groovy.json.*
import groovy.json.JsonOutput
def call(jsondata,jira)
{
def jsonString = jsondata
def jsonObj = readJSON text: jsonString
int ecount = jsonObj.config.emails.email.size()
def team=jsonObj.riglet_info.name
     def jsonString1 = jira
        def jsonObj1 = readJSON text: jsonString1
List<String> JSON = new ArrayList<String>();
  List<String> LIST = new ArrayList<String>();
  List<String> JSON1 = new ArrayList<String>();
  def jsonBuilder = new groovy.json.JsonBuilder()
	

  for(j=0;j<ecount;j++)
   {
	 def email=jsonObj.config.emails.email[j] 
	    def ccnt =jsonObj1.jira.outputDone[j].total
      //checkCheckCheck
       def email1=jsonObj1.jira.outputDone[j].email
        println(ccnt)
        int score=0
        if(email==email1 && ccnt>5)
        {
        score=score+10
         LIST.add(["metric":"No of done issues","score":score])
         }
            JSON1=LIST.clone()
	   
   JSON.add(["email":email,"metrics":JSON1])
    LIST.clear()
    }
	 jsonBuilder(
		 "team":team,
		 "metrics":JSON
  
  )
     
    
  println(jsonBuilder)
}
