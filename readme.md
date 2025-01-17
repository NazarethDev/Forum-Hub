O projeto Forum-hub foi elaborado em participação do programa Oracle Next Education (ONE) com Alura.

# Sobre a aplicação

O Forum Hub cria o backend de uma API Rest para uma aplicação que torna possível a criação de tópicos com dúvidas sobre conhecimentos relacionados a conteúdo acadêmico. 

Esse conteúdo é classificado de acordo com o curso, o qual é composto pela categoria com o qual é cadastrado, e possui um título e mensagem também o seu próprio título e mensagem, data e hora da criação e eventual atualização do tópico além de status definido para cada tópico (definido por 'respondido' (atributo answered) ou não de acordo com o registro de respostas aos tópicos).

Cada novo tópico criado é associado ao usuário autenticado no sistema na requisição que gera o novo tópico. 

Além disso, cada tópico pode ser respondido por outros usuários. A resposta por sua vez contém a mensagem, o usuário que a formulou e a data em que foi criada, além de eventualmente a data de sua atualização, caso ocorra. 

___

# Funcionalidades

Os endereços para as funcionalidades serão simulados se utilizando como exemplo o pré-fixo `http://localhost:8080/` para simular o endereço de um servidor.

Dentre as funcionalidades podemos indicar:

## Novos registros no banco de dados (POST)

### Cadastro de novos usuários
Para o cadastro de novos usuários no sistema, é necessário que seja informado um nome de usuário, e-mail e uma senha. Será necessário para de efetuar login o nome de usuário e senha do usuário.

A requisição para a API é `http://localhost:8080/user/cadastrar`, e deve conter levar o Json:
```
{
	"nome" : "usuario2",
	"email" : "usuario@gmail.com",
	"senha": "123"
}
```


### Efetuação de login
Como citado anteriormente, para que um usuário cadastrado realize login, ele deve informar o nome de usuário e senha. O endereço para se efetuar o login é `http://localhost:8080/user/login` o Json fornecido deve ser semelhante a este:
```
{
	"nome" : "Nazareth",
	"senha" : "123456"
}
```

A requisição devolve ao cliente um json com um token que deve ser usado em determinadas requisições à API, as quais estarão citadas neste documento.
```
{
	"token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJOYXphcmV0aCIsImlzcyI6IkFQSSBGb3J1bV9odWIiLCJleHAiOjE3MzY5OTI3MTB9.PgkJhnTUDgEReCRT6fKDVuAchwhfAX3X0ZcmGbtf7F4"
}
```

O token gerado deve ser devolvido para a API em determinadas requisições restritas a usuários e devem constar no cabeçalho das requisições para que ela seja aceita.

### Registro de novos tópicos
Novos tópicos com os atributos citados anteriormente devem possuir a requisição `http://localhost:8080/topico`. 

Com isso, um novo tópico é registrado no sistema, e possuirá todas as funcionalidades relativas a ele na API para serem manipulados de acordo com as demais funcionalidades da aplicação.

Quando um novo tópico usa de um curso que não existe na base de dados para que seja associado, o sistema automaticamente registra o nome do curso no tópico como “Nome do curso não consta no sistema”, e o tópico é assim registrado. Isso pode ser alterado posteriormente de acordo com a necessidade. O Json na requisição deve ser:
```
{
	"titulo":"Insomnia e Postman",
	"mensagem":"Qual a diferença entre essas duas aplicações?",
	"curso":"Engenharia de software"
}
```

### Cadastramento de cursos (de acordo com as categorias citadas em 'sobre a aplicação')
É possível registrar cursos no sistema informando apenas o nome do curso e sua categoria. Todas as categorias precisam ser enviadas com letras maiúsculas para que o sistema possa reconhecer elas e então associá-las ao seu respectivo curso. As categorias dos cursos são:
- Bacharelado (BACHARELADO);
- Licenciatura (LICENCIATURA);
- MBA (MBA);
- Pós-graduação (POS_GRADUACAO);
- Tecnólogo (TECNOLOGO);
Profissionalizante (PROFISSIONALIZANTE);
- Mestrado (MESTRADO).

A requisição precisa levar o seguinte body:

```{
	"nome":"Medicina",
	"categoria":"BACHARELADO"
}
```

O endereço para o cadastramento de cursos no sistema é `http://localhost:8080/cursos/cadastrar`.

### Registro de novas respostas a tópicos
Novas respostas para tópicos são feitos através da requisição `http://localhost:8080/answer`. Para que a respostas seja devidamente cadastrada e associada a um tópico, sua requisição precisa levar em um Json que deve conter o texto da resposta (chamado mensagem) e o Id do tópico que está sendo respondido.
```
{
	"topicId":"4",
	"mensagem":"Os dois são ótimos! Mas ao usar o Postman para testas aplicações na sua máquina, você precisará fazer o download dele para executar na sua máquina"
}
```

