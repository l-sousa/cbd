José Lucas Sousa
93019
# Chat System

Funcionalidades extra
* mensagens privadas
* listagem de users
* remoção de ursers
* adicionar informação extra sobre os users


messages/user_name -> contém as mensagens para o sistema do user_name
following/user_name -> contém os users que o user_name segue
info/user_name -> contém as informações do user_name
dm/user1:user2 -> contém as mensagens privadas que user1 enviou a user2


######  isValidInput (String input)
Este método serve de verificação do input do user.
Verifica que o número de argumentos está correto e permite que se prossiga para a execuçao do comando.

######  ShowMenu ()
Este método serve para dar print do menu de comandos.

######  add (String[] args)
Este método adiciona um novo user ao Redis.
Cria uma lsita de mensagens do user -> messages/user_name.
Esta estrutura permite ter divisões no redis para integrar followers, etc...

######  msg (String[] args)
Este método permite enviar mensagens para o sistema.
Aadiciona à lista messages/user_name conforme. 
 
######  follow (String[] args)
Este metodo permite integrar a funcionalidade de follow. 
Cria uma nova nova lista following/user_name e adiciona o novo user que quer seguir.

######  by (String[] args)
Este método permite imprimir todas as mensagens do user para o sistema


######  feed (String[] args)
Este método permite imprimir todas as mensagens de todos os users que o user segue.

######  Execute (String input)
Este método apenas serve para não atulhar o ciclo principal. O input já passou pelo metodo de validação, então temos a certeza que os argumentos vêm introduzidos de forma correta.

######  add_info (String[] args)
Este método permite adicionar informações sobre o user, tais como: idade e uma custom bio.

######  info (String[] args)
Este método permite imprimir as informações do user.

######  list_all (String[] args)
Este método permite listar todos os users do sistema.

######  rm (String[] args)
Este método permite remover um user (e todos os dados associados) do sistema

######  dm (String[] args)
Este método permite que um user1 mande mensagens privadas para um user2. 

######  read_dm (String[] args)
Este método permite que um user1 leia as mensagens privadas que enviou para o user2. 
