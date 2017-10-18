# *Módulo 6 - Wicket*

*Nível 3 - Programa de Estágio - Mirante Tecnologia*

------

## Instruções

1. O exercício consiste em criar um cadastro de automóveis.

2. Utilize o esqueleto do projeto como base para o projeto, mas seja livre para alterar as classes e arquivos existentes ou criar novos. Apenas siga a arquitetura indicada.

3. Configure a integração do *Wicket* com o *Spring Framework*.

4. Construa um cadastro de carros com um formulário e uma tabela.

5. O formulário deve possuir os campos obrigatórios **modelo**, **placa**, **tração**, **categoria** e **fabricante**.

6. O campo **modelo** deve ser um *autocomplete* que mostra os modelos já cadastrados para serem selecionados. Se o usuário digitar um modelo que não existe, deve ser cadastrado um novo modelo ao salvar os dados carro.

7. O campo **fabricante** deve mostrar o nome do fabricante, o país do fabricante (somente leitura) e um botão **pesquisar**.

8. Ao clicar no botão **pesquisar** do fabricante, deve ser aberta uma *modal* com um *textfield* de **filtro** e uma tabela mostrando os fabricantes (nome e país). A tabela deve mostrar om botão **selecionar** para cada fabricante. Ao digitar no *textfield* de **filtro**, a tabela deve ser filtrada pelo nome do fabricante. Se o usuário digitar um fabricante que não existe, deverá ser mostrado um campo **país** e um botão **adicionar**, que irá acionar o fabricante e selecioná-lo.

9. A funcionalidade de seleção e cadastro de fabricante deve ser construída com *Ajax*.

10. A tração deve ser um *radio button*, com os valores **combustão** e **elétrico**.

11. A categoria deve ser um *combobox*, com os valores **particular**, **aluguel** e **oficial**.

12. A tabela de consulta deve mostrar os carros cadastrados com os campos **modelo**, **placa**, **tração**, **categoria** e **fabricante** (nome e país).

13. Acrescente na página de consulta um filtro com os campos **modelo**, **placa**, **tração**, **categoria** e **fabricante** (nome). Esse filtro deverá ser aplicado à tabela de consulta automaticamente, sem clicar em nenhum botão.

14. A **placa** deve ser única, isto é, não devem ser cadastrados carros com placas iguais.

15. A **placa** deve estar no formato **XXX9999** (três letras e quatro números).

16. Não devem ser cadastrados carros com placas com formato inválido.

17. A tabela deve mostrar uma opção de **alterar** e **excluir** para cada carro.

18. Ao clicar em **alterar**, o formulário deve ser carregado com os dados do carro e, ao clicar em salvar no formulário, os dados do carro devem ser atualizados na tabela.

19. Ao clicar em **excluir**, os dados do carro devem ser retirados do cadastro.

20. O formulário deve mostrar o botão **cadastrar** quando for inclusão e **salvar** quando for alteração.

21. Quando não houver carros cadastrados, mostre a mensagem **nenhum carro cadastrado** ao invés da tabela.

22. Mostre mensagens bem definidas e individuais de todos os erros que ocorrerem no cadastro.

23. A página **HomePage** deve ser o template das outras páginas.

24. Não inclua arquivos na pasta **src/main/webapp**.

25. Insira recursos como **css** e **javascript** na página dinamicamente, utilizando o *Wicket*.

26. Para fazer as validações, utilize a especificação *JSR 303* integrada com o *Wicket*.

27. Adicione uma máscara ao campo **placa** para que o usuário só consiga digitar os números e as letras na posição desejada.

28. O programa deve funcionar com a partir das seguintes *URLs*:

    1. http://localhost:8080/

       Home page.

    2. http://localhost:8080/carros

       Consulta de carros cadastrados.

    3. http://localhost:8080/carro

       Cadastro de um novo carro.

    4. http://localhost:8080/carro/1

       Alteração do carro com id 1. O número pode variar na *URL*.

29. O projeto deve utilizar o *Lombok*.

    http://projectlombok.org