## Atualizações de registros no banco de dados (PUT)
### Atualização de tópicos 
Os tópicos cadastrados no sistema podem ser atualizados a partir da requisição `http://localhost:8080/topico/IDTOPICO`. Ao fazer isso, o tópico além de possuir uma data de criação, terá uma data e hora de atualização. Caso o tópico seja atualizado mais de uma vez, a data apresentada sempre será a mais recente. O Json provido na requisição deve ser:
```
{
	
	"titulo":"Postman ou Insomnia? ",
	"mensagem":"Para um iniciante, qual seria o melhor? Ou sugerem algum outro?",
	"cursoId":"2"
}
```

### Atualização de respostas
Para que usa resposta seja atualizada, a requisição a ser feita é `http://localhost:8080/answer/IDRESPOSTA`, e o corpo da requisição deve conter apenas a mensagem atualizada. 

### Atualização de cursos
Os atributos de um curso como o nome e a categoria podem ser atualizados a partir da requisição http://localhost:8080/cursos/IDCURSO`. O corpo da requisição precisa ser:
```
{
			"nome": "Técnico em Desenvolvimento de sistemas",
			"categoria": "TECNICO"
}
```

## Disposição da apresentação de dados da API (GET)
### Mostrar todos os tópicos
É possível visualizar todos os tópicos cadastrado no sistema orientado de acordo com sua data de criação a partir da requisição `http://localhost:8080/topico`. A resposta é devolvida da seguinte maneira:
```
{
	"content": [
		{
			"id": 1,
			"titulo": "ADS é bom?",
			"mensagem": "Tenho alguns amigos cursando ADS, e dizer ser bom, mas não é muito específico, apenas ensinam o básico da programação, e para aprender a programar mais afundo, um técnico é melhor. Isso é verdade?",
			"dataCriacao": "2025-01-14T18:57:17.086801",
			"dataAtualicacao": null,
			"answered": false,
			"cursoNome": "Análise e Desenvolvimento de Sistemas",
			"autorNome": "Nazareth"
		},
		{
			"id": 3,
			"titulo": "Graduação",
			"mensagem": "Qual é o período em média para fazer uma graduação em Engenharia de software?",
			"dataCriacao": "2025-01-14T23:25:29.392555",
			"dataAtualicacao": null,
			"answered": false,
			"cursoNome": "Engenharia de software",
			"autorNome": "Nazareth"
		},
		{
			"id": 4,
			"titulo": "Insomnia e Postman",
			"mensagem": "Qual a diferença entre essas duas aplicações?",
			"dataCriacao": "2025-01-15T18:40:35.368695",
			"dataAtualicacao": null,
			"answered": false,
			"cursoNome": "Engenharia de software",
			"autorNome": "Nazareth"
		}
	],
	"pageable": {
		"pageNumber": 0,
		"pageSize": 10,
		"sort": {
			"empty": false,
			"sorted": true,
			"unsorted": false
		},
		"offset": 0,
		"paged": true,
		"unpaged": false
	},
	"last": true,
	"totalElements": 3,
	"totalPages": 1,
	"first": true,
	"size": 10,
	"number": 0,
	"sort": {
		"empty": false,
		"sorted": true,
		"unsorted": false
	},
	"numberOfElements": 3,
	"empty": false
}
```

### Abrir tópico específico
Para se apresentar os dados de apenas um tópico, a requisição é `http://localhost:8080/topico/IDTOPICO`, e a resposta a ela é a seguinte:
```
{
	"id": 4,
	"titulo": "Insomnia e Postman",
	"mensagem": "Qual a diferença entre essas duas aplicações?",
	"dataCriacao": "2025-01-15T18:40:35.368695",
	"answered": true,
	"cursoNome": "Engenharia de software",
	"autorNome": "Nazareth",
	"autorEmail": "nazareth@gmail.com"
}
```

