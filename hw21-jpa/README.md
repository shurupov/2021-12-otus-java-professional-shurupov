# JPA

## Task
You need to add to [Client](src/main/java/ru/otus/crm/model/Client.java) `OneToOne` related field to the [Address](src/main/java/ru/otus/crm/model/Address.java) entity and `OneToMany` related to the [Phone](src/main/java/ru/otus/crm/model/Phone.java) entity.
Tag classes so if you save or read an object [Client](src/main/java/ru/otus/crm/model/Client.java) inner objects should be saved/read cascaded.

Hibernate should create only three tables: for phones, addresses and clients.
If you save new objects there shouldn't be `updates`. 

Check the log and determine those two requirements are complied.

