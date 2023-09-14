#Commands
- mvn clean install -> clean project and rebuild it
- mvn migration:new -Dmigration.description=name_of_the_migration
- mvn migration:up
- mvn migration:down -Dmigration.down.steps=Number_of_migrations_to_rollback
- mvn migration:status
- mvn clean verify sonar:sonar -Dsonar.projectKey=PlayQuiz -Dsonar.host.url=http://localhost:9000 -Dsonar.login=<sonar_token>
- mvn jacoco:report (target->site->index.html)

- git rm -r --cached . -> remove git cached files