### Mostra tópicos por usuário
A API é capaz de mostrar listas dos tópicos gerados por determinado autor, o qual é descrito pelo Id do usuário de que a requisição deve conter para isso. A requisição deve ser `http://localhost:8080/user/IDUSUARIO/topicos`. O Json fornecido é:
```
{
	"content": [
		{
			"id": 1,
			"titulo": "ADS é bom?",
			"mensagem": "Tenho alguns amigos cursando ADS, e dizer ser bom, mas não é muito específico, apenas ensinam o básico da programação, e para aprender a programar mais afundo, um técnico é melhor. Isso é verdade?",
			"dataCriacao": "2025-01-14T18:57:17.086801",
			"answered": false,
			"cursoNome": "Análise e Desenvolvimento de Sistemas",
			"autorNome": "Nazareth",
			"autorEmail": "nazareth@gmail.com"
		},
		{
			"id": 3,
			"titulo": "Graduação",
			"mensagem": "Qual é o período em média para fazer uma graduação em Engenharia de software?",
			"dataCriacao": "2025-01-14T23:25:29.392555",
			"answered": false,
			"cursoNome": "Engenharia de software",
			"autorNome": "Nazareth",
			"autorEmail": "nazareth@gmail.com"
		},
		{
			"id": 4,
			"titulo": "Insomnia e Postman",
			"mensagem": "Qual a diferença entre essas duas aplicações?",
			"dataCriacao": "2025-01-15T18:40:35.368695",
			"answered": true,
			"cursoNome": "Engenharia de software",
			"autorNome": "Nazareth",
			"autorEmail": "nazareth@gmail.com"
		}
	],
	"pageable": {
		"pageNumber": 0,
		"pageSize": 20,
		"sort": {
			"empty": true,
			"sorted": false,
			"unsorted": true
		},
		"offset": 0,
		"paged": true,
		"unpaged": false
	},
	"totalElements": 3,
	"totalPages": 1,
	"last": true,
	"size": 20,
	"number": 0,
	"sort": {
		"empty": true,
		"sorted": false,
		"unsorted": true
	},
	"numberOfElements": 3,
	"first": true,
	"empty": false
}
```

### Mostrar tópico em específico
Você pode obter o registro de um único tópico informando o Id de um tópico previamente registrado no sistema utilizando a requisição `http://localhost:8080/topico/IDTOPICO`. Isso retornará os registros apenas de determinado tópico.
```
{
	"id": 4,
	"titulo": "Insomnia e Postman",
	"mensagem": "Qual a diferença entre essas duas aplicações?",
	"dataCriacao": "2025-01-15T18:40:35.368695",
	"answered": true,
	"cursoNome": "Engenharia de software",
	"autorNome": "Nazareth",
	"autorEmail": "nazareth@gmail.com"
}
```

### Mostrar respostas de um tópico
As respostas de um determinado tópico podem ser devolvidas a partir da requisição `http://localhost:8080/answer/IDTOPICO`. A resposta é semelhante a essa:
```
{
	"content": [
		{
			"id": 4,
			"mensagem": "Você pode usar o Postman online, já o insomnia não.",
			"dataCriacao": "2025-01-15T18:41:35.26546"
		},
		{
			"id": 5,
			"mensagem": "Você pode usar o Postman online, já o insomnia não.",
			"dataCriacao": "2025-01-15T18:41:35.26546"
		},
		{
			"id": 6,
			"mensagem": "Os dois são ótimos! Mas ao usar o Postman para testas aplicações na sua máquina, você precisará faze ro download dele para executar na sua máquina",
			"dataCriacao": "2025-01-15T18:43:03.099079"
		}
	],
	"pageable": {
		"pageNumber": 0,
		"pageSize": 3,
		"sort": {
			"empty": true,
			"sorted": false,
			"unsorted": true
		},
		"offset": 0,
		"paged": true,
		"unpaged": false
	},
	"last": true,
	"totalElements": 3,
	"totalPages": 1,
	"first": true,
	"size": 3,
	"number": 0,
	"sort": {
		"empty": true,
		"sorted": false,
		"unsorted": true
	},
	"numberOfElements": 3,
	"empty": false
}
```

### Mostrar respostas por usuário
Assim como com os tópicos, ela também é capaz de listar as respostas elaboradas por determinados usuários. 
`http://localhost:8080/user/IDUSUARIO/respostas`. O Json retornado é semelhante a este:
```
{
	"content": [
		{
			"id": 4,
			"mensagem": "Você pode usar o Postman online, já o insomnia não.",
			"dataCriacao": "2025-01-15T18:41:35.26546",
			"dataAtualizacao": null,
			"topicoId": 4
		},
		{
			"id": 5,
			"mensagem": "Você pode usar o Postman online, já o insomnia não.",
			"dataCriacao": "2025-01-15T18:41:35.26546",
			"dataAtualizacao": null,
			"topicoId": 4
		},
		{
			"id": 6,
			"mensagem": "Os dois são ótimos! Mas ao usar o Postman para testas aplicações na sua máquina, você precisará faze ro download dele para executar na sua máquina",
			"dataCriacao": "2025-01-15T18:43:03.099079",
			"dataAtualizacao": null,
			"topicoId": 4
		}
	],
	"pageable": {
		"pageNumber": 0,
		"pageSize": 20,
		"sort": {
			"empty": true,
			"sorted": false,
			"unsorted": true
		},
		"offset": 0,
		"paged": true,
		"unpaged": false
	},
	"totalElements": 3,
	"totalPages": 1,
	"last": true,
	"size": 20,
	"number": 0,
	"sort": {
		"empty": true,
		"sorted": false,
		"unsorted": true
	},
	"numberOfElements": 3,
	"first": true,
	"empty": false
}
```

