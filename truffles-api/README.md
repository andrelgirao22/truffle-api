# Startup21

## Ambiente de desenvolvimento

	* MySQL 5.7
		Workbench 6.3.9
	* Oracle JDK 1.8.0_66
	* Eclipse >=  Oxygen.2
		UTF-8
			Eclipse -> General -> Workspace -> UTF-8

## Arquitetura

	Negócio -> Modelos
	
	Resources -> Service -> JPA -> integração
	
	Apresentação: ui(Angular 4), api
	Negócio: modelos, dominios
	Integração: consultas em banco de dados.

	Java, Spring Boot, Spring JPA, Rest, Angular 4
	
	Negocio
		Spring IoC
		
	Integração
		Spring JPA
		
	
## Codificação:
	
	- Inglês

## Pacotes:

	*br.com.alg.trufflesserver <camada>
	
## Git
	
	* Criando novas funcionalidades:
		Crie um "branch by feature" e envie pull requests pro master, ou seja, crie um branch específico para cada funcionalidade,
		faça um pull antes de enviar o "pull requests" pro master.
		
		issue
			task
				new feature
				request change
			bug
				blocker
				critical
				major
				minor
				improment
				
## Nomeclatura das tabelas
	
	* tb_entidade
		ex.:
			tb_user
			tb_user_account
			
		
	* atributos
		SIGLA DESCRIÇÃO
		id id                      VARCHAR(40)
		tx Texto                   VARCHAR(N)
		vl Valores                 DECIMAL (21,4)
		qt Quantidades             DECIMAL (13,4)
		pc Percentual/coeficiente  DECIMAL (15,8)
		dt Data                    DATE
		hr Hora                    TIME
		bn Binário                 MEDIUMBLOB
		bl Boolean                 BOOLEAN
		ts Time Stamp              DATETIME
		
## Tratamento de exceptions

	Cria uma classe dentro do pacote br.com.alg.trufflesserver.exceptions que herde de Runtime Exception.
	 	
	Ex.:
		TruffleNotFoundException
		