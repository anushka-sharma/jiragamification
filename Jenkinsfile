//library('shlib')_
pipeline{
agent any 
stages{
  stage('Collect Issue Summary'){
            steps {
                script{
                 jiraCollector.done(json)
                 jiraCollector.inprogress(json)
                 jiraCollector.todo(json)
               
                }
            }
      
        }
      }
 
   }