### Mostrar todos os cursos 
Quando a requisição `http://localhost:8080/cursos/all` é disparada para a API, ela apresentará todos os cursos registrados no banco de dados, seja o estado dele ativo ou inativo. O retorno deve ser:
```[
	{
		"id": 1,
		"nome": "Técnico em Desenvolvimento de sistemas",
		"categoria": "TECNICO",
		"cursoInativo": false
	},
	{
		"id": 2,
		"nome": "Engenharia de software",
		"categoria": "BACHARELADO",
		"cursoInativo": false
	},
	{
		"id": 3,
		"nome": "Engenharia civil",
		"categoria": "BACHARELADO",
		"cursoInativo": false
	},
	{
		"id": 4,
		"nome": "Direito",
		"categoria": "BACHARELADO",
		"cursoInativo": false
	},
	{
		"id": 5,
		"nome": "Tecnico em administração",
		"categoria": "TECNICO",
		"cursoInativo": true
	},
	{
		"id": 6,
		"nome": "Análise e Desenvolvimento de Sistemas",
		"categoria": "BACHARELADO",
		"cursoInativo": false
	}
]
```

### Mostrar cursos ativos
Esta requisição por sua vez, deve retornar apenas os cursos ativos que constam no banco de dados, sendo o endereço e retorno dela `http://localhost:8080/cursos`
```
[
	{
		"id": 1,
		"nome": "Técnico em Desenvolvimento de sistemas",
		"categoria": "TECNICO"
	},
	{
		"id": 2,
		"nome": "Engenharia de software",
		"categoria": "BACHARELADO"
	},
	{
		"id": 3,
		"nome": "Engenharia civil",
		"categoria": "BACHARELADO"
	},
	{
		"id": 4,
		"nome": "Direito",
		"categoria": "BACHARELADO"
	},
	{
		"id": 6,
		"nome": "Análise e Desenvolvimento de Sistemas",
		"categoria": "BACHARELADO"
	}
]
```

## Excluir itens (DELETE)
### Excluir usuário
Para excluir um usuário e dados relacionados a ele do sistema, a requisição a se usar é `http://localhost:8080/user/IDUSUARIO`. Ao fazer isso, não será mais possível que o usuário recupere sua conta.

### Excluir curso
Os cursos podem ser excluídos no sistema através da requisição `http://localhost:8080/cursos/IDCURSO`. Vale ressaltar que esse curso será apenas inativado, uma vez que o sistema utilizou o conceito de soft delete, para que assim tópicos que utilizaram o curso como referência no sistema não sofram os efeitos da exclusão do curso. 

A alteração visível nos tópicos, será a de que os tópicos apresentarão os cursos como ‘deletado’. Isso evita que os tópicos e suas respostas sofram as consequências dessa ação e seguirão registrados no banco de dados.

### Excluir resposta
Uma resposta pode ser excluída a partir da requisição `http://localhost:8080/answer/IDRESPOSTA`. Também não é possível recuperar uma resposta excluída. 

### Excluir tópico
Os tópicos podem ser excluídos se usando a requisição delete para o endereço `http://localhost:8080/topico/IDTOPICO`. Ao fazer isso, não será mais possível desfazer essa ação. 

___

# Segurança 
A aplicação usa para segurança o Spring Boot Security e gera tokens gerados pelo JWT Token.

Por configuração do sistema, os tokens gerados no login tem duração padrão de 03h (três horas), e devem ser levadas no cabeçalho de todas as requisições para a API, com exceção das seguintes:

- Cadastrar usuário, `http://localhost:8080/user/cadastrar`;
- Efetuar login, `http://localhost:8080/user/login`;
- Mostrar cursos ativos, `http://localhost:8080/cursos`;
- Mostrar repostas por usuário `http://localhost:8080/user/IDUSUARIO/respostas` e
- Mostrar tópicos por usuário, `http://localhost:8080/user/IDUSUARIO/topicos`.

___


# Tecnologias usadas

Esse projeto utiliza as seguintes tecnologias:

<table>
        <thead>
            <tr>
                <th>Ferramenta/Framework</th>
                <th>Versão</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>Java</td>
                <td>17.0.12</td>
            </tr>
            <tr>
                <td>Spring Boot</td>
                <td>3.4.1</td>
            </tr>
            <tr>
                <td>Spring Boot Security</td>
                <td>3.4.1</td>
            </tr>
            <tr>
                <td>MySQL (banco de dados)</td>
                <td>8.0.40</td>
            </tr>
            <tr>
                <td>Java JWT</td>
                <td>4.2.1</td>
            </tr>
            <tr>
                <td>Flyway-MySQL</td>
                <td>10.2.1</td>
            </tr>
        </tbody>
    </table>

