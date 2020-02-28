//library('shlib')_
pipeline{
agent any 
stages{
  stage('Collect Issue Summary'){
            steps {
                script{
                 jiraCollector.done()
                 jiraCollector.inprogress()
                 jiraCollector.todo()
               
                }
            }
      
        }
      }
 
   }
