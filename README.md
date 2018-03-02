# backend_challenge
O projeto é uma API Rest com comunicação com o MongoDB. O banco é populado por um arquivo CSV que é importado pela aplicação.

## compile
Para executar o projeto, primeiro deve-se configurar uma instância do mongodb local, disponibilizando as portas padrao para o serviço (27017). O projeto possui todas as dependências englobadas no Maven, logo é necessário instalar todas essas dependências especificadas no pom.xml obs: algumas IDEs já fazem essa importação automática, porém pode ser feita manualmente através do comando mvn clean e mvn install

Depois, é necessário executar a classe ChallengeApplication.java, que irá startar o serviço no endereço: 'localhost:8080/cities'

## endpoints
Todos os endpoints da aplicação estao no padrão da classe relacionada a base de dados, ou seja, quando for informar um campo na url que for uma label do csv (ex: ibge_id) é necessário informar o nome do atributo da classe (no exemplo: ibgeId). Pois todas as consultas serão feitas diretamente na base de dados.

Para facilitar, deixarei aqui os nomes dos atributos que eu utilizei:

ibgeId; uf; name; capital; lon; lat; noAccents; alternativeNames; microRegion; mesoRegion;

Para iniciar a importação é necessário dar um POST com o caminho do arquivo .CSV por parâmetro, como mostra o JSON abaixo:
{
  'path':'meu_path/cidades.csv'
}

