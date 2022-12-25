#Modules
- application
- persistence

#Commands
- mvn clean install -> clean and install the target directory
- mvn migration:new -Dmigration.description=name_of_the_migration
- mvn migration:up
- mvn migration:down -Dmigration.down.steps=Number_of_migrations_to_rollback
- mvn migration:status

- git rm -r --cached . -> remove git cached files

- git rm -r --cached . -> remove cached files from cache
